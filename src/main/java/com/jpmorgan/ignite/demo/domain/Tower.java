package com.jpmorgan.ignite.demo.domain;

import lombok.Data;

@Data
public class Tower {
    private String id;
    private String name;
    private float height;
    private String description;
    private TolkienCharacter owner;

    public Tower(String id, String name, float height, String description, TolkienCharacter owner) {
        this.id = id;
        this.name = name;
        this.height = height;
        this.description = description;
        this.owner = owner;
    }
}
