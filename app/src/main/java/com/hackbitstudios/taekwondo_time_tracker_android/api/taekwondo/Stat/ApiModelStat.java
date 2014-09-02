package com.hackbitstudios.taekwondo_time_tracker_android.api.taekwondo.stat;

import com.hackbitstudios.taekwondo_time_tracker_android.api.ApiModel;

/**
 * Created by Robert on 8/31/2014.
 * This class defines a model that can be used to query the database for stats
 */
public class ApiModelStat extends ApiModel {

    //region PRIVATE MEMBERS

    // This String is the model that needs to be invoked
    private final static String model_appointments = "api/v1/stats";

    // The authentication token of the user
    private String auth_token;

    //endregion

    //region CTOR
    /* The ctor for the api model
        _URI:: the uri to query at with no '/' at the end
        _auth_token:: the authentication token of the user */
    public ApiModelStat(String _uri, String _auth_token) {

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
