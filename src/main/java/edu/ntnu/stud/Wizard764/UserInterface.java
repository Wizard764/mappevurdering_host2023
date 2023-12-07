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
  private boolean mainRunningFlag = true;

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

    while (mainRunningFlag) {
      runMainMenu();
    }
  }

  /**
   * Runs the main menu that the user lands on when the program is run.
   */
  private void runMainMenu() {
    String[] testOpts = {"Select an option:",
                         "Display departures",
                         "Add departure",
                         "Set system time",
                         "Toggle comments (CURRENT:TBA)",
                         "Search for departure (by train number or destination)",
                         "Modify departure (comment, delay, track)",
                         "Exit application\nSelect: "};
    int chosen = runOptionBasedMenu(testOpts);
    switch (chosen) {
      case 1 -> printInformationBoard();
      case 2 -> System.out.println("TBA. Adds departure");
      case 3 -> System.out.println("TBA. Sets system time");
      case 4 -> System.out.println("TBA. Toggles comments");
      case 5 -> System.out.println("TBA. Searches for departure");
      case 6 -> System.out.println("TBA. Modifies departure");
      case 7 -> mainRunningFlag = false;
      default -> throw new Error("Error. Default condition executed unexpectedly.");
    }
    if (mainRunningFlag) {
      pressEnterToContinue();
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
   * Pauses the program until the user presses ENTER.
   * TODO: Figure out if this method can be refactored to a prettier form.
   */
  private void pressEnterToContinue() {
    System.out.println("Press ENTER to return to main menu");
    try {
      while (System.in.available() == 0){} //Runs (waits) until there is input from user.
      while (System.in.available() > 0) { //Consumes all input
        System.in.read();
      }
    } catch (Exception e) { /*try-catch needed to utilize System.in.available()*/ }
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
        System.out.print(prompt);
        int temp = sc.nextInt(); //Take Integer from scanner.
        sc.nextLine(); //Consume newline character.
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
   * Takes user inputted track-number (short).
   * TODO: Add max limit for track number as member variable.
   *
   * @param prompt Presented to the user before input is taken.
   * @param error Presented to the user when input is invalid.
   * @return Returns a valid track number.
   */
  private short inputTrack(String prompt, String error) {
    while (true) { //Keep asking until valid input is given
      try {
        System.out.print(prompt);
        String in = sc.nextLine(); //Take user input.
        if (in.isEmpty()) {
          return -1;
        }
        short temp = Short.parseShort(in); //Attempt to parse as short.
        if (temp == -1 || temp >= 1) { //If valid
          return temp;
        }
        throw new IllegalArgumentException();
      } catch (Exception e) {
        System.out.println(error);
      }
    }
  }

  /**
   * Gets unique train number from user.
   *
   * @param prompt Presented to the user before input is taken.
   * @param error Presented to the user when input is empty or duplicate.
   * @return Returns unique train number from user.
   */
  private String inputTrainNumberEnforceUniqueness(String prompt, String error) {
    while (true) {
      try {
        String trainNumber = inputEnforceNotEmpty(prompt, error);
        if (tdr.departureExists(trainNumber)) {
          throw new IllegalArgumentException("Duplicate departure.");
        }
        return trainNumber;
      } catch (IllegalArgumentException e) {
        System.out.println(error);
      }
    }
  }

  /**
   * Gets string input that is not empty.
   *
   * @param prompt Presented to the user before input is taken.
   * @param error Presented to the user when input is empty.
   * @return Returns string from user.
   */
  private String inputEnforceNotEmpty(String prompt, String error) {
    while (true) {
      try {
        System.out.print(prompt);
        String in = sc.nextLine();
        if (in.isEmpty()) {
          throw new InputMismatchException("Cannot be empty.");
        }
        return in;
      } catch (InputMismatchException e) {
        System.out.println(error);
      }
    }
  }

  /**
   * Constructs a LocalTime object from user input. Runs until user enter correctly formatted data.
   *
   * @param prompt String presented to the user before input is taken.
   * @param error String presented to the user when input is in the wrong format.
   * @return LocalTime object from user input.
   */
  private LocalTime inputLocalTime(String prompt, String error) {
    while (true) {
      try {
        System.out.print(prompt);
        return LocalTime.parse(sc.nextLine()); //Take user input.
      } catch (DateTimeParseException e) { //Handle wrong input format.
        System.out.println(error);
      }
    }
  }

  /**
   * Takes delay from the user in the correct format.
   *
   * @param departureTime Departure time of the departure that delay is assigned to.
   *                      This is used to avoid departure times spilling into next day.
   *                      If desirable this can be set to LocalTime.of(0, 0),
   *                      in which case this method gets a LocalTime object from the user
   *                      and returns LocalTime.of(0, 0) if input is empty.
   * @param prompt Presented to the user before input is taken.
   * @param error Presented to the user if input is invalid.
   * @return Returns LocalTime object that together with departureTime
   *         provided does not exceed 23:59.
   */
  private LocalTime inputDelay(LocalTime departureTime, String prompt, String error) {
    LocalTime delay;
    int depTimeMins = departureTime.getHour() * 60 + departureTime.getMinute();
    while (true) {
      try {
        System.out.print(prompt);
        String in = sc.nextLine();
        if (in.isEmpty()) {
          return LocalTime.of(0, 0);
        }
        delay = LocalTime.parse(in);
        int actDepTimeMins = depTimeMins + delay.getHour() * 60 + delay.getMinute();
        if (actDepTimeMins >= 60 * 24) {
          throw new IllegalArgumentException("Delay is too long. Departure is past day.");
        }
        return delay;
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      } catch (DateTimeParseException e) {
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