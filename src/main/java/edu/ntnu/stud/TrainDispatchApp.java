package edu.ntnu.stud;

import edu.ntnu.stud.Wizard764.UserInterface;

/**
 * This is the main class for the train dispatch application.
 */
public class TrainDispatchApp {
  /**
   * Main method.
   *
   * @param args args
   */
  public static void main(String[] args) {
    UserInterface ui = new UserInterface();
    ui.init();
    ui.start();
  }
}
