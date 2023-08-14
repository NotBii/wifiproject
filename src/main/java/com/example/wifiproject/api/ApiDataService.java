package com.example.wifiproject.api;

import com.example.wifiproject.DbPath;
import com.example.wifiproject.history.HistoryDataVO;
import com.example.wifiproject.history.HistoryService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.*;
import java.util.*;
public class ApiDataService {
    String path = DbPath.path;
    Connection con = null;
    Statement stat = null;
    ResultSet rs = null;
    PreparedStatement preparedStatement = null;

    public int getTotalData(){
        deleteApiData();
        int totalCount = getTotalCount();
        int startnum = 1;
        int endNum = 500;

        while (startnum < totalCount) {
            try {
                getData(startnum, endNum);
                startnum += 500;
                endNum += 500;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 1;
    }
    public static int getTotalCount() {
        int endnum = -1;
        JsonObject result = null;
        StringBuilder sb = new StringBuilder();
        ApiDataService apiDataService = new ApiDataService();

        try {
            StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088"); /*URL*/
            urlBuilder.append("/" + URLEncoder.encode("4145796459636f7331313151504c526d", "UTF-8")); /*인증키 (sample사용시에는 호출시 제한됩니다.)*/
            urlBuilder.append("/" + URLEncoder.encode("json", "UTF-8")); /*요청파일타입 */
            urlBuilder.append("/" + URLEncoder.encode("TbPublicWifiInfo", "UTF-8")); /*서비스명*/
            urlBuilder.append("/" + URLEncoder.encode("1", "UTF-8")); /*요청시작위치 */
            urlBuilder.append("/" + URLEncoder.encode("1", "UTF-8")); /*요청종료위치*/

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-type", "application/json");
            System.out.println("Response code: " + con.getResponseCode()); /* 연결 자체에 대한 확인이 필요하므로 추가합니다.*/
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            if (con.getResponseCode() >= 200 && con.getResponseCode() <= 300) {

                while (br.ready()) {
                    sb.append(br.readLine());
                }
                con.disconnect();
                System.out.println(sb);
            }
            result = (JsonObject) new JsonParser().parse(sb.toString());
            JsonObject data = (JsonObject) result.get("TbPublicWifiInfo");
            endnum = data.get("list_total_count").getAsInt();

        } catch (Exception e) {
            e.printStackTrace();

        }
        return endnum;
    }
    public static void getData(int startNum, int endNum) {
        JsonObject result = null;
        StringBuilder sb = new StringBuilder();
        ApiDataService apiDataService = new ApiDataService();

        try {
            StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088"); /*URL*/
            urlBuilder.append("/" + URLEncoder.encode("4145796459636f7331313151504c526d", "UTF-8")); /*인증키 (sample사용시에는 호출시 제한됩니다.)*/
            urlBuilder.append("/" + URLEncoder.encode("json", "UTF-8")); /*요청파일타입 */
            urlBuilder.append("/" + URLEncoder.encode("TbPublicWifiInfo", "UTF-8")); /*서비스명*/
            urlBuilder.append("/" + URLEncoder.encode(String.valueOf(startNum), "UTF-8")); /*요청시작위치 */
            urlBuilder.append("/" + URLEncoder.encode(String.valueOf(endNum), "UTF-8")); /*요청종료위치*/


            URL url = new URL(urlBuilder.toString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-type", "application/json");
            System.out.println("Response code: " + con.getResponseCode()); /* 연결 자체에 대한 확인이 필요하므로 추가합니다.*/
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

            if (con.getResponseCode() >= 200 && con.getResponseCode() <= 300) {


                while (br.ready()) {
                    sb.append(br.readLine());
                }

                con.disconnect();
                System.out.println(sb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        result = (JsonObject) new JsonParser().parse(sb.toString());
        StringBuilder out = new StringBuilder();

        JsonObject data = (JsonObject) result.get("TbPublicWifiInfo");
        JsonArray dataArr = (JsonArray) data.get("row");
        JsonObject tmp;

        List<JsonObject> datalist = new ArrayList<>();


        for (int i = 0; i < dataArr.size(); i++)  {
            tmp = (JsonObject) dataArr.get(i);

            datalist.add(tmp);
        }
        apiDataService.insertApiData(datalist);
    }

    public void insertApiData(List<JsonObject> dataList) {

            try {
                Class.forName("org.sqlite.JDBC");
                String dbFile = "WifiProjectDB.db";
                con = DriverManager.getConnection("jdbc:sqlite:" + path);

                stat = con.createStatement();

                for (JsonObject dataVo : dataList) {
                    String sql = "INSERT into wifidata (mgrno, wrdofc, wifiname, adr1, adr2, instlfloor, instltype," +
                            "instlmby, svcse, cmcwr, cnstcyear, inoutdoor, remars3, lat, lnt, workdttm) "
                            + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                    preparedStatement = con.prepareStatement(sql);
                    preparedStatement.setString(1, dataVo.get("X_SWIFI_MGR_NO").getAsString());
                    preparedStatement.setString(2, dataVo.get("X_SWIFI_WRDOFC").getAsString());
                    preparedStatement.setString(3, dataVo.get("X_SWIFI_MAIN_NM").getAsString());
                    preparedStatement.setString(4, dataVo.get("X_SWIFI_ADRES1").getAsString());
                    preparedStatement.setString(5, dataVo.get("X_SWIFI_ADRES2").getAsString());
                    preparedStatement.setString(6, dataVo.get("X_SWIFI_INSTL_FLOOR").getAsString());
                    preparedStatement.setString(7, dataVo.get("X_SWIFI_INSTL_TY").getAsString());
                    preparedStatement.setString(8, dataVo.get("X_SWIFI_INSTL_MBY").getAsString());
                    preparedStatement.setString(9, dataVo.get("X_SWIFI_SVC_SE").getAsString());
                    preparedStatement.setString(10, dataVo.get("X_SWIFI_CMCWR").getAsString());
                    preparedStatement.setInt(11, dataVo.get("X_SWIFI_CNSTC_YEAR").getAsInt());
                    preparedStatement.setString(12, dataVo.get("X_SWIFI_INOUT_DOOR").getAsString());
                    preparedStatement.setString(13, dataVo.get("X_SWIFI_REMARS3").getAsString());
                    preparedStatement.setDouble(14, dataVo.get("LNT").getAsDouble());
                    preparedStatement.setDouble(15, dataVo.get("LAT").getAsDouble());
                    preparedStatement.setString(16, dataVo.get("WORK_DTTM").getAsString());

                    preparedStatement.executeUpdate();
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (stat != null && !stat.isClosed()) {
                        stat.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    if (con != null && !con.isClosed()) {
                        con.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


    }

    public List<ApiDataVO> selectApiData(double lat, double lnt)  {
        CalcDistance calcDistance = new CalcDistance();
        List<ApiDataVO> dataList = new ArrayList<>();

        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + path);
            stat = con.createStatement();
            String sql = "SELECT * FROM wifidata";
            preparedStatement = con.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            while(rs.next()) {
                ApiDataVO data = new ApiDataVO();
                data.setMgrNo(rs.getString("no"));
                data.setWrdofc(rs.getString("wrdofc"));
                data.setWifiName(rs.getString("wifiname"));
                data.setAdr1(rs.getString("adr1"));
                data.setAdr2(rs.getString("adr2"));
                data.setInstlFloor(rs.getString("instlfloor"));
                data.setInstlType(rs.getString("instltype"));
                data.setInstlMby(rs.getString("instlmby"));
                data.setSvcSe(rs.getString("svcse"));
                data.setCmcwr(rs.getString("cmcwr"));
                data.setCnstcYear(rs.getInt("cnstcyear"));
                data.setInOutDoor(rs.getString("inoutdoor"));
                data.setRemars3(rs.getString("remars3"));
                data.setLat(rs.getDouble("lat"));
                data.setLnt(rs.getDouble("lnt"));
                data.setWorkDttm(rs.getString("workdttm"));
                dataList.add(data);
            }
            HistoryDataVO sData = new HistoryDataVO();
            sData.setLat(lat);
            sData.setLnt(lnt);
            HistoryService historyService = new HistoryService();
            historyService.insertHistory(sData);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stat != null && !stat.isClosed()) {
                    stat.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        return calcDistance.Calc(lat, lnt, dataList);

    }

    public void deleteApiData() {

        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + path);
            stat = con.createStatement();
            String sql = "Delete FROM wifidata";
            preparedStatement = con.prepareStatement(sql);
            int affected = preparedStatement.executeUpdate();
            if (affected > 0) {
                System.out.println("삭제완료");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stat != null && !stat.isClosed()) {
                    stat.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public int ApiDataCount() {
        int dataCount = -1;
        try {
            Class.forName("org.sqlite.JDBC");
            String dbFile = "WifiProjectDB.db";
            con = DriverManager.getConnection("jdbc:sqlite:" + path);
            stat = con.createStatement();
            ResultSet rs = null;
            String sql = "SELECT COUNT(no) FROM wifidata";
            preparedStatement = con.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            dataCount = rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                if (stat != null && !stat.isClosed()) {
                    stat.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return dataCount;
    }
}
