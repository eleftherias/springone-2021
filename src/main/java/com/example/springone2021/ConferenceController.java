package com.example.springone2021;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConferenceController {

    @GetMapping("/about")
    public String getAbout() {
        return "Join us online September 1–2!";
    }

    @GetMapping("/greeting")
    public String greeting(@AuthenticationPrincipal(expression = "username") String username) {
        return "Hello, " + username + "!";
    }
}
