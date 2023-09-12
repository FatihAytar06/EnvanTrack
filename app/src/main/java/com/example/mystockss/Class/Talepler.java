package com.example.mystockss.Class;

import java.util.ArrayList;
import java.util.Date;

public class Talepler {
    String talepEden,itemAdi,talepAdet,talepBirim,talepResim,talepEdenEmail,docId;
    boolean aktiflik;
    Date talepTarihi;
    static final private ArrayList<Talepler> taleplerList = new ArrayList<>();
    public Talepler() {
    }

    public Talepler(String talepEden,String talepEdenEmail, String itemAdi, String talepAdet, String talepBirim, String talepResim, Date talepTarihi,String docId,boolean aktiflik) {
        this.talepEden = talepEden;
        this.itemAdi = itemAdi;
        this.talepAdet = talepAdet;
        this.talepBirim = talepBirim;
        this.talepResim = talepResim;
        this.aktiflik = aktiflik;
        this.talepEdenEmail = talepEdenEmail;
        this.talepTarihi = talepTarihi;
        this.docId=docId;

        Talepler talepler = new Talepler();
        talepler.setTalepAdet(talepAdet);
        talepler.setItemAdi(itemAdi);
        talepler.setTalepEdenEmail(talepEdenEmail);
        talepler.setTalepEden(talepEden);
        talepler.setTalepBirim(talepBirim);
        talepler.setAktiflik(aktiflik);
        talepler.setTalepResim(talepResim);
        talepler.setTalepTarihi(talepTarihi);
        talepler.setDocId(docId);
        taleplerList.add(talepler);
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public boolean isAktiflik() {
        return aktiflik;
    }

    public void setAktiflik(boolean aktiflik) {
        this.aktiflik = aktiflik;
    }

    public String getTalepEdenEmail() {
        return talepEdenEmail;
    }

    public void setTalepEdenEmail(String talepEdenEmail) {
        this.talepEdenEmail = talepEdenEmail;
    }

    public String getTalepEden() {
        return talepEden;
    }

    public void setTalepEden(String talepEden) {
        this.talepEden = talepEden;
    }

    public String getItemAdi() {
        return itemAdi;
    }

    public void setItemAdi(String itemAdi) {
        this.itemAdi = itemAdi;
    }

    public String getTalepAdet() {
        return talepAdet;
    }

    public void setTalepAdet(String talepAdet) {
        this.talepAdet = talepAdet;
    }

    public String getTalepBirim() {
        return talepBirim;
    }

    public void setTalepBirim(String talepBirim) {
        this.talepBirim = talepBirim;
    }

    public String getTalepResim() {
        return talepResim;
    }

    public void setTalepResim(String talepResim) {
        this.talepResim = talepResim;
    }

    public Date getTalepTarihi() {
        return talepTarihi;
    }

    public void setTalepTarihi(Date talepTarihi) {
        this.talepTarihi = talepTarihi;
    }

    static public ArrayList<Talepler> getData(){

        return taleplerList;
    }
}
