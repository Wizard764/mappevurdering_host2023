package edu.ntnu.stud.Wizard764;

import java.time.LocalTime;
import org.junit.jupiter.api.Test;

/**
 * Test-class for TrainDepartureRegistry
 * Tests class methods, including negative tests where applicable.
 * NOTE: The class takes TrainDeparture objects as parameters in some cases.
 * Negative testing here will be done in the TrainDeparture class.
 */
public class TrainDepartureRegistryTest {
  @Test
  public void addValidDepartureTest() {
    TrainDepartureRegistry tdr = new TrainDepartureRegistry();
    tdr.addDeparture(new TrainDeparture(LocalTime.of(12, 31), "F4", "AR762", "Trondheim"));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(8, 15), "A1", "AR123", "Oslo"));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(9, 45), "B2", "AR456", "Bergen"));
  }

  @Test
  public void testConstructor() {
    TrainDeparture[] departures = new TrainDeparture[]{
      new TrainDeparture(LocalTime.of(12, 31), "F4", "AR762", "Dest"),
      new TrainDeparture(LocalTime.of(8, 15), "A1", "AR123", "Dest"),
      new TrainDeparture(LocalTime.of(9, 45), "B2", "AR456", "Dest"),
      new TrainDeparture(LocalTime.of(11, 20), "C3", "AR789", "Stavanger"),
      new TrainDeparture(LocalTime.of(13, 0), "D4", "AR101", "Kristiansand")};
    TrainDepartureRegistry tdr1 = new TrainDepartureRegistry(departures);
  }

  @Test
  public void addDuplicateDepartureTest() {
    try {
      TrainDepartureRegistry tdr = new TrainDepartureRegistry();
      tdr.addDeparture(new TrainDeparture(LocalTime.of(12, 31), "F4", "A1", "Trondheim"));
      tdr.addDeparture(new TrainDeparture(LocalTime.of(8, 15), "A1", "A1", "Oslo"));
      tdr.addDeparture(new TrainDeparture(LocalTime.of(9, 45), "B2", "AR456", "Bergen"));
      throw new Error("Test failed. Duplicate departures added.");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  @Test
  public void getExistingDepartureTest() {
    TrainDepartureRegistry tdr = new TrainDepartureRegistry();
    tdr.addDeparture(new TrainDeparture(LocalTime.of(12, 31), "F4", "AR762", "Trondheim"));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(8, 15), "A1", "AR123", "Oslo"));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(9, 45), "B2", "AR456", "Bergen"));
    System.out.println(tdr.getDeparture("AR456"));
  }

  @Test
  public void getNonexistentDepartureTest() {
    TrainDepartureRegistry tdr = new TrainDepartureRegistry();
    tdr.addDeparture(new TrainDeparture(LocalTime.of(12, 31), "F4", "AR762", "Trondheim"));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(8, 15), "A1", "AR123", "Oslo"));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(9, 45), "B2", "AR456", "Bergen"));
    try {
      System.out.println(tdr.getDeparture("Nonexistent ID"));
      throw new Error("Test failed. Nonexistent ID returned results.");
    } catch(IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
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
    assert(deps.length == 4);
    deps = tdr.getDeparturesByDestination("Nonexistent destination");
    System.out.println("Fetched departures:");
    for(TrainDeparture t : deps) {
      System.out.println(t);
    }
    assert(deps.length == 0);
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
    tdr.sortDeparturesByTime();
    System.out.println(tdr); //This will trigger sorting of the departures internally.
  }

  @Test
  public void sortDeparturesIncDelayTest() {
    TrainDepartureRegistry tdr = new TrainDepartureRegistry();
    tdr.addDeparture(new TrainDeparture(LocalTime.of(12, 31), "F4", "AR762", "Trondheim", LocalTime.of(6, 0)));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(8, 15), "A1", "AR123", "Oslo", LocalTime.of(12, 0)));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(9, 45), "B2", "AR456", "Bergen", LocalTime.of(10, 0)));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(11, 20), "C3", "AR789", "Stavanger", LocalTime.of(8, 0)));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(13, 0), "D4", "AR101", "Kristiansand", LocalTime.of(0, 0)));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(19, 15), "H8", "AR111", "Tromsø", LocalTime.of(0, 0)));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(20, 50), "I9", "AR222", "Haugesund", LocalTime.of(0, 0)));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(14, 30), "E5", "AR234", "Drammen", LocalTime.of(0, 0)));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(16, 5), "F6", "AR567", "Molde", LocalTime.of(0, 0)));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(17, 40), "G7", "AR890", "Alesund", LocalTime.of(0, 0)));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(22, 25), "J10", "AR333", "Bodø", LocalTime.of(0, 0)));
    tdr.sortDeparturesByTimeIncDelay();
    System.out.println(tdr); //This will trigger sorting of the departures internally.
  }

  @Test
  public void departureExistenceTest() throws Exception {
    TrainDepartureRegistry tdr = new TrainDepartureRegistry();
    tdr.addDeparture(new TrainDeparture(LocalTime.of(12, 31), "F4", "AR762", "Trondheim"));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(8, 15), "A1", "AR123", "Oslo"));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(9, 45), "B2", "AR456", "Bergen"));

    String id = "AR762";
    assert(tdr.departureExists(id));
    id = "AR76";
    assert(!tdr.departureExists(id));
  }

  @Test
  public void deleteDepsByTime() {
    TrainDepartureRegistry tdr = new TrainDepartureRegistry();
    tdr.addDeparture(new TrainDeparture(LocalTime.of(12, 31), "F4", "AR762", "Trondheim"));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(8, 15), "A1", "AR123", "Oslo"));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(9, 45), "B2", "AR456", "Trondheim"));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(11, 20), "C3", "AR789", "Stavanger"));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(13, 0), "D4", "AR101", "Kristiansand"));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(19, 15), "H8", "AR111", "Trondheim"));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(20, 50), "I9", "AR222", "Trondheim"));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(14, 30), "E5", "AR234", "Drammen", LocalTime.of(1, 40)));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(16, 5), "F6", "AR567", "Molde"));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(17, 40), "G7", "AR890", "Alesund"));
    tdr.addDeparture(new TrainDeparture(LocalTime.of(22, 25), "J10", "AR333", "Bodø"));

    tdr.deleteOldDepartures(LocalTime.of(15, 0));
    System.out.println(tdr);
    assert(tdr.getNoDepartures() == 6);
  }
}