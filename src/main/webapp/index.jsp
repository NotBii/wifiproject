<%@ page import="com.example.wifiproject.api.ApiDataService" %>
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
        <h1> 공공 wifi 검색하기 </h1>
        <br/>
        <div>
            <button id = "getLocation" onclick = "getLocation()">현재위치정보</button>
            <button id = "history" onclick = "location.href='history.jsp'"> 검색기록 </button>
            <button id = "download" onclick = "location.href='download.jsp'">데이터 받기</button>
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
                    <th>거리</th>
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
                    <th>lnt</th>
                    <th>lat</th>
                    <th>작업일자</th>
                </tr>
            </thead>

        </table>

    </body>
</html>
