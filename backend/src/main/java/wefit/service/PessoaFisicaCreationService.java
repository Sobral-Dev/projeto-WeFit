package wefit.service;

import wefit.dto.PessoaFisicaDTO;
import wefit.entity.PessoaFisica;

public interface PessoaFisicaCreationService {
    PessoaFisica cadastrarPessoaFisica(PessoaFisicaDTO pessoaFisicaDTO);
}