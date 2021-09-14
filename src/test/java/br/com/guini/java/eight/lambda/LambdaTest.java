package br.com.guini.java.eight.lambda;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

interface AgeOfMajority {
    boolean test(int age);
}

interface CountryAgeOfMajority {
    boolean test(int age, Country country);
}

class AgeOfMajorityBusiness {
    private final AgeOfMajority ageOfMajority;

    public AgeOfMajorityBusiness(AgeOfMajority ageOfMajority) {
        this.ageOfMajority = ageOfMajority;
    }

    public boolean test(int age) {
        return ageOfMajority.test(age);
    }
}

enum Country {
    BRAZIL,
    USA
}

class LambdaTest {

    @Test
    void method_call_without_lambda() {
        AgeOfMajority AgeOfMajorityInBrazil = new AgeOfMajority() {
            @Override
            public boolean test(int age) {
                return age >= 18;
            }
        };

        assertTrue(AgeOfMajorityInBrazil.test(18));
        assertTrue(AgeOfMajorityInBrazil.test(19));
        assertFalse(AgeOfMajorityInBrazil.test(17));
    }
    
    @Test
    void method_call_with_lambda() {
        AgeOfMajority ageOfMajorityInUSA = age -> age >= 16;
        assertTrue(ageOfMajorityInUSA.test(16));
        assertTrue(ageOfMajorityInUSA.test(17));
        assertFalse(ageOfMajorityInUSA.test(15));
    }

    @Test
    void method_call_with_two_parameters_with_lambda() {
        CountryAgeOfMajority ageOfMajority = (age, country) -> (age >= 18 && Country.BRAZIL.equals(country)) || (age >= 16 && Country.USA.equals(country));
        assertFalse(ageOfMajority.test(15, Country.USA));
        assertFalse(ageOfMajority.test(17, Country.BRAZIL));
    }

    @Test
    void specifying_parameter_type() {
        CountryAgeOfMajority ageOfMajority = (int age, Country country) -> (age >= 18 && Country.BRAZIL.equals(country)) || (age >= 16 && Country.USA.equals(country));
        assertFalse(ageOfMajority.test(15, Country.USA));
        assertFalse(ageOfMajority.test(17, Country.BRAZIL));
    }

    @Test
    void passing_lambda_expressions_to_methods() {
        AgeOfMajorityBusiness business = new AgeOfMajorityBusiness((age) -> age >= 18);
        assertTrue(business.test(18));
        assertFalse(business.test(17));
    }

}
