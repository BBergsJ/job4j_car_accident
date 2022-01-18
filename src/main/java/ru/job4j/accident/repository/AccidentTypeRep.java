package ru.job4j.accident.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.AccidentType;

@Repository
public interface AccidentTypeRep extends CrudRepository<AccidentType, Integer> {
}
