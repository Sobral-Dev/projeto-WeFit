package wefit.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CNPJValidatorTest {

    private CNPJValidator cnpjValidator;

    @BeforeEach
    void setUp() {
        cnpjValidator = new CNPJValidator();
    }

    @Test
    void isValid_validCnpj_returnsTrue() {
        assertTrue(cnpjValidator.isValid("88508547000143", null));
        assertTrue(cnpjValidator.isValid("88.508.547/0001-43", null));
    }

    @Test
    void isValid_cnpjWithIncorrectFirstDigit_returnsFalse() {
        assertFalse(cnpjValidator.isValid("11222333000180", null));
    }

    @Test
    void isValid_cnpjWithIncorrectSecondDigit_returnsFalse() {
        assertFalse(cnpjValidator.isValid("11222333000101", null));
    }

    @Test
    void isValid_cnpjTooShort_returnsFalse() {
        assertFalse(cnpjValidator.isValid("1122233300018", null));
    }

    @Test
    void isValid_cnpjTooLong_returnsFalse() {
        assertFalse(cnpjValidator.isValid("112223330001819", null));
    }

    @Test
    void isValid_cnpjWithLetters_returnsFalse() {
        assertFalse(cnpjValidator.isValid("1122233300018A", null));
    }

    @Test
    void isValid_cnpjWithAllSameDigits_returnsFalse() {
        assertFalse(cnpjValidator.isValid("11111111111111", null));
    }

    @Test
    void isValid_nullCnpj_returnsTrue() {
        assertTrue(cnpjValidator.isValid(null, null));
    }

    @Test
    void isValid_emptyCnpj_returnsTrue() {
        assertTrue(cnpjValidator.isValid("", null));
    }

    @Test
    void isValid_blankCnpj_returnsTrue() {
        assertTrue(cnpjValidator.isValid("   ", null));
    }
}