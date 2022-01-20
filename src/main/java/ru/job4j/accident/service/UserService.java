package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.User;
import ru.job4j.accident.repository.UserRepository;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isFree(User user) {
        boolean rsl = false;
        try {
            userRepository.save(user);
            rsl = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }
}
