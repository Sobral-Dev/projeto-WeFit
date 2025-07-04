package wefit.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CEPValidator implements ConstraintValidator<CEP, String> {

    @Override
    public void initialize(CEP constraintAnnotation) {
        // No initialization needed for this validator.
    }

    @Override
    public boolean isValid(String cep, ConstraintValidatorContext context) {
        if (cep == null || cep.trim().isEmpty()) {
            return true;
        }

        String cepLimpo = cep.replaceAll("\\D", "");

        if (cepLimpo.length() != 8) {
            log.error("CEP inválido: O CEP deve conter 8 dígitos numéricos. CEP fornecido: {}", cep);
            return false;
        }

        return true;
    }
}