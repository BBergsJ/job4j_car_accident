package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;

import java.util.HashMap;

@Repository
public class AccidentMem {
    private HashMap<Integer, Accident> accidents = new HashMap<>();

    public void init() {
        accidents.put(1, new Accident(1, "Name_1", "Text_1", "Address_1"));
        accidents.put(2, new Accident(2, "Name_2", "Text_2", "Address_2"));
        accidents.put(3, new Accident(3, "Name_3", "Text_3", "Address_3"));
        accidents.put(4, new Accident(4, "Name_4", "Text_4", "Address_4"));
        accidents.put(5, new Accident(5, "Name_5", "Text_5", "Address_5"));
    }

    public HashMap<Integer, Accident> findAll() {
        return this.accidents;
    }
}