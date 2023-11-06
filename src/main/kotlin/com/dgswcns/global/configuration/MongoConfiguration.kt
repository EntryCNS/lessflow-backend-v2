package com.dgswcns.global.configuration

import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import jakarta.annotation.PostConstruct
import org.springframework.boot.autoconfigure.mongo.MongoProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.convert.MappingMongoConverter
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@Configuration
@EnableReactiveMongoRepositories(
    basePackages = ["com.dgswcns"],
)
@EnableReactiveMongoAuditing
class MongoConfiguration(
    private val mongoProperties: MongoProperties
): AbstractReactiveMongoConfiguration() {
    @Bean
    fun mongoClient(): MongoClient = MongoClients.create(mongoProperties.uri)

    override fun getDatabaseName(): String = mongoProperties.database

    override fun reactiveMongoClient(): MongoClient = mongoClient()

    override fun reactiveMongoTemplate(databaseFactory: ReactiveMongoDatabaseFactory, mongoConverter: MappingMongoConverter) =
        ReactiveMongoTemplate(mongoClient(), databaseName)
}