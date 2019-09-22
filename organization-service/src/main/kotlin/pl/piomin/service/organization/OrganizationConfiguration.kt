package pl.piomin.service.organization

import io.r2dbc.pool.ConnectionPool
import io.r2dbc.pool.ConnectionPoolConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.dialect.PostgresDialect
import org.springframework.data.r2dbc.function.DatabaseClient
import org.springframework.data.r2dbc.function.DefaultReactiveDataAccessStrategy
import org.springframework.data.r2dbc.repository.support.R2dbcRepositoryFactory
import org.springframework.data.relational.core.mapping.RelationalMappingContext
import org.springframework.web.reactive.function.client.WebClient
import pl.piomin.service.organization.repository.OrganizationRepository
import java.time.Duration

@Configuration
class OrganizationConfiguration {

    @Bean
    internal fun connectionPool(): ConnectionPool {
        val connectionFactory = PostgresqlConnectionFactory(
                PostgresqlConnectionConfiguration.builder()
                        .host("localhost")
                        .port(5432)
                        .database("reactive")
                        .username("postgres")
                        .password("Company2019").build()
        )

        val configuration = ConnectionPoolConfiguration.builder(connectionFactory)
                .validationQuery("SELECT 1")
                .maxIdleTime(Duration.ofMinutes(30))
                .initialSize(5)
                .maxSize(20)
                .build()

        return ConnectionPool(configuration)
    }

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
    fun databaseClient(connectionPool: ConnectionPool): DatabaseClient {
        return DatabaseClient.create(connectionPool);
    }

    /*@Bean
    fun connectionFactory(): PostgresqlConnectionFactory {
        val config = PostgresqlConnectionConfiguration.builder() //
                .host("localhost") //
                .port(5432) //
                .database("reactive") //
                .username("postgres") //
                .password("Company2019") //
                .build()

        return PostgresqlConnectionFactory(config)
    }*/

    @Bean
    fun clientBuilder() : WebClient.Builder {
        return WebClient.builder()
    }

}