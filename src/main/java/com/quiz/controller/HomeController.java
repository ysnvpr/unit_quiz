package com.quiz.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    
    @GetMapping("/")
    public String welcome() {
        return "Quiz Uygulamasına Hoş Geldiniz! 🎯\n\n" +
               "Bu uygulama, interaktif bir quiz deneyimi sunmak için tasarlanmıştır.\n" +
               "API Endpoints yakında burada listelenecektir.";
    }
} 