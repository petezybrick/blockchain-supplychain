package io.swagger.api.factories;

import io.swagger.api.CanineApiService;
import io.swagger.api.impl.CanineApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-04-20T00:28:28.441Z")
public class CanineApiServiceFactory {
    private final static CanineApiService service = new CanineApiServiceImpl();

    public static CanineApiService getCanineApi() {
        return service;
    }
}
