package com.promptwars.travel.repository;

import com.promptwars.travel.model.TravelPlan;
import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TravelPlanRepository extends FirestoreReactiveRepository<TravelPlan> {
    Flux<TravelPlan> findByUsername(String username);
}
