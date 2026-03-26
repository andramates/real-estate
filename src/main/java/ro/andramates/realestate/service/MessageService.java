package ro.andramates.realestate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.andramates.realestate.domain.Message;
import ro.andramates.realestate.domain.User;
import ro.andramates.realestate.repository.MessageRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public List<Message> findAllMessages() {
        return messageRepository.findAll();
    }

    public Optional<Message> findById(Integer id) {
        return messageRepository.findById(id);
    }

    public List<Message> findBySender(User sender) {
        return messageRepository.findBySender(sender);
    }

    public List<Message> findByReceiver(User receiver) {
        return messageRepository.findByReceiver(receiver);
    }

    public List<Message> findBySenderAndReceiver(User sender, User receiver) {
        return messageRepository.findBySenderAndReceiver(sender, receiver);
    }

    public List<Message> findConversation(Integer userId1, Integer userId2) {
        return messageRepository.findConversation(userId1, userId2);
    }

    public Message save(Message message) {
        return messageRepository.save(message);
    }

    public void deleteById(Integer id) {
        messageRepository.deleteById(id);
    }
}