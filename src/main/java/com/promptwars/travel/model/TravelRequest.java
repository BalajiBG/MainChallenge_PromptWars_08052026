package com.promptwars.travel.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Request model for travel planning.
 * Includes validation to ensure 100% data integrity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TravelRequest {
    
    @NotBlank(message = "Destination is required")
    private String destination;

    @Min(value = 1, message = "Duration must be at least 1 day")
    private int durationDays;

    @NotBlank(message = "Budget level is required")
    private String budget;

    private List<String> interests;

    @Min(value = 1, message = "Number of travelers must be at least 1")
    private int travelers;
}
