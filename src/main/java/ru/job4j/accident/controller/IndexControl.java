package ru.job4j.accident.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.repository.AccidentMem;

import java.util.HashMap;
import java.util.List;

@Controller
public class IndexControl {
    @GetMapping("/")
    public String index(Model model) {
        AccidentMem accidents = new AccidentMem();
        accidents.init();
        model.addAttribute("accidents", accidents.findAll());
        model.addAttribute("user", "Petr Arsentev");
        return "index";
    }
}