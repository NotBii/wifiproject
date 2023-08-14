<%@ page import="com.example.wifiproject.api.ApiDataService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
      <title>공공wifi검색</title>
  </head>
  <body>
    <%
      ApiDataService api = new ApiDataService();
      api.getTotalData();
      response.sendRedirect("index.jsp");
    %>

  </body>
</html>
