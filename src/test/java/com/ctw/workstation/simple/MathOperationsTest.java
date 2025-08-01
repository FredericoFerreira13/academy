/**
package com.ctw.workstation.simple;

import org.assertj.core.api.Assertions;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MathOperationsTest {

    private static final Logger logger = Logger.getLogger(HelloAcademyTest.class);

    MathOperations mathOperations;

    public Stream<Arguments> provide_ints_to_add() {
        return Stream.of(
                Arguments.of(2, 2, 4),
                Arguments.of(1, 0, 1),
                Arguments.of(5 ,6 ,11)
        );
    }

    public Stream<Arguments> provide_ints_to_divide() {
        return Stream.of(
                Arguments.of(2, 2, 1),
                Arguments.of(6, 3, 2)
        );
    }

    @BeforeAll
    void setUp(){
        logger.info("before all");
        mathOperations = new MathOperations();
    }

    @ParameterizedTest
    @MethodSource(value = "provide_ints_to_add")
    @DisplayName("When providing two int values to add a int result should be returned")
    void testAdd(int a, int b, int result) {

        // when
        int actualResult = mathOperations.add(a, b);

        // then
        assertEquals(result, actualResult);

    }

    @ParameterizedTest
    @MethodSource(value = "provide_ints_to_divide")
    @DisplayName("When providing two int values to divide a result should be returned")
    void testDivide(int a, int b, int result) {

        // when
        int actualResult = mathOperations.divide(a, b);

        // then
        assertEquals(result, actualResult);
    }

    @Test
    @DisplayName("When providing a value is divided by zero a exception should be returned")
    void testDivide_with_zero() {

        // then
        // junit
        //assertThrows(ArithmeticException.class, () -> mathOperations.divide(0, 0));

        // assertj
        Assertions.assertThatException().isThrownBy(() -> mathOperations.divide(0, 0));

    }



}
 **/