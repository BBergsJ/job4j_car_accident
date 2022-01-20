package ru.job4j.accident.control;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.User;
import ru.job4j.accident.repository.AuthorityRepository;
import ru.job4j.accident.repository.UserRepository;
import ru.job4j.accident.service.UserService;

@Controller
public class RegControl {

    private final PasswordEncoder encoder;
    private final AuthorityRepository authorities;
    private final UserService userService;

    public RegControl(PasswordEncoder encoder, UserService userService, AuthorityRepository authorities) {
        this.encoder = encoder;
        this.userService = userService;
        this.authorities = authorities;
    }

    @PostMapping("/reg")
    public String regSave(@ModelAttribute User user, Model model) {
        user.setEnabled(true);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setAuthority(authorities.findByAuthority("ROLE_USER"));
        boolean rsl = userService.isFree(user);
        if (!rsl) {
            model.addAttribute("errorMessage", "Username is exist");
        }
        return rsl ? "login" : "reg";
    }

    @GetMapping("/reg")
    public String regPage() {
        return "reg";
    }
}