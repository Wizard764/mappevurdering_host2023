package edu.ntnu.stud.Wizard764;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;

public class TrainDepartureTest
{
    @Test
    public void testValidTrainDeparture() {
        // Valid TrainDeparture construction
        LocalTime departureTime = LocalTime.of(10, 30);
        String line = "TestLine";
        String trainNumber = "123";
        String destination = "TestDestination";
        LocalTime delay = LocalTime.of(0, 15);
        short track = 1;
        String comment = "TestComment";

        new TrainDeparture(departureTime, line, trainNumber, destination, delay, track, comment);
    }

    @Test
    public void testInvalidDelayPastCurrentDay() {
        try {
            // Attempt to create TrainDeparture with delay past the current day
            LocalTime departureTime = LocalTime.of(23, 0);
            String line = "TestLine";
            String trainNumber = "123";
            String destination = "TestDestination";
            LocalTime delay = LocalTime.of(2, 0);
            short track = 1;
            String comment = "TestComment";

            new TrainDeparture(departureTime, line, trainNumber, destination, delay, track, comment);
        } catch (IllegalArgumentException e) {
            System.out.println("Exception Message: " + e.getMessage());
        }
    }

    @Test
    public void testInvalidNegativeTrack() {
        try {
            // Attempt to create TrainDeparture with negative track
            LocalTime departureTime = LocalTime.of(12, 0);
            String line = "TestLine";
            String trainNumber = "123";
            String destination = "TestDestination";
            LocalTime delay = LocalTime.of(0, 15);
            short track = -2; // Change to -2
            String comment = "TestComment";

            new TrainDeparture(departureTime, line, trainNumber, destination, delay, track, comment);
        } catch (IllegalArgumentException e) {
            System.out.println("Exception Message: " + e.getMessage());
        }
    }

    @Test
    public void testConstructorWithoutDelay() {
        // Test constructor without a specified delay
        LocalTime departureTime = LocalTime.of(10, 30);
        String line = "TestLine";
        String trainNumber = "123";
        String destination = "TestDestination";
        short track = -1;
        String comment = "TestComment";

        new TrainDeparture(departureTime, line, trainNumber, destination, track, comment);
    }

    @Test
    public void testConstructorWithoutTrack() {
        // Test constructor without a specified track
        LocalTime departureTime = LocalTime.of(10, 30);
        String line = "TestLine";
        String trainNumber = "123";
        String destination = "TestDestination";
        LocalTime delay = LocalTime.of(0, 15);
        String comment = "TestComment";

        new TrainDeparture(departureTime, line, trainNumber, destination, delay, comment);
    }
    @Test
    public void testConstructorWithoutDelayAndTrack() {
        // Test constructor without a specified delay and track
        LocalTime departureTime = LocalTime.of(10, 30);
        String line = "TestLine";
        String trainNumber = "123";
        String destination = "TestDestination";
        String comment = "TestComment";

        new TrainDeparture(departureTime, line, trainNumber, destination, comment);
    }

    @Test
    public void testConstructorWithoutTrackAndComment() {
        // Test constructor without a specified track and comment
        LocalTime departureTime = LocalTime.of(10, 30);
        String line = "TestLine";
        String trainNumber = "123";
        String destination = "TestDestination";
        LocalTime delay = LocalTime.of(0, 15);

        new TrainDeparture(departureTime, line, trainNumber, destination, delay);
    }

    @Test
    public void testConstructorWithoutDelayAndComment() {
        // Test constructor without a specified delay and comment
        LocalTime departureTime = LocalTime.of(10, 30);
        String line = "TestLine";
        String trainNumber = "123";
        String destination = "TestDestination";
        short track = -1;

        new TrainDeparture(departureTime, line, trainNumber, destination, track);
    }

    @Test
    public void testConstructorWithoutDelayTrackAndComment() {
        // Test constructor without a specified delay, track, and comment
        LocalTime departureTime = LocalTime.of(10, 30);
        String line = "TestLine";
        String trainNumber = "123";
        String destination = "TestDestination";

        new TrainDeparture(departureTime, line, trainNumber, destination);
    }
    @Test
    public void testInvalidDepartureTimeFormatting() {
        try {
            // Attempt to create TrainDeparture with invalid departure time formatting
            String invalidDepartureTimeString = "12asd"; // Invalid format with seconds
            LocalTime invalidDepartureTime = LocalTime.parse(invalidDepartureTimeString);

            String line = "TestLine";
            String trainNumber = "123";
            String destination = "TestDestination";
            LocalTime delay = LocalTime.of(0, 15);
            short track = 1;
            String comment = "TestComment";

            new TrainDeparture(invalidDepartureTime, line, trainNumber, destination, delay, track, comment);
        } catch (Exception e) {
            System.out.println("Exception Message: " + e.getMessage());
        }
    }

    @Test
    public void testValidDepartureTimeFormatting() {
        // Test constructor with valid departure time formatting
        String validDepartureTimeString = "19:23";
        LocalTime validDepartureTime = LocalTime.parse(validDepartureTimeString);

        String line = "TestLine";
        String trainNumber = "123";
        String destination = "TestDestination";
        LocalTime delay = LocalTime.of(0, 15);
        short track = 1;
        String comment = "TestComment";

        new TrainDeparture(validDepartureTime, line, trainNumber, destination, delay, track, comment);
    }

    @Test
    public void testGetters()
    {
        LocalTime departureTime = LocalTime.of(9, 30);
        String line = "TestLine";
        String trainNumber = "1771";
        String destination = "This is a loooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooong destination name";
        LocalTime delay = LocalTime.parse("01:08");
        short track = 4;
        String comment = "Few tickets left";
        TrainDeparture a = new TrainDeparture(departureTime, line, trainNumber, destination, delay, track, comment);

        System.out.println("Line: " + a.getLine());
        System.out.println("Comment: " + a.getComment());
        System.out.println("Train number: " + a.getTrainNumber());
        System.out.println("Track: " + a.getTrack());
        System.out.println("Destination: " + a.getDestination());
        System.out.println("Departure time: " + a.getDepartureTime().toString());
        System.out.println("Departure time formatted: " + a.getDepartureTimeStr());
        System.out.println("Departure time in minutes: " + a.getDepartureTimeInMinutes());
        System.out.println("Delay time: " + a.getDelay().toString());
        System.out.println("Delay time formatted: " + a.getDelayStr());
        System.out.println("Delay time in minutes: " + a.getDelayInMinutes());
        System.out.println("Actual departure time: " + a.getDepartureTimeIncDelay().toString());
        System.out.println("Actual departure time formatted: " + a.getDepartureTimeIncDelayStr());
        System.out.println("Actual departure time in minutes: " + a.getDepartureTimeIncDelayInMinutes());
        System.out.println(a.toString());
    }

    @Test
    public void testSetters()
    {
        LocalTime departureTime = LocalTime.of(19, 0);
        String line = "TestLine";
        String trainNumber = "1771";
        String destination = "This is a loooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooong destination name";
        LocalTime delay = LocalTime.parse("01:08");
        short track = 4;
        String comment = "Few tickets left";
        TrainDeparture a = new TrainDeparture(departureTime, line, trainNumber, destination, delay, track, comment);

        a.setTrack((short)8);
        a.setDelay(LocalTime.of(0, 0));
        a.setComment("");

        System.out.println(a.toString());
    }
}