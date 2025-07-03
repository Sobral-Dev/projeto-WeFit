package wefit.kafka.impl;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import wefit.kafka.KafkaEventPublisher;

@Service
public class KafkaEventPublisherImpl implements KafkaEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaEventPublisherImpl(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(String topic, Object message) {
        kafkaTemplate.send(topic, message);
    }
}
