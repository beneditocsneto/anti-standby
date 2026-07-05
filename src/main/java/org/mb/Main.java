package org.mb;

import java.awt.*;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static final Clock CLOCK = Clock.systemDefaultZone();

    static void main(String[] args) throws InterruptedException {
        Main main = new Main();
        while (Main.shouldPreventStandby(args)) {
            main.preventStandby();
            Thread.sleep(25000L);
        }
    }

    private static boolean shouldPreventStandby(String[] userParameter) {
        String regex = "^([01]\\d|2[0-3]):[0-5]\\d$";
        if (userParameter == null || userParameter.length == 0) {
            LOGGER.info("No user parameter provided, prevent standby will run while the system is awake");
            return true;
        }
        if (!userParameter[0].matches(regex)) {
            LOGGER.info("Invalid user parameter format, prevent standby will run while the system is awake");
            return true;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime time = LocalTime.parse(userParameter[0], formatter);
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.now(CLOCK), time);
        return dateTime.isAfter(LocalDateTime.now(CLOCK));
    }

    private void preventStandby() {
        try {
            Robot robot = new Robot();
            Point point1 = MouseInfo.getPointerInfo().getLocation();
            Point point2 = point1.x > 0 ? new Point(point1.x - 10, point1.y) : new Point(point1.x + 10, point1.y);
            robot.mouseMove(point2.x, point2.y);
            robot.mouseMove(point1.x, point1.y);
        }
        catch (Exception e) {
            LOGGER.log(Level.WARNING, "Failed to move mouse to prevent standby", e);
        }
    }
}

