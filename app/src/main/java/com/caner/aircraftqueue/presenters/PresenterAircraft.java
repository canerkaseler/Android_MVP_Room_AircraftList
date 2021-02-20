package com.caner.aircraftqueue.presenters;

import com.caner.aircraftqueue.R;
import com.caner.aircraftqueue.databases.DatabaseAircraft;
import com.caner.aircraftqueue.databases.DatabaseAircraftDAO;
import com.caner.aircraftqueue.models.ModelAircraft;
import com.caner.aircraftqueue.models.enumsAircraft.AircraftSize;
import com.caner.aircraftqueue.models.enumsAircraft.AircraftType;

import java.util.Collections;
import java.util.List;

/**
 * Created by Caner Kaşeler on February/2021.
 */
public class PresenterAircraft implements PresenterAircraftContract.Presenter{

    private final PresenterAircraftContract.View mView;
    private final DatabaseAircraft mDatabaseAircraft;

    //private final String mAircraftSizeSmall = AircraftSize.Small.toString();
    private final String mAircraftSizeLarge = AircraftSize.Large.toString();
    private final String mAircraftTypeCargo = AircraftType.Cargo.toString();
    private final String mAircraftTypePassenger = AircraftType.Passenger.toString();

    public PresenterAircraft(PresenterAircraftContract.View view, DatabaseAircraft database){
        this.mView = view;
        this.mDatabaseAircraft = database;
    }

    @Override
    public void checkDatabase() {
        showList(getDatabaseList());
    }

    @Override
    public void addAircraft(String size, String type) {
        String order;

        //if-else is there so that the sequence number is not confused with deleting items after added.
        if (getDatabaseList().size() > 0){
            int lastItemPosition = getDatabaseList().size() - 1;
            order = getDatabaseList().get(lastItemPosition).getOrder();
            order = String.valueOf(Integer.parseInt(order) + 1);
        }
        else
            order = String.valueOf(getDatabaseList().size() + 1);

        insertDatabase(new ModelAircraft(0, order, size, type));
        showList(getDatabaseList());
    }

    @Override
    public void removeAircraft(ModelAircraft modelAircraft) {
        deleteModelDatabase(modelAircraft);
    }

    @Override
    public void resetAircraftList() {
        deleteAllDatabase();
        resetList();
    }

    private DatabaseAircraftDAO callDatabaseDAO(){
        return mDatabaseAircraft.getDatabaseAircraftDAO();
    }

    private List<ModelAircraft> getDatabaseList(){
        return callDatabaseDAO().getAircraftList();
    }

    private void showList(List<ModelAircraft> list){
        Collections.reverse(list);
        mView.showListAircraft(list);
    }

    private void resetList(){
        mView.resetListAircraft();
    }

    //Database insert data.
    private void insertDatabase(ModelAircraft model){
        callDatabaseDAO().insertAircraft(model);
    }

    //Database delete specific data.
    private void deleteModelDatabase(ModelAircraft model){
        if (isDeletable(model)) {
            callDatabaseDAO().deleteAircraft(model);
            showList(getDatabaseList());
        }
        else mView.showDeleteError(R.string.msg_delete_error);
    }

    //Database delete all data.
    private void deleteAllDatabase(){
        callDatabaseDAO().resetAircraftList();
    }

    private boolean isDeletable(ModelAircraft modelAircraft){
        String modelAircraftType = modelAircraft.getType();
        String modelAircraftSize = modelAircraft.getSize();
        int modelOrder = Integer.parseInt(modelAircraft.getOrder());
        int firstOrder;

        if (getDatabaseListPassenger().size() > 0 && modelAircraftType.equals(mAircraftTypeCargo))
            return false;

        //Only passenger.
        if (modelAircraftType.equals(mAircraftTypePassenger)){

            //First large.
            if (modelAircraftSize.equals(mAircraftSizeLarge)){

                firstOrder = getFirstTypeOrderInLarge(mAircraftTypePassenger);
            }
            else { //After small.

                if (getFirstTypeOrderInLarge(mAircraftTypePassenger) == 0)
                    firstOrder = getFirstTypeOrderInSmall(mAircraftTypePassenger);
                else
                    return false;
            }
        }
        else { //Only cargo.

            //First large.
            if (modelAircraftSize.equals(mAircraftSizeLarge)){
                firstOrder = getFirstTypeOrderInLarge(mAircraftTypeCargo);
            }
            else { //After small.

                if (getFirstTypeOrderInLarge(mAircraftTypeCargo) == 0)
                    firstOrder = getFirstTypeOrderInSmall(mAircraftTypeCargo);
                else
                    return false;
            }
        }
        return modelOrder == firstOrder;
    }

    private int getFirstTypeOrderInLarge(String type){
        for (ModelAircraft model : getDatabaseListLarge()){
            if (model.getType().equals(type))
                return Integer.parseInt(model.getOrder());
        }
        return 0;
    }

    private int getFirstTypeOrderInSmall(String type){
        for (ModelAircraft model : getDatabaseListSmall()){
            if (model.getType().equals(type))
                return Integer.parseInt(model.getOrder());
        }
        return 0;
    }

    private List<ModelAircraft> getDatabaseListSmall(){
        return callDatabaseDAO().getAircraftListSmall();
    }

    private List<ModelAircraft> getDatabaseListLarge(){
        return callDatabaseDAO().getAircraftListLarge();
    }

    /*private List<ModelAircraft> getDatabaseListCargo(){
        return callDatabaseDAO().getAircraftListCargo();
    }*/

    private List<ModelAircraft> getDatabaseListPassenger(){
        return callDatabaseDAO().getAircraftListPassenger();
    }
}
