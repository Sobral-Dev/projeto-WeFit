package wefit.service;

import wefit.dto.PessoaJuridicaDTO;
import wefit.entity.PessoaJuridica;

public interface PessoaJuridicaCreationService {
    PessoaJuridica cadastrarPessoaJuridica(PessoaJuridicaDTO pessoaJuridicaDTO);
}
