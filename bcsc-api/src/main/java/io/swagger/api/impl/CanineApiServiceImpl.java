package io.swagger.api.impl;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.petezybrick.bcsc.api.CvtVoToModel;
import com.petezybrick.bcsc.service.database.LotCanineDao;
import com.petezybrick.bcsc.service.database.LotTreeVo;

import io.swagger.api.CanineApiService;
import io.swagger.api.NotFoundException;
import io.swagger.model.LotTreeItem;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-04-20T00:28:28.441Z")
public class CanineApiServiceImpl extends CanineApiService {
    @Override
    public Response findLotTree( @NotNull String lotNumber, SecurityContext securityContext) throws NotFoundException {
    	LotTreeItem lotTreeItem = null;
        try { 
        	lotTreeItem = new LotTreeItem();
        	LotTreeVo lotTreeVo = LotCanineDao.findLotTree(lotNumber);
        	if( lotTreeVo != null ) {
        		lotTreeItem = CvtVoToModel.cvtLotTree(lotTreeVo);
        	}
        } catch(Exception e ) {
        	throw new NotFoundException(8, e.getMessage());
        }        
        return Response.ok(lotTreeItem).build();
    }
    
    @Override
    public Response findManLots( String fromDate,  String toDate,  Integer range, SecurityContext securityContext) throws NotFoundException {
        System.out.println( String.format("findManLots fromDate %s, toDate %s, range %d", fromDate, toDate, range));
        List<String> manLots = null;
        try { 
        	manLots = LotCanineDao.findAllLotNumbers();
        } catch(Exception e ) {
        	throw new NotFoundException(8, e.getMessage());
        }
        return Response.ok(manLots).build();
    }
}
