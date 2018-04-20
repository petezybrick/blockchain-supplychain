package io.swagger.api.impl;

import io.swagger.api.*;
import io.swagger.model.*;

import io.swagger.model.LotTreeItem;

import java.util.Arrays;
import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import com.petezybrick.bcsc.service.database.LotCanineDao;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-04-20T00:28:28.441Z")
public class CanineApiServiceImpl extends CanineApiService {
    @Override
    public Response findLotIngreds( @NotNull String lotNumber, SecurityContext securityContext) throws NotFoundException {
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
    @Override
    public Response findManLots( String fromDate,  String toDate,  Integer range, SecurityContext securityContext) throws NotFoundException {
        System.out.println( String.format("findManLots fromDate %s, toDate %s, range %d", fromDate, toDate, range));
        List<String> manLots = null;
        try { 
        	manLots = LotCanineDao.sqlFindAllLotNumbers();
        } catch(Exception e ) {
        	throw new NotFoundException(8, e.getMessage());
        }
        return Response.ok(manLots).build();
    }
}
