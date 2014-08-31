package com.hackbitstudios.taekwondo_time_tracker_android.api.models;

/**
 * This class defines an abstract model that can be used to query the database
 */
public abstract class ApiModel {

    //region PRIVATE MEMBERS

    // The URI to access
    private String uri;

    // The function to access (after the .com (with no '/' at the end))
    private String model;

    //endregion

    //region CTOR
    /* The ctor for the api model
        _URI:: the uri to query at
        model:: the function to access (after the .com)*/
    public ApiModel (String _URI, String _model) {

        // Copy the values
        uri = _URI;
        model = _model;
    }
    //endregion

    //region PUBLIC FUNCTIONS

    // Gets the whole url to perform the query on
    public String getURI (){
        return uri + '/' + model;
    }

    //endregion

}
