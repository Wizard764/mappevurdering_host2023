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
}