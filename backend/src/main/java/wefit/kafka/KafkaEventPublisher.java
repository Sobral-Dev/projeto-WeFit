package wefit.kafka;

public interface KafkaEventPublisher {
    void publish(String topic, Object message);
}
