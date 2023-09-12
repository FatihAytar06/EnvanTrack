package com.example.mystockss.Class;

import java.util.ArrayList;
import java.util.Date;

public class Stoklar {
    private String itemAdi,itemResim,itemAdet,docId,birimFiyat,marka;
    Date eklenmeTarihi;
    static final private ArrayList<Stoklar> stoklarList = new ArrayList<>();
    public Stoklar(){

    }

    public Stoklar(String itemAdi,String marka, String itemAdet, String itemResim,String birimFiyat,Date eklenmeTarihi,String docId) {
        this.itemAdi = itemAdi;
        this.itemAdet = itemAdet;
        this.itemResim = itemResim;
        this.docId = docId;
        this.birimFiyat = birimFiyat;
        this.eklenmeTarihi = eklenmeTarihi;
        this.marka = marka;

        Stoklar stok = new Stoklar();
        stok.setItemAdi(itemAdi);
        stok.setItemAdet(itemAdet);
        stok.setItemResim(itemResim);
        stok.setDocId(docId);
        stok.setMarka(marka);
        stok.setBirimFiyat(birimFiyat);
        stok.setEklenmeTarihi(eklenmeTarihi);

        stoklarList.add(stok);
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getItemAdi() {
        return itemAdi;
    }

    public void setItemAdi(String itemAdi) {
        this.itemAdi = itemAdi;
    }

    public String getItemAdet() {
        return itemAdet;
    }

    public void setItemAdet(String itemAdet) {
        this.itemAdet = itemAdet;
    }

    public String getItemResim() {
        return itemResim;
    }

    public void setItemResim(String itemResim) {
        this.itemResim = itemResim;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getBirimFiyat() {
        return birimFiyat;
    }

    public void setBirimFiyat(String birimFiyat) {
        this.birimFiyat = birimFiyat;
    }

    public Date getEklenmeTarihi() {
        return eklenmeTarihi;
    }

    public void setEklenmeTarihi(Date eklenmeTarihi) {
        this.eklenmeTarihi = eklenmeTarihi;
    }

    static public ArrayList<Stoklar> getData(){

        return stoklarList;
    }
}
