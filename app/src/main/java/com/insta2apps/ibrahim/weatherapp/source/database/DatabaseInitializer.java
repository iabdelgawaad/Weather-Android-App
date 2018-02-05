package com.insta2apps.ibrahim.weatherapp.source.database;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.insta2apps.ibrahim.weatherapp.source.database.entity.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ibrahim AbdelGawad on 2/3/2018.
 */

public class DatabaseInitializer {
    public interface OnDatabaseCrudOperation {
        void getSelectedCities(List<City> cityList);
    }

    OnDatabaseCrudOperation onDatabaseCrudOperation;

    public DatabaseInitializer(OnDatabaseCrudOperation onDatabaseCrudOperation) {
        this.onDatabaseCrudOperation = onDatabaseCrudOperation;
    }

    private static void addCity(final AppDatabase db, City city) {
        db.cityDao().insertAll(city);
    }

    private static void removeCity(final AppDatabase db, City city) {
        db.cityDao().deleteCityByName(city.getName());
    }


    private static final String TAG = DatabaseInitializer.class.getName();

    public void populateAsync(@NonNull final AppDatabase db) {
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }


    public void addAsync(@Nullable AppDatabase db, City city) {
        AddDbAsync addDbAsync = new AddDbAsync(db, city);
        addDbAsync.execute();
    }

    class AddDbAsync extends AsyncTask<Void, Void, Void> {
        private final AppDatabase mDb;
        private final City city;

        AddDbAsync(AppDatabase mDb, City city) {
            this.mDb = mDb;
            this.city = city;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            addCity(mDb, city);
            return null;
        }
    }

    class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase mDb;
        private List<City> cityList;

        PopulateDbAsync(AppDatabase db) {
            mDb = db;
            cityList = new ArrayList<>();
        }

        @Override
        protected Void doInBackground(final Void... params) {
             cityList = mDb.cityDao().getAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            onDatabaseCrudOperation.getSelectedCities(cityList);
        }
    }

    public void removeAsync(@Nullable AppDatabase db, City city) {
        RemoveDbAsync removeDbAsync = new RemoveDbAsync(db, city);
        removeDbAsync.execute();
    }

    class RemoveDbAsync extends AsyncTask<Void, Void, Void> {
        private final AppDatabase mDb;
        private final City city;

        RemoveDbAsync(AppDatabase mDb, City city) {
            this.mDb = mDb;
            this.city = city;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            removeCity(mDb, city);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            PopulateDbAsync populateDbAsync = new PopulateDbAsync(this.mDb);
            populateDbAsync.execute();
        }
    }
}
