package org.example.usermanagementservice.dal;

import org.example.usermanagementservice.dtos.LastSession;
import org.example.usermanagementservice.dtos.Role;
import org.example.usermanagementservice.dtos.User;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface UmsRepository {
    Map<UUID, User> findAllUsers();
    Map<String, Role> findAllRoles();
    User findUserById(UUID userId);
    UUID createUser(User user);
    UUID updateUser(User user);
    int deleteUser(UUID userId);
}
