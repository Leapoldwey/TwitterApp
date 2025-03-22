package org.example.subscriptionservice.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private UUID id;
    private UUID author;
    private String content;
    private long timestamp;
}
