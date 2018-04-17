package io.swagger.api;

import io.swagger.api.*;
import io.swagger.model.*;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import java.util.Map;
import io.swagger.model.Order;

import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-04-14T14:11:09.948Z")
public abstract class StoreApiService {
    public abstract Response deleteOrder( @Min(1)Long orderId,SecurityContext securityContext) throws NotFoundException;
    public abstract Response getInventory(SecurityContext securityContext) throws NotFoundException;
    public abstract Response getOrderById( @Min(1) @Max(10)Long orderId,SecurityContext securityContext) throws NotFoundException;
    public abstract Response placeOrder(Order body,SecurityContext securityContext) throws NotFoundException;
}
