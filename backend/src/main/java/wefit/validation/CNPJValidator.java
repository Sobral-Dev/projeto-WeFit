package wefit.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CNPJValidator implements ConstraintValidator<CNPJ, String> {

    @Override
    public void initialize(CNPJ constraintAnnotation) {
        // No initialization needed for this validator.
    }

    @Override
    public boolean isValid(String cnpj, ConstraintValidatorContext context) {
        if (cnpj == null || cnpj.trim().isEmpty()) {
            return true;
        }

        String cnpjLimpo = cnpj.replaceAll("\\D", "");

        if (cnpjLimpo.length() != 14) {
            log.error("CNPJ inválido: O CNPJ deve conter 14 dígitos numéricos. CNPJ fornecido: {}", cnpj);
            return false;
        }

        if (cnpjLimpo.matches("(\\d)\\1{13}")) {
            log.error("CNPJ inválido: Todos os dígitos são iguais. CNPJ fornecido: {}", cnpj);
            return false;
        }

        int[] pesosPrimeiroDigito = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int soma = 0;
        for (int i = 0; i < 12; i++) {
            soma += Character.getNumericValue(cnpjLimpo.charAt(i)) * pesosPrimeiroDigito[i];
        }

        int resto = soma % 11;
        int digitoVerificador1Calculado = (resto < 2) ? 0 : (11 - resto);

        if (Character.getNumericValue(cnpjLimpo.charAt(12)) != digitoVerificador1Calculado) {
            log.error("CNPJ inválido: Primeiro dígito verificador incorreto. CNPJ fornecido: {}", cnpj);
            return false;
        }

        int[] pesosSegundoDigito = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        soma = 0;
        for (int i = 0; i < 13; i++) {
            soma += Character.getNumericValue(cnpjLimpo.charAt(i)) * pesosSegundoDigito[i];
        }

        resto = soma % 11;
        int digitoVerificador2Calculado = (resto < 2) ? 0 : (11 - resto);

        boolean isValid = Character.getNumericValue(cnpjLimpo.charAt(13)) == digitoVerificador2Calculado;

        if (!isValid) {
            log.error("CNPJ inválido: Segundo dígito verificador incorreto. CNPJ fornecido: {}", cnpj);
        }

        return isValid;
    }
}
