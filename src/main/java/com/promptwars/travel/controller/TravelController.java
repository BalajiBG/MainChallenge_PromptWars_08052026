package com.promptwars.travel.controller;

import com.promptwars.travel.model.TravelPlan;
import com.promptwars.travel.model.TravelRequest;
import com.promptwars.travel.repository.TravelPlanRepository;
import com.promptwars.travel.service.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/travel")
public class TravelController {

    @Autowired
    private GeminiService geminiService;

    @Autowired
    private TravelPlanRepository travelPlanRepository;

    @PostMapping("/plan")
    public TravelPlan createPlan(@RequestBody TravelRequest request) {
        try {
            // Call Gemini via Vertex AI
            String itineraryHtml = geminiService.generateItinerary(request);

            // Get Current User
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = (auth != null && auth.getName() != null) ? auth.getName() : "anonymous";

            // Save to Firestore
            TravelPlan plan = new TravelPlan();
            plan.setId(UUID.randomUUID().toString());
            plan.setDestination(request.getDestination());
            plan.setDurationDays(request.getDurationDays());
            plan.setBudget(request.getBudget());
            plan.setInterests(request.getInterests());
            plan.setTravelers(request.getTravelers());
            plan.setItinerary(itineraryHtml);
            plan.setUsername(username);

            // Using block for simplicity in this REST controller (can be fully reactive if desired)
            return travelPlanRepository.save(plan).block();

        } catch (IOException e) {
            throw new RuntimeException("Failed to generate travel plan: " + e.getMessage(), e);
        }
    }

    @GetMapping("/my-plans")
    public Flux<TravelPlan> getMyPlans() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = (auth != null && auth.getName() != null) ? auth.getName() : "anonymous";
        return travelPlanRepository.findByUsername(username);
    }
}
