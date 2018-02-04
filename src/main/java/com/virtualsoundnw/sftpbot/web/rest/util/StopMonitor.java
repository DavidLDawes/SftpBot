package com.virtualsoundnw.sftpbot.web.rest.util;


import com.virtualsoundnw.sftpbot.domain.SftpTestCase;
import com.virtualsoundnw.sftpbot.domain.Sftproot;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class StopMonitor {


    public StopMonitor() {
        System.out.println("Stopping MonitorFiles now.");
        if (MonitorFiles.monitorThread != null) {
            MonitorFiles.monitorThread.interrupt();
        }
    }
}
