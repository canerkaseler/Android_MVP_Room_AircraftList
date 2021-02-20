package com.caner.aircraftqueue;

import com.caner.aircraftqueue.models.ModelAircraft;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by Caner Kaşeler on February/2021.
 */
public class CustomUnitTest {

    ArrayList<ModelAircraft> arrayList = new ArrayList<>();

    @BeforeClass
    public void initValue(){
        ModelAircraft modelAircraft1 = new ModelAircraft(0, "1", "Small", "Cargo");
        ModelAircraft modelAircraft2 = new ModelAircraft(0, "2", "Large", "Cargo");
        arrayList.add(modelAircraft1);
        arrayList.add(modelAircraft2);
    }

}
