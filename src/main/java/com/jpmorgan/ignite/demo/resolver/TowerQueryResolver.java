package com.jpmorgan.ignite.demo.resolver;

import com.jpmorgan.ignite.demo.domain.Tower;
import com.jpmorgan.ignite.demo.repository.TowerRepository;
import graphql.schema.DataFetchingEnvironment;

import java.util.List;

public class TowerQueryResolver {
    private final TowerRepository towerRepository;

    public TowerQueryResolver(TowerRepository towerRepository) {
        this.towerRepository = towerRepository;
    }

    public Tower getTowerById(DataFetchingEnvironment environment) {
        String id = environment.getArgument("id");
        return towerRepository.getTowerById(id);
    }

    public List<Tower> getAllTowers(DataFetchingEnvironment environment) {
        return towerRepository.getAllTowers();
    }
}
