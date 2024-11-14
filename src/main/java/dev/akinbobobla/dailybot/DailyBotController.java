package dev.akinbobobla.dailybot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DailyBotController {

    private static final Logger logger = LoggerFactory.getLogger(DailyBotController.class);

    private final Scheduler scheduler;

    public DailyBotController(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @GetMapping("/")
    public ResponseEntity<String> initiateStandupProcess() {
        try {
            logger.info("Starting scheduled standup tasks...");
            scheduler.schedule();
            logger.info("Scheduled standup tasks completed successfully.");

            return ResponseEntity.ok("Standup process initiated successfully.");
        } catch (Exception e) {
            logger.error("Error occurred during standup initiation process.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error triggering standup");
        }
    }
}
