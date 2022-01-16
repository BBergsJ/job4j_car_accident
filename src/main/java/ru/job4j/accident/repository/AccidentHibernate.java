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

@Repository
public class AccidentHibernate {
    private final SessionFactory sf;

    public AccidentHibernate(SessionFactory sf) {
        this.sf = sf;
    }

    public Accident create(Accident accident, String[] rules) {
        Set<Rule> rsl = new HashSet<>();
        if (rules != null) {
            rsl = Arrays.stream(rules)
                    .map(Integer::parseInt)
                    .map(this::findRuleById)
                    .collect(Collectors.toSet());
        }
        accident.setRules(rsl);
        accident.setType(findTypeById(accident.getType().getId()));
        try (Session session = sf.openSession()) {
            Transaction tx = session.beginTransaction();
            session.saveOrUpdate(accident);
            tx.commit();
        }
        return accident;
    }


    public List<Accident> getAll() {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery("select distinct a from Accident a "
                            + "join fetch a.rules "
                            + "join fetch a.type "
                            + "order by a.id", Accident.class)
                    .list();
        }
    }

    public Accident findById(int id) {
        try (Session session = sf.openSession()) {
            return session.createQuery("select a from Accident a "
                    + "join fetch a.rules "
                    + "join fetch a.type "
                    + "where a.id = :id", Accident.class)
                    .setParameter("id", id)
                    .uniqueResult();
        }
    }

    public List<AccidentType> findAllTypes() {
        try (Session session = sf.openSession()) {
            return session.createQuery("from AccidentType", AccidentType.class)
                    .list();
        }
    }

    public AccidentType findTypeById(int id) {
        try (Session session = sf.openSession()) {
            return session.get(AccidentType.class, id);
        }
    }

    public Set<Rule> findAllRules() {
        try (Session session = sf.openSession()) {
            List<Rule> rsl = session.createQuery("from Rule", Rule.class).list();
            return new HashSet<>(rsl);
        }
    }

    public Rule findRuleById(int id) {
        try (Session session = sf.openSession()) {
            return session.get(Rule.class, id);
        }
    }
}