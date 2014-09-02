package com.hackbitstudios.taekwondo_time_tracker_android.api.taekwondo.purpose;

import com.hackbitstudios.taekwondo_time_tracker_android.api.ApiModel;

/**
 * Created by Robert on 9/1/2014.
 * This model defines a class that can be used to query the api for purposes
 */
public class ApiModelPurpose extends ApiModel {

    //region PRIVATE MEMBERS

    // This String is the model that needs to be invoked
    private final static String model_appointments = "api/v1/purposes";

    // The authentication token of the user
    private String auth_token;

    //endregion

    //region CTOR
    /* The ctor for ApiObjectPurpose
        _URI:: the uri to query at with no '/' at the end
        _auth_token:: the authentication token of the user */
    public ApiModelPurpose(String _uri, String _auth_token) {

        // Populate the parent
        super (_uri, model_appointments);

        // Populate the other fields
        auth_token = _auth_token;
    }
    //endregion

    //region PUBLIC FUNCTIONS

    // Gets the whole url to perform the query on
    @Override
    public String getURI (){
        return (super.getURI() + '?' + "access_token=" + auth_token).toString();
    }
    //endregion

}
