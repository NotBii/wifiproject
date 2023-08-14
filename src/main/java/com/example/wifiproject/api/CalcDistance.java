package com.example.wifiproject.api;
import java.util.*;


public class CalcDistance {
    public List<ApiDataVO> Calc(double lat, double lnt, List<ApiDataVO> dataList)  {
        List<ApiDataVO> result = new ArrayList<>();
        if (lat < 39 && lat !=0 && lnt !=0) {
            for (int i = 0; i < dataList.size(); i++) {
                double lat1 = lat;
                double lnt1 = lnt;
                double lnt2 = dataList.get(i).getLnt();
                double lat2 = dataList.get(i).getLat();

                double theta = lnt1 - lnt2;
                double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

                dist = Math.acos(dist);
                dist = rad2deg(dist);
                dist = dist * 60 * 1.1515;
                dist = dist * 1609.344;

                dataList.get(i).setDist(dist);
            }
            Comparator<ApiDataVO> comparator = (o1, o2) -> (int) (o1.getDist() - o2.getDist());
            Collections.sort(dataList, comparator);
            int cnt = 0;
            for (ApiDataVO data : dataList) {
                if (cnt < 20) {
                    if (data.getDist() != 0) {
                        result.add(data);
                        cnt++;
                    }
                }
            }
        }
        return result;
    }


    // This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);

    }
}
