package com.metacoders.hurry.model;

public class completedTestModel {
    String CompleteDate , fareGained , fromLocation
            , toLocation , tripID ;

    public completedTestModel() {
    }

    public completedTestModel(String completeDate, String fareGained, String fromLocation, String toLocation, String tripID) {
        CompleteDate = completeDate;
        this.fareGained = fareGained;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.tripID = tripID;
    }

    public String getCompleteDate() {
        return CompleteDate;
    }

    public void setCompleteDate(String completeDate) {
        CompleteDate = completeDate;
    }

    public String getFareGained() {
        return fareGained;
    }

    public void setFareGained(String fareGained) {
        this.fareGained = fareGained;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public String getTripID() {
        return tripID;
    }

    public void setTripID(String tripID) {
        this.tripID = tripID;
    }
}
