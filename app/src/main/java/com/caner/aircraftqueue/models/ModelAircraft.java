package com.caner.aircraftqueue.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.caner.aircraftqueue.utils.Constants;

/**
 * Created by Caner Kaşeler on February/2021.
 */

@Entity(tableName = Constants.DATABASE_TABLE)
public class ModelAircraft {

    @ColumnInfo(name = Constants.DATABASE_SIZE)
    private String size;

    @ColumnInfo(name = Constants.DATABASE_TYPE)
    private String type;

    @ColumnInfo(name = Constants.DATABASE_ORDER)
    private String order;

    @ColumnInfo(name = Constants.DATABASE_ID)
    @PrimaryKey(autoGenerate = true)
    private long id;

    @Ignore
    public ModelAircraft() { }

    public ModelAircraft(long id, String order, String size, String type) {
        this.id = id;
        this.order = order;
        this.size = size;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}

