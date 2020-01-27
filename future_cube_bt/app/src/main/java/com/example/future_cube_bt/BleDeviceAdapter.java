package com.example.future_cube_bt;

import android.bluetooth.BluetoothDevice;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BleDeviceAdapter extends RecyclerView.Adapter{

    private DeviceScanActivity deviceScanActivity;
    private List<BluetoothDevice> deviceList = new ArrayList<>();

    public class DeviceViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView name;

        public DeviceViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.txt_item_name);
        }
    }

    public BleDeviceAdapter(DeviceScanActivity devAct) {
        this.deviceScanActivity = devAct;
    }

    public void addDevice(BluetoothDevice device){
        if(deviceList.isEmpty()){
            deviceList.add(device);
            this.notifyDataSetChanged();
        }
        else{
            if(notInList(device)){
                deviceList.add(device);
                this.notifyDataSetChanged();
            }
        }
    }

    private boolean notInList(BluetoothDevice device){
        for (int i = 0; i < deviceList.size(); i++){
            if(device.getAddress().equals(deviceList.get(i).getAddress())){
                return false;
            }
        }
        return true;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new DeviceViewHolder(view);
    }

    //Tried to put final here
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        ((DeviceViewHolder) holder).name.setText(deviceList.get(position).getName() + " MAC: " + deviceList.get(position).getAddress());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Item is clicked
            }
        });
    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }

    public List<BluetoothDevice> getDeviceList(){
        return deviceList;
    }
}
