package com.tatlicilar.downtoup;

/**
 * Created by sezinkokum on 29.08.2017.
 */

public class EbookIcerik {

    private String pdfIsmi;
    private String egitmenIsmi;
    private String icerik;
    private int coverImage;
    private String url;

    public EbookIcerik(String pdfIsmi, String egitmenIsmi, String icerik, int coverImage, String url) {
        this.pdfIsmi = pdfIsmi;
        this.egitmenIsmi = egitmenIsmi;
        this.icerik = icerik;
        this.coverImage = coverImage;
        this.url = url;
    }

    public String getpdfIsmi() {
        return pdfIsmi;
    }

    public void setpdfIsmi(String egitimIsmi) {
        this.pdfIsmi = egitimIsmi;
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