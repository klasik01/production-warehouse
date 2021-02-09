package klasik.group.commands.api.user.controllers;

import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.eventhandling.EventProcessor;
import org.axonframework.eventhandling.EventTrackerStatus;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * RebuildUsersController
 *
 * @author pc00275
 * @since 09.02.2021
 */
@RestController
public class RebuildUsersController {
    private final EventProcessingConfiguration eventProcessingConfiguration;

    @Autowired
    public RebuildUsersController(EventProcessingConfiguration eventProcessingConfiguration) {
        this.eventProcessingConfiguration = eventProcessingConfiguration;
    }

    @GetMapping("rebuild")
    public Map<String, Map<Integer, EventTrackerStatus>> rebuildStatus() {
        Map<String, EventProcessor> eventProcessors = eventProcessingConfiguration.eventProcessors();
        return eventProcessors.values().stream()
                .filter(processor -> processor instanceof TrackingEventProcessor)
                .collect(Collectors.toMap(EventProcessor::getName,
                        processor -> ((TrackingEventProcessor) processor).processingStatus()
                ));
    }
}
