package ru.job4j.accident.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.*;
import ru.job4j.accident.service.AccidentService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AccidentControl {
    private final AccidentRepository accidents;
    private final AccidentRulesRep rules;
    private final AccidentTypeRep types;

    public AccidentControl(AccidentRepository accidents, AccidentRulesRep rules, AccidentTypeRep types) {
        this.accidents = accidents;
        this.rules = rules;
        this.types = types;
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("rules", rules.findAll());
        model.addAttribute("types", types.findAll());
        return "accident/create";
    }

    @GetMapping("/update")
    public String edit(@RequestParam("id") int id, Model model) {
        model.addAttribute("rules", rules.findAll());
        model.addAttribute("types", types.findAll());
        model.addAttribute("accident", accidents.findById(id).get());
        return "accident/edit";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Accident accident, HttpServletRequest request) {
        accident.setType(types.findById(accident.getType().getId()).get());
        String[] ids = request.getParameterValues("rIds");
        accident.setRules(Arrays.stream(ids)
                .map(id -> rules.findById(Integer.parseInt(id)).get()).collect(Collectors.toSet())
        );
        accidents.save(accident);
        return "redirect:/";
    }
}