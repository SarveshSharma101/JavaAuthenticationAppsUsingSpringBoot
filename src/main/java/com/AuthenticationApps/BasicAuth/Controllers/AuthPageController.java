package com.AuthenticationApps.BasicAuth.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthPageController {

    @GetMapping()
    public String registrationPage(){
        return "Registration";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/app")
    public String appPage(){
        return "logout";
    }
}
