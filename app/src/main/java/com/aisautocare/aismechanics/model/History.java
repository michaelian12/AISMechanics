package com.aisautocare.aismechanics.model;

/**
 * Created by Michael on 2/16/2017.
 */

public class History {

    private String date, price, customerName, address, vehicleName, vehicleYear, serviceName, paymentMethod;
    private int rate;

    public History(String date, String price, String customerName, String address, String vehicleName, String vehicleYear, String serviceName, String paymentMethod, int rate) {
        this.date = date;
        this.price = price;
        this.customerName = customerName;
        this.address = address;
        this.vehicleName = vehicleName;
        this.vehicleYear = vehicleYear;
        this.serviceName = serviceName;
        this.paymentMethod = paymentMethod;
        this.rate = rate;
    }

    public String getDate() {
        return date;
    }

    public String getPrice() {
        return price;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getAddress() {
        return address;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public String getVehicleYear() {
        return vehicleYear;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public int getRate() {
        return rate;
    }
}
