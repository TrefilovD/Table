package com.example.table;

import com.google.gson.annotations.SerializedName;

public class AuthorisationData {
    private String clientCode;
    private String clientEngine;
    private String clientEngineVersion;
    private String clientName;
    private String clientType;
    private String clientVersion;
    private String countryCode;
    private String countryName;
    private String deviceBrand;
    private String deviceModel;
    private String deviceName;
    private String $id;
    @SerializedName("mail")
    private String providerUid;
    private String userId;

    public String getUserID(){
        return userId;
    }

    public AuthorisationData(String clientCode, String clientEngine, String clientEngineVersion, String clientName, String clientType, String clientVersion, String countryCode, String countryName, String deviceBrand, String deviceModel, String deviceName, String $id, String providerUid, String userId) {
        this.clientCode = clientCode;
        this.clientEngine = clientEngine;
        this.clientEngineVersion = clientEngineVersion;
        this.clientName = clientName;
        this.clientType = clientType;
        this.clientVersion = clientVersion;
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.deviceBrand = deviceBrand;
        this.deviceModel = deviceModel;
        this.deviceName = deviceName;
        this.$id = $id;
        this.providerUid = providerUid;
        this.userId = userId;
    }
}
