package com.sap.icn.hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by I321761 on 2017/5/22.
 * Scheduling Tasks
 */
@Component
public class ScheduledTasks {
    private static Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 2 * 1000L)
    public void reportCurrentTime() {
        logger.info("The time is now {}", dateFormat.format(new Date()));
    }

    @Scheduled(cron = "15 * 8-20 * * *")
    public void runWindowsCalculator() throws IOException {
        Runtime.getRuntime().exec("calc");
    }
}
