package ru.job4j.accident.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.job4j.accident.repository.HibernateUtil.tx;

/*@Repository*/
public class AccidentHibernate {
    private final SessionFactory sf;

    public AccidentHibernate(SessionFactory sf) {
        this.sf = sf;
    }

    public Accident create(Accident accident, String[] rules) {
        return tx(sf, session -> {
            Set<Rule> rsl = new HashSet<>();
            if (rules != null) {
                rsl = Arrays.stream(rules)
                        .map(Integer::parseInt)
                        .map(this::findRuleById)
                        .collect(Collectors.toSet());
            }
            accident.setRules(rsl);
            accident.setType(findTypeById(accident.getType().getId()));
            session.saveOrUpdate(accident);
            return accident;
        });
    }


    public List<Accident> getAll() {
        return tx(sf, session -> session
                .createQuery("select distinct a from Accident a "
                        + "join fetch a.rules "
                        + "join fetch a.type "
                        + "order by a.id", Accident.class)
                .list()
        );
    }

    public Accident findById(int id) {
        return tx(sf, session -> session.get(Accident.class, id));
    }

    public List<AccidentType> findAllTypes() {
        return tx(sf, session -> session.createQuery("from AccidentType ", AccidentType.class)
                .list()
        );
    }

    public AccidentType findTypeById(int id) {
        return tx(sf, session -> session.get(AccidentType.class, id));
    }

    public Set<Rule> findAllRules() {
        return new HashSet<>(
                tx(sf, session -> session.createQuery("from Rule", Rule.class)
                        .list()
                )
        );
    }

    public Rule findRuleById(int id) {
        return tx(sf, session -> session.get(Rule.class, id));
    }
}