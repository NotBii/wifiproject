package com.example.wifiproject.history;

import com.example.wifiproject.DbPath;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class HistoryService {
        String path = DbPath.path;
        Connection con = null;
        PreparedStatement preparedStatement = null;
        Statement stat = null;
        ResultSet rs = null;
    public List<HistoryDataVO> selectHistory() {
        List<HistoryDataVO> searchDataList = new ArrayList<>();


        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + path);

            stat = con.createStatement();

            String sql = "SELECT no, lat, lnt, time "
                    + "FROM history";

            preparedStatement = con.prepareStatement(sql);

            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int no = rs.getInt("no");
                double lat = rs.getDouble("lat");
                double lnt = rs.getDouble("lnt");
                String time = rs.getString("time");

                HistoryDataVO sd = new HistoryDataVO();
                sd.setNo(no);
                sd.setLat(lat);
                sd.setLnt(lnt);
                sd.setTime(time);
                searchDataList.add(sd);

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
        return searchDataList;
    }
    public void insertHistory(HistoryDataVO searchData) {

        try {
            Class.forName("org.sqlite.JDBC");
            String dbFile = "WifiProjectDB.db";
            con = DriverManager.getConnection("jdbc:sqlite:" + path);

            stat = con.createStatement();

            String sql = "INSERT into history (lat , lnt) "
                        + "VALUES ( ?, ?)";

            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(searchData.lat));
            preparedStatement.setString(2, String.valueOf(searchData.lnt));

            int affected = preparedStatement.executeUpdate();
            if (affected > 0) {
                System.out.println("저장완료");
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

    public void historyDelete(int no) {


        try {
            Class.forName("org.sqlite.JDBC");
            String dbFile = "WifiProjectDB.db";
            con = DriverManager.getConnection("jdbc:sqlite:" + path);

            stat = con.createStatement();

            String sql = "DELETE FROM history "
                        + "WHERE no = ? ";

            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, no);

            int affected = preparedStatement.executeUpdate();
            if (affected > 0) {
                System.out.println("삭제 성공");
            } else {
                System.out.println("삭제 실패");
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

}
