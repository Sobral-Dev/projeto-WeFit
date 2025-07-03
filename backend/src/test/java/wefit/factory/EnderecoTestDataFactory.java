package wefit.factory;

import wefit.dto.EnderecoDTO;

public class EnderecoTestDataFactory {

    public static EnderecoDTO createValidEnderecoDTO(String cep, String logradouro, String numero, String complemento, String cidade, String bairro, String estado) {
        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setCep(cep);
        enderecoDTO.setLogradouro(logradouro);
        enderecoDTO.setNumero(numero);
        enderecoDTO.setComplemento(complemento);
        enderecoDTO.setCidade(cidade);
        enderecoDTO.setBairro(bairro);
        enderecoDTO.setEstado(estado);
        return enderecoDTO;
    }

    public static EnderecoDTO createInvalidCepEnderecoDTO() {
        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setCep("123");
        enderecoDTO.setLogradouro("Rua Invalida");
        enderecoDTO.setNumero("0");
        enderecoDTO.setCidade("Cidade Invalida");
        enderecoDTO.setBairro("Bairro Invalido");
        enderecoDTO.setEstado("IV");
        return enderecoDTO;
    }

    public static EnderecoDTO createMissingFieldsEnderecoDTO() {
        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setCep("12345678");
        return enderecoDTO;
    }
}