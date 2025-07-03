package wefit.kafka.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import wefit.dto.UsuarioCadastradoEventDTO;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KafkaEventPublisherImplTest {

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @InjectMocks
    private KafkaEventPublisherImpl kafkaEventPublisher;

    private UsuarioCadastradoEventDTO eventDTO;

    @BeforeEach
    void setUp() {
        eventDTO = new UsuarioCadastradoEventDTO(1L, "PESSOA_FISICA", "Test User", "test@example.com", "12345678901");
    }

    @Test
    void publish_sendsMessageToKafka() {
        String topic = "usuario.cadastrado";
        doReturn(null).when(kafkaTemplate).send(anyString(), any());

        kafkaEventPublisher.publish(topic, eventDTO);

        verify(kafkaTemplate, times(1)).send(topic, eventDTO);
    }
}