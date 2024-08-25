package com.htadg.taskit.config;

import lombok.Setter;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Setter
@Configuration
public class TaskItTablesNamingStrategy implements PhysicalNamingStrategy {

    @Value("${taskit.table_prefix}")
    private String TASKIT_TABLE_PREFIX;

    @Override
    public Identifier toPhysicalCatalogName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        if (identifier == null) return null;
        return this.addPrefix(identifier);
    }

    @Override
    public Identifier toPhysicalSchemaName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        if (identifier == null) return null;
        return this.addPrefix(identifier);
    }

    @Override
    public Identifier toPhysicalTableName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        if (identifier == null) return null;
        return this.addPrefix(identifier);
    }

    @Override
    public Identifier toPhysicalSequenceName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        if (identifier == null) return null;
        return this.addPrefix(identifier);
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return identifier;
    }

    private Identifier addPrefix(Identifier identifier) {
        return Identifier.toIdentifier(TASKIT_TABLE_PREFIX + identifier.getText());
    }
}
