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
public class User {
    private UUID id;
    private String name;
    private String email;
    private String password;
    private int created;
    private List<Roles> roles = new ArrayList<>();
    private LastSession lastSession;

    public void addRole(Roles role) {
        this.roles.add(role);
    }

    public boolean hasRole(String s) {
        for (Roles role : roles) {
            if(role.getRole().equalsIgnoreCase(s)) return true;
        }
        return false;
    }
}
