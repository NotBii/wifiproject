package com.example.wifiproject.db;

public class SqlMain {
    public static void main(String[] args) {
        SqlService sql = new SqlService();
        SearchDataVO searchData = new SearchDataVO();
        searchData.setxCor(31);
        searchData.setyCor(32);


//        sql.dbInsert(searchData);
        System.out.println(sql.SelectHistory());
//        sql.dbUpdate();
//        sql.dbDelete();

    }
}
