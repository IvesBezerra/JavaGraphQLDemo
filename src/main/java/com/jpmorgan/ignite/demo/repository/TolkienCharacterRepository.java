package com.jpmorgan.ignite.demo.repository;

import com.jpmorgan.ignite.demo.domain.TolkienCharacter;
import lombok.SneakyThrows;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Repository;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TolkienCharacterRepository {

    private static final String CHARACTERS_DATA = "src/main/resources/characters.csv";

    private final List<TolkienCharacter> characters = new ArrayList<>();

    @SneakyThrows
    public TolkienCharacterRepository() {
        try (CSVParser csvParser = new CSVParser(new FileReader(CHARACTERS_DATA), CSVFormat.DEFAULT.withHeader())) {
            for (CSVRecord record : csvParser) {
                String id = record.get("id");
                String name = record.get("name");
                String allegiance = record.get("allegiance");
                characters.add(new TolkienCharacter(id, name, allegiance));
            }
        }
    }

    public TolkienCharacter getCharacterById(String id) {
        return characters.stream()
                .filter(character -> character.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
