package com.virtualsoundnw.sftpbot.web.rest.util;


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
import java.util.List;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

public class MonitorFiles implements Runnable {

    public void run() {
        System.out.println("Test file monitoring thread starting up.");
        Path monitorDir = Paths.get("/Users/David/" + sftproot.getIncomingDirectory());

        for (;true;) {
            try {
                 WatchService watcher = monitorDir.getFileSystem().newWatchService();
                monitorDir.register(watcher, ENTRY_CREATE);

                WatchKey watchKey = watcher.take();

                List<WatchEvent<?>> events = watchKey.pollEvents();
                for (WatchEvent event : events) {
                    if (event.kind() == ENTRY_CREATE) {
                        System.out.println("Created: " + event.context().toString());
                        WatchEvent.Kind<?> kind = event.kind();
                        if (kind == OVERFLOW) {
                            System.out.println("Error: overflow");
                            break;
                        }
                        // The filename is the
                        // context of the event.
                        WatchEvent<Path> ev = (WatchEvent<Path>) event;
                        Path filename = ev.context();
                        for (SftpTestCase testCase : testCases) {
                            if (testCase.getIncomingFileName().equals(filename.toString())) {
                                // Verify that the new
                                //  file is a text file.
                                String outgoingFileName = "/Users/david/" + sftproot.getOutgoingDirectory() + "/" + testCase.getResultFileName();
                                File outgoing = new File(outgoingFileName);
                                BufferedWriter bw = null;
                                FileWriter fw = null;

                                try {
                                    fw = new FileWriter(outgoing);
                                    bw = new BufferedWriter(fw);
                                    bw.write(testCase.getFileContents());
                                    System.out.println("Executed test case for inout file " + testCase.getIncomingFileName());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } finally {
                                    try {
                                        if (bw != null)
                                            bw.close();
                                        if (fw != null)
                                            fw.close();
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    }

                                }
                                break;
                            }
                        }
                    }
                }
                // Reset the key -- this step is critical if you want to
                // receive further watch events.  If the key is no longer valid,
                // the directory is inaccessible so exit the loop.
                boolean valid = watchKey.reset();
                if (!valid) {
                    break;
                }
            } catch (InterruptedException ie) {
                System.out.println("Test interrupted");
            } catch (IOException ioe) {
                System.out.println("Unable to monitor for file creation, test error");
            }
        }
    }

    private final Sftproot  sftproot;
    private final List<SftpTestCase> testCases;

    public MonitorFiles(Sftproot sftproot, List<SftpTestCase> testCases) {
        this.sftproot =sftproot;
        this.testCases = testCases;
    }
}
