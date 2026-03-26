package ro.andramates.realestate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ro.andramates.realestate.domain.Message;
import ro.andramates.realestate.domain.User;
import ro.andramates.realestate.dto.MessageRequest;
import ro.andramates.realestate.service.MessageService;
import ro.andramates.realestate.service.UserService;

import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;

    @GetMapping({"/buyer/messages", "/seller/messages"})
    public String inbox(Authentication authentication, Model model) {
        User currentUser = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String sendAction = currentUser.getRole().name().equals("BUYER")
                ? "/buyer/messages/send"
                : "/seller/messages/send";

        model.addAttribute("conversationList", messageService.findConversationList(currentUser));
        model.addAttribute("messages", Collections.emptyList());
        model.addAttribute("messageRequest", new MessageRequest());
        model.addAttribute("sendAction", sendAction);
        model.addAttribute("otherUser", null);

        return "message/conversations";
    }

    @GetMapping({"/buyer/messages/{userId}", "/seller/messages/{userId}"})
    public String conversation(@PathVariable Integer userId, Authentication authentication, Model model) {
        User currentUser = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        User otherUser = userService.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Message> conversation = messageService.findConversation(currentUser.getId(), otherUser.getId());

        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setReceiverId(otherUser.getId());

        String sendAction = currentUser.getRole().name().equals("BUYER")
                ? "/buyer/messages/send"
                : "/seller/messages/send";

        model.addAttribute("conversationList", messageService.findConversationList(currentUser));
        model.addAttribute("messages", conversation);
        model.addAttribute("otherUser", otherUser);
        model.addAttribute("messageRequest", messageRequest);
        model.addAttribute("sendAction", sendAction);

        return "message/conversations";
    }

    @PostMapping({"/buyer/messages/send", "/seller/messages/send"})
    public String sendMessage(
            @Valid @ModelAttribute("messageRequest") MessageRequest messageRequest,
            BindingResult bindingResult,
            Authentication authentication,
            Model model
    ) {
        User sender = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        User receiver = userService.findById(messageRequest.getReceiverId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        String sendAction = sender.getRole().name().equals("BUYER")
                ? "/buyer/messages/send"
                : "/seller/messages/send";

        if (bindingResult.hasErrors()) {
            model.addAttribute("conversationList", messageService.findConversationList(sender));
            model.addAttribute("messages", messageService.findConversation(sender.getId(), receiver.getId()));
            model.addAttribute("otherUser", receiver);
            model.addAttribute("sendAction", sendAction);
            return "message/conversations";
        }

        Message message = Message.builder()
                .sender(sender)
                .receiver(receiver)
                .content(messageRequest.getContent())
                .build();

        messageService.save(message);

        if (sender.getRole().name().equals("BUYER")) {
            return "redirect:/buyer/messages/" + receiver.getId();
        }

        return "redirect:/seller/messages/" + receiver.getId();
    }
}