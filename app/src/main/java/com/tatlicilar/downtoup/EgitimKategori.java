package com.tatlicilar.downtoup;

/**
 * Created by sezinkokum on 4.08.2017.
 */

public class EgitimKategori {

    private String egitimIsmi;
    private String egitmenIsmi;
    private String icerik;
    private int coverImage;
    private String url;

    public EgitimKategori(String egitimIsmi, String egitmenIsmi, String icerik, int coverImage, String url) {
        this.egitimIsmi = egitimIsmi;
        this.egitmenIsmi = egitmenIsmi;
        this.icerik = icerik;
        this.coverImage = coverImage;
        this.url = url;
    }

    public String getEgitimIsmi() {
        return egitimIsmi;
    }

    public void setEgitimIsmi(String egitimIsmi) {
        this.egitimIsmi = egitimIsmi;
    }

    public String getEgitmenIsmi() {
        return egitmenIsmi;
    }

    public void setEgitmenIsmi(String egitmenIsmi) {
        this.egitmenIsmi = egitmenIsmi;
    }

    public String getIcerik() {
        return icerik;
    }

    public void setIcerik(String icerik) {
        this.icerik = icerik;
    }

    public int getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(int coverImage) {
        this.coverImage = coverImage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}