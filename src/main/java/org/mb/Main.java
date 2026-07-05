package org.mb;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Main main = new Main();
        while (Main.shouldPreventStandby(args)) {
            main.preventStandby();
            Thread.sleep(25000L);
        }
    }

    private static boolean shouldPreventStandby(String[] userParameter) {
        String regex = "^([01]\\d|2[0-3]):[0-5]\\d$";
        if (userParameter == null || userParameter.length == 0) {
            System.out.println("No user parameter provided, prevent standby will run while the system is awake");
            return true;
        }
        if (!userParameter[0].matches(regex)) {
            System.out.println("Invalid user parameter format, prevent standby will run while the system is awake");
            return true;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime time = LocalTime.parse(userParameter[0], formatter);
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.now(), time);
        return dateTime.isAfter(LocalDateTime.now());
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
            System.out.println(e.getMessage());
        }
    }
}

