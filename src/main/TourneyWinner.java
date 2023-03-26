package main;

import DataTypes.Participant;
import DataTypes.RaceResult;
import FileParser.RaceResultsFileParser;

import java.util.*;

public class TourneyWinner {
    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Please provide a file path as an argument.");
            return;
        }


        String filePath = args[0];
        RaceResultsFileParser parser = new RaceResultsFileParser();

        List<RaceResult> results = parser.ParseFile(filePath);

        CheckParticipantNameConsistency(results);

        FindWinner(results);

    }

    public static String FindWinner(List<RaceResult> results){
        Map<Integer, Participant> participants = new HashMap<>();

        for (RaceResult result : results) {
            int participantID = result.getParticipantID();

            Participant participant = participants.getOrDefault(participantID, new Participant(participantID, result.getParticipantName()));
            participant.addRace(result);
            participants.put(participantID, participant);
        }

        List<Participant> sortedParticipants = new ArrayList<>(participants.values());
        sortedParticipants.sort(Comparator.comparingDouble(Participant::getAverageTime));

        String winnerName = sortedParticipants.get(0).getName();
        System.out.println("The winner is " + winnerName + " with an average time of " + sortedParticipants.get(0).getAverageTime());
        return winnerName;
    }

    public static void CheckParticipantNameConsistency(List<RaceResult> raceResults) {
        Map<Integer, String> participantNames = new HashMap<>();

        for (RaceResult result : raceResults) {
            int participantID = result.getParticipantID();
            String participantName = result.getParticipantName();

            if (participantNames.containsKey(participantID)) {
                String previousName = participantNames.get(participantID);
                if (!participantName.equals(previousName)) {
                    System.out.println("Inconsistent participant name for ID " + participantID + ": " + participantName + " vs " + previousName);
                }
            } else {
                participantNames.put(participantID, participantName);
            }
        }
    }
}
