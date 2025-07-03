package wefit.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CPFValidatorTest {

    private CPFValidator cpfValidator;

    @BeforeEach
    void setUp() {
        cpfValidator = new CPFValidator();
    }

    @Test
    void isValid_validCpf_returnsTrue() {
        assertTrue(cpfValidator.isValid("71674004087", null));
        assertTrue(cpfValidator.isValid("716.740.040-87", null));
    }

    @Test
    void isValid_cpfWithIncorrectFirstDigit_returnsFalse() {
        assertFalse(cpfValidator.isValid("12345678910", null));
    }

    @Test
    void isValid_cpfWithIncorrectSecondDigit_returnsFalse() {
        assertFalse(cpfValidator.isValid("12345678900", null));
    }

    @Test
    void isValid_cpfTooShort_returnsFalse() {
        assertFalse(cpfValidator.isValid("1234567890", null));
    }

    @Test
    void isValid_cpfTooLong_returnsFalse() {
        assertFalse(cpfValidator.isValid("123456789012", null));
    }

    @Test
    void isValid_cpfWithLetters_returnsFalse() {
        assertFalse(cpfValidator.isValid("1234567890A", null));
    }

    @Test
    void isValid_nullCpf_returnsTrue() {
        assertTrue(cpfValidator.isValid(null, null));
    }

    @Test
    void isValid_emptyCpf_returnsTrue() {
        assertTrue(cpfValidator.isValid("", null));
    }

    @Test
    void isValid_blankCpf_returnsTrue() {
        assertTrue(cpfValidator.isValid("   ", null));
    }
}
