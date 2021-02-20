package com.caner.aircraftqueue.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.caner.aircraftqueue.R;
import com.caner.aircraftqueue.adapters.AdapterAircraftList;
import com.caner.aircraftqueue.databases.DatabaseAircraft;
import com.caner.aircraftqueue.databinding.ActivityMainBinding;
import com.caner.aircraftqueue.models.ModelAircraft;
import com.caner.aircraftqueue.presenters.PresenterAircraft;
import com.caner.aircraftqueue.presenters.PresenterAircraftContract;
import com.caner.aircraftqueue.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PresenterAircraftContract.View {

    private ActivityMainBinding mBinding;
    private AdapterAircraftList mAdapterAircraftList;
    private PresenterAircraftContract.Presenter mPresenter;
    private boolean isDatabaseHasData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
        setOnClicks();
        checkDatabase();
    }

    @Override
    public void showListAircraft(List<ModelAircraft> aircraftList) {
        if (aircraftList.isEmpty())
            actionResetList();
        else
            actionListFull(aircraftList);
    }

    @Override
    public void resetListAircraft() {
        actionResetList();
    }

    @Override
    public void showDeleteError(int stringId) {
        showDeleteErrorMessage(stringId);
    }

    private void init(){
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mAdapterAircraftList = new AdapterAircraftList(this);
        mPresenter = new PresenterAircraft(MainActivity.this, getDatabaseAircraft());

        mBinding.rvAircraftList.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rvAircraftList.setAdapter(mAdapterAircraftList);
    }

    private void setOnClicks(){
        //View.
        mBinding.btnAdd.setOnClickListener(v -> {
            callDialogAddAircraft(true);
            callAddButton(false);
            callResetButton(false);
        });
        mBinding.btnReset.setOnClickListener(v -> resetAircraftDatabase());

        //Dialog.
        mBinding.dialogAircraftAdd.btnCancel.setOnClickListener(v -> {
            callDialogAddAircraft(false);
            callAddButton(true);
            callResetButton(isDatabaseHasData);
        });
        mBinding.dialogAircraftAdd.btnOk.setOnClickListener(v -> {
            actionBtnOk();
            callAddButton(true);
            callResetButton(true);
        });
    }

    private DatabaseAircraft getDatabaseAircraft(){
        return Room.databaseBuilder(
                getApplicationContext(), DatabaseAircraft.class, Constants.DATABASE_NAME
        ).allowMainThreadQueries().build();
    }

    private void checkDatabase(){
        mPresenter.checkDatabase();
    }

    private void actionBtnOk(){
        if (isDialogAddAircraftSizeChecked() && isDialogAddAircraftTypeChecked()){

            saveNewAircraft(getSizeNewAircraft(), getTypeNewAircraft());
            callDialogAddAircraft(false);
        }
        else showDialogAddNewAircraftError();
    }

    private void actionListFull(List<ModelAircraft> aircraftList){
        isDatabaseHasData = true;
        callResetButton(true);
        callEmptyListMessage(false);
        mAdapterAircraftList.setAircraftList(aircraftList);
    }

    private void actionResetList(){
        isDatabaseHasData = false;
        mAdapterAircraftList.setAircraftList(new ArrayList<>());
        callResetButton(false);
        callEmptyListMessage(true);
    }

    //Database insert action by Presenter.
    private void saveNewAircraft(String size, String type){
        mPresenter.addAircraft(size, type);
    }

    //Database reset action by Presenter.
    private void resetAircraftDatabase(){
        mPresenter.resetAircraftList();
    }

    //Database delete model action by Presenter.
    public void deleteAircraft(ModelAircraft modelAircraft){
        mPresenter.removeAircraft(modelAircraft);
    }

    private void callDialogAddAircraft(boolean isVisible){
        clearDialogAddAircraftSizeSelection();
        clearDialogAddAircraftTypeSelection();
        mBinding.dialogAircraftAdd.getRoot().setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    private boolean isDialogAddAircraftSizeChecked(){
        int checkedRadioButtonId = mBinding.dialogAircraftAdd.rgAircraftSize.getCheckedRadioButtonId();
        RadioButton radioButton = mBinding.dialogAircraftAdd.rgAircraftSize.findViewById(checkedRadioButtonId);
        return radioButton != null;
    }

    private boolean isDialogAddAircraftTypeChecked(){
        int checkedRadioButtonId = mBinding.dialogAircraftAdd.rgAircraftType.getCheckedRadioButtonId();
        RadioButton radioButton = mBinding.dialogAircraftAdd.rgAircraftType.findViewById(checkedRadioButtonId);
        return radioButton != null;
    }

    private String getTypeNewAircraft(){
        int checkedRadioButtonId = mBinding.dialogAircraftAdd.rgAircraftType.getCheckedRadioButtonId();
        RadioButton radioButton = mBinding.dialogAircraftAdd.rgAircraftType.findViewById(checkedRadioButtonId);
        return radioButton.getText().toString();
    }

    private String getSizeNewAircraft(){
        int checkedRadioButtonId = mBinding.dialogAircraftAdd.rgAircraftSize.getCheckedRadioButtonId();
        RadioButton radioButton = mBinding.dialogAircraftAdd.rgAircraftSize.findViewById(checkedRadioButtonId);
        return radioButton.getText().toString();
    }

    private void clearDialogAddAircraftSizeSelection(){
        mBinding.dialogAircraftAdd.rgAircraftSize.clearCheck();
    }

    private void clearDialogAddAircraftTypeSelection(){
        mBinding.dialogAircraftAdd.rgAircraftType.clearCheck();
    }

    private void showDialogAddNewAircraftError(){
        Toast.makeText(this, R.string.msg_dialog_error, Toast.LENGTH_SHORT).show();
    }

    public void showDeleteErrorMessage(int stringId){
        Toast.makeText(this, stringId, Toast.LENGTH_SHORT).show();
    }

    private void callAddButton(boolean isVisible){
        mBinding.btnAdd.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    private void callResetButton(boolean isVisible) {
        mBinding.btnReset.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    private void callEmptyListMessage(boolean isVisible){
        mBinding.tvEmptyListMessage.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }
}