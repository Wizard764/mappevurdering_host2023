package edu.ntnu.stud.Wizard764;

/**
 * Custom comparator for comparing TrainDeparture objects based on departure time and delay
 * Implements the java.util.Comparator interface.
 */
class DepartureComparatorIncDelay implements java.util.Comparator<TrainDeparture> {
  /**
   * Compares two TrainDeparture objects based on their departure times plus any delay.
   *
   * @param a the first TrainDeparture object
   * @param b the second TrainDeparture object
   * @return a negative integer, zero, or a positive integer as the first argument
   *         is less than, equal to, or greater than the second argument, respectively,
   *         based on departure times.
   */
  @Override
  public int compare(TrainDeparture a, TrainDeparture b) {
    return a.getDepartureTimeIncDelayInMinutes() - b.getDepartureTimeIncDelayInMinutes();
  }
}
