package com.spectron.dragoesdeshangrila;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Message;

import com.spectron.dragoesdeshangrila.activities.BluetoothDevicesActivity;

import java.io.IOException;
import java.util.UUID;

public class ConnectionThread extends Thread {

    private BluetoothSocket btSocket = null;
    private BluetoothServerSocket btServerSocket = null;
    private String btDevAddress;
    private String myUUID;
    private boolean server;
    private boolean running;

    public ConnectionThread() {
        this.server = true;
        running = false;
        myUUID = "00001101-0000-1000-8000-00805F9B34FB";
    }

    public ConnectionThread(String btDevAddress) {
        this.server = false;
        this.btDevAddress = null;
        this.btDevAddress = btDevAddress;
        running = false;
        myUUID = "00001101-0000-1000-8000-00805F9B34FB";
    }

    public void run() {
        this.running = true;
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        try {
            if (this.server) {
                btServerSocket = btAdapter.listenUsingRfcommWithServiceRecord("Super Bluetooth", UUID.fromString(myUUID));
                btSocket = btServerSocket.accept();

                if (btSocket != null) {
                    btServerSocket.close();
                }
            } else {
                BluetoothDevice btDevice = btAdapter.getRemoteDevice(btDevAddress);
                btSocket = btDevice.createRfcommSocketToServiceRecord(UUID.fromString(myUUID));

                btAdapter.cancelDiscovery();

                if (btSocket != null) {
                    btSocket.connect();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            toMainActivity("---N".getBytes());
        }
    }

    private void toMainActivity(byte[] data) {
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putByteArray("data", data);
        message.setData(bundle);
        BluetoothDevicesActivity.handler.sendMessage(message);
    }

    public void cancel() {
        running = false;
        try {
            btServerSocket.close();
            btSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}