package com.ctw.workstation.simple;

import io.quarkus.test.junit.mockito.InjectSpy;
import jakarta.inject.Inject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
/**
@ExtendWith(MockitoExtension.class)
class HelloExtAcademyTest {

    @Spy
    ExternalMessageServiceImpl externalMessageServiceSpy;

    @InjectMocks
    HelloExtAcademy helloExtAcademy;

    CarService carService;

    @BeforeEach
    void setUp() {
        // carService = Mockito.mock(CarService.class);
        //externalMessageServiceSpy = spy(new ExternalMessageServiceImpl(carService));
        //helloExtAcademy = new HelloExtAcademy(externalMessageServiceSpy);
    }

    @Test
    @DisplayName("When providing a null name a message from outer space should be returned")
    void when_providing_a_null_name() {
        //given
        String name = null;
        Mockito.doReturn("Hey Rennan")
                .when(externalMessageServiceSpy)
                .sayHelloFromOuterSpace(name);
       // ExternalMessageService externalMessageServiceMock = Mockito.mock(ExternalMessageService.class);
       // helloExtAcademy.externalMessageService = externalMessageServiceMock;
        //when
       // Mockito.when(externalMessageServiceSpy.sayHelloFromOuterSpace()).thenReturn("Hello from outer space");
        String actualName = helloExtAcademy.SayHello(name);
        // Then
        Assertions.assertThat(actualName)
                .as("valid message is returned even when provided a null name")
                .isEqualTo("Hello from outer space");

    }

    @Test
    @DisplayName("When providing a valid name a hello from outer space for the given name is returned")
    void when_providing_a_valid_name_a_hello_from_outer_space() {
        //given
        String name = "Rennan";
        ExternalMessageService externalMessageServiceMock = Mockito.mock(ExternalMessageService.class);
        helloExtAcademy.externalMessageService = externalMessageServiceMock;
        when(externalMessageServiceMock.sayHelloFromOuterSpace(name)).thenReturn("Hello Rennan");
        //When
        String actualName = helloExtAcademy.SayHello(name);
        //then
        Assertions.assertThat(actualName)
                .isEqualTo("Hello Rennan");
    }

    @Test
    void spy_test(){
        List<String> spyed_list = spy(new ArrayList<String>());

        //when(spyed_list.get(20)).thenReturn("Hello");

          doReturn("Hello").when(spyed_list).get(0);

          System.out.println(spyed_list.get(0));


    }

    @Test
    void new_when_providing_null(){
        String name = null;

        when(externalMessageServiceSpy.sayHelloFromOuterSpace()).thenReturn("Hello from outer space");

        String actualName = helloExtAcademy.SayHello(name);

        Assertions.assertThat(actualName)
                .as("valid message is returned even when provided a null name")
                .isEqualTo("Hello from outer space");

        //Mockito.verify(carService).doSomething("PARAM");

    }

    @Test
    void exception(){
        String name = null;
        doThrow(NullPointerException.class)
                .when(externalMessageServiceSpy)
                .sayHelloFromOuterSpace();



    }

    @Test
    void new_test(){

        String name = null;

        doNothing().when(externalMessageServiceSpy).fazAlgo();

        String actualName = helloExtAcademy.SayHello(name);




    }


}**/