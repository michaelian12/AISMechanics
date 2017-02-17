package com.aisautocare.aismechanics;

/**
 * Created by Michael on 2/16/2017.
 */

public class GlobalVar {
    public String hostAPI = "http://192.168.43.142:8080/API/public/api";
    public static boolean isVehicleSelected = false;
    public static int waktuTempuh = 900; //second
    public boolean isVehicleSelected() {
        return isVehicleSelected;
    }

    public void setVehicleSelected(boolean vehicleSelected) {
        isVehicleSelected = vehicleSelected;
    }

    public String getHostAPI() {
        return hostAPI;
    }
}
