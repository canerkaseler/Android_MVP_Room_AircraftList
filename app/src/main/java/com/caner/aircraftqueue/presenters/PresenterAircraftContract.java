package com.caner.aircraftqueue.presenters;

import com.caner.aircraftqueue.models.ModelAircraft;

import java.util.List;

/**
 * Created by Caner Kaşeler on February/2021.
 */
public interface PresenterAircraftContract {

    interface View {

        void showListAircraft(List<ModelAircraft> aircraftList);

        void resetListAircraft();

        void showDeleteError(int stringId);
    }

    interface Presenter {

        void checkDatabase();

        void addAircraft(String size, String type);

        void removeAircraft(ModelAircraft modelAircraft);

        void resetAircraftList();
    }

}
