package wefit.controller;

import wefit.dto.PessoaJuridicaDTO;
import wefit.service.PessoaJuridicaCreationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/pessoas-juridicas")
public class PessoaJuridicaController {

    private final PessoaJuridicaCreationService pessoaJuridicaCreationService;

    public PessoaJuridicaController(PessoaJuridicaCreationService pessoaJuridicaCreationService) {
        this.pessoaJuridicaCreationService = pessoaJuridicaCreationService;
    }

    @PostMapping
    public ResponseEntity<String> cadastrarPessoaJuridica(@Valid @RequestBody PessoaJuridicaDTO pessoaJuridicaDTO) {
        pessoaJuridicaCreationService.cadastrarPessoaJuridica(pessoaJuridicaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Pessoa Jurídica cadastrada com sucesso!");
    }
}
