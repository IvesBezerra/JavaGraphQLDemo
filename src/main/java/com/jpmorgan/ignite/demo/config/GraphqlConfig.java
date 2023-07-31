package com.jpmorgan.ignite.demo.config;

import com.jpmorgan.ignite.demo.repository.TowerRepository;
import com.jpmorgan.ignite.demo.resolver.TowerQueryResolver;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
@RequiredArgsConstructor
public class GraphqlConfig {

    private final TowerRepository towerRepository;

    @Bean
    public GraphQL graphQL() throws IOException {
        String schemaStr = Files.readString(Paths.get("src/main/resources/schema.graphqls"));

        GraphQLSchema schema = buildSchema(schemaStr);
        return GraphQL.newGraphQL(schema).build();

//     String query = """
//        {
//          getAllTowers {
//            id
//            name
//            height
//            owner {
//              id
//              name
//              race
//              age
//            }
//            description
//          }
//        }
//        """;
    }

    private GraphQLSchema buildSchema(String schemaStr) {
        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeRegistry = schemaParser.parse(schemaStr);

        final TowerQueryResolver towerQueryResolver = new TowerQueryResolver(towerRepository);

        // Create the RuntimeWiring
        RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
                .type(TypeRuntimeWiring.newTypeWiring("Query").dataFetcher("getTowerById", towerQueryResolver::getTowerById))
                .type(TypeRuntimeWiring.newTypeWiring("Query").dataFetcher("getAllTowers", towerQueryResolver::getAllTowers))
                .build();

        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }
}
