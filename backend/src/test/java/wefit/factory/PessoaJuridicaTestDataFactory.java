package wefit.factory;

import wefit.dto.EnderecoDTO;
import wefit.dto.PessoaJuridicaDTO;

public class PessoaJuridicaTestDataFactory {

    public static PessoaJuridicaDTO createValidPessoaJuridicaDTO(String nome, String email, String cnpj, String cpfResponsavel, String celular, EnderecoDTO enderecoDTO) {
        PessoaJuridicaDTO pessoaJuridicaDTO = new PessoaJuridicaDTO();
        pessoaJuridicaDTO.setNome(nome);
        pessoaJuridicaDTO.setEmail(email);
        pessoaJuridicaDTO.setCnpj(cnpj);
        pessoaJuridicaDTO.setCpfResponsavel(cpfResponsavel);
        pessoaJuridicaDTO.setCelular(celular);
        pessoaJuridicaDTO.setEndereco(enderecoDTO);
        return pessoaJuridicaDTO;
    }

    public static PessoaJuridicaDTO createInvalidCnpjPessoaJuridicaDTO(String nome, String email, String cnpj, String cpfResponsavel, String celular, EnderecoDTO enderecoDTO) {
        PessoaJuridicaDTO pessoaJuridicaDTO = new PessoaJuridicaDTO();
        pessoaJuridicaDTO.setNome(nome);
        pessoaJuridicaDTO.setEmail(email);
        pessoaJuridicaDTO.setCnpj(cnpj);
        pessoaJuridicaDTO.setCpfResponsavel(cpfResponsavel);
        pessoaJuridicaDTO.setCelular(celular);
        pessoaJuridicaDTO.setEndereco(enderecoDTO);
        return pessoaJuridicaDTO;
    }

    public static PessoaJuridicaDTO createMissingFieldsPessoaJuridicaDTO(String cnpj, String cpfResponsavel, EnderecoDTO enderecoDTO) {
        PessoaJuridicaDTO pessoaJuridicaDTO = new PessoaJuridicaDTO();
        pessoaJuridicaDTO.setCnpj(cnpj);
        pessoaJuridicaDTO.setCpfResponsavel(cpfResponsavel);
        pessoaJuridicaDTO.setEndereco(enderecoDTO);
        return pessoaJuridicaDTO;
    }
}
