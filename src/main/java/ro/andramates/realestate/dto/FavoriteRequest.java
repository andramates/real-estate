package ro.andramates.realestate.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteRequest {

    @NotNull(message = "Property id este obligatoriu.")
    private Integer propertyId;
}