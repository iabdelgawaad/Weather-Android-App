package com.insta2apps.ibrahim.weatherapp.view.forecast.model;

import com.insta2apps.ibrahim.weatherapp.view.home.model.Coord;

/**
 * Created by Ibrahim AbdelGawad on 2/1/2018.
 */

public class City {
    private Integer id;
    private String name;
    private Coord coord;
    private String country;

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

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
