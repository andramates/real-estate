package ro.andramates.realestate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ConversationItem {
    private Integer userId;
    private String username;
}