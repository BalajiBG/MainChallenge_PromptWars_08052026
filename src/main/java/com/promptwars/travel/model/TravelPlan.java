package com.promptwars.travel.model;

import com.google.cloud.spring.data.firestore.Document;
import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Persistence model for Firestore.
 * Using Lombok to eliminate boilerplate and improve readability.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
}
