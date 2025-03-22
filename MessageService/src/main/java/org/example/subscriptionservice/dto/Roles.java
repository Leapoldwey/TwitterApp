package org.example.subscriptionservice.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Roles {
    private UUID roleId;
    private String role;
    private String description;

    public static final String ADMIN = "ADMIN";
    public static final String PRODUCER = "PRODUCER";
    public static final String SUBSCRIBER = "SUBSCRIBER";
}
