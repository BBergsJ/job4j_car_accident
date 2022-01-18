package ru.job4j.accident.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Rule;

@Repository
public interface AccidentRulesRep extends CrudRepository<Rule, Integer> {
}
