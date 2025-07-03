package wefit.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wefit.entity.PessoaFisica;
import wefit.entity.PessoaJuridica;
import wefit.entity.Usuario;
import wefit.repository.PessoaFisicaRepository;
import wefit.repository.PessoaJuridicaRepository;
import wefit.repository.UsuarioRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioQueryServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private PessoaFisicaRepository pessoaFisicaRepository;
    @Mock
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    @InjectMocks
    private UsuarioQueryServiceImpl usuarioQueryService;

    private Usuario usuario1;
    private PessoaFisica pessoaFisica1;
    private PessoaJuridica pessoaJuridica1;

    @BeforeEach
    void setUp() {
        usuario1 = new Usuario("User 1", "11111111111", null, "user1@example.com") {};
        usuario1.setId(1L);

        pessoaFisica1 = new PessoaFisica("PF 1", "22222222222", null, "pf1@example.com", "11111111111");
        pessoaFisica1.setId(2L);

        pessoaJuridica1 = new PessoaJuridica("PJ 1", "33333333333", null, "pj1@example.com", "11111111000100", "44444444444");
        pessoaJuridica1.setId(3L);
    }

    @Test
    void listarTodosUsuarios_returnsAllUsers() {
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuario1, pessoaFisica1, pessoaJuridica1));

        List<Usuario> result = usuarioQueryService.listarTodosUsuarios();

        assertNotNull(result);
        assertEquals(3, result.size());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void listarTodasPessoasFisicas_returnsAllPessoasFisicas() {
        when(pessoaFisicaRepository.findAll()).thenReturn(Arrays.asList(pessoaFisica1));

        List<PessoaFisica> result = usuarioQueryService.listarTodasPessoasFisicas();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(pessoaFisicaRepository, times(1)).findAll();
    }

    @Test
    void listarTodasPessoasJuridicas_returnsAllPessoasJuridicas() {
        when(pessoaJuridicaRepository.findAll()).thenReturn(Arrays.asList(pessoaJuridica1));

        List<PessoaJuridica> result = usuarioQueryService.listarTodasPessoasJuridicas();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(pessoaJuridicaRepository, times(1)).findAll();
    }

    @Test
    void buscarUsuarioPorId_userFound_returnsUser() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario1));

        Usuario result = usuarioQueryService.buscarUsuarioPorId(1L);

        assertNotNull(result);
        assertEquals(usuario1.getId(), result.getId());
        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    void buscarUsuarioPorId_userNotFound_throwsException() {
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            usuarioQueryService.buscarUsuarioPorId(99L);
        });

        assertEquals("Usuário não encontrado com ID: 99", exception.getMessage());
        verify(usuarioRepository, times(1)).findById(99L);
    }
}