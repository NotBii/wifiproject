package com.example.wifiproject.api;

import com.example.wifiproject.DbPath;

import java.sql.*;
import java.util.*;
public class ApiDataService {
    String path = DbPath.path;
    Connection con = null;
    Statement stat = null;
    ResultSet rs = null;
    PreparedStatement preparedStatement = null;

    public List<ApiDataVO> selectApiData() {
        return null;
    }

    public void insertApiData(List<ApiDataVO> dataList) {

            try {
                Class.forName("org.sqlite.JDBC");
                String dbFile = "WifiProjectDB.db";
                con = DriverManager.getConnection("jdbc:sqlite:" + path);

                stat = con.createStatement();

                for (ApiDataVO dataVo : dataList) {
                     String sql = "INSERT into wifidata (mgrno, wrdofc, wifiname, adr1, adr2, instlfloor, instltype," +
                            "instlmby, svcse, cmcwr, cnstcyear, inoutdoor, remars3, xcor, ycor, workdttm) "
                            + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                    preparedStatement = con.prepareStatement(sql);
                    preparedStatement.setString(1, dataVo.getMgrNo());
                    preparedStatement.setString(2, dataVo.getWrdofc());
                    preparedStatement.setString(3, dataVo.getWifiName());
                    preparedStatement.setString(4, dataVo.getAdr1());
                    preparedStatement.setString(5, dataVo.getAdr2());
                    preparedStatement.setString(6, dataVo.getInstlFloor());
                    preparedStatement.setString(7, dataVo.getInstlType());
                    preparedStatement.setString(8, dataVo.getInstlMby());
                    preparedStatement.setString(9, dataVo.getSvcSe());
                    preparedStatement.setString(10, dataVo.getCmcwr());
                    preparedStatement.setInt(11, dataVo.getCnstcYear());
                    preparedStatement.setString(12, dataVo.getInOutDoor());
                    preparedStatement.setString(13, dataVo.getRemars3());
                    preparedStatement.setDouble(14, dataVo.getxCor());
                    preparedStatement.setDouble(15, dataVo.getyCor());
                    preparedStatement.setString(16, dataVo.getWorkDttm());

                    int affected = preparedStatement.executeUpdate();
                    if (affected > 0) {
                        System.out.println("저장완료");
                    }
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

    public void deleteAPiData()  {
        try {
            Class.forName("org.sqlite.JDBC");
            String dbFile = "WifiProjectDB.db";
            con = DriverManager.getConnection("jdbc:sqlite:" + path);
            stat = con.createStatement();
            String sql = "DELETE FROM wifidata";
            preparedStatement = con.prepareStatement(sql);
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
}
