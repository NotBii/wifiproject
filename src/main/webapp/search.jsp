<%@ page import="com.example.wifiproject.api.ApiDataService" %>
<%@ page import="com.example.wifiproject.api.ApiDataVO" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
  <head>
    <title>공공wifi검색</title>
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
    function getLocation() {
      navigator.geolocation.getCurrentPosition(function (pos) {
        console.log(pos);
        var latitude = pos.coords.latitude;
        var longitude = pos.coords.longitude;
        document.getElementById("lat").value = latitude;
        document.getElementById("lnt").value = longitude;
      })
    };
  </script>

  <body>

    <%
      double lat = Double.parseDouble(request.getParameter("lat"));
      double lnt = Double.parseDouble(request.getParameter("lnt"));
      ApiDataService api = new ApiDataService();
      List<ApiDataVO> dataList = api.selectApiData(lat, lnt);
    %>

    <h1> 공공 wifi 검색하기 </h1>
    <br/>
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
    <table>
      <thead>
        <tr>
          <th>거리(m)</th>
          <th>관리번호</th>
          <th>자치구</th>
          <th>와이파이명</th>
          <th>도로명주소</th>
          <th>상세주소</th>
          <th>설치위치(층)</th>
          <th>설치유형</th>
          <th>설치기관</th>
          <th>서비스구분</th>
          <th>망종류</th>
          <th>설치년도</th>
          <th>실내외구분</th>
          <th>wifi접속환경</th>
          <th>x좌표</th>
          <th>y좌표</th>
          <th>작업일자</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <%
            for(ApiDataVO data : dataList) {
          %>
              <tr>
                <td><%=(int)data.getDist()%></td>
                <td><%=data.getMgrNo()%></td>
                <td><%=data.getWrdofc()%></td>
                <td><%=data.getWifiName()%></td>
                <td><%=data.getAdr1()%></td>
                <td><%=data.getAdr2()%></td>
                <td><%=data.getInstlFloor()%></td>
                <td><%=data.getInstlType()%></td>
                <td><%=data.getInstlMby()%></td>
                <td><%=data.getSvcSe()%></td>
                <td><%=data.getCmcwr()%></td>
                <td><%=data.getCnstcYear()%></td>
                <td><%=data.getInOutDoor()%></td>
                <td><%=data.getRemars3()%></td>
                <td><%=data.getLat()%></td>
                <td><%=data.getLnt()%></td>
                <td><%=data.getWorkDttm()%></td>
              </tr>
        <%
          }
        %>
        </tr>
      </tbody>
    </table>
  </body>
</html>
