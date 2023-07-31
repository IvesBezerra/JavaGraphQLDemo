package com.jpmorgan.ignite.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TolkienCharacter {
    private String id;
    private String name;
    private String allegiance;
}
