package wefit.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import wefit.validation.CNPJ;
import wefit.validation.CPF;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class PessoaJuridica extends Usuario {

    @CNPJ
    @NotBlank(message = "O CNPJ não pode estar em branco")
    @Column(unique = true)
    private String cnpj;

    @CPF
    @NotBlank(message = "O CPF do responsável não pode estar em branco")
    private String cpfResponsavel;

    public PessoaJuridica(String nome, String celular, String telefone, String email, String cnpj, String cpfResponsavel) {
        super(nome, celular, telefone, email);
        this.cnpj = cnpj;
        this.cpfResponsavel = cpfResponsavel;
    }
}