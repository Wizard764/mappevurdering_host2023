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
     * Will ideally function as a catch-all for unforeseen circumstances in use. Might even serve certain needs that develop as the system ages, thus increasing its lifespan.
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
        setTrack(track); //throws IllegalArgumentException
        this.departureTime = LocalTime.of(departureTime.getHour(), departureTime.getMinute());
        this.line = line;
        this.trainNumber = trainNumber;
        this.destination = destination;
        this.delay = LocalTime.of(delay.getHour(), delay.getMinute());
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
    //Simple getters
    public LocalTime getDepartureTime()
    {
        return departureTime;
    }
    public String getLine()
    {
        return line;
    }
    public String getTrainNumber()
    {
        return trainNumber;
    }
    public String getDestination()
    {
        return destination;
    }
    public LocalTime getDelay()
    {
        return delay;
    }
    public short getTrack()
    {
        return track;
    }
    public String getComment()
    {
        return comment;
    }
    //Getters
    /**
     * For string-formatted departure time.
     * @return Returns a string in the format: "hh:mm".
     */
    public String getDepartureTimeStr()
    {
        return fromLocalTimeToString(departureTime);
    }

    /**
     * Useful for comparing timestamps with simple integer manipulation
     * @return Returns the time of departure in minutes, i.e: 12:04 returns 724 & 03:30 returns 210
     */
    public int getDepartureTimeInMinutes()
    {
        return departureTime.getHour() * 60 + departureTime.getMinute();
    }
    /**
     * For string-formatted delay.
     * @return Returns a string in the format: "hh:mm".
     */
    public String getDelayStr()
    {
        return fromLocalTimeToString(delay);
    }
    /**
     * Useful for comparing timestamps with simple integer manipulation
     * @return Returns the time of departure in minutes, i.e: 12:04 returns 724 & 03:30 returns 210
     */
    public int getDelayInMinutes()
    {
        return delay.getHour() * 60 + departureTime.getMinute();
    }
    /**
     * Calculates the actual time of departure including the delay.
     * @return Returns the actual time of departure
     */
    public LocalTime getDepartureTimeIncDelay()
    {
        return LocalTime.of(departureTime.getHour() + delay.getHour(), departureTime.getMinute() + delay.getMinute());
    }

    /**
     * Calculates the actual time of departure including the delay.
     * @return Returns the actual time of departure as a String in the format: "hh:mm".
     */
    public String getDepartureTimeIncDelayStr()
    {
        int minutes = departureTime.getMinute() + delay.getMinute();
        int hours = departureTime.getHour() + delay.getHour() + minutes / 60;
        minutes %= 60;
        return fromLocalTimeToString(LocalTime.of(hours, minutes));
    }
    /**
     * Calculates the actual time of departure including the delay.
     * @return Returns the actual time of departure as minutes into the day, ie: departure time of 12:10 and delay of 00:05 returns 735.
     */
    public int getDepartureTimeIncDelayInMinutes()
    {
        return departureTime.getHour() * 60 + departureTime.getMinute() + delay.getHour() * 60 + delay.getMinute();
    }
    //Setters
    public void setDelay(LocalTime delay) throws IllegalArgumentException
    {
        this.delay = delay;
    }
    /**
     * Advanced setter, assures track is valid.
     * @param track The track number to assign the departure.
     */
    public void setTrack(short track)
    {
        if(track <= (short)0 && track != (short)-1)
        {
            throw new IllegalArgumentException("Track must be a positive number");
        }
        this.track = track;
    }
    /**
     * track value of -1 signifies it's not assigned
     */
    public void unsetTrack()
    {
        track = (short)-1;
    }
    public void setComment(String comment)
    {
        this.comment = comment;
    }

    /**
     * Builds a complex multi-line string containing information about the departure in an aesthetic fashion.
     * @return Returns a string containing all relevant information about the departure formatted over several lines.
     */
    @Override
    public String toString()
    {
        int desiredSegmentWidth = CalcDesiredSegmentWidth(); //Determine the minimum required length of a segment so a line will fit. At least 25.
        StringBuilder out = new StringBuilder(); //Initialize output string
        //Add the first line where all 3 segments are filled with "#".
        out.append("##").append(CreateInfoLine(new String[]{     //FIRST LINE beginning with ##
            "#".repeat(desiredSegmentWidth),                     //1. segment (#)
            "#".repeat(desiredSegmentWidth),                     //2. segment (#)
            "#".repeat(desiredSegmentWidth)                      //3. segment (#)
        }, desiredSegmentWidth)).append("##\n");                 //End with ##\n
        //Add the departure time, line and train number.
        out.append("# ").append(CreateInfoLine(new String[]{     //SECOND LINE  beginning with #
            "Departure time: " + getDepartureTimeStr(),          //1. segment (departure time)
            "Line: " + line,                                     //2. segment (line)
            "Train number: " + trainNumber                       //3. segment (train number)
        }, desiredSegmentWidth)).append(" #\n");                 //End with ##\n
        //Add the destination in a separate line
        out.append("# ").append(CreateInfoLine(new String[]{     //THIRD LINE  beginning with #
            "Destination: " + destination,                       //1. segment (destination)
            "",                                                  //2. segment (empty)
            ""                                                   //3. segment (empty)
        }, desiredSegmentWidth)).append(" #\n");                 //End with ##\n
        if(getDelayInMinutes() != 0) //Add delay information if applicable
        {
            out.append("# ").append(CreateInfoLine(new String[]{ //FOURTH LINE  beginning with #
                "Delay: " + getDelayStr(),                                                  //1. segment (delay)
                "Actual departure time(including delay): " + getDepartureTimeIncDelayStr(), //2. segment (departure time including delay)
                ""                                                                          //3. segment (empty)
            }, desiredSegmentWidth)).append(" #\n");             //End with ##\n
        }
        if(track != -1) //Add track information if applicable
        {
            out.append("# ").append(CreateInfoLine(new String[]{ //FIFTH LINE  beginning with #
                "Track: " + track,                               //1. segment (track)
                "",                                              //2. segment (empty)
                ""                                               //3. segment (empty)
            }, desiredSegmentWidth)).append(" #\n");             //End with ##\n
        }
        if(!comment.isEmpty()) //Add comment if there is one
        {
            out.append("# ").append(CreateInfoLine(new String[]{ //SIXTH LINE  beginning with #
                "Comment: " + comment,                           //1. segment (comment)
                "",                                              //2. segment (empty)
                ""                                               //3. segment (empty)
            }, desiredSegmentWidth)).append(" #\n");             //End with ##\n
        }
        //Add the last line, the "bottom lid", row of "#"
        out.append("##").append(CreateInfoLine(new String[]{     //LAST LINE  beginning with ##
            "#".repeat(desiredSegmentWidth),                     //1. segment (#)
            "#".repeat(desiredSegmentWidth),                     //2. segment (#)
            "#".repeat(desiredSegmentWidth)                      //3. segment (#)
        }, desiredSegmentWidth)).append("##");                   //End with ##
        return out.toString(); //Return completed string
    }
    private int CalcDesiredSegmentWidth()
    {
        //Determine the minimum required length by first determining the length of each line of significant length and using the longest
        int minimumRequiredLength = Integer.max(46 + line.length() + trainNumber.length(), Integer.max(13 + destination.length(), 9 + comment.length()));
        int desiredSegmentWidth = 25; //Standard segment width. Each line is 3 segments long
        //Increase the desiredSegment width until sufficient
        while(desiredSegmentWidth * 3 < minimumRequiredLength)
        {
            desiredSegmentWidth++;
        }
        return desiredSegmentWidth;
    }
    /**
     * Creates a formatted line by concatenating an array of strings, adjusting spacing for each segment based on the desired segment width.
     *
     * @param parts                An array of strings representing the segments to be concatenated.
     * @param desiredSegmentWidth The desired width for each segment.
     * @return A formatted line containing concatenated segments with adjusted spacing.
     */
    private String CreateInfoLine(String[] parts, int desiredSegmentWidth)
    {
        StringBuilder out = new StringBuilder();
        for(int i = 0; i < parts.length; i++)
        {
            out.append(parts[i]);
            out.append(" ".repeat(Integer.max(desiredSegmentWidth * (i+1) - out.length(), 0)));
        }
        return out.toString();
    }

    /**
     * Converts a LocalTime into a standardised string format.
     * @param l The LocalTime object to convert
     * @return A string in the format: "hh:mm"
     */
    private String fromLocalTimeToString(LocalTime l)
    {
        String out = "";
        if(l.getHour() < 10)
        {
            out = "0";
        }
        out += l.getHour() + ":";
        if(l.getMinute() < 10)
        {
            out += "0";
        }
        out += l.getMinute();
        return out;
    }
}