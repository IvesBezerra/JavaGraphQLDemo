package com.jpmorgan.ignite.demo.repository;

import com.jpmorgan.ignite.demo.domain.TolkienCharacter;
import com.jpmorgan.ignite.demo.domain.Tower;
import lombok.SneakyThrows;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Repository;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TowerRepository {

    private static final String TOWERS_DATA = "src/main/resources/towers.csv";

    private final List<Tower> towers = new ArrayList<>();

    @SneakyThrows
    public TowerRepository(TolkienCharacterRepository tolkienCharacterRepository) {
        try (CSVParser csvParser = new CSVParser(new FileReader(TOWERS_DATA), CSVFormat.DEFAULT.withHeader())) {
            for (CSVRecord record : csvParser) {
                String id = record.get("id");
                String name = record.get("name");
                float height = Float.parseFloat(record.get("height"));
                TolkienCharacter owner = tolkienCharacterRepository.getCharacterById(record.get("ownerId"));
                String description = record.get("description");
                towers.add(new Tower(id, name, height, description, owner));
            }
        }
    }

    public Tower getTowerById(String id) {
        return towers.stream()
                .filter(tower -> tower.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Tower> getAllTowers() {
        return towers;
    }
}