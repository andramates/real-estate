package ro.andramates.realestate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ro.andramates.realestate.domain.Property;
import ro.andramates.realestate.domain.PropertyStatus;
import ro.andramates.realestate.domain.User;
import ro.andramates.realestate.service.PropertyService;
import ro.andramates.realestate.service.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SellerController {

    private final UserService userService;
    private final PropertyService propertyService;

    @GetMapping("/seller")
    public String sellerDashboard(Authentication authentication, Model model) {
        User seller = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Property> properties = propertyService.findBySeller(seller);

        long pendingCount = properties.stream().filter(p -> p.getStatus() == PropertyStatus.PENDING).count();
        long approvedCount = properties.stream().filter(p -> p.getStatus() == PropertyStatus.APPROVED).count();
        long rejectedCount = properties.stream().filter(p -> p.getStatus() == PropertyStatus.REJECTED).count();

        model.addAttribute("seller", seller);
        model.addAttribute("properties", properties);
        model.addAttribute("pendingCount", pendingCount);
        model.addAttribute("approvedCount", approvedCount);
        model.addAttribute("rejectedCount", rejectedCount);

        return "seller/dashboard";
    }
}