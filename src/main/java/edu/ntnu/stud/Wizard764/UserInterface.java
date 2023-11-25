package edu.ntnu.stud.Wizard764;

import java.time.LocalTime;
import java.util.ArrayList;

public class UserInterface {
  private ArrayList<TrainDeparture> trainDepartures;

  UserInterface() {
    trainDepartures = new ArrayList<>();
  }

  public void init() {
    LocalTime[] departureTimes = {LocalTime.of(9, 30), LocalTime.of(10, 30), LocalTime.parse("00:05")};
    String[] lines = {"TestLine", "R60", "F4"};
    String[] trainNumbers = {"1771", "123", "ABC123"};
    String[] destinations = {"TestDestination", "Trondheim", "This is a loooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooong destination name"};
    LocalTime[] delays = {LocalTime.parse("01:08"), LocalTime.of(0, 15)};
    short[] tracks = {4, 1};
    String comment = "Few tickets left";
    trainDepartures.add(new TrainDeparture(departureTimes[0], lines[0], trainNumbers[0], destinations[0], delays[0], tracks[0]));
    trainDepartures.add(new TrainDeparture(departureTimes[1], lines[1], trainNumbers[1], destinations[1]));
    trainDepartures.add(new TrainDeparture(departureTimes[2], lines[2], trainNumbers[2], destinations[2], delays[1], tracks[1], comment));
  }

  public void start() {

  }
}