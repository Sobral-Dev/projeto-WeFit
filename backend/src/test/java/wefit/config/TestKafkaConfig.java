package wefit.config;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import wefit.kafka.KafkaEventPublisher;

@TestConfiguration
public class TestKafkaConfig {

    @Bean
    @Primary
    public KafkaEventPublisher mockKafkaEventPublisher() {
        return Mockito.mock(KafkaEventPublisher.class);
    }
}