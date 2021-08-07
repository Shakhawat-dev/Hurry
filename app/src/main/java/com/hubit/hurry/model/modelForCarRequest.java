package com.hubit.hurry.model;

import java.io.Serializable;

public class modelForCarRequest implements Serializable {

     String postId  , userId  , userNotificationID  , driverId  , driverNotificationID ,
    toLoc , fromLoc ,  timeDate , carModl , driverName , status  , carLicNum , fare , carType ,
    reqDate , tripDetails  , returnTimee  , numOfPpl , rideType , transId , hour_time  ;

    public modelForCarRequest() {
    }

    public modelForCarRequest(String postId, String userId, String userNotificationID, String driverId, String driverNotificationID, String toLoc, String fromLoc, String timeDate, String carModl, String driverName, String status, String carLicNum, String fare, String carType, String reqDate, String tripDetails, String returnTimee, String numOfPpl, String rideType, String transId, String hour_time) {
        this.postId = postId;
        this.userId = userId;
        this.userNotificationID = userNotificationID;
        this.driverId = driverId;
        this.driverNotificationID = driverNotificationID;
        this.toLoc = toLoc;
        this.fromLoc = fromLoc;
        this.timeDate = timeDate;
        this.carModl = carModl;
        this.driverName = driverName;
        this.status = status;
        this.carLicNum = carLicNum;
        this.fare = fare;
        this.carType = carType;
        this.reqDate = reqDate;
        this.tripDetails = tripDetails;
        this.returnTimee = returnTimee;
        this.numOfPpl = numOfPpl;
        this.rideType = rideType;
        this.transId = transId;
        this.hour_time = hour_time;
    }

    public String getHour_time() {
        return hour_time;
    }

    public void setHour_time(String hour_time) {
        this.hour_time = hour_time;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNotificationID() {
        return userNotificationID;
    }

    public void setUserNotificationID(String userNotificationID) {
        this.userNotificationID = userNotificationID;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverNotificationID() {
        return driverNotificationID;
    }

    public void setDriverNotificationID(String driverNotificationID) {
        this.driverNotificationID = driverNotificationID;
    }

    public String getToLoc() {
        return toLoc;
    }

    public void setToLoc(String toLoc) {
        this.toLoc = toLoc;
    }

    public String getFromLoc() {
        return fromLoc;
    }

    public void setFromLoc(String fromLoc) {
        this.fromLoc = fromLoc;
    }

    public String getTimeDate() {
        return timeDate;
    }

    public void setTimeDate(String timeDate) {
        this.timeDate = timeDate;
    }

    public String getCarModl() {
        return carModl;
    }

    public void setCarModl(String carModl) {
        this.carModl = carModl;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCarLicNum() {
        return carLicNum;
    }

    public void setCarLicNum(String carLicNum) {
        this.carLicNum = carLicNum;
    }

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getReqDate() {
        return reqDate;
    }

    public void setReqDate(String reqDate) {
        this.reqDate = reqDate;
    }

    public String getTripDetails() {
        return tripDetails;
    }

    public void setTripDetails(String tripDetails) {
        this.tripDetails = tripDetails;
    }

    public String getReturnTimee() {
        return returnTimee;
    }

    public void setReturnTimee(String returnTimee) {
        this.returnTimee = returnTimee;
    }

    public String getNumOfPpl() {
        return numOfPpl;
    }

    public void setNumOfPpl(String numOfPpl) {
        this.numOfPpl = numOfPpl;
    }

    public String getRideType() {
        return rideType;
    }

    public void setRideType(String rideType) {
        this.rideType = rideType;
    }
}
