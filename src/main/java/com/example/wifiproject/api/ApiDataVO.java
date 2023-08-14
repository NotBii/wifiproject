package com.example.wifiproject.api;

public class ApiDataVO {
    double dist; //거리
    String mgrNo; // 관리번호
    String wrdofc; // 자치구
    String wifiName; //와이파이명
    String adr1; //도로명주소
    String adr2; //상세주소
    String instlFloor; //설치위치
    String instlType; //설치유형
    String instlMby; //설치기관
    String svcSe; //서비스구분
    String cmcwr; //망종류
    int cnstcYear; //설치연도
    String inOutDoor; //실내외구분
    String remars3; //접속환경
    Double lat; //x좌표(LNT)
    Double lnt; //y좌표(LAT)
    String workDttm; // 작업일자

    public double getDist() {
        return dist;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }

    public String getMgrNo() {
        return mgrNo;
    }

    public void setMgrNo(String mgrNo) {
        this.mgrNo = mgrNo;
    }

    public String getWrdofc() {
        return wrdofc;
    }

    public void setWrdofc(String wrdofc) {
        this.wrdofc = wrdofc;
    }

    public String getWifiName() {
        return wifiName;
    }

    public void setWifiName(String wifiName) {
        this.wifiName = wifiName;
    }

    public String getAdr1() {
        return adr1;
    }

    public void setAdr1(String adr1) {
        this.adr1 = adr1;
    }

    public String getAdr2() {
        return adr2;
    }

    public void setAdr2(String adr2) {
        this.adr2 = adr2;
    }

    public String getInstlFloor() {
        return instlFloor;
    }

    public void setInstlFloor(String instlFloor) {
        this.instlFloor = instlFloor;
    }

    public String getInstlType() {
        return instlType;
    }

    public void setInstlType(String instlType) {
        this.instlType = instlType;
    }

    public String getInstlMby() {
        return instlMby;
    }

    public void setInstlMby(String instlMby) {
        this.instlMby = instlMby;
    }

    public String getSvcSe() {
        return svcSe;
    }

    public void setSvcSe(String svcSe) {
        this.svcSe = svcSe;
    }

    public String getCmcwr() {
        return cmcwr;
    }

    public void setCmcwr(String cmcwr) {
        this.cmcwr = cmcwr;
    }

    public int getCnstcYear() {
        return cnstcYear;
    }

    public void setCnstcYear(int cnstcYear) {
        this.cnstcYear = cnstcYear;
    }

    public String getInOutDoor() {
        return inOutDoor;
    }

    public void setInOutDoor(String inOutDoor) {
        this.inOutDoor = inOutDoor;
    }

    public String getRemars3() {
        return remars3;
    }

    public void setRemars3(String remars3) {
        this.remars3 = remars3;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLnt() {
        return lnt;
    }

    public void setLnt(Double lnt) {
        this.lnt = lnt;
    }

    public String getWorkDttm() {
        return workDttm;
    }

    public void setWorkDttm(String workDttm) {
        this.workDttm = workDttm;
    }

}
