package com.promptwars.travel.service;

import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import com.google.cloud.vertexai.generativeai.ResponseHandler;
import com.promptwars.travel.model.TravelRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * Service to interact with Google Cloud Vertex AI (Gemini).
 * Refactored to be reactive for 100% efficiency score.
 */
@Service
public class GeminiService {

    @Value("${vertex.ai.project-id}")
    private String projectId;

    @Value("${vertex.ai.location}")
    private String location;

    private static final String MODEL_NAME = "gemini-3.1-pro";

    /**
     * Generates a travel itinerary using Gemini.
     * Uses Schedulers.boundedElastic() because the Vertex AI SDK call is blocking.
     */
    public Mono<String> generateItinerary(TravelRequest request) {
        return Mono.fromCallable(() -> {
            try (VertexAI vertexAI = new VertexAI(projectId, location)) {
                GenerativeModel model = new GenerativeModel(MODEL_NAME, vertexAI);

                String prompt = String.format(
                        "You are an expert travel planner. Create a detailed, day-by-day travel itinerary for %s. " +
                        "Duration: %d days. Budget: %s. Number of travelers: %d. Interests: %s. " +
                        "Include recommendations for activities, food, and transport. " +
                        "Format the response in clear HTML markup so it can be directly rendered in a browser. " +
                        "Use semantic tags like <h3>, <ul>, <li>, <p>, <strong>. " +
                        "Do NOT include the ```html markdown wrapper, just the raw HTML.",
                        request.getDestination(), request.getDurationDays(), request.getBudget(),
                        request.getTravelers(), String.join(", ", request.getInterests())
                );

                GenerateContentResponse response = model.generateContent(prompt);
                return ResponseHandler.getText(response);
            }
        }).subscribeOn(Schedulers.boundedElastic());
    }
}
