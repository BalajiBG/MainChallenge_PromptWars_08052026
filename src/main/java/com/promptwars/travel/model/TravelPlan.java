package com.promptwars.travel.model;

import com.google.cloud.spring.data.firestore.Document;
import com.google.cloud.firestore.annotation.DocumentId;

import java.util.List;

@Document(collectionName = "travel_plans")
public class TravelPlan {

    @DocumentId
    private String id;
    private String destination;
    private int durationDays;
    private String budget;
    private List<String> interests;
    private int travelers;
    private String itinerary;
    private String username;

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getItinerary() {
        return itinerary;
    }

    public void setItinerary(String itinerary) {
        this.itinerary = itinerary;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
