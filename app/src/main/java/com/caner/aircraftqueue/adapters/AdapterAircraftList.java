package com.caner.aircraftqueue.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.caner.aircraftqueue.R;
import com.caner.aircraftqueue.databinding.ItemListAircraftBinding;
import com.caner.aircraftqueue.models.ModelAircraft;
import com.caner.aircraftqueue.views.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Caner Kaşeler on February/2021.
 */
public class AdapterAircraftList extends RecyclerView.Adapter<AdapterAircraftList.AircraftHolder> {

    private final MainActivity mActivity;
    private List<ModelAircraft> mAircraftList = new ArrayList<>();

    public AdapterAircraftList(MainActivity mainActivity){
        this.mActivity = mainActivity;
    }

    @NonNull
    @Override
    public AircraftHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        ItemListAircraftBinding binding = DataBindingUtil.inflate(
                layoutInflater, R.layout.item_list_aircraft, parent, false);

        return new AircraftHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AircraftHolder holder, int position) {
        holder.itemListAircraftBinding.setModelAircraft(mAircraftList.get(position));
        holder.itemListAircraftBinding.btnAircraftDelete.setOnClickListener(v -> actionDelete(position));
    }

    @Override
    public int getItemCount() {
        return getAircraftListSize();
    }

    public void setAircraftList(List<ModelAircraft> list){
        mAircraftList.clear();
        mAircraftList.addAll(list);
        notifyDataSetChanged();
    }

    private int getAircraftListSize(){
        return mAircraftList.size();
    }

    private void actionDelete(int position){
        mActivity.deleteAircraft(mAircraftList.get(position));
    }

    //Holder class.
    static class AircraftHolder extends RecyclerView.ViewHolder {

        private ItemListAircraftBinding itemListAircraftBinding;

        public AircraftHolder(ItemListAircraftBinding viewDataBinding) {
            super(viewDataBinding.getRoot());
            this.itemListAircraftBinding = viewDataBinding;
        }
    }
}
