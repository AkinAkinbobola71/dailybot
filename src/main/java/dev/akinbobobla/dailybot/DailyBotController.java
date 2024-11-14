package dev.akinbobobla.dailybot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class DailyBotController {

    private static final Logger logger = LoggerFactory.getLogger(DailyBotController.class);

    private final Scheduler scheduler;
    private final ExcelToDatabase excelToDatabase;

    public DailyBotController(Scheduler scheduler, ExcelToDatabase excelToDatabase) {
        this.scheduler = scheduler;
        this.excelToDatabase = excelToDatabase;
    }

    @GetMapping("/")
    public String initiateStandupProcess() {
        logger.info("Starting standup initiation process...");

        try {
            logger.info("Saving Excel data to the database...");
            excelToDatabase.saveToDatabase();
            logger.info("Excel data saved successfully.");

            logger.info("Starting scheduled standup tasks...");
            scheduler.schedule();
            logger.info("Scheduled standup tasks completed successfully.");

            return "Standup process initiated successfully.";
        } catch (Exception e) {
            logger.error("Error occurred during standup initiation process.", e);
            return "Failed to initiate standup process: " + e.getMessage();
        }
    }
}
