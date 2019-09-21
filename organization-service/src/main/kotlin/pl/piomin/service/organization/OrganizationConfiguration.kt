package pl.piomin.service.organization

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.dialect.PostgresDialect
import org.springframework.data.r2dbc.function.DatabaseClient
import org.springframework.data.r2dbc.function.DefaultReactiveDataAccessStrategy
import org.springframework.data.r2dbc.repository.support.R2dbcRepositoryFactory
import org.springframework.data.relational.core.mapping.RelationalMappingContext
import org.springframework.web.reactive.function.client.WebClient
import pl.piomin.service.organization.repository.OrganizationRepository

@Configuration
class OrganizationConfiguration {

    @Bean
    fun repository(factory: R2dbcRepositoryFactory): OrganizationRepository {
        return factory.getRepository(OrganizationRepository::class.java)
    }

    @Bean
    fun factory(client: DatabaseClient): R2dbcRepositoryFactory {
        val context = RelationalMappingContext()
        context.afterPropertiesSet()
        return R2dbcRepositoryFactory(client, context, DefaultReactiveDataAccessStrategy(PostgresDialect()))
    }

    @Bean
    fun databaseClient(factory: ConnectionFactory): DatabaseClient {
        return DatabaseClient.builder().connectionFactory(factory).build()
    }

    @Bean
    fun connectionFactory(): PostgresqlConnectionFactory {
        val config = PostgresqlConnectionConfiguration.builder() //
                .host("localhost") //
                .port(5432) //
                .database("reactive") //
                .username("postgres") //
                .password("Company2019") //
                .build()

        return PostgresqlConnectionFactory(config)
    }

    @Bean
    fun clientBuilder() : WebClient.Builder {
        return WebClient.builder()
    }

}