package wefit.controller;

import wefit.entity.Usuario;
import wefit.entity.PessoaFisica;
import wefit.entity.PessoaJuridica;
import wefit.service.UsuarioQueryService;
import wefit.service.UsuarioDeletionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/usuarios")
public class UsuarioQueryController {

    private final UsuarioQueryService usuarioQueryService;
    private final UsuarioDeletionService usuarioDeletionService;

    public UsuarioQueryController(UsuarioQueryService usuarioQueryService,
                                  UsuarioDeletionService usuarioDeletionService) {
        this.usuarioQueryService = usuarioQueryService;
        this.usuarioDeletionService = usuarioDeletionService;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodosUsuarios() {
        List<Usuario> usuarios = usuarioQueryService.listarTodosUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/pessoas-fisicas")
    public ResponseEntity<List<PessoaFisica>> listarTodasPessoasFisicas() {
        List<PessoaFisica> pessoasFisicas = usuarioQueryService.listarTodasPessoasFisicas();
        return ResponseEntity.ok(pessoasFisicas);
    }

    @GetMapping("/pessoas-juridicas")
    public ResponseEntity<List<PessoaJuridica>> listarTodasPessoasJuridicas() {
        List<PessoaJuridica> pessoasJuridicas = usuarioQueryService.listarTodasPessoasJuridicas();
        return ResponseEntity.ok(pessoasJuridicas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Long id) {
        Usuario usuario = usuarioQueryService.buscarUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarUsuario(@PathVariable Long id) {
        usuarioDeletionService.deletarUsuario(id);
        return ResponseEntity.ok("Usuário deletado com sucesso!");
    }
}
