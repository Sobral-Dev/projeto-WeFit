package wefit.dto;

import lombok.Data;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotNull;
import wefit.validation.CNPJ;
import wefit.validation.CPF;

@Data
public class PessoaJuridicaDTO {
    @NotBlank(message = "O nome não pode estar em branco")
    private String nome;

    @NotBlank(message = "O celular não pode estar em branco")
    @Pattern(regexp = "\\d{10,11}", message = "Celular inválido. Deve conter 10 ou 11 dígitos.")
    private String celular;

    @Pattern(regexp = "\\d{10,11}", message = "Telefone inválido. Deve conter 10 ou 11 dígitos.")
    private String telefone;

    @NotBlank(message = "O email não pode estar em branco")
    @Email(message = "Email inválido")
    private String email;

    @CNPJ(message = "CNPJ inválido.")
    @NotBlank(message = "O CNPJ não pode estar em branco")
    private String cnpj;

    @CPF(message = "CPF do responsável inválido.")
    @NotBlank(message = "O CPF do responsável não pode estar em branco")
    private String cpfResponsavel;

    @NotNull(message = "O endereço não pode ser nulo")
    @Valid
    private EnderecoDTO endereco;
}