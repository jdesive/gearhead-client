/*
 * Copyright (C) 2017  GearHead
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.desive.gearhead.entities;

import org.json.simple.JSONObject;

public class Car {

    private int carsid;
    private String make;
    private String model;
    private String color;
    private long vin;
    private String oilType;
    private double oilCapacity;
    private double coolantCapacity;
    private String oilFilterModel;
    private String airFilterModel;
    private String cabinFilterModel;
    private String batteryModel;

    public Car(JSONObject obj) {
        this.carsid = Integer.valueOf(String.valueOf(obj.get("id")));
        this.make = ((String) obj.get("make"));
        this.model = ((String) obj.get("model"));
        this.color = ((String) obj.get("color"));
        this.vin = ((Long) obj.get("vin"));
        this.oilType = ((String) obj.get("oilType"));
        this.oilCapacity = ((Double) obj.get("oilCapacity"));
        this.coolantCapacity = ((Double) obj.get("coolantCapacity"));
        this.oilFilterModel = ((String) obj.get("oilFilterModel"));
        this.airFilterModel = ((String) obj.get("airFilterModel"));
        this.cabinFilterModel = ((String) obj.get("cabinFilterModel"));
        this.batteryModel = ((String) obj.get("batteryModel"));
    }

    public int getId() {
        return carsid;
    }

    public void setId(int id) {
        this.carsid = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public long getVin() {
        return vin;
    }

    public void setVin(long vin) {
        this.vin = vin;
    }

    public String getOilType() {
        return oilType;
    }

    public void setOilType(String oilType) {
        this.oilType = oilType;
    }

    public double getOilCapacity() {
        return oilCapacity;
    }

    public void setOilCapacity(double oilCapacity) {
        this.oilCapacity = oilCapacity;
    }

    public double getCoolantCapacity() {
        return coolantCapacity;
    }

    public void setCoolantCapacity(double coolantCapacity) {
        this.coolantCapacity = coolantCapacity;
    }

    public String getOilFilterModel() {
        return oilFilterModel;
    }

    public void setOilFilterModel(String oilFilterModel) {
        this.oilFilterModel = oilFilterModel;
    }

    public String getAirFilterModel() {
        return airFilterModel;
    }

    public void setAirFilterModel(String airFilterModel) {
        this.airFilterModel = airFilterModel;
    }

    public String getCabinFilterModel() {
        return cabinFilterModel;
    }

    public void setCabinFilterModel(String cabinFilterModel) {
        this.cabinFilterModel = cabinFilterModel;
    }

    public String getBatteryModel() {
        return batteryModel;
    }

    public void setBatteryModel(String batteryModel) {
        this.batteryModel = batteryModel;
    }

    @Override
    public String toString(){
        return make + ", " + model + " (" + color + ") - " + vin + " [" + carsid + "]";
    }

}
