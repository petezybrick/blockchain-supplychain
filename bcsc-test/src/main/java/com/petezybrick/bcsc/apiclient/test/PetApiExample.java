package com.petezybrick.bcsc.apiclient.test;

import java.util.ArrayList;
import java.util.List;

import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.PetApi;
import io.swagger.client.auth.OAuth;
import io.swagger.client.model.Pet;

public class PetApiExample {

    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath("http://localhost:8092/bcsc-webapi/v2");
        
        // Configure OAuth2 access token for authorization: petstore_auth
        OAuth petstore_auth = (OAuth) apiClient.getAuthentication("petstore_auth");
        petstore_auth.setAccessToken("special-key");

        PetApi apiInstance = new PetApi(apiClient);
        Pet body = new Pet(); // Pet | Pet object that needs to be added to the store
        try {
        	List<String> statuss = new ArrayList<String>();
        	statuss.add("fatty");
        	statuss.add("beggar");
        	List<Pet> pets = apiInstance.findPetsByStatus(statuss);
        	System.out.println(pets);
        	System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");
        	Pet pet = apiInstance.getPetById(111L);
        	System.out.println(pet);
        } catch (ApiException e) {
            System.err.println("Exception when calling PetApi#addPet");
            e.printStackTrace();
        }
    }
}
