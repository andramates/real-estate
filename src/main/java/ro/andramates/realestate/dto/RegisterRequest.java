package ro.andramates.realestate.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ro.andramates.realestate.domain.SellerType;
import ro.andramates.realestate.domain.UserRole;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank(message = "Username-ul este obligatoriu.")
    @Size(min = 3, max = 50, message = "Username-ul trebuie sa aiba intre 3 si 50 de caractere.")
    private String username;

    @NotBlank(message = "Parola este obligatorie.")
    @Size(min = 6, message = "Parola trebuie sa aiba minimum 6 caractere.")
    private String password;

    @NotBlank(message = "Email-ul este obligatoriu.")
    @Email(message = "Email invalid.")
    private String email;

    @NotBlank(message = "Telefonul este obligatoriu.")
    private String phone;

    @NotNull(message = "Rolul este obligatoriu.")
    private UserRole role;

    private SellerType sellerType;
}