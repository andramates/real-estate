package ro.andramates.realestate.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageRequest {

    @NotNull(message = "Receiver-ul este obligatoriu.")
    private Integer receiverId;

    @NotBlank(message = "Mesajul nu poate fi gol.")
    private String content;
}