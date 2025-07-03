package wefit.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CEPValidatorTest {

    private CEPValidator cepValidator;

    @BeforeEach
    void setUp() {
        cepValidator = new CEPValidator();
    }

    @Test
    void isValid_validCep_returnsTrue() {
        assertTrue(cepValidator.isValid("12345678", null));
        assertTrue(cepValidator.isValid("00000000", null));
        assertTrue(cepValidator.isValid("99999999", null));
    }

    @Test
    void isValid_cepWithMask_returnsTrue() {
        assertTrue(cepValidator.isValid("12345-678", null));
    }

    @Test
    void isValid_cepWithLetters_returnsFalse() {
        assertFalse(cepValidator.isValid("1234567A", null));
    }

    @Test
    void isValid_cepTooShort_returnsFalse() {
        assertFalse(cepValidator.isValid("1234567", null));
    }

    @Test
    void isValid_cepTooLong_returnsFalse() {
        assertFalse(cepValidator.isValid("123456789", null));
    }

    @Test
    void isValid_nullCep_returnsTrue() {
        assertTrue(cepValidator.isValid(null, null));
    }

    @Test
    void isValid_emptyCep_returnsTrue() {
        assertTrue(cepValidator.isValid("", null));
    }

    @Test
    void isValid_blankCep_returnsTrue() {
        assertTrue(cepValidator.isValid("   ", null));
    }
}