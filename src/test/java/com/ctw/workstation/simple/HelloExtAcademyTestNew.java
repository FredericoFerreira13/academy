package com.ctw.workstation.simple;

import com.ctw.workstation.config.CommodoreTestConfig;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;
/**
@QuarkusTest
@QuarkusTestResource(CommodoreTestConfig.class)
class HelloExtAcademyTestNew {

    @Inject
    HelloExtAcademy helloExtAcademy;


    @Test
    @DisplayName("Say hello with valid name")
    void helloWithValidName() {
        // given
        String name = null;
        stubFor(post(urlEqualTo("/external/hello"))
                .willReturn(aResponse()
                        .withHeader("Content_Type", "application/json")
                .withStatus(200)
                .withBody("{\"message\":\"Hello rennan!\"}");
        //when
        String actualHello = helloExtAcademy.sayHello(name);
        //then
        Assertions.assertThat(actualHello)
                .isNotNull();
    }

}**/