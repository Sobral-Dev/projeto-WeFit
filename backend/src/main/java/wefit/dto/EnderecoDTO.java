package wefit.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import wefit.validation.CEP;

@Data
public class EnderecoDTO {
    @CEP(message = "CEP inválido.")
    @NotBlank(message = "O CEP não pode estar em branco")
    private String cep;

    @NotBlank(message = "O logradouro não pode estar em branco")
    private String logradouro;

    @NotBlank(message = "O número não pode estar em branco")
    private String numero;

    private String complemento;

    @NotBlank(message = "A cidade não pode estar em branco")
    private String cidade;

    @NotBlank(message = "O bairro não pode estar em branco")
    private String bairro;

    @NotBlank(message = "O estado não pode estar em branco")
    private String estado;
}