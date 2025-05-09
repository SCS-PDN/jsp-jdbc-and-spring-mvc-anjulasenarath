package com.example.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
public class CourseController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    
    @GetMapping("/courses")
    public String showCourses(Model model, HttpSession session) {
        Integer studentId = (Integer) session.getAttribute("studentId");
        if (studentId == null) {
            return "redirect:/login";
        }

        String sql = "SELECT * FROM courses";
        List<Map<String, Object>> courses = jdbcTemplate.queryForList(sql);
        model.addAttribute("courses", courses);
        return "courses"; 
    }


    @PostMapping("/register/{courseId}")
    public String registerCourse(@PathVariable int courseId, HttpSession session, Model model) {
        Integer studentId = (Integer) session.getAttribute("studentId");
        if (studentId == null) {
            return "redirect:/login";
        }


        String checkSql = "SELECT COUNT(*) FROM registrations WHERE student_id = ? AND course_id = ?";
        Integer count = jdbcTemplate.queryForObject(checkSql, Integer.class, studentId, courseId);

        if (count != null && count > 0) {
            model.addAttribute("message", "You are already registered for this course.");
        } else {
            String insertSql = "INSERT INTO registrations (student_id, course_id, date) VALUES (?, ?, ?)";
            jdbcTemplate.update(insertSql, studentId, courseId, LocalDate.now());
            model.addAttribute("message", "Successfully registered for the course!");
        }

        return "success"; 
    }
}
