package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentMem;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class AccidentService {
    private AccidentMem accidentMem;

    public AccidentService(AccidentMem accidentMem) {
        this.accidentMem = accidentMem;
    }

    public void create(Accident accident, String[] rules) {
        accidentMem.create(accident, rules);
    }

    public List<Accident> findAll() {
        return accidentMem.findAll();
    }

    public Accident findById(int id) {
        return accidentMem.findById(id);
    }

    public List<AccidentType> findAllTypes() {
        return new ArrayList<>(accidentMem.findAllTypes());
    }

    public AccidentType findTypeById(int id) {
        return accidentMem.findTypeById(id);
    }

    public Set<Rule> findAllRules() {
        return accidentMem.findAllRules();
    }

    public Rule findRuleById(int id) {
        return accidentMem.findRuleById(id);
    }
}