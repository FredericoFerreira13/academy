package com.ctw.workstation.simple;

public class ExternalMessageServiceImpl implements ExternalMessageService {



    @Override
    public String sayHelloFromOuterSpace(String name) {
        doSomethingPrivately();
        return "Hello from outer space " + name ;
    }

    @Override
    public String sayHelloFromOuterSpace() {
        doSomethingPrivately();
        return "Hello from outer space";
    }

    @Override
    public void fazAlgo() {
        throw new RuntimeException();
    }

    private void doSomethingPrivately() {
    }
}
