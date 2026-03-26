package ro.andramates.realestate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ro.andramates.realestate.domain.Property;
import ro.andramates.realestate.domain.User;
import ro.andramates.realestate.dto.PropertyRequest;
import ro.andramates.realestate.service.PropertyService;
import ro.andramates.realestate.service.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;
    private final UserService userService;

    @GetMapping("/properties")
    public String listProperties(@RequestParam(required = false) String location, Model model) {
        List<Property> properties;

        if (location != null && !location.isBlank()) {
            properties = propertyService.findApprovedByLocation(location);
        } else {
            properties = propertyService.findApprovedProperties();
        }

        model.addAttribute("properties", properties);
        model.addAttribute("location", location);
        return "property/list";
    }

    @GetMapping("/properties/{id}")
    public String propertyDetails(@PathVariable Integer id, Model model) {
        Property property = propertyService.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        model.addAttribute("property", property);
        return "property/details";
    }

    @GetMapping("/seller/properties")
    public String myProperties(Authentication authentication, Model model) {
        User seller = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        model.addAttribute("properties", propertyService.findBySeller(seller));
        return "property/my-properties";
    }

    @GetMapping("/seller/properties/add")
    public String addPropertyPage(Model model) {
        model.addAttribute("propertyRequest", new PropertyRequest());
        return "property/add";
    }

    @PostMapping("/seller/properties/add")
    public String addProperty(
            @Valid @ModelAttribute("propertyRequest") PropertyRequest propertyRequest,
            BindingResult bindingResult,
            Authentication authentication,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "property/add";
        }

        User seller = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Property property = Property.builder()
                .title(propertyRequest.getTitle())
                .description(propertyRequest.getDescription())
                .price(propertyRequest.getPrice())
                .location(propertyRequest.getLocation())
                .seller(seller)
                .build();

        propertyService.addProperty(property);
        return "redirect:/seller/properties";
    }

    @GetMapping("/seller/properties/edit/{id}")
    public String editPropertyPage(@PathVariable Integer id, Authentication authentication, Model model) {
        Property property = propertyService.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        if (!property.getSeller().getUsername().equals(authentication.getName())) {
            throw new RuntimeException("Nu ai acces la aceasta proprietate.");
        }

        PropertyRequest propertyRequest = new PropertyRequest();
        propertyRequest.setTitle(property.getTitle());
        propertyRequest.setDescription(property.getDescription());
        propertyRequest.setPrice(property.getPrice());
        propertyRequest.setLocation(property.getLocation());

        model.addAttribute("propertyId", property.getId());
        model.addAttribute("propertyRequest", propertyRequest);
        return "property/edit";
    }

    @PostMapping("/seller/properties/edit/{id}")
    public String editProperty(
            @PathVariable Integer id,
            @Valid @ModelAttribute("propertyRequest") PropertyRequest propertyRequest,
            BindingResult bindingResult,
            Authentication authentication,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("propertyId", id);
            return "property/edit";
        }

        Property property = propertyService.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        if (!property.getSeller().getUsername().equals(authentication.getName())) {
            throw new RuntimeException("Nu ai acces la aceasta proprietate.");
        }

        property.setTitle(propertyRequest.getTitle());
        property.setDescription(propertyRequest.getDescription());
        property.setPrice(propertyRequest.getPrice());
        property.setLocation(propertyRequest.getLocation());

        propertyService.save(property);
        return "redirect:/seller/properties";
    }

    @PostMapping("/seller/properties/delete/{id}")
    public String deleteProperty(@PathVariable Integer id, Authentication authentication) {
        Property property = propertyService.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        if (!property.getSeller().getUsername().equals(authentication.getName())) {
            throw new RuntimeException("Nu ai acces la aceasta proprietate.");
        }

        propertyService.deleteById(id);
        return "redirect:/seller/properties";
    }
}