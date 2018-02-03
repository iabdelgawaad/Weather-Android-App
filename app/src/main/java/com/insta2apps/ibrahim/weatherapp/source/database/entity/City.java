package com.insta2apps.ibrahim.weatherapp.source.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.insta2apps.ibrahim.weatherapp.view.home.model.Coord;

/**
 * Created by Ibrahim AbdelGawad on 2/3/2018.
 */

@Entity(tableName = "city")
public class City {

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    @PrimaryKey(autoGenerate = true)

    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "country")
    private String country;
}
