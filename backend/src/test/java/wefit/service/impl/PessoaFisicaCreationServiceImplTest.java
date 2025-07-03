package wefit.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wefit.dto.PessoaFisicaDTO;
import wefit.dto.EnderecoDTO;
import wefit.entity.PessoaFisica;
import wefit.entity.Usuario;
import wefit.exception.UsuarioExistenteException;
import wefit.kafka.KafkaEventPublisher;
import wefit.mapper.UsuarioMapper;
import wefit.repository.PessoaFisicaRepository;
import wefit.repository.UsuarioRepository;
import wefit.service.EnderecoService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PessoaFisicaCreationServiceImplTest {

    @Mock
    private PessoaFisicaRepository pessoaFisicaRepository;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private EnderecoService enderecoService;
    @Mock
    private KafkaEventPublisher kafkaEventPublisher;
    @Mock
    private UsuarioMapper usuarioMapper;

    @InjectMocks
    private PessoaFisicaCreationServiceImpl pessoaFisicaCreationService;

    private PessoaFisicaDTO pessoaFisicaDTO;
    private PessoaFisica pessoaFisica;
    private EnderecoDTO enderecoDTO;

    @BeforeEach
    void setUp() {
        enderecoDTO = new EnderecoDTO();
        enderecoDTO.setCep("12345678");
        enderecoDTO.setLogradouro("Rua Teste");
        enderecoDTO.setNumero("123");
        enderecoDTO.setCidade("Cidade Teste");
        enderecoDTO.setBairro("Bairro Teste");
        enderecoDTO.setEstado("TS");

        pessoaFisicaDTO = new PessoaFisicaDTO();
        pessoaFisicaDTO.setNome("Test User");
        pessoaFisicaDTO.setEmail("test@example.com");
        pessoaFisicaDTO.setCpf("12345678901");
        pessoaFisicaDTO.setCelular("11987654321");
        pessoaFisicaDTO.setEndereco(enderecoDTO);

        pessoaFisica = new PessoaFisica();
        pessoaFisica.setId(1L);
        pessoaFisica.setNome("Test User");
        pessoaFisica.setEmail("test@example.com");
        pessoaFisica.setCpf("12345678901");
        pessoaFisica.setCelular("11987654321");
    }

    @Test
    void cadastrarPessoaFisica_success() {
        when(pessoaFisicaRepository.findByCpf(anyString())).thenReturn(Optional.empty());
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(usuarioMapper.toPessoaFisica(any(PessoaFisicaDTO.class))).thenReturn(pessoaFisica);
        when(usuarioMapper.toEndereco(any(EnderecoDTO.class))).thenReturn(new wefit.entity.Endereco());
        when(pessoaFisicaRepository.save(any(PessoaFisica.class))).thenReturn(pessoaFisica);
        doNothing().when(kafkaEventPublisher).publish(anyString(), any());

        PessoaFisica result = pessoaFisicaCreationService.cadastrarPessoaFisica(pessoaFisicaDTO);

        assertNotNull(result);
        assertEquals(pessoaFisica.getId(), result.getId());
        verify(pessoaFisicaRepository, times(1)).findByCpf(pessoaFisicaDTO.getCpf());
        verify(usuarioRepository, times(1)).findByEmail(pessoaFisicaDTO.getEmail());
        verify(pessoaFisicaRepository, times(1)).save(any(PessoaFisica.class));
        verify(kafkaEventPublisher, times(1)).publish(eq("usuario.cadastrado"), any());
    }

    @Test
    void cadastrarPessoaFisica_cpfAlreadyExists_throwsException() {
        when(pessoaFisicaRepository.findByCpf(anyString())).thenReturn(Optional.of(new PessoaFisica()));

        Exception exception = assertThrows(UsuarioExistenteException.class, () -> {
            pessoaFisicaCreationService.cadastrarPessoaFisica(pessoaFisicaDTO);
        });

        assertEquals("Já existe uma Pessoa Física cadastrada com o CPF: " + pessoaFisicaDTO.getCpf(), exception.getMessage());
        verify(pessoaFisicaRepository, times(1)).findByCpf(pessoaFisicaDTO.getCpf());
        verify(usuarioRepository, never()).findByEmail(anyString());
        verify(pessoaFisicaRepository, never()).save(any(PessoaFisica.class));
        verify(kafkaEventPublisher, never()).publish(anyString(), any());
    }

    @Test
    void cadastrarPessoaFisica_emailAlreadyExists_throwsException() {
        when(pessoaFisicaRepository.findByCpf(anyString())).thenReturn(Optional.empty());
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(new Usuario() {}));

        Exception exception = assertThrows(UsuarioExistenteException.class, () -> {
            pessoaFisicaCreationService.cadastrarPessoaFisica(pessoaFisicaDTO);
        });

        assertEquals("Já existe um usuário cadastrado com o email: " + pessoaFisicaDTO.getEmail(), exception.getMessage());
        verify(pessoaFisicaRepository, times(1)).findByCpf(pessoaFisicaDTO.getCpf());
        verify(usuarioRepository, times(1)).findByEmail(pessoaFisicaDTO.getEmail());
        verify(pessoaFisicaRepository, never()).save(any(PessoaFisica.class));
        verify(kafkaEventPublisher, never()).publish(anyString(), any());
    }
}