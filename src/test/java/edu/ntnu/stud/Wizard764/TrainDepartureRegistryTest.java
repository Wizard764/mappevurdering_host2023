package edu.ntnu.stud.Wizard764;

import java.time.LocalTime;
import org.junit.jupiter.api.Test;

public class TrainDepartureRegistryTest {
  @Test
  public void addValidDepartureTest() {
    TrainDepartureRegistry tdr = new TrainDepartureRegistry();
    tdr.addDeparture(new TrainDeparture(LocalTime.of(12, 31), "F4", "AR762", "Trondheim"));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(8, 15), "A1", "AR123", "Oslo"));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(9, 45), "B2", "AR456", "Bergen"));
  }

  @Test
  public void addDuplicateDepartureTest() {
    try {
      TrainDepartureRegistry tdr = new TrainDepartureRegistry();
      tdr.addDeparture(new TrainDeparture(LocalTime.of(12, 31), "F4", "A1", "Trondheim"));
      tdr.addDeparture(new TrainDeparture(LocalTime.of(8, 15), "A1", "A1", "Oslo"));
      tdr.addDeparture(new TrainDeparture(LocalTime.of(9, 45), "B2", "AR456", "Bergen"));
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  @Test
  public void getDepartureTest() {
    TrainDepartureRegistry tdr = new TrainDepartureRegistry();
    tdr.addDeparture(new TrainDeparture(LocalTime.of(12, 31), "F4", "AR762", "Trondheim"));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(8, 15), "A1", "AR123", "Oslo"));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(9, 45), "B2", "AR456", "Bergen"));
    System.out.println(tdr.getDeparture("AR456"));
  }

  @Test
  public void getDeparturesByDestinationTest() {
    TrainDepartureRegistry tdr = new TrainDepartureRegistry();
    tdr.addDeparture(new TrainDeparture(LocalTime.of(12, 31), "F4", "AR762", "Trondheim"));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(8, 15), "A1", "AR123", "Oslo"));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(9, 45), "B2", "AR456", "Trondheim"));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(11, 20), "C3", "AR789", "Stavanger"));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(13, 0), "D4", "AR101", "Kristiansand"));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(19, 15), "H8", "AR111", "Trondheim"));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(20, 50), "I9", "AR222", "Trondheim"));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(14, 30), "E5", "AR234", "Drammen"));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(16, 5), "F6", "AR567", "Molde"));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(17, 40), "G7", "AR890", "Alesund"));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(22, 25), "J10", "AR333", "Bodø"));
    TrainDeparture[] deps = tdr.getDeparturesByDestination("Trondheim");
    System.out.println("Fetched departures:");
    for(TrainDeparture t : deps) {
      System.out.println(t);
    }
  }

  @Test
  public void sortDeparturesTest() {
    TrainDepartureRegistry tdr = new TrainDepartureRegistry();
    tdr.addDeparture(new TrainDeparture(LocalTime.of(12, 31), "F4", "AR762", "Trondheim"));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(8, 15), "A1", "AR123", "Oslo"));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(9, 45), "B2", "AR456", "Bergen"));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(11, 20), "C3", "AR789", "Stavanger"));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(13, 0), "D4", "AR101", "Kristiansand"));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(19, 15), "H8", "AR111", "Tromsø"));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(20, 50), "I9", "AR222", "Haugesund"));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(14, 30), "E5", "AR234", "Drammen"));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(16, 5), "F6", "AR567", "Molde"));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(17, 40), "G7", "AR890", "Alesund"));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(22, 25), "J10", "AR333", "Bodø"));
    System.out.println(tdr.toString()); //This will trigger sorting of the departures internally.
  }

  @Test
  public void departureExistenceTest() throws Exception {
    TrainDepartureRegistry tdr = new TrainDepartureRegistry();
    tdr.addDeparture(new TrainDeparture(LocalTime.of(12, 31), "F4", "AR762", "Trondheim"));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(8, 15), "A1", "AR123", "Oslo"));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(9, 45), "B2", "AR456", "Bergen"));

    String id = "AR762";
    if(tdr.departureExists(id)) {
      System.out.println("Train departure " + id + " exists [Expected result]");
    } else {
      throw new Exception("Test failed.");
    }
    id = "AR76";
    if(tdr.departureExists(id)) {
      throw new Exception("Test failed.");
    } else {
      System.out.println("Train departure " + id + " does not exist  [Expected result]");
    }
  }
}