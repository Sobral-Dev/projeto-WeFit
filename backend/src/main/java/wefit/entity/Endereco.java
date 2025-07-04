package wefit.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import wefit.validation.CEP;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    // Construtor para facilitar a criação de Endereço
    public Endereco(String cep, String logradouro, String numero, String complemento, String cidade, String bairro, String estado) {
        this.cep = cep;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.cidade = cidade;
        this.bairro = bairro;
        this.estado = estado;
    }
}