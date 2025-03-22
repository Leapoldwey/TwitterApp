package org.example.usermanagementservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.usermanagementservice.dal.UmsRepository;
import org.example.usermanagementservice.dtos.Constants;
import org.example.usermanagementservice.dtos.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
public class RoleController {
    private final UmsRepository umsRepository;
    Map<String, Object> response = new HashMap<>();

    @GetMapping()
    public Mono<ResponseEntity<Map<String, Object>>> findAllRoles() {
        Map<String, Role> roles = umsRepository.findAllRoles();

        if (roles.isEmpty()) {
            response.put(Constants.CODE, "500");
            response.put(Constants.MESSAGE, "No roles found");
            response.put(Constants.DATA, roles);
        } else {
            response.put(Constants.CODE, "200");
            response.put(Constants.MESSAGE, "List of Roles has been requested successfully");
            response.put(Constants.DATA, new ArrayList<>(roles.values()));
        }

        return Mono.just(ResponseEntity.ok()
                .header(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON)
                .header(Constants.ACCEPT, Constants.APPLICATION_JSON)
                .body(response));
    }
}
