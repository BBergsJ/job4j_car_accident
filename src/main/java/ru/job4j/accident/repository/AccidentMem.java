package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.service.AccidentService;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class AccidentMem {
    private final HashMap<Integer, Accident> accidents = new HashMap<>();
    private final HashMap<Integer, AccidentType> types = new HashMap<>();
    private final HashMap<Integer, Rule> rules = new HashMap<>();
    private final static AtomicInteger ID = new AtomicInteger(5);

    public AccidentMem() {
        rules.put(1, Rule.of(1, "Статья. 1"));
        rules.put(2, Rule.of(2, "Статья. 2"));
        rules.put(3, Rule.of(3, "Статья. 3"));
        types.put(1, AccidentType.of(1, "Две машины"));
        types.put(2, AccidentType.of(2, "Машина и человек"));
        types.put(3, AccidentType.of(3, "Машина и велосипед"));
        accidents.put(1, new Accident("Name_1", "Text_1", "Address_1", types.get(1), new HashSet<>(rules.values())));
        accidents.put(2, new Accident("Name_2", "Text_2", "Address_2", types.get(2), new HashSet<>(rules.values())));
        accidents.put(3, new Accident("Name_3", "Text_3", "Address_3", types.get(3), new HashSet<>(rules.values())));
        accidents.put(4, new Accident("Name_4", "Text_4", "Address_4", types.get(1), new HashSet<>(rules.values())));
        accidents.put(5, new Accident("Name_5", "Text_5", "Address_5", types.get(2), new HashSet<>(rules.values())));
    }

    public void create(Accident accident, String[] rules) {
        if (accident.getId() == 0) {
            accident.setId(ID.incrementAndGet());
        }
        accident.setType(findTypeById(accident.getType().getId()));
        Set<Rule> rsl = Arrays.stream(rules)
                .map(Integer::parseInt)
                .map(this::findRuleById)
                .collect(Collectors.toSet());
        accident.setRules(rsl);
        accidents.put(accident.getId(), accident);
    }

    public List<Accident> findAll() {
        return new ArrayList<>(accidents.values());
    }

    public Accident findById(int id) {
        return accidents.get(id);
    }

    public List<AccidentType> findAllTypes() {
        return new ArrayList<>(types.values());
    }

    public AccidentType findTypeById(int id) {
        return types.get(id);
    }

    public Set<Rule> findAllRules() {
        return new HashSet<>(rules.values());
    }

    public Rule findRuleById(int id) {
        return rules.get(id);
    }
}