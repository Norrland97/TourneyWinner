package FileParser;

import DataTypes.RaceResult;
import DataTypes.TimeOnlyDate;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RaceResultsFileParser {

    public List<RaceResult> ParseFile(String path){
        List<RaceResult> results = new ArrayList<>();
        List<String> lines = Readfile(path);

        int lineNr = 0;
        int skippedLines = 0;
        for (String line: lines) {
            lineNr++;
            String error;
            if((error = CheckLineFormat(line)).equals("")) {
                try {
                    results.add(ResultFromLine(line));
                } catch (RuntimeException e) {
                    skippedLines++;
                    System.out.println("Unable to format row: " + line + "\n Skipping row number: " + lineNr + ". And continuing.");
                }
            } else {
                skippedLines++;
                System.out.println("at row " + lineNr + ", " + error +  "skipping row. \n Skipping row number: " + lineNr + ". And continuing.");
            }
        }

        if (skippedLines != 0){
            System.out.println("There has been " + skippedLines + " skipped rows, please double check the results.");
        }

        return results;
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

    // improve by adding success/error object instead of string matching
    // add regex and ability to point to more specific error
    private String CheckLineFormat(String line) {
        String[] segments = line.split(",");

        if (segments.length < 5)
            return "Missing segment ";
        if (segments.length > 5)
            return "Too many segments ";

        // Name
        String error = "";
        if (segments[0].length()<1)
            error += "Participant has to have a name, ";
        Pattern p = Pattern.compile("^[a-zA-Z ]+$");
        if (!p.matcher(segments[0]).matches())
            error += "Name of participant has to be letters and spaces only, ";

        // ID
        if (!segments[1].matches("[0-9]+"))
            error += "Participant id has to be numbers only, ";

        // Times
        error += IsTimeOk(segments[2], "Start");
        error += IsTimeOk(segments[3], "Finish");

        // RaceType
        List<String> raceTypes = new ArrayList<>();
        raceTypes.add("1000m");
        raceTypes.add("eggRace");
        raceTypes.add("sackRace");
        if (!raceTypes.contains(segments[4]))
            error += "Invalid race type, ";

        return error;
    }

    private String IsTimeOk(String segment, String type) {
        String regex = "^([0-1][0-9]|[2][0-3]):([0-5][0-9]):([0-5][0-9])$";

        if (!segment.matches(regex))
            return type + " time does not match the format HH:mm:ss, ";

        return "";
    }

    private List<String> Readfile(String path){

        List<String> contents = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            while((line = reader.readLine()) != null){
                contents.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("No file found on path: " + path);
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return contents;
    }

}
