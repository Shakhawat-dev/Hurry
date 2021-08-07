package com.hubit.hurry.model;

import java.io.Serializable;
public class driverProfileModel implements Serializable {
    String carLic, driverEarnedLifeLong, driverEarnedThisMonth, driverFined, driverIdActivated,
            driverJoinedDate, driverName, driverRating, driver_license_image, email,
            fitness_license_image, nid_card_image, phone, profile_picture, tax_token_image, totalRides, userID, vehicle_reg_image, acType, carType, buildCompany, carModel, carYear, truckSize, sitCount, tripCounter,driverNotificationID;

    carPics carPics ;


    public driverProfileModel() {
    }

    public driverProfileModel(String carLic, String driverEarnedLifeLong, String driverEarnedThisMonth, String driverFined,
                              String driverIdActivated, String driverJoinedDate, String driverName, String driverRating,
                              String driver_license_image, String email, String fitness_license_image,
                              String nid_card_image, String phone, String profile_picture, String tax_token_image,
                              String totalRides, String userID, String vehicle_reg_image, String acType, String carType,
                              String buildCompany, String carModel, String carYear, String truckSize, String sitCount,
                              String tripCounter) {
        this.carLic = carLic;
        this.driverEarnedLifeLong = driverEarnedLifeLong;
        this.driverEarnedThisMonth = driverEarnedThisMonth;
        this.driverFined = driverFined;
        this.driverIdActivated = driverIdActivated;
        this.driverJoinedDate = driverJoinedDate;
        this.driverName = driverName;
        this.driverRating = driverRating;
        this.driver_license_image = driver_license_image;
        this.email = email;
        this.fitness_license_image = fitness_license_image;
        this.nid_card_image = nid_card_image;
        this.phone = phone;
        this.profile_picture = profile_picture;
        this.tax_token_image = tax_token_image;
        this.totalRides = totalRides;
        this.userID = userID;
        this.vehicle_reg_image = vehicle_reg_image;
        this.acType = acType;
        this.carType = carType;
        this.buildCompany = buildCompany;
        this.carModel = carModel;
        this.carYear = carYear;
        this.truckSize = truckSize;
        this.sitCount = sitCount;
        this.tripCounter = tripCounter;
    }

    public String getDriverNotificationID() {
        return driverNotificationID;
    }

    public void setDriverNotificationID(String driverNotificationID) {
        this.driverNotificationID = driverNotificationID;
    }


    public driverProfileModel.carPics getCarPics() {
        return carPics;
    }

    public void setCarPics(driverProfileModel.carPics carPics) {
        this.carPics = carPics;
    }

    public String getTripCounter() {
        return tripCounter;
    }

    public void setTripCounter(String tripCounter) {
        this.tripCounter = tripCounter;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getBuildCompany() {
        return buildCompany;
    }

    public void setBuildCompany(String buildCompany) {
        this.buildCompany = buildCompany;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarYear() {
        return carYear;
    }

    public void setCarYear(String carYear) {
        this.carYear = carYear;
    }

    public String getTruckSize() {
        return truckSize;
    }

    public void setTruckSize(String truckSize) {
        this.truckSize = truckSize;
    }

    public String getSitCount() {
        return sitCount;
    }

    public void setSitCount(String sitCount) {
        this.sitCount = sitCount;
    }

    public String getCarLic() {
        return carLic;
    }

    public void setCarLic(String carLic) {
        this.carLic = carLic;
    }

    public String getDriverEarnedLifeLong() {
        return driverEarnedLifeLong;
    }

    public void setDriverEarnedLifeLong(String driverEarnedLifeLong) {
        this.driverEarnedLifeLong = driverEarnedLifeLong;
    }

    public String getDriverEarnedThisMonth() {
        return driverEarnedThisMonth;
    }

    public void setDriverEarnedThisMonth(String driverEarnedThisMonth) {
        this.driverEarnedThisMonth = driverEarnedThisMonth;
    }

    public String getDriverFined() {
        return driverFined;
    }

    public void setDriverFined(String driverFined) {
        this.driverFined = driverFined;
    }

    public String getDriverIdActivated() {
        return driverIdActivated;
    }

    public void setDriverIdActivated(String driverIdActivated) {
        this.driverIdActivated = driverIdActivated;
    }

    public String getDriverJoinedDate() {
        return driverJoinedDate;
    }

    public void setDriverJoinedDate(String driverJoinedDate) {
        this.driverJoinedDate = driverJoinedDate;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverRating() {
        return driverRating;
    }

    public void setDriverRating(String driverRating) {
        this.driverRating = driverRating;
    }

    public String getDriver_license_image() {
        return driver_license_image;
    }

    public void setDriver_license_image(String driver_license_image) {
        this.driver_license_image = driver_license_image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFitness_license_image() {
        return fitness_license_image;
    }

    public void setFitness_license_image(String fitness_license_image) {
        this.fitness_license_image = fitness_license_image;
    }

    public String getNid_card_image() {
        return nid_card_image;
    }

    public void setNid_card_image(String nid_card_image) {
        this.nid_card_image = nid_card_image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getTax_token_image() {
        return tax_token_image;
    }

    public void setTax_token_image(String tax_token_image) {
        this.tax_token_image = tax_token_image;
    }

    public String getTotalRides() {
        return totalRides;
    }

    public void setTotalRides(String totalRides) {
        this.totalRides = totalRides;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getVehicle_reg_image() {
        return vehicle_reg_image;
    }

    public void setVehicle_reg_image(String vehicle_reg_image) {
        this.vehicle_reg_image = vehicle_reg_image;
    }

    public  static  class  carPics implements  Serializable {
        public  String car_back_image , car_front_image , car_inside_image ,car_side_image ;

        public carPics() {
        }

        public carPics(String car_back_image, String car_front_image, String car_inside_image, String car_side_image) {
            this.car_back_image = car_back_image;
            this.car_front_image = car_front_image;
            this.car_inside_image = car_inside_image;
            this.car_side_image = car_side_image;
        }

        public String getCar_back_image() {
            return car_back_image;
        }

        public void setCar_back_image(String car_back_image) {
            this.car_back_image = car_back_image;
        }

        public String getCar_front_image() {
            return car_front_image;
        }

        public void setCar_front_image(String car_front_image) {
            this.car_front_image = car_front_image;
        }

        public String getCar_inside_image() {
            return car_inside_image;
        }

        public void setCar_inside_image(String car_inside_image) {
            this.car_inside_image = car_inside_image;
        }

        public String getCar_side_image() {
            return car_side_image;
        }

        public void setCar_side_image(String car_side_image) {
            this.car_side_image = car_side_image;
        }
    }
}
