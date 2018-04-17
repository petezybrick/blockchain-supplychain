package io.swagger.api.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import io.swagger.api.ApiResponseMessage;
import io.swagger.api.NotFoundException;
import io.swagger.api.PetApiService;
import io.swagger.model.Pet;
import io.swagger.model.Order.StatusEnum;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-04-14T14:11:09.948Z")
public class PetApiServiceImpl extends PetApiService {
    @Override
    public Response addPet(Pet body, SecurityContext securityContext) throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
    @Override
    public Response deletePet(Long petId, String apiKey, SecurityContext securityContext) throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
    @Override
    public Response findPetsByStatus( @NotNull List<String> status, SecurityContext securityContext) throws NotFoundException {
        System.out.println(status);
    	List<Pet> pets = new ArrayList<Pet>();
    	Pet pet = new Pet();
    	pet.setId(111L);
    	pet.setName("Aimee");
    	pets.add(pet);    	
    	pet = new Pet();
    	pet.setId(222L);
    	pet.setName("OyeOye");
    	pets.add(pet);
        return Response.ok(pets).build();
    }
    @Override
    public Response findPetsByTags( @NotNull List<String> tags, SecurityContext securityContext) throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
    @Override
    public Response getPetById(Long petId, SecurityContext securityContext) throws NotFoundException {
    	System.out.println("+++++ " + petId);
    	Pet pet = new Pet();
    	pet.setId(111L);
    	pet.setName("Aimee");
    	pet.setStatus(Pet.StatusEnum.SOLD);
        return Response.ok(pet).build();
        //return Response.ok("abc").entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
    @Override
    public Response updatePet(Pet body, SecurityContext securityContext) throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
    @Override
    public Response updatePetWithForm(Long petId, String name, String status, SecurityContext securityContext) throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
    @Override
    public Response uploadFile(Long petId, String additionalMetadata, InputStream fileInputStream, FormDataContentDisposition fileDetail, SecurityContext securityContext) throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
}
