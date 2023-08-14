package com.example.wifiproject.api;

import com.example.wifiproject.DbPath;
import com.example.wifiproject.history.HistoryDataVO;
import com.example.wifiproject.history.HistoryService;
import com.google.gson.JsonObject;

import java.sql.*;
import java.util.*;
public class ApiDataService {
    String path = DbPath.path;
    Connection con = null;
    Statement stat = null;
    ResultSet rs = null;
    PreparedStatement preparedStatement = null;


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
