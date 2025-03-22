package org.example.subscriptionservice.dao;

import org.example.subscriptionservice.dto.Message;

import java.util.List;
import java.util.UUID;

public interface MessageRepository {
    public Message getMessagebyId(UUID messageId);
    public List<Message> getMessagesForProducerById(UUID producerId);
    public List<Message> getMessagesForSubscriberById(UUID subscriberId);
    public UUID createMessage(Message message);
    public int deleteMessageById(UUID messageId);
}
