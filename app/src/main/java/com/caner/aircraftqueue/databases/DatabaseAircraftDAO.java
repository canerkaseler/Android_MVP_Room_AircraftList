package com.caner.aircraftqueue.databases;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.caner.aircraftqueue.models.ModelAircraft;

import java.util.List;

/**
 * Created by Caner Kaşeler on February/2021.
 *
 * DAO: Data Access Objects.
 */

@Dao
public interface DatabaseAircraftDAO {

    //Elementary functions.

    @Insert
    void insertAircraft(ModelAircraft contactEntity);

    @Delete
    void deleteAircraft(ModelAircraft contactEntity);

    @Query("select * from aircraft_list")
    List<ModelAircraft> getAircraftList();

    @Query("DELETE FROM aircraft_list")
    void resetAircraftList();

    //This part for analyze.

    @Query("select * from aircraft_list where aircraft_type == 'Cargo' ")
    List<ModelAircraft> getAircraftListCargo();

    @Query("select * from aircraft_list where aircraft_type == 'Passenger'")
    List<ModelAircraft> getAircraftListPassenger();

    @Query("select * from aircraft_list where aircraft_size == 'Small' ")
    List<ModelAircraft> getAircraftListSmall();

    @Query("select * from aircraft_list where aircraft_size == 'Large'")
    List<ModelAircraft> getAircraftListLarge();
}
