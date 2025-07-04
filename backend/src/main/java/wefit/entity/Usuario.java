package wefit.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome não pode estar em branco")
    private String nome;

    @NotBlank(message = "O celular não pode estar em branco")
    @Pattern(regexp = "\\d{10,11}", message = "Celular inválido. Deve conter 10 ou 11 dígitos.")
    private String celular;

    @Pattern(regexp = "\\d{10,11}", message = "Telefone inválido. Deve conter 10 ou 11 dígitos.",
            groups = {jakarta.validation.groups.Default.class})
    private String telefone;

    @NotBlank(message = "O email não pode estar em branco")
    @Email(message = "Email inválido")
    @Column(unique = true)
    private String email;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    @JsonIgnore
    private Endereco endereco;

    protected Usuario(String nome, String celular, String telefone, String email) {
        this.nome = nome;
        this.celular = celular;
        this.telefone = telefone;
        this.email = email;
    }
}