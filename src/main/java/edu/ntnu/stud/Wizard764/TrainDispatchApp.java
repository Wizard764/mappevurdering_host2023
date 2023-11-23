package edu.ntnu.stud.Wizard764;

import java.time.LocalTime;

/** This is the main class for the train dispatch application. */
public class TrainDispatchApp {
  /**
   * The main method of the application.
   *
   * @param args arguments
   */
  public static void main(String[] args) {
    UserInterface ui = new UserInterface();
    ui.init();
    ui.start();
  }
}