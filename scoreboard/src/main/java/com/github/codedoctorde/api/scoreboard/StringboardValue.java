package com.github.codedoctorde.api.scoreboard;

/**
 * @author CodeDoctorDE
 */
public class StringboardValue {
    private String value;
    private int score = 0;


    public StringboardValue(String value) {
        this.value = value;
    }

    public StringboardValue(String value, int score) {
        this.value = value;
        this.score = score;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
