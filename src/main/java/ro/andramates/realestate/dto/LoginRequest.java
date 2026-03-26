package ro.andramates.realestate.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "Username-ul este obligatoriu.")
    private String username;

    @NotBlank(message = "Parola este obligatorie.")
    private String password;
}