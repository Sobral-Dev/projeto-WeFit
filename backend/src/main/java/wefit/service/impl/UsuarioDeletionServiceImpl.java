package wefit.service.impl;

import wefit.repository.UsuarioRepository;
import wefit.service.UsuarioDeletionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wefit.exception.UsuarioNaoEncontradoException;

@Service
public class UsuarioDeletionServiceImpl implements UsuarioDeletionService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioDeletionServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional
    public void deletarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new UsuarioNaoEncontradoException("Usuário não encontrado com ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }
}
