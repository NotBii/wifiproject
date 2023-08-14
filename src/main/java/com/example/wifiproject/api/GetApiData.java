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

    public static int getEndNum() {
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

    public static void totalGet(int startNum, int endNum) {
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
//        ApiDataVO apiData = new ApiDataVO();

        JsonObject data = (JsonObject) result.get("TbPublicWifiInfo");
        JsonArray dataArr = (JsonArray) data.get("row");
        JsonObject tmp;

        List<JsonObject> datalist = new ArrayList<>();


        for (int i = 0; i < dataArr.size(); i++)  {
            tmp = (JsonObject) dataArr.get(i);
//            apiData.setMgrNo(tmp.get("X_SWIFI_MGR_NO").getAsString());
//            apiData.setWrdofc(tmp.get("X_SWIFI_WRDOFC").getAsString());
//            apiData.setWifiName(tmp.get("X_SWIFI_MAIN_NM").getAsString());
//            apiData.setAdr1(tmp.get("X_SWIFI_ADRES1").getAsString());
//            apiData.setAdr2(tmp.get("X_SWIFI_ADRES2").getAsString());
//            apiData.setInstlFloor(tmp.get("X_SWIFI_INSTL_FLOOR").getAsString());
//            apiData.setInstlType(tmp.get("X_SWIFI_INSTL_TY").getAsString());
//            apiData.setInstlMby(tmp.get("X_SWIFI_INSTL_MBY").getAsString());
//            apiData.setSvcSe(tmp.get("X_SWIFI_SVC_SE").getAsString());
//            apiData.setCmcwr(tmp.get("X_SWIFI_CMCWR").getAsString());
//            apiData.setCnstcYear(tmp.get("X_SWIFI_CNSTC_YEAR").getAsInt());
//            apiData.setInOutDoor(tmp.get("X_SWIFI_INOUT_DOOR").getAsString());
//            apiData.setRemars3(tmp.get("X_SWIFI_REMARS3").getAsString());
//            apiData.setxCor(tmp.get("LAT").getAsDouble());
//            apiData.setyCor(tmp.get("LNT").getAsDouble());
//            apiData.setWorkDttm(tmp.get("WORK_DTTM").getAsString());
            datalist.add(tmp);
        }
        apiDataService.insertApiData(datalist);
    }

    public static void main(String[] args) {
        ApiDataService apiDataService = new ApiDataService();
//
//        apiDataService.deleteApiData();
//        int startNum = 1;
//        int endNum = 500;
//        int totalNum = getEndNum();
//        System.out.println(totalNum);
//
//        while ( endNum < totalNum) {
//            totalGet(startNum, endNum);
//            startNum += 500;
//            endNum += 500;
//
//            if (endNum > totalNum) {
//                endNum = totalNum;
//                totalGet(startNum, endNum);
//                break;
//            }
//        }
        List<ApiDataVO> arr = new ArrayList<>();
        arr = apiDataService.selectApiData(37.464264,126.88717);
        for (int i = 0; i < arr.size(); i++) {
            System.out.println(arr.get(i).getDist());

        }
    }
}
