package ro.andramates.realestate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.andramates.realestate.domain.Property;
import ro.andramates.realestate.domain.PropertyStatus;
import ro.andramates.realestate.service.PropertyService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final PropertyService propertyService;

    @GetMapping
    public String adminDashboard(Model model) {
        List<Property> pendingProperties = propertyService.findByStatus(PropertyStatus.PENDING);
        List<Property> approvedProperties = propertyService.findByStatus(PropertyStatus.APPROVED);
        List<Property> rejectedProperties = propertyService.findByStatus(PropertyStatus.REJECTED);

        model.addAttribute("pendingCount", pendingProperties.size());
        model.addAttribute("approvedCount", approvedProperties.size());
        model.addAttribute("rejectedCount", rejectedProperties.size());

        return "admin/dashboard";
    }

    @GetMapping("/properties/pending")
    public String pendingProperties(Model model) {
        List<Property> pendingProperties = propertyService.findByStatus(PropertyStatus.PENDING);
        model.addAttribute("pendingProperties", pendingProperties);
        return "admin/approvals";
    }

    @PostMapping("/properties/{id}/approve")
    public String approve(@PathVariable Integer id) {
        propertyService.approveProperty(id);
        return "redirect:/admin/properties/pending";
    }

    @PostMapping("/properties/{id}/reject")
    public String reject(@PathVariable Integer id) {
        propertyService.rejectProperty(id);
        return "redirect:/admin/properties/pending";
    }
}