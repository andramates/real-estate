package ro.andramates.realestate.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PropertyRequest {

    @NotBlank(message = "Titlul este obligatoriu.")
    private String title;

    @NotBlank(message = "Descrierea este obligatorie.")
    private String description;

    @NotNull(message = "Pretul este obligatoriu.")
    @DecimalMin(value = "0.01", message = "Pretul trebuie sa fie mai mare decat 0.")
    private BigDecimal price;

    @NotBlank(message = "Locatia este obligatorie.")
    private String location;
}