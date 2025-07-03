package wefit.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioCadastradoEventDTO {
    private Long id;
    private String tipoUsuario;
    private String nome;
    private String email;
    private String documento;
}
