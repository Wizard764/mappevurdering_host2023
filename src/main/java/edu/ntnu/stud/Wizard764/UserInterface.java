package edu.ntnu.stud.Wizard764;

import java.time.LocalTime;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Class to handle the user interface as well as high level program flow.
 */
public class UserInterface {
  private TrainDepartureRegistry tdr;
  private java.util.Scanner sc;

  /**
   * Constructor to initialize scanner.
   */
  public UserInterface() {
    sc = new Scanner(System.in);
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

    //Test code for main menu.
    String[] testOpts = {"Select an option:",
                         "Display information board",
                         "Do a flip",
                         "How about a cookie?"};
    int chosen = runOptionBasedMenu(testOpts);
    switch (chosen) {
      case 1 -> printInformationBoard();
      case 2 -> System.out.println("Did a flip.");
      case 3 -> System.out.println("Here's a cookie! :)");
      default -> throw new Error("Error. Default condition executed.");
    }
  }

  /**
   * Prints a visual representation of all departures stored in the registry to the console.
   * Departures are sorted by departure time, not including delay.
   */
  public void printInformationBoard() {
    tdr.sortDeparturesByTime();
    System.out.println(tdr);
  }

  /**
   * Takes numeric input from user using Scanner.
   *
   * @param prompt The prompt to present the user.
   * @param error The error to present user when input is wrong format.
   * @param min Minimum acceptable integer value.
   * @param max Maximum acceptable integer value.
   * @return Returns integer from the user in the interval specified.
   */
  private int inputInt(String prompt, String error, int min, int max) {
    while (true) { //Keep asking until valid input is given
      try {
        System.out.println(prompt);
        int temp = sc.nextInt();
        if (temp >= min && temp <= max) {
          return temp;
        }
        throw new IllegalArgumentException();
      } catch (InputMismatchException e) { //Triggered when input is not integer.
        System.out.println(error);
        sc.next(); //"Clean" scanner if input is wrong.
      } catch (IllegalArgumentException e) { //Triggered when int is not in specified range.
        System.out.println(error);
      }
    }
  }

  /**
   * Presents a menu of simple options to the user and returns the users chosen option as an int.
   *
   * @param content A string array where item 0 is the title of the menu.
   *                Subsequent items are options the user may select.
   * @return Returns the option the user selected as an integer.
   * @throws IllegalArgumentException Throws exception if there are no options i menu.
   *                                  At least one is required.
   */
  private int runOptionBasedMenu(String[] content) throws IllegalArgumentException {
    if (content.length < 2) {
      throw new IllegalArgumentException("At least one option is required");
    }
    String prompt = generateOptionBasedMenu(content);
    String error = "Please enter a valid option between 1 and " + (content.length - 1);
    return inputInt(prompt, error, 1, content.length - 1);
  }

  /**
   * Generates a string containing information presented to the user in an option-based menu.
   *
   * @param content A string array where item 0 is the title of the menu.
   *                Subsequent items are options the user may select.
   * @return Returns formatted string containing menu title and options.
   */
  private String generateOptionBasedMenu(String[] content) {
    StringBuilder out = new StringBuilder(content[0]);
    for (int i = 1; i < content.length; i++) {
      out.append("\n").append(i).append(". ").append(content[i]);
    }
    return out.toString();
  }
}