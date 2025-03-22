package org.example.usermanagementservice.dal;

import java.time.Instant;
import java.util.*;

import org.example.usermanagementservice.dtos.Constants;
import org.example.usermanagementservice.dtos.LastSession;
import org.example.usermanagementservice.dtos.Role;
import org.example.usermanagementservice.dtos.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JdbcUmsRepository implements UmsRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public UUID updateUser(User user) {
        UUID userId = UUID.randomUUID();
        Map<String, Role> roles = this.findAllRoles();
        List<Object> users = jdbcTemplate.query(Constants.GET_USER_BY_ID_FULL,
                (rs, rowNum) -> new User(DaoHelper.bytesArraytoUUID(rs.getBytes("users.id")),
                        rs.getString("users.name"), rs.getString("users.email"),
                        rs.getString("users.password"), rs.getInt("users.created"),
                        Arrays.asList(new Role(DaoHelper.bytesArraytoUUID(rs.getBytes("roles.id")),
                                rs.getString("roles.name"), rs.getString("roles.description"))),
                        new LastSession(rs.getInt("last_visit.in"), rs.getInt("last_visit.out"))),
                user.getId().toString());

        if (users.isEmpty()) {
            throw new RuntimeException("Пользователь с ID " + user.getId() + " не найден");
        }

        User existingUser = (User) users.getFirst();

        existingUser.setName(user.getName() != null ? user.getName() : existingUser.getName());
        existingUser.setEmail(user.getEmail() != null ? user.getEmail() : existingUser.getEmail());
        existingUser.setPassword(user.getPassword() != null ? user.getPassword() : existingUser.getPassword());

        jdbcTemplate.update(Constants.DELETE_USER, existingUser.getId().toString());

        jdbcTemplate.update(Constants.CREATE_USER, userId.toString(), existingUser.getName(),
                    existingUser.getEmail(), existingUser.getPassword(), existingUser.getCreated(), null);

        for (Role role : user.getRoles() == null ? existingUser.getRoles() : user.getRoles()) {
            jdbcTemplate.update(Constants.ASSIGN_ROLE, userId.toString(),
                    roles.get(role.getRole()).getRoleId().toString());
        }

        return userId;
    }



    @Override
    public Map<UUID, User> findAllUsers() {
        Map<UUID, User> users = new HashMap<>();

        List<Object> oUsers = jdbcTemplate.query(Constants.GET_ALL_USERS,
                (rs, rowNum) -> new User(DaoHelper.bytesArraytoUUID(rs.getBytes("users.id")),
                        rs.getString("users.name"), rs.getString("users.email"),
                        rs.getString("users.password"), rs.getInt("users.created"),
                        Arrays.asList(new Role(DaoHelper.bytesArraytoUUID(rs.getBytes("roles.id")),
                                rs.getString("roles.name"), rs.getString("roles.description"))),
                        new LastSession(rs.getInt("last_visit.in"), rs.getInt("last_visit.out"))));

        for (Object oUser : oUsers) {
            if (!users.containsKey(((User) oUser).getId())) {
                User user = new User();
                user.setId(((User) oUser).getId());
                user.setName(((User) oUser).getName());
                user.setEmail(((User) oUser).getEmail());
                user.setPassword(((User) oUser).getPassword());
                user.setCreated(((User) oUser).getCreated());
                user.setLastSession(((User) oUser).getLastSession());
                users.put(((User) oUser).getId(), user);
            }
            users.get(((User) oUser).getId()).addRole(((User) oUser).getRoles().get(0));
        }
        return users;
    }

    @Override
    public User findUserById(UUID userId) {
        User user = new User();
        List<Object> users = jdbcTemplate.query(Constants.GET_USER_BY_ID_FULL,
                (rs, rowNum) -> new User(DaoHelper.bytesArraytoUUID(rs.getBytes("users.id")),
                        rs.getString("users.name"), rs.getString("users.email"),
                        rs.getString("users.password"), rs.getInt("users.created"),
                        Arrays.asList(new Role(DaoHelper.bytesArraytoUUID(rs.getBytes("roles.id")),
                                rs.getString("roles.name"), rs.getString("roles.description"))),
                        new LastSession(rs.getInt("last_visit.in"), rs.getInt("last_visit.out"))),
                userId.toString());
        for (Object oUser : users) {
            if (user.getId() == null) {
                user.setId(((User) oUser).getId());
                user.setName(((User) oUser).getName());
                user.setEmail(((User) oUser).getEmail());
                user.setPassword(((User) oUser).getPassword());
                user.setCreated(((User) oUser).getCreated());
                user.setLastSession(((User) oUser).getLastSession());
            }
            user.addRole(((User) oUser).getRoles().get(0));
        }
        return user;
    }

    @Override
    public UUID createUser(User user) {
        long timestamp = Instant.now().getEpochSecond();
        Map<String, Role> roles = this.findAllRoles();
        UUID userId = UUID.randomUUID();

        try {
            jdbcTemplate.update(Constants.CREATE_USER, userId.toString(), user.getName(), user.getEmail(),
                    user.getPassword(), timestamp, null);
            for (Role role : user.getRoles()) {
                jdbcTemplate.update(Constants.ASSIGN_ROLE, userId.toString(),
                        roles.get(role.getRole()).getRoleId().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return userId;
    }

    @Override
    public int deleteUser(UUID userId) {
        return jdbcTemplate.update(Constants.DELETE_USER, userId.toString());
    }

    @Override
    public Map<String, Role> findAllRoles() {
        Map<String, Role> roles = new HashMap<>();
        jdbcTemplate.query(Constants.GET_ALL_ROLES, rs -> {
            Role role = new Role(DaoHelper.bytesArraytoUUID(rs.getBytes("roles.id")), rs.getString("roles.name"),
                    rs.getString("roles.description"));
            roles.put(rs.getString("roles.name"), role);
        });
        return roles;
    }
}
