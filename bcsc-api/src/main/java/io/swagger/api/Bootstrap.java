package io.swagger.api;

import java.security.Security;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.petezybrick.bcsc.common.config.SupplyBlockchainConfig;
import com.petezybrick.bcsc.service.database.SupplierDataSource;

import io.swagger.jaxrs.config.SwaggerContextService;
import io.swagger.models.Contact;
import io.swagger.models.Info;
import io.swagger.models.License;
import io.swagger.models.Swagger;

public class Bootstrap extends HttpServlet {
  @Override
  public void init(ServletConfig config) throws ServletException {
    Info info = new Info()
      .title("Swagger Server")
      .description("Pet Nutrition")
      .termsOfService("http://github/petezybrick/terms/$TODO")
      .contact(new Contact()
        .email("pzybrick@gmail.com"))
      .license(new License()
        .name("Apache 2.0")
        .url("http://www.apache.org/licenses/LICENSE-2.0.html"));

    ServletContext context = config.getServletContext();
    Swagger swagger = new Swagger().info(info);

    new SwaggerContextService().withServletConfig(config).updateSwagger(swagger);
    
    try {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		SupplyBlockchainConfig supplyBlockchainConfig = 
				SupplyBlockchainConfig.getInstance( System.getenv("ENV"), System.getenv("CONTACT_POINT"),System.getenv("KEYSPACE_NAME") );
		SupplierDataSource.getInstance(supplyBlockchainConfig);
    } catch(Exception e ) {
    	throw new ServletException(e);
    }

  }
}
