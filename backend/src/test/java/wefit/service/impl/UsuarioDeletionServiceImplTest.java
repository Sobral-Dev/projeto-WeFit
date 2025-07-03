package wefit.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wefit.repository.UsuarioRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioDeletionServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioDeletionServiceImpl usuarioDeletionService;

    @Test
    void deletarUsuario_userExists_deletesUser() {
        when(usuarioRepository.existsById(1L)).thenReturn(true);
        doNothing().when(usuarioRepository).deleteById(1L);

        assertDoesNotThrow(() -> usuarioDeletionService.deletarUsuario(1L));

        verify(usuarioRepository, times(1)).existsById(1L);
        verify(usuarioRepository, times(1)).deleteById(1L);
    }

    @Test
    void deletarUsuario_userNotFound_throwsException() {
        when(usuarioRepository.existsById(anyLong())).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            usuarioDeletionService.deletarUsuario(99L);
        });

        assertEquals("Usuário não encontrado com ID: 99", exception.getMessage());
        verify(usuarioRepository, times(1)).existsById(99L);
        verify(usuarioRepository, never()).deleteById(anyLong());
    }
}