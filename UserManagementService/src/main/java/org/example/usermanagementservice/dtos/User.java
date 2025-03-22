package org.example.usermanagementservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private UUID id;
    private String name;
    private String email;
    private String password;
    private int created;
    private List<Role> roles = new ArrayList<>();
    private LastSession lastSession;

    public void addRole(Role role) {
        this.roles.add(role);
    }
}
