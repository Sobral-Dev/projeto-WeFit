package wefit.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import wefit.validation.CPF;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class PessoaFisica extends Usuario {

    @CPF
    @NotBlank(message = "O CPF não pode estar em branco")
    @Column(unique = true)
    private String cpf;

    public PessoaFisica(String nome, String celular, String telefone, String email, String cpf) {
        super(nome, celular, telefone, email);
        this.cpf = cpf;
    }
}