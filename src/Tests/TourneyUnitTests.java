package Tests;

import DataTypes.TimeOnlyDate;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;
import DataTypes.RaceResult;
import DataTypes.Participant;
import FileParser.RaceResultsFileParser;
import main.TourneyWinner;
public class TourneyUnitTests {

    @Test
    public void testFindWinner() {
        List<RaceResult> results = new ArrayList<>();
        results.add(ResultFromLine("Harrison Ford,3,16:42:03,16:49:23,eggRace"));
        results.add(ResultFromLine("Eve,1,14:47:37,14:50:58,1000m"));
        results.add(ResultFromLine("Bob,1772,13:50:20,13:51:13,eggRace"));
        results.add(ResultFromLine("Bob,1772,13:50:20,13:51:13,1000m"));
        results.add(ResultFromLine("Bob,1772,13:50:20,13:51:13,sackRace"));
        results.add(ResultFromLine("Carr1e Underwood,108522,14:19:17,14:26:09,1000m"));

        String expectedWinner = "Bob";
        String actualWinner = TourneyWinner.FindWinner(results);

        assertEquals(expectedWinner, actualWinner);
    }

    @Test
    public void testCheckParticipantNameConsistency() {
        List<RaceResult> results = new ArrayList<>();
        results.add(ResultFromLine("Harrison Ford,3,16:42:03,16:49:23,eggRace"));
        results.add(ResultFromLine("Eve,1,14:47:37,14:50:58,1000m"));
        results.add(ResultFromLine("Alice,1,09:30:00,10:00:00,1000m"));

        String expectedOutput = "Inconsistent participant name for ID 1: Alice vs Eve\r\n";
        String actualOutput = runAndGetOutput(() -> TourneyWinner.CheckParticipantNameConsistency(results));

        assertEquals(expectedOutput, actualOutput);
    }

    public static String runAndGetOutput(Runnable runnable) {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        runnable.run();

        System.setOut(originalOut);

        return outContent.toString();
    }
    private RaceResult ResultFromLine(String line) {
        String[] segments = line.split(",");
        String name = segments[0];
        int id = Integer.parseInt(segments[1]);
        TimeOnlyDate start;
        TimeOnlyDate end;
        try {
            start = new TimeOnlyDate(segments[2]);
            end = new TimeOnlyDate(segments[3]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String type = segments[4];

        return new RaceResult(name, id, start, end, type);
    }
}
