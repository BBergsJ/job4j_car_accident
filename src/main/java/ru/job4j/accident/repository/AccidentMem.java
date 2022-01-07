package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.service.AccidentService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {
    private final HashMap<Integer, Accident> accidents = new HashMap<>();
    private final static AtomicInteger ID = new AtomicInteger(5);


    public AccidentMem() {
        accidents.put(1, new Accident("Name_1", "Text_1", "Address_1"));
        accidents.put(2, new Accident("Name_2", "Text_2", "Address_2"));
        accidents.put(3, new Accident("Name_3", "Text_3", "Address_3"));
        accidents.put(4, new Accident("Name_4", "Text_4", "Address_4"));
        accidents.put(5, new Accident("Name_5", "Text_5", "Address_5"));
    }

    public void create(Accident accident) {
        if (accident.getId() == 0) {
            accident.setId(ID.incrementAndGet());
        }
        accidents.put(accident.getId(), accident);
    }

    public List<Accident> findAll() {
        return new ArrayList<>(accidents.values());
    }

    public Accident findById(int id) {
        return accidents.get(id);
    }
}