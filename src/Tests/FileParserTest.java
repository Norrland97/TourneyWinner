package Tests;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import DataTypes.RaceResult;
import FileParser.RaceResultsFileParser;
import org.junit.jupiter.api.Test;

import java.util.List;

public class FileParserTest {

    @Test
    public void testParseFile() {
        String filePath = "src/Tests/test.txt";
        RaceResultsFileParser parser = new RaceResultsFileParser();
        List<RaceResult> results = parser.ParseFile(filePath);
        assertEquals(36, results.size());
    }

    @Test
    public void testParseFileThrowsExceptionWhenFileNotFound() {
        String filePath = "./non-existing-file.txt";
        RaceResultsFileParser parser = new RaceResultsFileParser();
        assertThrows(RuntimeException.class, () -> parser.ParseFile(filePath));
    }

    @Test
    public void testParseFileThrowsExceptionWhenInvalidTimeFormat() {
        String filePath = "./invalid-time-format.txt";
        RaceResultsFileParser parser = new RaceResultsFileParser();
        assertThrows(RuntimeException.class, () -> parser.ParseFile(filePath));
    }

    // Add more test methods to cover other scenarios
}