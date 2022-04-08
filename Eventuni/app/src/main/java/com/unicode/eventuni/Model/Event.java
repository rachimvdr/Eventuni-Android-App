package com.unicode.eventuni.Model;

import com.google.gson.annotations.SerializedName;

public class Event {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("longdescription")
    private String longdescription;
    @SerializedName("kategori")
    private String kategori;
    @SerializedName("kegiatan")
    private String kegiatan;
    @SerializedName("lokasi")
    private String lokasi;
    @SerializedName("tiket")
    private String tiket;
    @SerializedName("waktu")
    private String waktu;
    @SerializedName("cp")
    private String cp;
    @SerializedName("link")
    private String link;
    @SerializedName("image")
    private String image;

    public Event(){}

    public Event(String id, String name, String description, String longdescription, String kategori, String kegiatan, String lokasi, String tiket, String waktu, String cp, String link, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.longdescription = longdescription;
        this.kategori = kategori;
        this.kegiatan = kegiatan;
        this.lokasi = lokasi;
        this.tiket = tiket;
        this.waktu = waktu;
        this.cp = cp;
        this.link = link;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLongdescription() {
        return longdescription;
    }

    public void setLongdescription(String longdescription) {
        this.longdescription = longdescription;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getKegiatan() {
        return kegiatan;
    }

    public void setKegiatan(String kegiatan) {
        this.kegiatan = kegiatan;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getTiket() {
        return tiket;
    }

    public void setTiket(String tiket) {
        this.tiket = tiket;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
