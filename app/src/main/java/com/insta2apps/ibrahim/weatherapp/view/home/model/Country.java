package com.insta2apps.ibrahim.weatherapp.view.home.model;

/**
 * Created by Ibrahim AbdelGawad on 1/30/2018.
 */

public class Country {
    private Integer id;
    private String name;
    private String country;
    private Coordinator Coordinator;

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

    public Coordinator getCoordinator() {
        return Coordinator;
    }

    public void setCoordinator(Coordinator Coordinator) {
        this.Coordinator = Coordinator;
    }
}
