package wefit.service.impl;

import wefit.entity.Usuario;
import wefit.entity.PessoaFisica;
import wefit.entity.PessoaJuridica;
import wefit.repository.UsuarioRepository;
import wefit.repository.PessoaFisicaRepository;
import wefit.repository.PessoaJuridicaRepository;
import wefit.service.UsuarioQueryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioQueryServiceImpl implements UsuarioQueryService {

    private final UsuarioRepository usuarioRepository;
    private final PessoaFisicaRepository pessoaFisicaRepository;
    private final PessoaJuridicaRepository pessoaJuridicaRepository;

    public UsuarioQueryServiceImpl(UsuarioRepository usuarioRepository,
                                   PessoaFisicaRepository pessoaFisicaRepository,
                                   PessoaJuridicaRepository pessoaJuridicaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.pessoaFisicaRepository = pessoaFisicaRepository;
        this.pessoaJuridicaRepository = pessoaJuridicaRepository;
    }

    @Override
    public List<Usuario> listarTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public List<PessoaFisica> listarTodasPessoasFisicas() {
        return pessoaFisicaRepository.findAll();
    }

    @Override
    public List<PessoaJuridica> listarTodasPessoasJuridicas() {
        return pessoaJuridicaRepository.findAll();
    }

    @Override
    public Usuario buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));
    }
}