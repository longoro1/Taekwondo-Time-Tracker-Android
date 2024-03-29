package com.hackbitstudios.taekwondo_time_tracker_android.api.taekwondo.stat;

/**
 * Created by Robert on 8/31/2014.
 * this object is a container for the fields that result from an http request for stats
 */
public class ApiObjectStat {

    //region MEMBERS

    // The values from the db
    String name;
    int totalHours;

    //endregion

    /* Constructor */
    public ApiObjectStat(String _name, int _totalHours)
    {
        name = _name;
        totalHours = _totalHours;
    }
    //endregion

    //region PUBLIC ACCESSORS

    public String getName() { return name; }
    public int getTotalHours() { return totalHours; }

    public String getStat() {
        return name + " - " + new Integer(totalHours).toString() + " H";
    }

    //endregion

}
