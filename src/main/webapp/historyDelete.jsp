<%@ page import="com.example.wifiproject.history.HistoryService" %><%--
  Created by IntelliJ IDEA.
  User: crito
  Date: 2023-08-14
  Time: 오후 8:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
  <%
    int no = Integer.parseInt(request.getParameter("no"));
    HistoryService historyService = new HistoryService();
    historyService.historyDelete(no);
    response.sendRedirect("history.jsp");
  %>
</body>
</html>
