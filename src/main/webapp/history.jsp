<%@ page import="com.example.wifiproject.history.HistoryService" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.wifiproject.history.HistoryDataVO" %>
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
    <script>
        <%
            HistoryService sql = new HistoryService();
            List<HistoryDataVO> searchDataList = sql.selectHistory();
        %>
        function getLocation() {
            navigator.geolocation.getCurrentPosition(function (pos) {
                console.log(pos);
                var latitude = pos.coords.latitude;
                var longitude = pos.coords.longitude;
                document.getElementById("lat").value = latitude;
                document.getElementById("lnt").value = longitude;
            })
        };

        function historySearch(lat, lnt) {
          var url = "search.jsp?lat="+lat+"&lnt="+lnt ;
          window.location.href = url;
        }

        function historyDelete(no) {
            var url = "historyDelete.jsp?no="+no;
            window.location.href = url;
        }
    </script>
    <body>

        <h1> 검색이력 </h1>
        <div>
            <button id = "getLocation" onclick = "getLocation()">현재위치정보</button>
            <button id = "history" onclick = "location.href='history.jsp'"> 검색기록 </button>
            <form action = "search.jsp" method = "post">
                LAT
                <input id="lat" name="lat" value=""/>
                LNT
                <input id="lnt" name="lnt" value=""/>
                <input type = "submit" value="검색하기"/>
            </form>
        </div>
        <br/>
        <table>
            <thead>
                <tr>
                    <th>번 호</th>
                    <th>lnt</th>
                    <th>lat</th>
                    <th>조회일자</th>
                    <th>삭제</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <%
                        for (HistoryDataVO sd : searchDataList) {
                    %>
                            <tr>
                            <td onclick="historySearch('<%=sd.getLat()%>', '<%=sd.getLnt()%>')">  <%=sd.getNo()%>  </td>
                            <td onclick="historySearch('<%=sd.getLat()%>', '<%=sd.getLnt()%>')">  <%=String.valueOf(sd.getLnt())%>  </td>
                            <td onclick="historySearch('<%=sd.getLat()%>', '<%=sd.getLnt()%>')">  <%=String.valueOf(sd.getLat())%>  </td>
                            <td onclick="historySearch('<%=sd.getLat()%>', '<%=sd.getLnt()%>')">  <%=sd.getTime()%>  </td>
                            <td>  <button onClick="historyDelete('<%=sd.getNo()%>')"> 삭제 </button>  </td>
                            </tr>
                    <%
                        }
                    %>
                </tr>
            </tbody>
        </table>

    </body>
</html>