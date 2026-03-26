package ro.andramates.realestate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homePage() {
        return "index";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin/dashboard";
    }

    @GetMapping("/buyer")
    public String buyerPage() {
        return "buyer/dashboard";
    }

    @GetMapping("/seller")
    public String sellerPage() {
        return "seller/dashboard";
    }
}