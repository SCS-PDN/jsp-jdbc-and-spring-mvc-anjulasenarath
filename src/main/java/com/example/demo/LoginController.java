package com.example.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @GetMapping("/login")
    public String showLoginPage() {
        return "login";  
    }


    @PostMapping("/login")
    public String handleLogin(@RequestParam("email") String email,
                              @RequestParam("password") String password,
                              Model model,
                              HttpSession session) {
        try {
            String sql = "SELECT student_id FROM students WHERE email = ? AND password = ?";
            Integer studentId = jdbcTemplate.queryForObject(sql, Integer.class, email, password);

            if (studentId != null) {
                session.setAttribute("studentId", studentId); 
                return "redirect:/courses";
            }
        } catch (Exception e) {
          
        }

        model.addAttribute("error", "Invalid email or password");
        return "login";
    }
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String processLogin(@RequestParam String email,
                               @RequestParam String password,
                               HttpSession session,
                               HttpServletRequest request,
                               Model model) {
       
        boolean success = ;
        request.setAttribute("loginSuccess", success);

        if (success) {
            session.setAttribute("studentId", id);
            return "redirect:/courses";
        } else {
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }
    }

}
