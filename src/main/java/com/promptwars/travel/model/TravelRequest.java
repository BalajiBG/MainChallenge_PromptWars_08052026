package com.promptwars.travel.model;

import java.util.List;

public class TravelRequest {
    private String destination;
    private int durationDays;
    private String budget;
    private List<String> interests;
    private int travelers;

    // Getters and Setters
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(int durationDays) {
        this.durationDays = durationDays;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }

    public int getTravelers() {
        return travelers;
    }

    public void setTravelers(int travelers) {
        this.travelers = travelers;
    }
}
