/**
package com.ctw.workstation.simple;

import org.assertj.core.api.Assertions;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HelloAcademyTest {

    private static final Logger logger = Logger.getLogger(HelloAcademyTest.class);

    HelloAcademy helloAcademy;

    @BeforeAll
    void beforeAll() {
        helloAcademy = new HelloAcademy();
        logger.info("before all");
    }

    @BeforeEach
    void setUp() {
        logger.info("before each");
    }

    @BeforeEach
    void beforeEach() {
        logger.info("before each");
    }

    @AfterEach
    void afterEach() {
        logger.info("after each");
    }

    @AfterAll
    void afterAll() {
        logger.info("after all");
    }

    public static Stream<Arguments> provide_name() {
        return Stream.of(
                Arguments.of("Rennan", "Hello Rennan"),
                Arguments.of("João", "Hello João"),
                Arguments.of("Maria", "Hello Maria")
        );
    }

    @Order(1)
    @Test
    @DisplayName("When providing a null name to say hello just \"Hello\" should be returned")
    void provide_null_name() {

        String name = null;
        // when
       // String actualName = helloAcademy.SayHello(name);
        // then
       // assertEquals("Hello",actualName, "Returned name should be just Hello when providing a null name");


    }

    @Order(2)
    @ParameterizedTest
    @MethodSource(value = "provide_name")
    @DisplayName("When providing a name to say hello just \"Hello name\" should be returned")
    void provide_name(String name, String expected){

        // When
        //String actualName = helloAcademy.SayHello(name);
        // Then
        assertAll(
            //    () -> assertNotNull(actualName),
               // () -> assertEquals(expected, actualName, "Returned name should be Hello Fred when providing a name")
        );

    //    Assertions.assertThat(actualName)
             //   .isNotNull()
//.isNotEmpty()
              //  .startsWith("Hello");

    }

    @Order(3)
    @Test
    @DisplayName("When providing a empty name to say hello just \"Hello \" when providing a empty name")
    void provide_empty_name(){

        String name = "";
        // When
       // String actualName = helloAcademy.SayHello(name);
        // Then
        //assertEquals("Hello ", actualName, "Returned name should be just Hello when providing a empty name");
    }

}
 **/