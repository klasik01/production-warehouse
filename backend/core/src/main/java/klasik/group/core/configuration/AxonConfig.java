package klasik.group.core.configuration;

import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import org.axonframework.config.EventProcessingModule;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.extensions.mongo.DefaultMongoTemplate;
import org.axonframework.extensions.mongo.MongoTemplate;
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoFactory;
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoSettingsFactory;
import org.axonframework.extensions.mongo.eventsourcing.tokenstore.MongoTokenStore;
import org.axonframework.serialization.Serializer;
import org.axonframework.spring.config.AxonConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Optional;

@Configuration
public class AxonConfig {
    @Value("${spring.data.mongodb.host:localhost}")
    private String mongoHost;

    @Value("${spring.data.mongodb.port:27017}")
    private int mongoPort;

    @Value("${spring.data.mongodb.database:prod-warehouse}")
    private String mongoDatabase;

    @Autowired
    private EventProcessingModule eventProcessingModule;

    @Bean
    public MongoClient mongo() {
        var mongoFactory = new MongoFactory();
        var mongoSettingsFactory = new MongoSettingsFactory();
        mongoSettingsFactory.setMongoAddresses(Collections.singletonList(new ServerAddress(mongoHost, mongoPort)));
        mongoFactory.setMongoClientSettings(mongoSettingsFactory.createMongoClientSettings());

        return mongoFactory.createMongo();
    }

    @Bean
    public MongoTemplate axonMongoTemplate() {
        return DefaultMongoTemplate.builder()
                .mongoDatabase(mongo(), mongoDatabase)
                .build();
    }

    @Bean
    public TokenStore tokenStore(Serializer serializer) {
        return MongoTokenStore.builder()
                .mongoTemplate(axonMongoTemplate())
                .serializer(serializer)
                .build();
    }

    /**
     * The MongoEventStorageEngine stores each event in a separate MongoDB document
     *
     * @param client
     * @return EventStorageEngine
     */
    @Bean
    public EventStorageEngine storageEngine(MongoClient client) {
        return MongoEventStorageEngine.builder()
                .mongoTemplate(DefaultMongoTemplate.builder()
                        .mongoDatabase(client)
                        .build())
                .build();
    }

    /**
     * Axon provides an event store `EmbeddedEventStore`. It delegates actual storage and retrieval of events to an `EventStorageEngine`.
     *
     * @param storageEngine
     * @param configuration
     * @return EmbeddedEventStore
     */
    @Bean
    public EmbeddedEventStore eventStore(EventStorageEngine storageEngine, AxonConfiguration configuration) {
        return EmbeddedEventStore.builder()
                .storageEngine(storageEngine)
                .messageMonitor(configuration.messageMonitor(EventStore.class, "eventStore"))
                .build();
    }

    @PostConstruct
    public void startTrackingProjections() throws ClassNotFoundException {
        ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(RebuildableProjection.class));

        for (BeanDefinition bd : scanner.findCandidateComponents("klasik.group")) {
            Class<?> aClass = Class.forName(bd.getBeanClassName());
            RebuildableProjection rebuildableProjection = aClass.getAnnotation(RebuildableProjection.class);

            if (rebuildableProjection.rebuild()) {
                registerRebuildableProjection(aClass, rebuildableProjection);
            }
        }
    }

    private void registerRebuildableProjection(Class<?> aClass, RebuildableProjection rebuildableProjection) {
        ProcessingGroup processingGroup = aClass.getAnnotation(ProcessingGroup.class);

        String name = Optional.ofNullable(processingGroup).map(ProcessingGroup::value)
                .orElse(aClass.getName() + "/" + rebuildableProjection.version());

        //		eventProcessingModule.assignHandlersMatching(
        //			name,
        //			Integer.MAX_VALUE,
        //			(eventHandler) -> aClass.isAssignableFrom(eventHandler.getClass()));
        //
        eventProcessingModule.registerTrackingEventProcessor(name);
    }
}
