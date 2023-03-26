package DataTypes;

import java.util.Date;

public class RaceResult {
    String participantName;
    int participantID;
    TimeOnlyDate start;
    TimeOnlyDate end;
    String raceType;

    public RaceResult(String participantName, int participantID, TimeOnlyDate start, TimeOnlyDate end, String raceType){
        this.participantName = participantName;
        this.participantID = participantID;
        this.start = start;
        this.end = end;
        this.raceType = raceType;
    }

    public String getParticipantName() {
        return participantName;
    }

    public int getParticipantID() {
        return participantID;
    }

    public TimeOnlyDate getStart() {
        return start;
    }

    public TimeOnlyDate getEnd() {
        return end;
    }

    public String getRaceType() {
        return raceType;
    }

    @Override
    public String toString() {
        return "Participant Name: " + participantName +
                "\nParticipant ID: " + participantID +
                "\nStart Time: " + start +
                "\nEnd Time: " + end +
                "\nRace Type: " + raceType;
    }

    public double getDuration() {
        return start.getTimeDifference(end);
    }
}
