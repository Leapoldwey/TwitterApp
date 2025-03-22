package org.example.subscriptionservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.subscriptionservice.dto.Constants;
import org.example.subscriptionservice.dto.Message;
import org.example.subscriptionservice.service.MessagesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MessageController {
    private final MessagesService messages;

    @RequestMapping(method = RequestMethod.GET, path = Constants.URI_MESSAGE + "/{message-id}")
    public Mono<ResponseEntity<Map<String, Object>>> getMessagebyId(
            @PathVariable(value = "message-id", required = true) String messageId) {
        return messages.getMessagebyId(UUID.fromString(messageId));
    }

    @RequestMapping(method = RequestMethod.GET, path = Constants.URI_PRODUCER + "/{producer-id}")
    public Mono<ResponseEntity<Map<String, Object>>> getMessagesForProducerById(
            @PathVariable(value = "producer-id", required = true) String producerId) {
        return messages.getMessagesForProducerById(UUID.fromString(producerId));
    }

    @RequestMapping(method = RequestMethod.GET, path = Constants.URI_SUBSCRIBER + "/{subscriber-id}")
    public Mono<ResponseEntity<Map<String, Object>>> getMessagesForSubscriberById(
            @PathVariable(value = "subscriber-id", required = true) String subscriberId) {
        return messages.getMessagesForSubscriberById(UUID.fromString(subscriberId));
    }

    @RequestMapping(method = RequestMethod.POST, path = Constants.URI_MESSAGE, consumes = Constants.APPLICATION_JSON)
    public Mono<ResponseEntity<Map<String, Object>>> createMessage(@RequestBody Message message) {
        return messages.createMessage(message);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = Constants.URI_MESSAGE + "/{message-id}")
    public Mono<ResponseEntity<Map<String, Object>>> deleteMessageById(
            @PathVariable(value = "message-id", required = true) String messageId) {
        return messages.deleteMessageById(UUID.fromString(messageId));
    }

}
