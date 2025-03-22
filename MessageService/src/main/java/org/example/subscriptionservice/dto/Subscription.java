package org.example.subscriptionservice.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Subscription {
    private UUID subscriber;
    private List<UUID> producers = new ArrayList<>();

    public void addProducer(UUID producerId) {
        this.producers.add(producerId);
    }
}
