package wefit.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wefit.dto.PessoaJuridicaDTO;
import wefit.dto.EnderecoDTO;
import wefit.entity.PessoaJuridica;
import wefit.entity.Usuario;
import wefit.exception.UsuarioExistenteException;
import wefit.kafka.KafkaEventPublisher;
import wefit.mapper.UsuarioMapper;
import wefit.repository.PessoaJuridicaRepository;
import wefit.repository.UsuarioRepository;
import wefit.service.EnderecoService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PessoaJuridicaCreationServiceImplTest {

    @Mock
    private PessoaJuridicaRepository pessoaJuridicaRepository;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private EnderecoService enderecoService;
    @Mock
    private KafkaEventPublisher kafkaEventPublisher;
    @Mock
    private UsuarioMapper usuarioMapper;

    @InjectMocks
    private PessoaJuridicaCreationServiceImpl pessoaJuridicaCreationService;

    private PessoaJuridicaDTO pessoaJuridicaDTO;
    private PessoaJuridica pessoaJuridica;
    private EnderecoDTO enderecoDTO;

    @BeforeEach
    void setUp() {
        enderecoDTO = new EnderecoDTO();
        enderecoDTO.setCep("87654321");
        enderecoDTO.setLogradouro("Av. Principal");
        enderecoDTO.setNumero("456");
        enderecoDTO.setCidade("Outra Cidade");
        enderecoDTO.setBairro("Centro");
        enderecoDTO.setEstado("OT");

        pessoaJuridicaDTO = new PessoaJuridicaDTO();
        pessoaJuridicaDTO.setNome("Empresa Teste");
        pessoaJuridicaDTO.setEmail("empresa@example.com");
        pessoaJuridicaDTO.setCnpj("12345678000190");
        pessoaJuridicaDTO.setCpfResponsavel("98765432109");
        pessoaJuridicaDTO.setCelular("11998877665");
        pessoaJuridicaDTO.setEndereco(enderecoDTO);

        pessoaJuridica = new PessoaJuridica();
        pessoaJuridica.setId(2L);
        pessoaJuridica.setNome("Empresa Teste");
        pessoaJuridica.setEmail("empresa@example.com");
        pessoaJuridica.setCnpj("12345678000190");
        pessoaJuridica.setCpfResponsavel("98765432109");
        pessoaJuridica.setCelular("11998877665");
    }

    @Test
    void cadastrarPessoaJuridica_success() {
        when(pessoaJuridicaRepository.findByCnpj(anyString())).thenReturn(Optional.empty());
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(usuarioMapper.toPessoaJuridica(any(PessoaJuridicaDTO.class))).thenReturn(pessoaJuridica);
        when(usuarioMapper.toEndereco(any(EnderecoDTO.class))).thenReturn(new wefit.entity.Endereco());
        when(pessoaJuridicaRepository.save(any(PessoaJuridica.class))).thenReturn(pessoaJuridica);
        doNothing().when(kafkaEventPublisher).publish(anyString(), any());

        PessoaJuridica result = pessoaJuridicaCreationService.cadastrarPessoaJuridica(pessoaJuridicaDTO);

        assertNotNull(result);
        assertEquals(pessoaJuridica.getId(), result.getId());
        verify(pessoaJuridicaRepository, times(1)).findByCnpj(pessoaJuridicaDTO.getCnpj());
        verify(usuarioRepository, times(1)).findByEmail(pessoaJuridicaDTO.getEmail());
        verify(pessoaJuridicaRepository, times(1)).save(any(PessoaJuridica.class));
        verify(kafkaEventPublisher, times(1)).publish(eq("usuario.cadastrado"), any());
    }

    @Test
    void cadastrarPessoaJuridica_cnpjAlreadyExists_throwsException() {
        when(pessoaJuridicaRepository.findByCnpj(anyString())).thenReturn(Optional.of(new PessoaJuridica()));

        Exception exception = assertThrows(UsuarioExistenteException.class, () -> {
            pessoaJuridicaCreationService.cadastrarPessoaJuridica(pessoaJuridicaDTO);
        });

        assertEquals("Já existe uma Pessoa Jurídica cadastrada com o CNPJ: " + pessoaJuridicaDTO.getCnpj(), exception.getMessage());
        verify(pessoaJuridicaRepository, times(1)).findByCnpj(pessoaJuridicaDTO.getCnpj());
        verify(usuarioRepository, never()).findByEmail(anyString());
        verify(pessoaJuridicaRepository, never()).save(any(PessoaJuridica.class));
        verify(kafkaEventPublisher, never()).publish(anyString(), any());
    }

    @Test
    void cadastrarPessoaJuridica_emailAlreadyExists_throwsException() {
        when(pessoaJuridicaRepository.findByCnpj(anyString())).thenReturn(Optional.empty());
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(new Usuario() {}));

        Exception exception = assertThrows(UsuarioExistenteException.class, () -> {
            pessoaJuridicaCreationService.cadastrarPessoaJuridica(pessoaJuridicaDTO);
        });

        assertEquals("Já existe um usuário cadastrado com o email: " + pessoaJuridicaDTO.getEmail(), exception.getMessage());
        verify(pessoaJuridicaRepository, times(1)).findByCnpj(pessoaJuridicaDTO.getCnpj());
        verify(usuarioRepository, times(1)).findByEmail(pessoaJuridicaDTO.getEmail());
        verify(pessoaJuridicaRepository, never()).save(any(PessoaJuridica.class));
        verify(kafkaEventPublisher, never()).publish(anyString(), any());
    }
}