package edu.ntnu.stud.Wizard764;

import java.time.LocalTime;

/**
 * Class to handle the user interface as well as high level program flow.
 */
public class UserInterface {
  private TrainDepartureRegistry tdr;

  UserInterface() {

  }

  public void init() {
    tdr = new TrainDepartureRegistry();
  }

  /**
   * "Main program method"
   * Runs the user interface and manages the train departure registry based on user input.
   */
  public void start() {
    LocalTime[] departureTimes = {LocalTime.of(9, 30), LocalTime.of(10, 30), LocalTime.of(0, 5)};
    String[] lines = {"TestLine", "R60", "F4"};
    String[] trainNumbers = {"1771", "123", "ABC123"};
    String[] destinations = {"TestDestination", "Trondheim",
        "This is a loooooooooooooooooooooooooooooooooooooooooooooooooooooooong destination name"
    };
    LocalTime[] delays = {LocalTime.of(1, 8), LocalTime.of(0, 15), LocalTime.of(0, 0)};
    short[] tracks = {4, 1, -1};
    for (int i = 0; i < departureTimes.length; i++) {
      tdr.addDeparture(new TrainDeparture(departureTimes[i], lines[i],
              trainNumbers[i], destinations[i], delays[i], tracks[i]));
    }
    printInformationBoard();
  }

  /**
   * Prints a visual representation of all departures stored in the registry to the console.
   * Departures are sorted by departure time, not including delay.
   */
  public void printInformationBoard() {
    tdr.sortDeparturesByTime();
    System.out.println(tdr);
  }
}