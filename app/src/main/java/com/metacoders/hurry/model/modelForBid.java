package com.metacoders.hurry.model;

public class modelForBid {

    String  postID ,tripID , driverUid , driverName , driverCarModel , driverRating  , bidPrice  ,drivercarcondition ,driverImageLink;


    public modelForBid() {
    }

    public modelForBid(String postID, String tripID, String driverUid, String driverName, String driverCarModel, String driverRating, String bidPrice, String drivercarcondition, String driverImageLink) {
        this.postID = postID;
        this.tripID = tripID;
        this.driverUid = driverUid;
        this.driverName = driverName;
        this.driverCarModel = driverCarModel;
        this.driverRating = driverRating;
        this.bidPrice = bidPrice;
        this.drivercarcondition = drivercarcondition;
        this.driverImageLink = driverImageLink;
    }

    public modelForBid(String postID, String tripID, String driverUid, String driverName, String driverCarModel, String driverRating, String bidPrice, String drivercarcondition) {
        this.postID = postID;
        this.tripID = tripID;
        this.driverUid = driverUid;
        this.driverName = driverName;
        this.driverCarModel = driverCarModel;
        this.driverRating = driverRating;
        this.bidPrice = bidPrice;
        this.drivercarcondition = drivercarcondition;
    }

    public String getDriverImageLink() {
        return driverImageLink;
    }

    public void setDriverImageLink(String driverImageLink) {
        this.driverImageLink = driverImageLink;
    }

    public String getDrivercarcondition() {
        return drivercarcondition;
    }

    public void setDrivercarcondition(String drivercarcondition) {
        this.drivercarcondition = drivercarcondition;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getTripID() {
        return tripID;
    }

    public void setTripID(String tripID) {
        this.tripID = tripID;
    }

    public String getDriverUid() {
        return driverUid;
    }

    public void setDriverUid(String driverUid) {
        this.driverUid = driverUid;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverCarModel() {
        return driverCarModel;
    }

    public void setDriverCarModel(String driverCarModel) {
        this.driverCarModel = driverCarModel;
    }

    public String getDriverRating() {
        return driverRating;
    }

    public void setDriverRating(String driverRating) {
        this.driverRating = driverRating;
    }

    public String getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(String bidPrice) {
        this.bidPrice = bidPrice;
    }
}
