package ro.andramates.realestate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.andramates.realestate.domain.Favorite;
import ro.andramates.realestate.domain.Property;
import ro.andramates.realestate.domain.User;
import ro.andramates.realestate.service.FavoriteService;
import ro.andramates.realestate.service.PropertyService;
import ro.andramates.realestate.service.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/buyer/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final UserService userService;
    private final PropertyService propertyService;

    @GetMapping
    public String favoritesPage(Authentication authentication, Model model) {
        User buyer = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Favorite> favorites = favoriteService.findByBuyer(buyer);
        model.addAttribute("favorites", favorites);
        return "property/favorites";
    }

    @PostMapping("/{propertyId}")
    public String addToFavorites(@PathVariable Integer propertyId, Authentication authentication) {
        User buyer = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Property property = propertyService.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        favoriteService.addToFavorites(buyer, property);
        return "redirect:/buyer/favorites";
    }

    @PostMapping("/remove/{propertyId}")
    public String removeFromFavorites(@PathVariable Integer propertyId, Authentication authentication) {
        User buyer = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Property property = propertyService.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        favoriteService.removeFromFavorites(buyer, property);
        return "redirect:/buyer/favorites";
    }
}