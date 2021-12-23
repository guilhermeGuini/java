package br.com.guini.java.eleven;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

interface MathOperation {
    int operation(int value1, int value2);
}

class LocalVariableSyntaxLambdaParametersTest {

    @Test
    void testAllParametersAsVarInLambda() {
        MathOperation operation = (var value1, var value2) -> value1 + value2;
        assertEquals(4, operation.operation(2, 2));
    }

    @Test
    void testAllImplicitlyParametersInLambda() {
        MathOperation operation = (value1, value2) -> value1 + value2;
        assertEquals(7, operation.operation(3, 4));
    }

    @Test
    void testErrorWithMixedVarAndImplicitlyParams() {
//        MathOperation operation = (var value1, value2) -> value1 + value2;
//        Cannot resolved symbol value2
    }

    @Test
    void testErrorWithMixedVarAndTypedParams() {
//        MathOperation operation = (var value1, int value2) -> value1 + value2;
//        Cannot mixed var and explicitly param in lambda expression
    }
}
