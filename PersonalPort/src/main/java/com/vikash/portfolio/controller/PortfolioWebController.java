package com.vikash.portfolio.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PortfolioWebController {

    private static final String RESUME_STATIC_PATH = "static/assets/Vikash-Pandey-Resume.pdf";
    private static final String RESUME_PUBLIC_URL = "/assets/Vikash-Pandey-Resume.pdf";

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        boolean resumeExists = checkResumeExists();
        model.addAttribute("resumeExists", resumeExists);
        model.addAttribute("resumeUrl", RESUME_PUBLIC_URL);
        return "index";
    }

    @GetMapping("/admin/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid administrator username or password.");
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", "You have been logged out securely.");
        }
        return "admin/login";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }

    private boolean checkResumeExists() {
        try {
            ClassPathResource resource = new ClassPathResource(RESUME_STATIC_PATH);
            return resource.exists() && resource.contentLength() > 0;
        } catch (Exception e) {
            return false;
        }
    }
}
