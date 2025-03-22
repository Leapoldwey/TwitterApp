package org.example.subscriptionservice.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LastSession {
    int lastLoginTimeStamp;
    int lastLogoutTimeStamp;
}
