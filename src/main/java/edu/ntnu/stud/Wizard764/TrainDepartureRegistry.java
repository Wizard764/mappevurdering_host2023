package edu.ntnu.stud.Wizard764;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The TrainDepartureRegistry class represents a registry for train departures.
 * It allows adding, retrieving, and checking the existence of train departures,
 * as well as getting departures by destination and generating a formatted string
 * containing the complete list of departures.
 */
public class TrainDepartureRegistry {
  /**
   * List containing TrainDeparture objects representing the train departures.
   */
  private ArrayList<TrainDeparture> departures;

  /**
   * Constructor for TrainDepartureRegistry. Initializes the list of departures.
   */
  TrainDepartureRegistry() {
    departures = new ArrayList<>();
  }

  /**
   * Initializes TrainDepartureRegistry by list of departures.
   *
   * @param departures Array of departures to add to the registry.
   */
  TrainDepartureRegistry(TrainDeparture[] departures) {
    this();
    this.departures.addAll(Arrays.asList(departures));
  }

  /**
   * Adds a train departure to the registry.
   *
   * @param departure The TrainDeparture object to be added.
   * @throws IllegalArgumentException if a departure with the same identification
   *                                  number already exists (IDs must be unique).
   */
  void addDeparture(TrainDeparture departure) throws IllegalArgumentException {
    departures.forEach(x -> {
      if (x.getTrainNumber().equals(departure.getTrainNumber())) {
        String s = "A departure with that identification number already exists. ID must be unique.";
        throw new IllegalArgumentException(s);
      }
    });
    departures.add(departure);
  }

  /**
   * Retrieves a train departure based on the provided train number.
   *
   * @param trainNumIn The train number of the departure to retrieve.
   * @return The TrainDeparture object corresponding to the provided train number.
   * @throws IllegalArgumentException if the departure does not exist.
   */
  TrainDeparture getDeparture(String trainNumIn) throws IllegalArgumentException {
    for (TrainDeparture departure : departures) {
      if (departure.getTrainNumber().equals(trainNumIn)) {
        return departure;
      }
    }
    throw new IllegalArgumentException("Departure does not exist.");
  }

  /**
   * Checks if a train departure with the given train number exists.
   *
   * @param trainNumIn The train number to check.
   * @return true if the departure exists, false otherwise.
   */
  boolean departureExists(String trainNumIn) {
    try {
      getDeparture(trainNumIn);
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

  /**
   * Retrieves an array of train departures based on the provided destination.
   *
   * @param destinationIn The destination to filter departures.
   * @return An array of TrainDeparture objects with the specified destination.
   */
  TrainDeparture[] getDeparturesByDestination(String destinationIn) {
    ArrayList<TrainDeparture> tempResult = new ArrayList<>();
    for (TrainDeparture departure : departures) {
      if (departure.getDestination().equals(destinationIn)) {
        tempResult.add(departure);
      }
    }
    return tempResult.toArray(new TrainDeparture[0]);
  }

  /**
   * Deletes departures that are past as of the time given as parameter.
   * METACOMMENT-NOTE: In actual use it would make sense for this method to fetch the current time,
   *     but the task description asks for time to be adjusted manually.
   * Final version will be private. Public for test purposes.
   *
   * @param currentTime The current time.
   */
  public void deleteOldDepartures(LocalTime currentTime) {
    if (departures.isEmpty()) {
      return;
    }
    sortDeparturesByTimeIncDelay();
    final int currentTimeMins = currentTime.getHour() * 60 + currentTime.getMinute();
    int currentDepActDepartureTimeMins = departures.get(0).getDepartureTimeIncDelayInMinutes();
    while (currentDepActDepartureTimeMins < currentTimeMins) {
      departures.remove(0);
      if (departures.isEmpty()) {
        return;
      }
      currentDepActDepartureTimeMins = departures.get(0).getDepartureTimeIncDelayInMinutes();
    }
  }

  /**
   * Sets the comment of a departure in the registry.
   *
   * @param trainNumber Train number of the departure to be modified.
   * @param comment New comment.
   * @throws IllegalArgumentException Throws exception if departure doesn't exist.
   */
  public void setComment(String trainNumber, String comment) throws IllegalArgumentException {
    for (TrainDeparture t : departures) {
      if (t.getTrainNumber().equals(trainNumber)) {
        t.setComment(comment);
        return;
      }
    }
    String ex = "\"No departure exists with train number: " + trainNumber + ".";
    throw new IllegalArgumentException();
  }

  /**
   * Sets the delay of a departure in the registry.
   *
   * @param trainNumber Train number of the departure to be modified.
   * @param delay New delay.
   * @throws IllegalArgumentException Throws exception if departure doesn't exist.
   */
  public void setDelay(String trainNumber, LocalTime delay) throws IllegalArgumentException {
    for (TrainDeparture t : departures) {
      if (t.getTrainNumber().equals(trainNumber)) {
        t.setDelay(delay);
        return;
      }
    }
    String ex = "\"No departure exists with train number: " + trainNumber + ".";
    throw new IllegalArgumentException();
  }

  /**
   * Sets the track of a departure in the registry.
   *
   * @param trainNumber Train number of the departure to be modified.
   * @param track New track.
   * @throws IllegalArgumentException Throws exception if departure doesn't exist.
   */
  public void setTrack(String trainNumber, short track) throws IllegalArgumentException {
    for (TrainDeparture t : departures) {
      if (t.getTrainNumber().equals(trainNumber)) {
        t.setTrack(track);
        return;
      }
    }
    String ex = "\"No departure exists with train number: " + trainNumber + ".";
    throw new IllegalArgumentException();
  }

  /**
   * Private method to sort the list of departures by time using DepartureComparator.
   */
  public void sortDeparturesByTime() {
    departures.sort(new DepartureComparator());
  }

  /**
   * Private method to sort the list of departures by actual departure time
   * using DepartureComparatorIncDelay.
   */
  public void sortDeparturesByTimeIncDelay() {
    departures.sort(new DepartureComparatorIncDelay());
  }

  /**
   * Gets number of departures stored in the registry.
   *
   * @return Number of TrainDeparture objects stored in the registry.
   */
  public int getNoDepartures() {
    return departures.size();
  }

  /**
   * Sets comment state for each departure in registry.
   *
   * @param state New comment state.
   */
  public void setCommentState(boolean state) {
    departures.forEach(t -> t.setCommentState(state));
  }

  /**
   * Overrides the default toString() method to provide a formatted string
   * representation of the complete list of departures sorted by time.
   *
   * @return A formatted string containing the complete list of departures.
   */
  @Override
  public String toString() {
    StringBuilder out = new StringBuilder("Complete list of departures:");
    for (TrainDeparture departure : departures) {
      out.append("\n").append(departure.toString());
    }
    return out.toString();
  }
}