package io.swagger.api;

import io.swagger.api.*;
import io.swagger.model.*;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import io.swagger.model.LotTreeItem;

import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-04-21T14:06:47.951Z")
public abstract class CanineApiService {
    public abstract Response findLotTree( @NotNull String lotNumber,SecurityContext securityContext) throws NotFoundException;
    public abstract Response findManLots( String fromDate, String toDate, Integer range,SecurityContext securityContext) throws NotFoundException;
}
