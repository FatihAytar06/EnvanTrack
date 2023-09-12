package com.example.mystockss.Class;

import java.util.ArrayList;
import java.util.Date;

public class Loglar {
    private String envanterAdi,envanteriTeslimAlan,islem,islemSahibi,resimUri,stokAdet;
    private Date timestamp;
    static final private ArrayList<Loglar> loglarList = new ArrayList<>();

    public Loglar(){

    }

    public Loglar(String envanterAdi, String envanteriTeslimAlan, String islem, String islemSahibi, String resimUri, String stokAdet, Date timestamp) {
        this.envanterAdi = envanterAdi;
        this.envanteriTeslimAlan = envanteriTeslimAlan;
        this.islem = islem;
        this.islemSahibi = islemSahibi;
        this.resimUri = resimUri;
        this.stokAdet = stokAdet;
        this.timestamp = timestamp;

        Loglar log = new Loglar();
        log.setEnvanterAdi(envanterAdi);
        log.setEnvanteriTeslimAlan(envanteriTeslimAlan);
        log.setIslem(islem);
        log.setIslemSahibi(islemSahibi);
        log.setResimUri(resimUri);
        log.setStokAdet(stokAdet);
        log.setTimestamp(timestamp);

        loglarList.add(log);

    }

    public String getEnvanterAdi() {
        return envanterAdi;
    }

    public void setEnvanterAdi(String envanterAdi) {
        this.envanterAdi = envanterAdi;
    }

    public String getEnvanteriTeslimAlan() {
        return envanteriTeslimAlan;
    }

    public void setEnvanteriTeslimAlan(String envanteriTeslimAlan) {
        this.envanteriTeslimAlan = envanteriTeslimAlan;
    }

    public String getIslem() {
        return islem;
    }

    public void setIslem(String islem) {
        this.islem = islem;
    }

    public String getIslemSahibi() {
        return islemSahibi;
    }

    public void setIslemSahibi(String islemSahibi) {
        this.islemSahibi = islemSahibi;
    }

    public String getResimUri() {
        return resimUri;
    }

    public void setResimUri(String resimUri) {
        this.resimUri = resimUri;
    }

    public String getStokAdet() {
        return stokAdet;
    }

    public void setStokAdet(String stokAdet) {
        this.stokAdet = stokAdet;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    static public ArrayList<Loglar> getData(){

        return loglarList;
    }
}
