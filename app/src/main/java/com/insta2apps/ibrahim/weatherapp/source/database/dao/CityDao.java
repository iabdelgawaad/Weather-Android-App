package com.insta2apps.ibrahim.weatherapp.source.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.insta2apps.ibrahim.weatherapp.source.database.entity.City;

import java.util.List;

/**
 * Created by Ibrahim AbdelGawad on 2/3/2018.
 */
@Dao
public interface CityDao {
    @Query("SELECT * FROM city")
    List<City> getAll();

    @Query("SELECT * FROM city where name LIKE  :cityName")
    City findByName(String cityName);

    @Query("SELECT COUNT(*) from city")
    int countCities();

    @Insert
    void insertAll(City... cities);

    @Delete
    void delete(City city);
}
