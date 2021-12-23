package br.com.guini.java.eleven;

import org.junit.jupiter.api.Test;

interface MyFunctionalInterface {
    void invokeMethodReference(int a);
}

class MethodReferenceTest {

    private void print(int value) {
        System.out.println(value);
    }

    @Test
    void test() {
        class Inner {
            MyFunctionalInterface createMethodReference() {
                print(12);
                return MethodReferenceTest.this::print;
            }
        }
        MyFunctionalInterface fi = new Inner().createMethodReference();
        fi.invokeMethodReference(1);
    }

}
