package ro.andramates.realestate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ro.andramates.realestate.domain.Favorite;
import ro.andramates.realestate.domain.Property;
import ro.andramates.realestate.domain.User;
import ro.andramates.realestate.service.FavoriteService;
import ro.andramates.realestate.service.MessageService;
import ro.andramates.realestate.service.PropertyService;
import ro.andramates.realestate.service.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BuyerController {

    private final UserService userService;
    private final FavoriteService favoriteService;
    private final MessageService messageService;
    private final PropertyService propertyService;

    @GetMapping("/buyer")
    public String buyerDashboard(Authentication authentication, Model model) {
        User buyer = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Favorite> favorites = favoriteService.findByBuyer(buyer);
        List<Property> approvedProperties = propertyService.findApprovedProperties();

        model.addAttribute("buyer", buyer);
        model.addAttribute("favoritesCount", favorites.size());
        model.addAttribute("messagesCount", messageService.findInbox(buyer).size());
        model.addAttribute("approvedPropertiesCount", approvedProperties.size());

        return "buyer/dashboard";
    }
}