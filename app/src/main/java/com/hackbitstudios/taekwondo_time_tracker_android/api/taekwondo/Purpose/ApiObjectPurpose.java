package com.hackbitstudios.taekwondo_time_tracker_android.api.taekwondo.Purpose;

/**
 * Created by Robert on 9/1/2014.
 * This object is a container that will hold the results when trying to retrieve the training purposes from the db*/

public class ApiObjectPurpose {

    //region MEMBERS

    // The values from the db
    String name;
    int id;

    //endregion

    /* Constructor */
    public ApiObjectPurpose(String _name, int _id)
    {
        name = _name;
        id = _id;
    }
    //endregion

    //region PUBLIC ACCESSORS

    public String getName() { return name; }
    public int getId() { return id; }

    //endregion
}
