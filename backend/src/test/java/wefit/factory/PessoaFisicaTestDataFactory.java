package wefit.factory;

import wefit.dto.EnderecoDTO;
import wefit.dto.PessoaFisicaDTO;

public class PessoaFisicaTestDataFactory {

    public static PessoaFisicaDTO createValidPessoaFisicaDTO(String nome, String email, String cpf, String celular, EnderecoDTO enderecoDTO) {
        PessoaFisicaDTO pessoaFisicaDTO = new PessoaFisicaDTO();
        pessoaFisicaDTO.setNome(nome);
        pessoaFisicaDTO.setEmail(email);
        pessoaFisicaDTO.setCpf(cpf);
        pessoaFisicaDTO.setCelular(celular);
        pessoaFisicaDTO.setEndereco(enderecoDTO);
        return pessoaFisicaDTO;
    }

    public static PessoaFisicaDTO createInvalidCpfPessoaFisicaDTO(String nome, String email, String cpf, String celular, EnderecoDTO enderecoDTO) {
        PessoaFisicaDTO pessoaFisicaDTO = new PessoaFisicaDTO();
        pessoaFisicaDTO.setNome(nome);
        pessoaFisicaDTO.setEmail(email);
        pessoaFisicaDTO.setCpf(cpf);
        pessoaFisicaDTO.setCelular(celular);
        pessoaFisicaDTO.setEndereco(enderecoDTO);
        return pessoaFisicaDTO;
    }

    public static PessoaFisicaDTO createMissingFieldsPessoaFisicaDTO(String cpf, EnderecoDTO enderecoDTO) {
        PessoaFisicaDTO pessoaFisicaDTO = new PessoaFisicaDTO();
        pessoaFisicaDTO.setCpf(cpf);
        pessoaFisicaDTO.setEndereco(enderecoDTO);
        return pessoaFisicaDTO;
    }
}