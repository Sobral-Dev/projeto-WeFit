package wefit.service;

import wefit.entity.Usuario;
import wefit.entity.PessoaFisica;
import wefit.entity.PessoaJuridica;

import java.util.List;

public interface UsuarioQueryService {
    List<Usuario> listarTodosUsuarios();
    List<PessoaFisica> listarTodasPessoasFisicas();
    List<PessoaJuridica> listarTodasPessoasJuridicas();
    Usuario buscarUsuarioPorId(Long id);
}
