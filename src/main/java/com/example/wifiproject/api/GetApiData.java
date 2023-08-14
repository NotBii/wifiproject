package com.example.wifiproject.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.util.*;



public class GetApiData {

        public static void totalGet() {
            JsonObject result = null;
            StringBuilder sb = new StringBuilder();
            ApiDataService apiDataService = new ApiDataService();

            try {
                StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088"); /*URL*/
                urlBuilder.append("/" + URLEncoder.encode("4145796459636f7331313151504c526d", "UTF-8")); /*인증키 (sample사용시에는 호출시 제한됩니다.)*/
                urlBuilder.append("/" + URLEncoder.encode("json", "UTF-8")); /*요청파일타입 */
                urlBuilder.append("/" + URLEncoder.encode("TbPublicWifiInfo", "UTF-8")); /*서비스명*/
                urlBuilder.append("/" + URLEncoder.encode("1", "UTF-8")); /*요청시작위치 */
                urlBuilder.append("/" + URLEncoder.encode("5", "UTF-8")); /*요청종료위치*/

                // 서비스별 추가 요청 인자이며 자세한 내용은 각 서비스별 '요청인자'부분에 자세히 나와 있습니다.

                URL url = new URL(urlBuilder.toString());
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Content-type", "application/json");
                System.out.println("Response code: " + con.getResponseCode()); /* 연결 자체에 대한 확인이 필요하므로 추가합니다.*/
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

                // 서비스코드가 정상이면 200~300사이의 숫자가 나옵니다.
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
            ApiDataVO apiData = new ApiDataVO();

            JsonObject data = (JsonObject) result.get("TbPublicWifiInfo");
            JsonArray dataArr = (JsonArray) data.get("row");
            JsonObject tmp;

            List<ApiDataVO> datalist = new ArrayList<>();

            apiDataService.deleteAPiData();


            for (int i = 0; i < dataArr.size(); i++)  {
                tmp = (JsonObject) dataArr.get(i);
                apiData.setMgrNo(tmp.get("X_SWIFI_MGR_NO").getAsString());
                apiData.setWrdofc(tmp.get("X_SWIFI_WRDOFC").getAsString());
                apiData.setWifiName(tmp.get("X_SWIFI_MAIN_NM").getAsString());
                apiData.setAdr1(tmp.get("X_SWIFI_ADRES1").getAsString());
                apiData.setAdr2(tmp.get("X_SWIFI_ADRES2").getAsString());
                apiData.setInstlFloor(tmp.get("X_SWIFI_INSTL_FLOOR").getAsString());
                apiData.setInstlType(tmp.get("X_SWIFI_INSTL_TY").getAsString());
                apiData.setInstlMby(tmp.get("X_SWIFI_INSTL_MBY").getAsString());
                apiData.setSvcSe(tmp.get("X_SWIFI_SVC_SE").getAsString());
                apiData.setCmcwr(tmp.get("X_SWIFI_CMCWR").getAsString());
                apiData.setCnstcYear(tmp.get("X_SWIFI_CNSTC_YEAR").getAsInt());
                apiData.setInOutDoor(tmp.get("X_SWIFI_INOUT_DOOR").getAsString());
                apiData.setRemars3(tmp.get("X_SWIFI_REMARS3").getAsString());
                apiData.setxCor(tmp.get("LAT").getAsDouble());
                apiData.setyCor(tmp.get("LNT").getAsDouble());
                apiData.setWorkDttm(tmp.get("WORK_DTTM").getAsString());
                System.out.println(apiData);
                datalist.add(apiData);
            }
            System.out.println(datalist);
            apiDataService.insertApiData(datalist);
        }

        public static void main(String[] args) {
            totalGet();

    }
}
