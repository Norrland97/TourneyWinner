package DataTypes;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeOnlyDate {
    private int hours;
    private int minutes;
    private int seconds;
    private Date date;

    public TimeOnlyDate(int hours, int minutes, int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        this.date = new Date(0);
        this.date.setHours(hours);
        this.date.setMinutes(minutes);
        this.date.setSeconds(seconds);
    }

    public TimeOnlyDate(String time) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        this.date = format.parse(time);
        this.hours = this.date.getHours();
        this.minutes = this.date.getMinutes();
        this.seconds = this.date.getSeconds();
    }

    public int getHours() {
        return this.hours;
    }

    public int getMinutes() {
        return this.minutes;
    }

    public int getSeconds() {
        return this.seconds;
    }

    public void setHours(int hours) {
        this.hours = hours;
        this.date.setHours(hours);
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
        this.date.setMinutes(minutes);
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
        this.date.setSeconds(seconds);
    }

    public int getTimeDifference(TimeOnlyDate other) {
        long time1 = this.date.getTime();
        long time2 = other.date.getTime();
        long diff = Math.abs(time1 - time2) / 1000;
        return (int) diff;
    }

    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(this.date);
    }
}
