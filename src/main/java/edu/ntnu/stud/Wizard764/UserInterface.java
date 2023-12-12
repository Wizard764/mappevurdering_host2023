package edu.ntnu.stud.Wizard764;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Class to handle the user interface as well as high level program flow.
 */
public class UserInterface {
  private TrainDepartureRegistry tdr;
  private java.util.Scanner sc;
  private LocalTime systemTime;
  private short noTracks;
  private int maxNoDepartures;
  private boolean mainRunningFlag;
  private boolean commentState;

  /**
   * Constructor.
   */
  public UserInterface() {

  }

  /**
   * Initialization method.
   * Initialized member variables. Runs once upon program execution.
   */
  public void init() {
    sc = new Scanner(System.in);
    tdr = new TrainDepartureRegistry();
    systemTime = LocalTime.of(0, 0);
    noTracks = 100;
    maxNoDepartures = 2000;
    mainRunningFlag = true;
    commentState = true;
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
    System.out.println("\n\n\n#####   TRAIN DISPATCH SYSTEM   #####");
    while (mainRunningFlag) {
      runMainMenu();
    }
  }

  /**
   * Runs the main menu that the user lands on when the program is run.
   */
  private void runMainMenu() {
    String[] testOpts = {"Main menu:",
                         "Display departures",
                         "Add departure",
                         "Search for departure (by train number or destination)",
                         "Modify departure (comment, delay, track)",
                         "Settings (max values, limits and comments)",
                         "Exit application\nSelect: "};
    int chosen = runOptionBasedMenu(testOpts);
    switch (chosen) {
      case 1 -> printInformationBoard();
      case 2 -> addDeparture();
      case 3 -> searchForDeparture();
      case 4 -> modifyDeparture();
      case 5 -> runSettingsMenu();
      case 6 -> mainRunningFlag = false;
      default -> throw new Error("Error. Default condition executed unexpectedly.");
    }
    if (mainRunningFlag) {
      pressEnterToContinue("Press ENTER to return to main menu");
    }
  }

  /**
   * Generates user-presentable string for comment state.
   *
   * @return "ENABLED" or "DISABLED".
   */
  private String getCommentStateStr() {
    if (commentState) {
      return "ENABLED";
    } else {
      return "DISABLED";
    }
  }

  /**
   * Wrapper for recursive method addDeparture(int).
   * Called to add first departure in a "set".
   */
  private void addDeparture() {
    addDeparture(0);
  }

  /**
   * Adds a departure to the registry with user input.
   * Supports a recursive structure for adding several departures.
   *
   * @param noDepsAdded Number of departures already added. Used for recursion.
   */
  private void addDeparture(int noDepsAdded) {
    if (tdr.getNoDepartures() >= maxNoDepartures) { //If max no departures reached.
      System.out.println("Maximum number of departures reached.");
      System.out.println("To add more, either delete some or increase the limit.");
      System.out.println("(This option is found under 'Settings -> Max no. departures')");
      return;
    }
    //Take departure time.
    String prompt = "Enter departure time(Format: 'HH:MM'): ";
    String error = "You must enter departure time in the following format: "
                 + "'12:34' (without quotation marks))";
    final LocalTime departureTime = inputLocalTime(prompt, error);
    //Take train line.
    prompt = "Enter the train line: ";
    error = "Departure must have a line.";
    final String line = inputEnforceNotEmpty(prompt, error);
    //Take train number.
    prompt = "Enter the train number: ";
    error = "Departure must have a unique train number.";
    final String trainNumber = inputTrainNumberEnforceUniqueness(prompt, error);
    //Take destination.
    prompt = "Enter the destination: ";
    error = "Departure must have a destination.";
    final String destination = inputEnforceNotEmpty(prompt, error);
    //Take delay.
    prompt = "Enter delay(can be empty): ";
    error = "You must enter a delay in the following format: "
            + "'12:34' (without quotation marks))";
    final LocalTime delay = inputDelay(departureTime, prompt, error, true);
    //Take track.
    prompt = "Enter track(can be empty): ";
    error = "You must enter a positive track number no higher than " + noTracks + ".";
    final short track = inputTrack(prompt, error);
    //Take comment.
    System.out.println("Enter comment(if applicable/can be empty): ");
    String comment = sc.nextLine();
    //Construct departure.
    TrainDeparture newDeparture = new TrainDeparture(departureTime, line, trainNumber,
                                                     destination, delay, track, comment);
    //Confirm information is correct with user.
    System.out.println("Confirm the information below is correct: ");
    System.out.println(newDeparture);
    if (inputBinaryDecision()) { //If information is correct
      tdr.addDeparture(newDeparture); //Add departure to registry
      noDepsAdded++; //Increment number of departures added.
      System.out.println("SUCCESS: new departure added.");
      System.out.println("Would you like to add another departure?"
              + " If no, you will be returned to the main menu.");
      if (inputBinaryDecision()) { //If user wants to add another departure.
        addDeparture(noDepsAdded); //Recursive method call.
      } else { //Is user is finished adding departures.
        System.out.println("Successfully added " + noDepsAdded + " departure(s).");
      }
    } else { //If information entered is incorrect according to user.
      System.out.println("Would you like to re-enter the information for the departure?"
                       + " If no, you will be returned to the main menu.");
      if (inputBinaryDecision()) { //If user wants to re-enter information.
        addDeparture(noDepsAdded); //Recursive method call.
      } else { //If user does not want to re-enter information.
        System.out.println("Operation cancelled. No departure was added.");
        System.out.println("Successfully added " + noDepsAdded + " departure(s).");
      }
    }
  }

  /**
   * Takes user input to update system time.
   * New time must be after current time.
   * Also deletes (now) past departures.
   */
  private void setSystemTime() {
    while (true) {
      try {
        System.out.println("Current time: " + systemTime);
        String prompt = "Enter new system time on the format 'HH:MM': ";
        String error = "System time must be on the format '12:34' (without quotation marks)";
        LocalTime in = inputLocalTime(prompt, error);
        if ((in.getHour() * 60 + in.getMinute())
                < (systemTime.getHour() * 60 + systemTime.getMinute())) {
          throw new IllegalArgumentException("New time must be after current time.");
        }
        String warning = "WARNING: Updating system time will automatically delete old departures!";
        System.out.println(warning);
        System.out.println("Confirm new system time: " + in);
        if (inputBinaryDecision()) {
          systemTime = in;
          System.out.println("Successfully changed system time to: " + systemTime);
          int noDepsDeleted = tdr.getNoDepartures();
          tdr.deleteOldDepartures(systemTime);
          noDepsDeleted -= tdr.getNoDepartures();
          System.out.println(noDepsDeleted + " departure(s) were deleted.");
          return;
        }
        System.out.println("Operation cancelled. System time remains unchanged.");
        return;
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  /**
   * Allows user to search for one or more departure,
   * either by train number or destination.
   * Runs recursively until user is finished searching.
   */
  private void searchForDeparture() {
    String[] opts = {"Search for departure(s) by:",
                     "Train number",
                     "Destination\nSelect: "};
    int chosen = runOptionBasedMenu(opts);
    switch (chosen) {
      case 1 -> searchDepartureByTrainNumber();
      case 2 -> searchDeparturesByDestination();
      default -> throw new Error("Error. Default condition executed unexpectedly.");
    }
    System.out.println("Would you like to search for more departures?");
    if (inputBinaryDecision()) {
      searchForDeparture();
    }
  }

  /**
   * Searches for a departure by user input.
   * Presents departure to user.
   */
  private void searchDepartureByTrainNumber() {
    try {
      String trainNum = inputEnforceNotEmpty("Please enter train number: ",
                                             "Train number may not be blank.");
      System.out.println("Found departure:\n" + tdr.getDeparture(trainNum));
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Searches for all departures that match destination given by user.
   */
  private void searchDeparturesByDestination() {
    String prompt = "Enter destination to search for: ";
    String error = "Destination cannot be blank.";
    TrainDeparture[] tds = tdr.getDeparturesByDestination(inputEnforceNotEmpty(prompt, error));
    System.out.println("Found " + tds.length + " departure(s) with matching destination.");
    Arrays.stream(tds).forEach(System.out::println);
  }

  /**
   * Toggles commentState and prints receipt in console.
   */
  private void toggleComments() {
    commentState = !commentState;
    tdr.setCommentState(commentState);
    System.out.println("Comment state toggled.");
    System.out.println("Current state: " + getCommentStateStr());
  }

  /**
   * Allows user to modify modifiable values of departure.
   * Departure is chosen by user input.
   * Calls itself recursively if user selects to modify several departures.
   * Should not throw exceptions, as train number is ensured to be valid.
   */
  private void modifyDeparture() {
    String trainNumber = "";
    while (!tdr.departureExists(trainNumber)) {
      String prompt = "Enter train number of the departure you wish to modify: ";
      String error = "Train number cannot be blank";
      trainNumber = inputEnforceNotEmpty(prompt, error);
      if (!tdr.departureExists(trainNumber)) {
        System.out.println("Departure does not exist. Would you like to try another train number?");
        System.out.println("If no, you will be returned to the main menu.");
        if (!inputBinaryDecision()) {
          trainNumber = "";
          break;
        }
      }
    }
    if (trainNumber.isEmpty()) {
      return;
    }
    modifyDeparture(trainNumber);
    System.out.println("Would you like to modify another departure?");
    if (inputBinaryDecision()) {
      modifyDeparture();
    }
  }

  /**
   * Allows user to modify modifiable values of specified departure.
   * Calls itself recursively if user selects to modify departure several times.
   *
   * @param trainNumber Train number of the departure to be modified.
   * @throws IllegalArgumentException Throws exception if departure doesn't exist.
   */
  private void modifyDeparture(String trainNumber) throws IllegalArgumentException {
    System.out.println("Selected departure:");
    System.out.println(tdr.getDeparture(trainNumber));
    String[] opts = {"Select value to modify: ",
                     "Comment",
                     "Delay",
                     "Track\nSelect: "};
    int choice = runOptionBasedMenu(opts);
    boolean chain = true; //Used to determine whether to chain modifications of the same departure.
    switch (choice) {
      case 1 -> modifyComment(trainNumber);
      case 2 -> chain = modifyDelay(trainNumber);
      case 3 -> modifyTrack(trainNumber);
      default -> throw new Error("Error. Default condition executed unexpectedly.");
    }
    if (chain) {
      System.out.println("Would you like to further modify this departure?");
      if (inputBinaryDecision()) {
        modifyDeparture(trainNumber);
      }
    }
  }

  /**
   * Modifies the comment of a specific departure with user input.
   *
   * @param trainNumber trainNumber of the departure to be modified.
   * @throws IllegalArgumentException Throws exception is departure doesn't exist.
   */
  private void modifyComment(String trainNumber) throws IllegalArgumentException {
    String initialComment = tdr.getDeparture(trainNumber).getComment();
    if (initialComment.isEmpty()) {
      System.out.println("Current comment is empty.");
    } else {
      System.out.println("Current comment: " + initialComment);
    }
    System.out.println("Enter new comment(can be empty):");
    tdr.setComment(trainNumber, sc.nextLine());
  }

  /**
   * Runs menu allowing the user to change certain program settings.
   */
  private void runSettingsMenu() {
    while (true) {
      String[] prompt = {"Settings (select to modify):",
                         "Number of tracks, i.e.: max track number (Current: " + noTracks + ")",
                         "Max number of departures (Current: " + maxNoDepartures + ")",
                         "Toggle comments (CURRENT: " + getCommentStateStr() + ")",
                         "Set system time (Current time: " + systemTime + ")",
                         "Return to main menu\n"
                       + "Select: "};
      int chosen = runOptionBasedMenu(prompt);
      switch (chosen) {
        case 1 -> modifyNoTracks();
        case 2 -> modifyMaxNoDepartures();
        case 3 -> toggleComments();
        case 4 -> setSystemTime();
        case 5 -> {
          return;
        }
        default -> throw new Error("Error. Default condition executed unexpectedly.");
      }
      pressEnterToContinue("Press ENTER to return to settings");
    }
  }

  /**
   * Modifies noTracks using user input and updates affected departures accordingly.
   */
  private void modifyNoTracks() {
    System.out.println("Max number of tracks decide what the maximum"
            + " allowed track number will be.");
    System.out.println("Current limit: " + noTracks);
    System.out.println("To disable limit (i.e. set it to " + Short.MAX_VALUE
            + "), press ENTER with blank input.");
    String newNoTracksStr;
    short newNoTracks;
    while (true) { //Loop persists until valid input is given.
      try {
        System.out.print("Enter new number of tracks: ");
        newNoTracksStr = sc.nextLine();
        if (newNoTracksStr.isEmpty()) { //If blank.
          newNoTracks = Short.MAX_VALUE; //Set number of tracks to limit of short datatype.
          break; //The loop exits here.
        }
        newNoTracks = Short.parseShort(newNoTracksStr); //Throws exception if unparsable.
        if (newNoTracks < 1) {
          throw new IllegalArgumentException("Track limit has to be at least 1.");
        }
        break; //If newNoTracks is valid, move out of the loop.
      } catch (NumberFormatException e) {
        System.out.println("Please enter a valid short on the format '123'"
                + " (without quotation marks).");
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
    }
    boolean flag = false;
    System.out.println("Please confirm new track limit: " + newNoTracks);
    if (newNoTracks < tdr.getHighestTrackNo()) {
      System.out.println("WARNING: Selected track number is lower than certain track"
              + " numbers associated with existing departures.");
      System.out.println("Applying this change will cause those"
              + " departures to have their track unset.");
      flag = true;
    }
    if (inputBinaryDecision()) {
      noTracks = newNoTracks;
      System.out.println("Number of tracks was successfully changed to: " + noTracks);
      if (flag) {
        System.out.println("Track number of " + tdr.unsetTrackBelowLimit(noTracks)
                + " departure(s) were unset.");
      }
    } else {
      System.out.println("Operation cancelled. Track number remains unchanged.");
    }
  }

  /**
   * Modifies maxNoDepartures using user input.
   */
  private void modifyMaxNoDepartures() {
    System.out.println("Max number of departures decide how many departures"
            + " can be stored in the registry.");
    System.out.println("Current limit: " + maxNoDepartures);
    System.out.println("To disable limit (i.e. set it to " + Integer.MAX_VALUE
            + "), press ENTER with blank input.");
    String newMaxNoDepsStr;
    int newMaxNoDeps;
    while (true) { //Loop persists until valid input is given.
      try {
        System.out.print("Enter new max number of departures: ");
        newMaxNoDepsStr = sc.nextLine();
        if (newMaxNoDepsStr.isEmpty()) { //If blank.
          newMaxNoDeps = Integer.MAX_VALUE; //Set number of tracks to limit of short datatype.
          break; //The loop exits here.
        }
        newMaxNoDeps = Short.parseShort(newMaxNoDepsStr); //Throws exception if unparsable.
        if (newMaxNoDeps < 1) {
          throw new IllegalArgumentException("Departure limit has to be at least 1.");
        }
        break; //If newNoTracks is valid, move out of the loop.
      } catch (NumberFormatException e) {
        System.out.println("Please enter a valid integer on the format '123'"
                + " (without quotation marks).");
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
    }
    if (newMaxNoDeps < tdr.getNoDepartures()) {
      System.out.println("New max number of departures is lower than"
              + " current number of departures stored in registry.");
      System.out.println("If you want to set this limit, you must first delete some departures.");
      return;
    }
    System.out.println("Please confirm new departure limit: " + newMaxNoDeps);
    if (inputBinaryDecision()) {
      maxNoDepartures = newMaxNoDeps;
      System.out.println("Departure limit was successfully changed to: " + maxNoDepartures);
    } else {
      System.out.println("Operation cancelled. Departure limit remains unchanged.");
    }
  }

  /**
   * Modifies the delay of a specific departure with user input.
   *
   * @param trainNumber trainNumber of the departure to be modified.
   * @throws IllegalArgumentException Throws exception is departure doesn't exist.
   */
  private boolean modifyDelay(String trainNumber) throws IllegalArgumentException {
    System.out.println("Current delay: " + tdr.getDeparture(trainNumber).getDelay());
    String prompt = "Enter delay in the format 'HH:MM': ";
    String error = "Delay must be in the following format: '12:34' (without quotation marks)";
    LocalTime departureTime = tdr.getDeparture(trainNumber).getDepartureTime();
    LocalTime delay = inputDelay(departureTime, prompt, error, false);
    int departureTimeMins = departureTime.getHour() * 60 + departureTime.getMinute();
    int delayMins = delay.getHour() * 60 + delay.getMinute();
    int systemTimeMins = systemTime.getHour() * 60 + systemTime.getMinute();
    boolean chain = true;
    if (departureTimeMins + delayMins < systemTimeMins) {
      System.out.println("New delay will cause departure to be automatically deleted.");
      System.out.println("Are you sure you want to proceed?");
      if (inputBinaryDecision()) {
        System.out.println("Departure deleted.");
        chain = false;
      } else {
        System.out.println("Delay remains unchanged.");
        return chain;
      }
    }
    tdr.setDelay(trainNumber, delay);
    tdr.deleteOldDepartures(systemTime);
    return chain;
  }

  /**
   * Modifies the track of a specific departure with user input.
   *
   * @param trainNumber trainNumber of the departure to be modified.
   * @throws IllegalArgumentException Throws exception is departure doesn't exist.
   */
  private void modifyTrack(String trainNumber) throws IllegalArgumentException {
    short currentTrack = tdr.getDeparture(trainNumber).getTrack();
    if (currentTrack == -1) {
      System.out.println("Current track is not set.");
    } else {
      System.out.println("Current track: " + currentTrack);
    }
    String error = "Track must be a positive number below " + Short.MAX_VALUE;
    short track = inputTrack("Enter track: ", error);
    tdr.setTrack(trainNumber, track);
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
  private void pressEnterToContinue(String prompt) {
    System.out.println(prompt);
    try {
      while (System.in.available() == 0){} //Runs (waits) until there is input from user.
      while (System.in.available() > 0) { //Consumes all input
        System.in.read();
      }
    } catch (Exception e) { /*try-catch needed to utilize System.in.available()*/ }
  }

  /**
   * Takes numeric input from user using Scanner.
   * TODO: Fix bug that causes "duplicate input" if input contains a space between other characters.
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
   * Takes a binary decision from the user.
   *
   * @return True if user selects yes, false if no.
   */
  private boolean inputBinaryDecision() {
    while (true) {
      System.out.print(("y/n: "));
      String in = sc.nextLine().toLowerCase();
      if (in.equals("y")) {
        return true;
      } else if (in.equals("n")) {
        return false;
      }
      System.out.println("Please enter either 'y' for yes or 'n' for no"
                       + "(without quotation marks).");
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
        if (in.isEmpty()) { //Track not yet set.
          return -1;
        }
        short temp = Short.parseShort(in); //Attempt to parse as short.
        if (temp == -1 || (temp >= 1 && temp <= noTracks)) { //If valid
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
   * Ensures delay is not too short or long so that departure
   * fits between current system time and end of day.
   *
   * @param departureTime Departure time of the departure that delay is assigned to.
   *                      This is used to avoid departure times spilling into next day.
   *                      If desirable this can be set to LocalTime.of(0, 0),
   *                      in which case this method gets a LocalTime object from the user
   *                      and returns LocalTime.of(0, 0) if input is empty.
   * @param prompt Presented to the user before input is taken.
   * @param error Presented to the user if input is invalid.
   * @param enforceValid If true, only delays that land departure time after system time is allowed.
   * @return Returns LocalTime object that together with departureTime
   *         provided does not exceed 23:59.
   */
  private LocalTime inputDelay(LocalTime departureTime, String prompt,
                               String error, boolean enforceValid) {
    LocalTime delay;
    int depTimeMins = departureTime.getHour() * 60 + departureTime.getMinute();
    int systemTimeMins = systemTime.getHour() * 60 + systemTime.getMinute();
    while (true) {
      try {
        System.out.print(prompt);
        String in = sc.nextLine();
        if (in.isEmpty()) {
          delay = LocalTime.of(0, 0);
        } else {
          delay = LocalTime.parse(in);
        }
        int actDepTimeMins = depTimeMins + delay.getHour() * 60 + delay.getMinute();
        if (actDepTimeMins >= 60 * 24) { //If departure time is after 23:59.
          throw new IllegalArgumentException("Delay is too long. Departure is past day.");
        } else if (actDepTimeMins < systemTimeMins && enforceValid) {
          throw new IllegalArgumentException("Departure time plus delay lands "
            + "before current system time. Please enter a larger delay.");
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
    String prompt = generateOptionBasedMenu(content); //Generate complete menu as string.
    String error = "Please enter a valid option between '1' and '"
            + (content.length - 1 + "' (without quotation marks)");
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