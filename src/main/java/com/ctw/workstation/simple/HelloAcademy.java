package com.ctw.workstation.simple;

import com.ctw.workstation.external.ExternalApi;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class HelloAcademy {

    @Inject
    @RestClient
    ExternalApi externalApi;

    public String SayHello(String name) {
        if (name != null) {
            return "Hello " + name;
        }
        else return "Hello";
    }
}

