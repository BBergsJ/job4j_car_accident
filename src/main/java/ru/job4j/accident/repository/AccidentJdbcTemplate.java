package ru.job4j.accident.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

/*@Repository*/
public class AccidentJdbcTemplate {
    private final JdbcTemplate jdbc;

    public AccidentJdbcTemplate(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Transactional
    public Accident create(Accident accident, String[] rules) {
        Set<Rule> rsl = new HashSet<>();
        if (rules != null) {
            rsl = Arrays.stream(rules)
                    .map(Integer::parseInt)
                    .map(this::findRuleById)
                    .collect(Collectors.toSet());
        }
        accident.setRules(rsl);
        if (accident.getId() == 0) {
            return save(accident);
        } else {
            return update(accident);
        }
    }

    public Accident save(Accident accident) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection
                .prepareStatement("insert into accident (name, text, address, type_id) values (?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, accident.getName());
                ps.setString(2, accident.getText());
                ps.setString(3, accident.getAddress());
                ps.setInt(4, accident.getType().getId());
                return ps;
                }, keyHolder);
        accident.getRules().forEach(rule ->
                        jdbc.update("insert into accident_rules (accident_id, rules_id) values (?, ?)",
                        keyHolder.getKeys().get("id"), rule.getId())
                );
        return accident;
    }

    public Accident update(Accident accident) {
        jdbc.update("update accident set name = ?, text = ?, address = ?, type_id = ? where id = ?",
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getType().getId(),
                accident.getId()
        );
        for (Rule rule : accident.getRules()) {
            jdbc.update("delete from accident_rules where accident_id = ?", accident.getId());
            jdbc.update("insert into accident_rules (accident_id, rules_id) values (?, ?)",
                    accident.getId(),
                    rule.getId());
        }
        return accident;
    }

    public List<Accident> getAll() {
        Map<Integer, Accident> accidentMap = new HashMap<>();
        jdbc.query("select a.*, t.name type_name, r.id rule_id, r.name rule_name from accident a "
                        + "inner join types t on a.type_id = t.id "
                        + "inner join accident_rules ar on a.id = ar.accident_id "
                        + "inner join rules r on ar.rules_id = r.id",
                (rs, row) -> {
                    AccidentType type = AccidentType.of(rs.getInt("type_id"), rs.getString("type_name"));
                    Rule rule = Rule.of(rs.getInt("rules_id"), rs.getString("rule_name"));
                    Accident accident = accidentMap.get(rs.getInt("id"));
                    if (accident == null) {
                        accident = Accident.of(
                                rs.getString("name"),
                                rs.getString("text"),
                                rs.getString("address"),
                                type,
                                new HashSet<>(Set.of(rule)));
                        accident.setId(rs.getInt("id"));
                        accidentMap.put(rs.getInt("id"), accident);
                    } else {
                        accident.getRules().add(rule);
                    }
                    return accident;
                });
        return new ArrayList<>(accidentMap.values());
    }

    public Accident findById(int id) {
        Accident rsl = new Accident();
        jdbc.query("select a.*, t.name type_name, r.id rule_id, r.name rule_name from accident a "
                        + "inner join types t on a.type_id = t.id "
                        + "inner join accident_rules ar on a.id = ar.accident_id "
                        + "inner join rules r on ar.rules_id = r.id where a.id = ?",
            (res, row) -> {
                AccidentType type = AccidentType.of(res.getInt("type_id"), res.getString("type_name"));
                Rule rule = Rule.of(res.getInt("rules_id"), res.getString("rule_name"));
                if (rsl.getId() == 0) {
                    rsl.setId(id);
                    rsl.setName(res.getString("name"));
                    rsl.setText(res.getString("text"));
                    rsl.setAddress(res.getString("address"));
                    rsl.setType(type);
                    rsl.setRules(new HashSet<>(Set.of(rule)));
                }
                rsl.getRules().add(rule);
                return rsl;
            }, id);
        return rsl;
    }

    public List<AccidentType> findAllTypes() {
        return jdbc.query("select * from types",
            (res, row) -> {
                AccidentType type = new AccidentType();
                type.setId(res.getInt("id"));
                type.setName(res.getString("name"));
                return type;
        });
    }

    public AccidentType findTypeById(int id) {
        return jdbc.queryForObject("select id, name from types where id = ?",
            (res, row) -> {
                AccidentType type = new AccidentType();
                type.setId(res.getInt("id"));
                type.setName(res.getString("name"));
                return type;
        }, id);
    }

    public Set<Rule> findAllRules() {
        List<Rule> rules = jdbc.query("select * from rules",
            (res, row) -> {
                Rule rule = new Rule();
                rule.setId(res.getInt("id"));
                rule.setName(res.getString("name"));
                return rule;
        });
        return new HashSet<>(rules);
    }

    public Rule findRuleById(int id) {
        return jdbc.queryForObject("select * from rules where id = ?",
            (res, row) -> {
                Rule rule = new Rule();
                rule.setId(res.getInt("id"));
                rule.setName(res.getString("name"));
                return rule;
        }, id);
    }
}