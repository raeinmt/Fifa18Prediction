package com.example.rteymouri.fifa18prediction.footballMatchDataModel;

/**
 * Created by rteymouri on 4/17/18.
 */

public class ResultsMatch {

    private String team1_name;
    private String team2_name;
    private Integer team1_score;
    private Integer team2_score;
    private String match_date_time;

    public ResultsMatch(){

    }

    public ResultsMatch(String team1_name, Integer team1_score,
                         String team2_name, Integer team2_score,
                         String match_date_time) {

        this.team1_name = team1_name;
        this.team2_name = team2_name;
        this.team1_score = team1_score;
        this.team2_score = team2_score;
        this.match_date_time = match_date_time;

    }

    // TODO: Add a compareTo method to return 0,1 or 3 after comparing results
    // TODO: Think about toString. Setters and getters (Remove if not needed)
    @Override
    public String toString() {
        return team1_name+team2_name;
    }

    // Getter
    public String getTeam1_name() {
        return team1_name;
    }

    public String getTeam2_name() {
        return team2_name;
    }

    public Integer getTeam1_score() {
        return team1_score;
    }

    public Integer getTeam2_score() {
        return team2_score;
    }

    public String getMatch_date_time() {
        return match_date_time;
    }

    //Setter
    public void setTeam1_name(String team1_name) {
        this.team1_name = team1_name;
    }

    public void setTeam2_name(String team2_name) {
        this.team2_name = team2_name;
    }

    public void setTeam1_score(Integer team1_score) {
        this.team1_score = team1_score;
    }

    public void setTeam2_score(Integer team2_score) {
        this.team2_score = team2_score;
    }

}
