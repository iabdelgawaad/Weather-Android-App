package com.insta2apps.ibrahim.weatherapp.view.home.model;

/**
 * Created by Ibrahim AbdelGawad on 1/30/2018.
 */

public class Country {
    private Integer id;
    private String name;
    private String country;
    private Coord Coord;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Coord getCoord() {
        return Coord;
    }

    public void setCoord(Coord Coord) {
        this.Coord = Coord;
    }
}
