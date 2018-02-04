package com.virtualsoundnw.sftpbot.web.rest.util;


import com.coreoz.wisp.Scheduler;
import com.coreoz.wisp.schedule.Schedules;
import com.virtualsoundnw.sftpbot.domain.SftpTestCase;
import com.virtualsoundnw.sftpbot.domain.Sftproot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

public class MonitorFiles implements Runnable {

    static Scheduler scheduler = new Scheduler();
    private List<SftpTestCase> delayedCases = new LinkedList<>();
    static protected Thread monitorThread;

    public void run() {
        System.out.println("Test file monitoring thread starting up.");
        monitorThread = Thread.currentThread();
        Path monitorDir = Paths.get(sftproot.getIncomingDirectory());
        for (;true;) {
            System.out.println("Next pass through the forever loop");
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("Got an interrupt, goodbye!");
                monitorThread = null;
                break;
            }
            try {
                System.out.println("Watch for creation");
                WatchService watcher = monitorDir.getFileSystem().newWatchService();
                monitorDir.register(watcher, ENTRY_CREATE);

                System.out.println("Take the watch key");
                WatchKey watchKey = watcher.take();

                System.out.println("pollEvents() for List of WatchEvents");
                List<WatchEvent<?>> events = watchKey.pollEvents();
                for (WatchEvent event : events) {
                    System.out.println("For each event");
                    if (event.kind() == ENTRY_CREATE) {
                        System.out.println("Created: " + event.context().toString());
                        WatchEvent.Kind<?> kind = event.kind();
                        if (kind == OVERFLOW) {
                            System.out.println("Error: overflow, goodbye!");
                            monitorThread = null;
                            break;
                        }
                        // The filename is the
                        // context of the event.
                        WatchEvent<Path> ev = (WatchEvent<Path>) event;
                        Path filename = ev.context();
                        System.out.println("Watcher: Created " + filename.toString()
                            + ", check the test cases for a match");

                        for (SftpTestCase testCase: testCases) {
                            System.out.println("Watcher checks: Checking against "
                                + testCase.getIncomingFileName());
                            if (testCase.getIncomingFileName().equals(filename.toString())) {
                                System.out.println("Got a match - SftpTestCase firing");
                                // check for delay request
                                if (testCase.getDelay() != null
                                    && testCase.getDelay() > 0) {
                                    // delay requested, so schedule it for later
                                    System.out.println("Delay this one for "
                                        + testCase.getDelay());
                                    delayedCases.add(testCase);
                                    scheduler.schedule(
                                        () -> createResponseFileAndRemoveFromList(
                                            delayedCases.get(0).getResultFileName(),
                                            delayedCases.get(0).getErrorFileName(),
                                            delayedCases.get(0).getFileContents()),
                                        Schedules.executeOnce(Schedules
                                            .fixedDelaySchedule(
                                                Duration.ofMillis(testCase.getDelay())))
                                    );
                                } else {
                                    // No delay do it immediately
                                    System.out.println("Immediate file drop "
                                    + sftproot.getOutgoingDirectory() + ", "
                                    + testCase.getResultFileName() + "\n\n"
                                    + "Contents " + testCase.getFileContents());
                                    createResponseFile(
                                        testCase.getResultFileName(),
                                        testCase.getErrorFileName(),
                                        testCase.getFileContents());
                                }
                            }
                        }
                    }
                }
                // Reset the key -- this step is critical if you want to
                // receive further watch events.  If the key is no longer valid,
                // the directory is inaccessible so exit the loop.
                boolean valid = watchKey.reset();
                if (!valid) {
                    System.out.println("watchKey reset invalid, goodbye!");
                    monitorThread = null;
                    break;
                }
            } catch (InterruptedException ie) {
                System.out.println("Test interrupted, goodbye!");
                monitorThread = null;
                break;
            } catch (IOException ioe) {
                System.out.println("Unable to monitor for file creation, test error. Goodbye!");
                monitorThread = null;
                break;
            }
        }
        monitorThread = null;
    }

    private final Sftproot  sftproot;
    private final List<SftpTestCase> testCases;

    public MonitorFiles(Sftproot sftproot, List<SftpTestCase> testCases) {
        this.sftproot =sftproot;
        this.testCases = testCases;
        System.out.println("Constructor for MnitorFiles(), sftproot = "
            + sftproot.toString()
            + ", got " + testCases.size() + " test cases.");
    }

    void createResponseFileAndRemoveFromList(String updateFileName, String errFileName, String content) {
        createResponseFile(updateFileName, errFileName, content);
        delayedCases.remove(0);
    }

    void createResponseFile(String updateFileName, String errFileName, String content) {
        String outgoingFileName;
        if (updateFileName != null && updateFileName.length() > 0) {
            outgoingFileName = sftproot.getOutgoingDirectory() + "/" + updateFileName;
        } else {
            outgoingFileName = sftproot.getErrorDirectory() + "/" + errFileName;
        }
        File outgoing = new File(outgoingFileName);
        BufferedWriter bw = null;
        FileWriter fw = null;

        System.out.println("Create response file");
        try {
            fw = new FileWriter(outgoing);
            bw = new BufferedWriter(fw);
            bw.write(content);
            if (updateFileName != null && updateFileName.length() > 0) {
                System.out.println("Executed test case, creating UPDATE file  " + updateFileName);
            } else {
                System.out.println("Executed test case, creating ERROR file  " + errFileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("Close the file and buffered writer out");
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException ex) {
                System.out.println("IOException trying to close bw or fw");
                ex.printStackTrace();
            }

        }
    }
}
