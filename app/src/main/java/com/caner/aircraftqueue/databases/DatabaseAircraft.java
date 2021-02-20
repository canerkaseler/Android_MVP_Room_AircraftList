package com.caner.aircraftqueue.databases;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.caner.aircraftqueue.models.ModelAircraft;

/**
 * Created by Caner Kaşeler on February/2021.
 */

@Database(entities = {ModelAircraft.class}, version = 1)
public abstract class DatabaseAircraft extends RoomDatabase {

    public abstract DatabaseAircraftDAO getDatabaseAircraftDAO();
}
