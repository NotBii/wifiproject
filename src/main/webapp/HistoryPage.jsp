<%@ page import="com.example.wifiproject.db.SqlService" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.wifiproject.db.SearchDataVO" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
    <head>
        <title>JSP - Hello World</title>
        <style>
            table {
                width: 100%;
            }
            th, td {
                border:solid 1px #000;
            }
        </style>
    </head>
    <body>
        <%
            SqlService sql = new SqlService();
            List<SearchDataVO> searchDataList = sql.SelectHistory();
        %>
        <h1> 검색이력 </h1>
        <br/>
        <table>
            <thead>
                <tr>
                    <th>번 호</th>
                    <th>X 좌표</th>
                    <th>Y 좌표</th>
                    <th>조회일자</th>
                    <th>삭제</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <%
                        for (SearchDataVO sd : searchDataList) {
                    %>
                            <tr>
                            <td>  <%=sd.getNo()%>  </td>
                            <td>  <%=String.valueOf(sd.getxCor())%>  </td>
                            <td>  <%=String.valueOf(sd.getyCor())%>  </td>
                            <td>  <%=sd.getTime()%>  </td>
                            <td>  "삭제"  </td>
                            </tr>
                    <%
                        }
                    %>
                </tr>
            </tbody>
        </table>

    </body>
</html>