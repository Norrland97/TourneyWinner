package DataTypes;

import java.util.ArrayList;
import java.util.List;

public class Participant {
    private int id;
    private String name;
    private List<RaceResult> races;

    public Participant(int id, String name) {
        this.id = id;
        this.name = name;
        this.races = new ArrayList<>();
    }

    public void addRace(RaceResult result) {
        races.add(result);
    }

    public double getAverageTime() {
        double total = 0;
        int count = 0;

        for (RaceResult race : races) {
            total += race.getDuration();
            count++;
        }

        return total / count;
    }

    public String getName() {
        return name;
    }

}
