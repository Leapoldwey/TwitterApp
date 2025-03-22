package org.example.usermanagementservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.usermanagementservice.dal.UmsRepository;
import org.example.usermanagementservice.dtos.Constants;
import org.example.usermanagementservice.dtos.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UmsRepository umsRepository;
    Map<String, Object> response = new HashMap<>();

    @PostMapping
    public Mono<ResponseEntity<Map<String, Object>>> create(@RequestBody User user) {
        UUID creating = umsRepository.createUser(user);

        if (creating != null) {
            response.put(Constants.CODE, "201");
            response.put(Constants.MESSAGE, "User Created");
            response.put(Constants.DATA, creating);
        } else {
            response.put(Constants.CODE, "500");
            response.put(Constants.MESSAGE, "User Creation Failed");
            response.put(Constants.DATA, new HashMap<>());
        }

        return Mono.just(ResponseEntity.status(HttpStatus.CREATED)
                .header(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON)
                .header(Constants.ACCEPT, Constants.APPLICATION_JSON).body(response));
    }

    @PutMapping
    public Mono<ResponseEntity<Map<String, Object>>> update(@RequestBody User user) {
        UUID id = umsRepository.updateUser(user);

        if (id != null) {
            response.put(Constants.CODE, "200");
            response.put(Constants.MESSAGE, "User Updated");
            response.put(Constants.DATA, id);
        } else {
            response.put(Constants.CODE, "500");
            response.put(Constants.MESSAGE, "User Update Failed");
            response.put(Constants.DATA, new HashMap<>());
        }

        return Mono.just(ResponseEntity.status(HttpStatus.OK)
                .header(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON)
                .header(Constants.ACCEPT, Constants.APPLICATION_JSON).body(response));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Map<String, Object>>> findUserById(@PathVariable UUID id) {
        User user = umsRepository.findUserById(id);

        if (user != null) {
            response.put(Constants.CODE, "200");
            response.put(Constants.MESSAGE, "User Found");
            response.put(Constants.DATA, user);
        } else {
            response.put(Constants.CODE, "404");
            response.put(Constants.MESSAGE, "User Not Found");
            response.put(Constants.DATA, new HashMap<>());
        }

        return Mono.just(ResponseEntity.status(HttpStatus.CREATED)
                .header(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON)
                .header(Constants.ACCEPT, Constants.APPLICATION_JSON).body(response));
    }

    @GetMapping("")
    public Mono<ResponseEntity<Map<String, Object>>> findAllUsers() {
        Map<UUID, User> users = umsRepository.findAllUsers();
        if (users == null) {
            response.put(Constants.CODE, "500");
            response.put(Constants.MESSAGE, "Users have not been retrieved");
            response.put(Constants.DATA, new HashMap<>());
        } else {
            response.put(Constants.CODE, "200");
            response.put(Constants.MESSAGE, "List of Users has been requested successfully");
            response.put(Constants.DATA, new ArrayList<>(users.values()));
        }
        return Mono.just(ResponseEntity.ok().header(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON)
                .header(Constants.ACCEPT, Constants.APPLICATION_JSON).body(response));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Map<String, Object>>> delete(@PathVariable UUID id) {
        int result = umsRepository.deleteUser(id);

        if (result > 0) {
            response.put(Constants.CODE, "200");
            response.put(Constants.MESSAGE, "User Deleted");
            response.put(Constants.DATA, id);
        } else {
            response.put(Constants.CODE, "500");
            response.put(Constants.MESSAGE, "User Deletion Failed");
            response.put(Constants.DATA, id);
        }

        return Mono.just(ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }
}
