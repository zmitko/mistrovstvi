package com.example.future_cube_bt;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class DeviceScanActivity extends AppCompatActivity {

    BluetoothManager bluetoothManager;
    BluetoothAdapter bluetoothAdapter;
    private BluetoothAdapter.LeScanCallback leScanCallback;

    private boolean mScanning;
    private Handler handler;
    private static final long SCAN_PERIOD = 10000;

    BleDeviceAdapter bleDeviceAdapter;

    private TextView txtListCount;
    private Button btnStop;
    private RecyclerView rvDevices;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_scan);

        txtListCount = findViewById(R.id.txt_list_count);
        btnStop = findViewById(R.id.btn_stop);
        rvDevices = findViewById(R.id.rv_devices);

        handler = new Handler();

        leScanCallback = new BluetoothAdapter.LeScanCallback() {
                    @Override
                    public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                bleDeviceAdapter.addDevice(device);
                            }
                        });
                    }
                };

        bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
        bleDeviceAdapter = new BleDeviceAdapter(this);

        setListeners();
    }

    private void setListeners(){
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScanning = false;
                bluetoothAdapter.stopLeScan(leScanCallback);
                txtListCount.setText(String.valueOf(bleDeviceAdapter.getItemCount()));
            }
        });
    }

    @Override protected void onResume(){
        super.onResume();

        rvDevices = findViewById(R.id.rv_devices);
        layoutManager = new LinearLayoutManager(this);


        rvDevices.setLayoutManager(layoutManager);
        rvDevices.setAdapter(bleDeviceAdapter);

        mScanning = true;
        scanLeDevice(mScanning);
    }

    // Stops scanning after 10 seconds.
    private void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    bluetoothAdapter.stopLeScan(leScanCallback);
                    txtListCount.setText(String.valueOf(bleDeviceAdapter.getItemCount()));
                    showToast("Search has stopped.");
                }
            }, SCAN_PERIOD);

            mScanning = true;
            bluetoothAdapter.startLeScan(leScanCallback);
        } else {
            mScanning = false;
            bluetoothAdapter.stopLeScan(leScanCallback);
        }
    }

    private void showToast(String message){
        Toast.makeText(DeviceScanActivity.this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause(){
        super.onPause();

        mScanning = false;
        bluetoothAdapter.stopLeScan(leScanCallback);
    }
}
