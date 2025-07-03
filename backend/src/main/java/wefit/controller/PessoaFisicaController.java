package wefit.controller;

import wefit.dto.PessoaFisicaDTO;
import wefit.service.PessoaFisicaCreationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/pessoas-fisicas")
public class PessoaFisicaController {

    private final PessoaFisicaCreationService pessoaFisicaCreationService;

    public PessoaFisicaController(PessoaFisicaCreationService pessoaFisicaCreationService) {
        this.pessoaFisicaCreationService = pessoaFisicaCreationService;
    }

    @PostMapping
    public ResponseEntity<String> cadastrarPessoaFisica(@Valid @RequestBody PessoaFisicaDTO pessoaFisicaDTO) {
        pessoaFisicaCreationService.cadastrarPessoaFisica(pessoaFisicaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Pessoa Física cadastrada com sucesso!");
    }
}
