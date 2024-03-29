package com.hackbitstudios.taekwondo_time_tracker_android.api.taekwondo.stat;

import com.hackbitstudios.taekwondo_time_tracker_android.api.ApiDownloader;
import com.hackbitstudios.taekwondo_time_tracker_android.api.ApiModel;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Robert on 8/31/2014.
 * This class extends the ApiDownloader to download stats from the database
 */
public abstract class ApiDownloaderStat extends ApiDownloader {

    //region MEMBERS
    ArrayList<ApiObjectStat> statArrayList;
    //endregion

    //region CTOR
    public ApiDownloaderStat(ApiModel _apiModel){
        super(_apiModel);

        // Initialize the array list
        statArrayList = new ArrayList<ApiObjectStat>();
    }

    //endregion

    //region METHOD OVERRIDES
    @Override
    protected void onPostExecute(Void result)
    {
        super.onPostExecute(result);

        onResponse (statArrayList, wasSuccessful, error);
    }

    /* Processes the HTTP Entity and spits out the result in the form of an Array List */
    @Override
    protected void processHttpEntity (HttpEntity _entity) {

        try {
            // Process the entity as a JSON Array
            JSONArray jsonArray = new JSONArray(EntityUtils.toString(_entity));

            // Finally, get the string
            // Add every object in array to Array List
            for (int i = 0; i < jsonArray.length(); i++){
                statArrayList.add(getStat(jsonArray.getJSONObject(i)));
            }
            wasSuccessful = true;

        } catch (Exception e) {
            e.printStackTrace();

            try {
                JSONObject jsonObject= new JSONObject(EntityUtils.toString(_entity));

                if (jsonObject.getString("response_type").equals("error"))
                    error = jsonObject.getString("response");
                else
                    error = "Unknown Error";

                wasSuccessful = false;
            } catch (Exception e2)
            {
                e2.printStackTrace();
            }
        }
    }

    //endregion

    //region PUBLIC METHODS

    /* Invoked the constructor of the APIObjectStat using values in the JSONObject
    * _object:: one item in the JSONArray
    * RETURNS:: the constructed class */
    private ApiObjectStat getStat (JSONObject _object) {

        try {
            // Try to construct the object
            return new ApiObjectStat(_object.getString("name"), _object.getInt("total_hours"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /* Return stats and error information to calling class */
    public abstract void onResponse(ArrayList<ApiObjectStat> stats, boolean wasSuccessful, String error);


    //endregion


}
