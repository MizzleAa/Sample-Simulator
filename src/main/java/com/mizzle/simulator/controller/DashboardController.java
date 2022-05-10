package com.mizzle.simulator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    
    @GetMapping(value="/register")
    public String register() {
        return "dashboard/register";
    }

    @GetMapping(value="/view/{id}")
    public String viewer(@PathVariable long id, Model model) {
        model.addAttribute("id", id);
        return "dashboard/view";
    }

}
