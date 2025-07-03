package wefit.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CPFValidator implements ConstraintValidator<CPF, String> {

    @Override
    public void initialize(CPF constraintAnnotation) {
    }

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        if (cpf == null || cpf.trim().isEmpty()) {
            return true;
        }

        String cpfLimpo = cpf.replaceAll("[^0-9]", "");

        if (cpfLimpo.length() != 11) {
            log.error("CPF inválido: O CPF deve conter 11 dígitos numéricos. CPF fornecido: {}", cpf);
            return false;
        }

        // Aplica o algoritmo de validação de CPF (lógica dos dígitos verificadores / os dois últimos dígitos)
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpfLimpo.charAt(i)) * (10 - i);
        }

        int resto = soma % 11;
        int digitoVerificador1 = (resto < 2) ? 0 : (11 - resto);

        if (Character.getNumericValue(cpfLimpo.charAt(9)) != digitoVerificador1) {
            log.error("CPF inválido: Primeiro dígito verificador incorreto. CPF fornecido: {}", cpf);
            return false;
        }

        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpfLimpo.charAt(i)) * (11 - i);
        }

        resto = soma % 11;
        int digitoVerificador2 = (resto < 2) ? 0 : (11 - resto);

        boolean isValid = Character.getNumericValue(cpfLimpo.charAt(10)) == digitoVerificador2;

        if (!isValid) {
            log.error("CPF inválido: Segundo dígito verificador incorreto. CPF fornecido: {}", cpf);
        }

        return isValid;
    }
}
