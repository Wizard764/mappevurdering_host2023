package edu.ntnu.stud.Wizard764;

import java.time.LocalTime;
/**
 * Represents a train set to depart from the station.
 * Stores the time and delay of departure, the destination of the train, as well as the line, unique identifying number and track the train will be departing from.
 * Only one train may have a specific unique identifier within a given system within the same day.
 * Only the delay of a given departure may change, all other fields are immutable.
 */
public class TrainDeparture
{
    /**
     * The time of departure, only accurate to hours and minutes; seconds and below should be 0. Note that second != 0 does not affect function and can simply be ignored.
     * The departure time is immutable, any change is represented by delay.
     */
    private final LocalTime departureTime;
    /**
     * The line the train runs on.
     * Core information defining a departure, and is thus immutable. If need be, one should create a new departure to avoid confusion.
     * Note: I have elaborated on my choice of variable type in the report. This one is spicy.
     */
    private final String line;
    /**
     * Unique number identifying a specific train within a day. Immutable.
     * Depending on numbering convention this could conceivably be a very large number or contain letters, hence String.
     */
    private final String trainNumber;
    /**
     * The destination of the train.
     * Should there be a reconfiguration of the destination of a given train, a new departure should always be created to avoid confusion. Therefore, the destination is immutable.
     */
    private final String destination;
    /**
     * Delay past departure time if relevant. This may change over time and is thus variable.
     */
    private LocalTime delay;
    /**
     * The track the train will be departing from.
     * It is impossible unlikely that it will ever be necessary to have more than 32'767 tracks, regardless of naming convention. Therefore, it is represented by short.
     * The track is decided by local logistics in a given station and is therefore prone to sudden changes, thus variable.
     */
    private short track;
    /**
     * String containing any extra or special-case information.
     * Will ideally function as a catch-all for unforeseen circumstances in use. Might even serve certain needs that develop as the system ages, thus increasing it's lifespan.
     */
    private String comment;
    /**
     * Constructs a TrainDeparture with specified parameters including departure time, line, train number, destination, delay, track, and comment.
     *
     * @param departureTime The time of departure, accurate to hours and minutes.
     * @param line The line the train runs on.
     * @param trainNumber Unique number identifying a specific train within a day.
     * @param destination The destination of the train.
     * @param delay Delay past departure time if relevant.
     * @param track The track the train will be departing from.
     * @param comment String containing any extra or special-case information.
     * @throws IllegalArgumentException if the train is delayed past the current day or if the track is not a positive number.
     */
    public TrainDeparture(LocalTime departureTime, String line, String trainNumber, String destination, LocalTime delay, short track, String comment) throws IllegalArgumentException
    {
        if(departureTime.getHour() + delay.getHour() + ((departureTime.getMinute() + delay.getMinute()) / 60) > 24)
        {
            throw new IllegalArgumentException("Train is delayed past current day and is not allowed in system");
        }
        if(track <= (short)0 && track != (short)-1)
        {
            throw new IllegalArgumentException("Track must be a positive number");
        }
        this.departureTime = departureTime; //It's not harmful if seconds don't equal 0, so this can be safely copied without checking.
        this.line = line;
        this.trainNumber = trainNumber;
        this.destination = destination;
        this.delay = delay;
        this.track = track;
        this.comment = comment;
    }
    /**
     * Constructs a TrainDeparture without a specified delay. Delay is set to "00:00".
     *
     * @param departureTime The time of departure, accurate to hours and minutes.
     * @param line The line the train runs on.
     * @param trainNumber Unique number identifying a specific train within a day.
     * @param destination The destination of the train.
     * @param track The track the train will be departing from.
     * @param comment String containing any extra or special-case information.
     * @throws IllegalArgumentException if the train is delayed past the current day or if the track is not a positive number.
     */
    public TrainDeparture(LocalTime departureTime, String line, String trainNumber, String destination, short track, String comment) throws IllegalArgumentException
    {
        this(departureTime, line, trainNumber, destination, LocalTime.parse("00:00"), track, comment); //Throws IllegalArgumentException
    }
    /**
     * Constructs a TrainDeparture without a specified track. Track is set to -1, awaiting assignment by user.
     *
     * @param departureTime The time of departure, accurate to hours and minutes.
     * @param line The line the train runs on.
     * @param trainNumber Unique number identifying a specific train within a day.
     * @param destination The destination of the train.
     * @param delay Delay past departure time if relevant.
     * @param comment String containing any extra or special-case information.
     * @throws IllegalArgumentException if the train is delayed past the current day or if the track is not a positive number.
     */
    public TrainDeparture(LocalTime departureTime, String line, String trainNumber, String destination, LocalTime delay, String comment) throws IllegalArgumentException
    {
        this(departureTime, line, trainNumber, destination, delay, (short)-1, comment); //Throws IllegalArgumentException
    }
    /**
     * Constructs a TrainDeparture without a specified delay or track. Delay = "00:00", track = -1.
     *
     * @param departureTime The time of departure, accurate to hours and minutes.
     * @param line The line the train runs on.
     * @param trainNumber Unique number identifying a specific train within a day.
     * @param destination The destination of the train.
     * @param comment String containing any extra or special-case information.
     * @throws IllegalArgumentException if the train is delayed past the current day or if the track is not a positive number.
     */
    public TrainDeparture(LocalTime departureTime, String line, String trainNumber, String destination, String comment) throws IllegalArgumentException
    {
        this(departureTime, line, trainNumber, destination, (short)-1, comment); //Throws IllegalArgumentException
    }
    /**
     * Constructs a TrainDeparture without specified comment. Comment = "".
     *
     * @param departureTime The time of departure, accurate to hours and minutes.
     * @param line The line the train runs on.
     * @param trainNumber Unique number identifying a specific train within a day.
     * @param destination The destination of the train.
     * @param delay Delay past departure time if relevant.
     * @param track The track the train will be departing from.
     * @throws IllegalArgumentException if the train is delayed past the current day or if the track is not a positive number.
     */
    public TrainDeparture(LocalTime departureTime, String line, String trainNumber, String destination, LocalTime delay, short track) throws IllegalArgumentException
    {
        this(departureTime, line, trainNumber, destination, delay, track, "");
    }
    /**
     * Constructs a TrainDeparture without a specified delay or comment. Delay = "00:00", comment = "".
     *
     * @param departureTime The time of departure, accurate to hours and minutes.
     * @param line The line the train runs on.
     * @param trainNumber Unique number identifying a specific train within a day.
     * @param destination The destination of the train.
     * @param track The track the train will be departing from.
     * @throws IllegalArgumentException if the train is delayed past the current day or if the track is not a positive number.
     */
    public TrainDeparture(LocalTime departureTime, String line, String trainNumber, String destination, short track) throws IllegalArgumentException
    {
        this(departureTime, line, trainNumber, destination, track, ""); //Throws IllegalArgumentException
    }
    /**
     * Constructs a TrainDeparture without a specified track or comment. Track = -1, comment = "".
     *
     * @param departureTime The time of departure, accurate to hours and minutes.
     * @param line The line the train runs on.
     * @param trainNumber Unique number identifying a specific train within a day.
     * @param destination The destination of the train.
     * @param delay Delay past departure time if relevant.
     * @param track The track the train will be departing from.
     * @param comment String containing any extra or special-case information.
     * @throws IllegalArgumentException if the train is delayed past the current day or if the track is not a positive number.
     */
    public TrainDeparture(LocalTime departureTime, String line, String trainNumber, String destination, LocalTime delay) throws IllegalArgumentException
    {
        this(departureTime, line, trainNumber, destination, delay, ""); //Throws IllegalArgumentException
    }
    /**
     * Constructs a TrainDeparture without a specified delay, track or comment. Delay = "00:00", track = -1, comment = "".
     *
     * @param departureTime The time of departure, accurate to hours and minutes.
     * @param line The line the train runs on.
     * @param trainNumber Unique number identifying a specific train within a day.
     * @param destination The destination of the train.
     * @throws IllegalArgumentException if the train is delayed past the current day or if the track is not a positive number.
     */
    public TrainDeparture(LocalTime departureTime, String line, String trainNumber, String destination) throws IllegalArgumentException
    {
        this(departureTime, line, trainNumber, destination, ""); //Throws IllegalArgumentException
    }
}