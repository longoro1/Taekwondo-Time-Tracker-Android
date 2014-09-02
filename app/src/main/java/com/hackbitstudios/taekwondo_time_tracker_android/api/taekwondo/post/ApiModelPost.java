package com.hackbitstudios.taekwondo_time_tracker_android.api.taekwondo.post;

import com.hackbitstudios.taekwondo_time_tracker_android.api.ApiModel;

/**
 * Created by Robert on 9/2/2014.
 * This object extends the ApiModel to post to the website a new punchcard
 */
public class ApiModelPost extends ApiModel {

    //region PRIVATE MEMBERS

    // This String is the model that needs to be invoked
    private final static String model_appointments = "api/v1/posts";

    // The authentication token of the user
    private String auth_token;

    // Other Members (see Ctor for details)
    int trainingPurposeId;
    String date, startTime, endTime;

    //endregion

    //region CTOR
    /* The ctor for ApiObjectPurpose
        _URI:: the uri to query at with no '/' at the end
        _auth_token:: the authentication token of the user */
    public ApiModelPost(String _uri, String _auth_token, int _trainingPurposeId, String _date, String _startTime, String _endTime) {

        // Populate the parent
        super (_uri, model_appointments);

        // Populate the other fields
        auth_token = _auth_token;
        trainingPurposeId = _trainingPurposeId;
        date = _date;
        startTime = _startTime;
        endTime = _endTime;
    }
    //endregion

    //region PUBLIC FUNCTIONS

    // Gets the whole url to perform the query on
    @Override
    public String getURI (){
        return super.getURI() + '?' + "access_token=" + auth_token
                + '&' + "training_purpose_id=" + new Integer(trainingPurposeId).toString()
                + '&' + "start_time=" + startTime
                + '&' + "end_time=" + endTime
                + '&' + "day=" + date;
    }
    //endregion

}
