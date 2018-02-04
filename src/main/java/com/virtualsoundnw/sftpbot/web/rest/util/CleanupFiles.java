package com.virtualsoundnw.sftpbot.web.rest.util;


import com.coreoz.wisp.Scheduler;
import com.coreoz.wisp.schedule.Schedules;
import com.virtualsoundnw.sftpbot.domain.SftpTestCase;
import com.virtualsoundnw.sftpbot.domain.Sftproot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
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

public class CleanupFiles  {

    static private Sftproot  sftproot;
    static private final List<SftpTestCase> testCases = new LinkedList<>();

    static public void initCleanupFiles() {
        sftproot = new Sftproot("/root/INWARD", "/root/UPDATE", "/root/ERROR");
        System.out.println("Constructor for CleanupFiles(), sftproot default, no test cases.");
    }

    public static void initCleanupFiles(Sftproot inSftproot) {
        sftproot = inSftproot;
        System.out.println("Constructor for CleanupFiles(), sftproot = "
            + sftproot.toString());
    }

    public static void cleanupFiles() {
        System.out.println("Remove files form all three test locations: " + sftproot.toString());
        deleteFilesFromDirectory(sftproot.getIncomingDirectory());
        deleteFilesFromDirectory(sftproot.getOutgoingDirectory());
        deleteFilesFromDirectory(sftproot.getErrorDirectory());
    }

    private static void deleteFilesFromDirectory(String directory) {
        File folder = new File(directory);
        final File[] files = folder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(final File dir,
                                  final String name) {
                // delete them all, so every one matches
                return true;
            }
        });
        for (final File file : files) {
            if (!file.delete()) {
                System.err.println("Can't remove " + file.getAbsolutePath());
            }
        }
    }
}
