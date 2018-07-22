<%@ page import="java.util.Date" %>
<%@ page import="io.swagger.client.*,io.swagger.client.api.*,io.swagger.client.auth.*,io.swagger.client.model.*" %>
<%@ page import="com.petezybrick.bcsc.webui.LotTreeToDetailTree" %>
<%  String manLotNumber = request.getParameter("manLotNumber") != null ? request.getParameter("manLotNumber") : ""; %>
<html>
<head>
  <title>Canine Nutrition Lineage</title>
</head>
<body>
  <div style="font-family:Arial">
  <h3>Canine Nutrition Lineage</h3>
  <form method="post">
    Manufacturer Lot Number: <input type="text" name="manLotNumber" value="<%=manLotNumber%>">
    <input type="submit" value="Submit">
  </form>
 
  <%
	ApiClient apiClient = new ApiClient();
    apiClient.setBasePath("http://bcsc-webapi:8080/bcsc-webapi/v2");
    
    // Configure OAuth2 access token for authorization: petstore_auth
    OAuth petnutrition_auth = (OAuth) apiClient.getAuthentication("petnutrition_auth");
    petnutrition_auth.setAccessToken("special-key");

    LotApi apiInstance = new LotApi(apiClient);
    LotTreeItem lotTreeItem = null;
    if( manLotNumber != null && manLotNumber.length() > 0 ) {
		lotTreeItem = apiInstance.findLotTree(manLotNumber);
    }
  %>
  
  <% if( manLotNumber != null && manLotNumber.length() > 0 ) { %>
  <%=LotTreeToDetailTree.createDetailTreeHtml5(lotTreeItem) %>
  <% } %>
  </div>
</body>
</html>