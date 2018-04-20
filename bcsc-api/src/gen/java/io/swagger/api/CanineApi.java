package io.swagger.api;

import io.swagger.model.*;
import io.swagger.api.CanineApiService;
import io.swagger.api.factories.CanineApiServiceFactory;

import io.swagger.annotations.ApiParam;
import io.swagger.jaxrs.*;

import io.swagger.model.LotTreeItem;

import java.util.Map;
import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.servlet.ServletConfig;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;
import javax.validation.constraints.*;

@Path("/canine")


@io.swagger.annotations.Api(description = "the canine API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-04-20T00:28:28.441Z")
public class CanineApi  {
   private final CanineApiService delegate;

   public CanineApi(@Context ServletConfig servletContext) {
      CanineApiService delegate = null;

      if (servletContext != null) {
         String implClass = servletContext.getInitParameter("CanineApi.implementation");
         if (implClass != null && !"".equals(implClass.trim())) {
            try {
               delegate = (CanineApiService) Class.forName(implClass).newInstance();
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         } 
      }

      if (delegate == null) {
         delegate = CanineApiServiceFactory.getCanineApi();
      }

      this.delegate = delegate;
   }

    @GET
    @Path("/nutrition/findLotIngredsByManLotNumber")
    @Consumes({ "application/xml", "application/json", "multipart/form-data", "application/x-www-form-urlencoded" })
    @Produces({ "application/xml", "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Finds Lot Ingredients Blockchains by Manufacturer Lot Number", notes = "Finds Lot Ingredients Blockchains by Manufacturer Lot Number", response = LotTreeItem.class, responseContainer = "List", authorizations = {
        @io.swagger.annotations.Authorization(value = "petnutrition_auth", scopes = {
            @io.swagger.annotations.AuthorizationScope(scope = "write:pets", description = "modify pets in your account"),
            @io.swagger.annotations.AuthorizationScope(scope = "read:pets", description = "read your pets")
        })
    }, tags={ "lot","manufacturerLot", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "successful operation", response = LotTreeItem.class, responseContainer = "List"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Invalid status value", response = Void.class) })
    public Response findLotIngreds(@ApiParam(value = "Manufacturer Lot Number",required=true) @QueryParam("lotNumber") String lotNumber
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.findLotIngreds(lotNumber,securityContext);
    }
    @GET
    @Path("/nutrition/findAllManLots")
    @Consumes({ "application/xml", "application/json", "multipart/form-data", "application/x-www-form-urlencoded" })
    @Produces({ "application/xml", "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Finds Manufacturer Lot Numbers by Optional Date Range and Limit", notes = "Finds Manufacturer Lot Numbers by Optional Date Range and Limit", response = String.class, responseContainer = "List", authorizations = {
        @io.swagger.annotations.Authorization(value = "petnutrition_auth", scopes = {
            @io.swagger.annotations.AuthorizationScope(scope = "write:pets", description = "modify pets in your account"),
            @io.swagger.annotations.AuthorizationScope(scope = "read:pets", description = "read your pets")
        })
    }, tags={ "lot","manufacturerLot", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "successful operation", response = String.class, responseContainer = "List"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Invalid status value", response = Void.class) })
    public Response findManLots(@ApiParam(value = "Start date of range, inclusive") @QueryParam("fromDate") String fromDate
,@ApiParam(value = "End date of range, inclusive") @QueryParam("toDate") String toDate
,@ApiParam(value = "Maximum number of returned values") @QueryParam("range") Integer range
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.findManLots(fromDate,toDate,range,securityContext);
    }
}
