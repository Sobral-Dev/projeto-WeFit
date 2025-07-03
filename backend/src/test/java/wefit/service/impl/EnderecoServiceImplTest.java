package wefit.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wefit.entity.Endereco;
import wefit.repository.EnderecoRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnderecoServiceImplTest {

    @Mock
    private EnderecoRepository enderecoRepository;

    @InjectMocks
    private EnderecoServiceImpl enderecoService;

    private Endereco endereco;

    @BeforeEach
    void setUp() {
        endereco = new Endereco("12345678", "Rua Teste", "123", null, "Cidade Teste", "Bairro Teste", "TS");
    }

    @Test
    void salvarEndereco_success() {
        when(enderecoRepository.save(any(Endereco.class))).thenReturn(endereco);

        Endereco result = enderecoService.salvarEndereco(endereco);

        assertNotNull(result);
        assertEquals(endereco.getCep(), result.getCep());
        verify(enderecoRepository, times(1)).save(endereco);
    }
}
