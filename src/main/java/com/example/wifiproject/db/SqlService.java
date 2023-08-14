package com.example.wifiproject.db;

import com.example.wifiproject.DbPath;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class SqlService {
        String path = DbPath.path;

    public List<SearchDataVO> SelectHistory() {
        List<SearchDataVO> searchDataList = new ArrayList<>();
        Connection con = null;
        PreparedStatement preparedStatement = null;
        Statement stat = null;
        ResultSet rs = null;

        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + path);

            stat = con.createStatement();

            String sql = "SELECT no, xCor, yCor, time "
                    + "FROM history";

            preparedStatement = con.prepareStatement(sql);
//            preparedStatement.setString(1, table);

            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int no = rs.getInt("no");
                double xCor = rs.getDouble("xCor");
                double yCor = rs.getDouble("yCor");
                String time = rs.getString("time");

                SearchDataVO sd = new SearchDataVO();
                sd.setNo(no);
                sd.setxCor(xCor);
                sd.setyCor(yCor);
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
    public void dbInsert(SearchDataVO searchData) {
        Connection con = null;
        Statement stat = null;
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName("org.sqlite.JDBC");
            String dbFile = "WifiProjectDB.db";
            con = DriverManager.getConnection("jdbc:sqlite:" + path);

            stat = con.createStatement();

            String sql = "INSERT into history (XCOR, YCOR) "
                        + "VALUES ( ?, ?)";

            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(searchData.xCor));
            preparedStatement.setString(2, String.valueOf(searchData.yCor));

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

//    public void dbUpdate() {
//        Connection con = null;
//        PreparedStatement preparedStatement = null;
//        Statement stat = null;
//        ResultSet rs = null;
//
//        try {
//            Class.forName("org.sqlite.JDBC");
//            String dbFile = "WifiProjectDB.db";
//            con = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
//
//            stat = con.createStatement();
//
//            String sql = "UPDATE history  "
//                    + "SET name = \"test3\""
//                    + "WHERE name = \"test2\"";
//
//            preparedStatement = con.prepareStatement(sql);
//
//            int affected = preparedStatement.executeUpdate();
//            if (affected > 0) {
//                System.out.println("저장 성공");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (stat != null && !stat.isClosed()) {
//                    stat.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            try {
//                if (con != null && !con.isClosed()) {
//                    con.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }

    public void dbDelete() {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        Statement stat = null;
        ResultSet rs = null;
        String number = "2";
        String name = "test2";
        String date = "2023-08-13";
        String table = "history";
        try {
            Class.forName("org.sqlite.JDBC");
            String dbFile = "WifiProjectDB.db";
            con = DriverManager.getConnection("jdbc:sqlite:" + path);

            stat = con.createStatement();

            String sql = "DELETE FROM history "
                        + "WHERE name = ?";

            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, "test3");


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
