package com.hackbitstudios.taekwondo_time_tracker_android.api.taekwondo.Purpose;

import com.hackbitstudios.taekwondo_time_tracker_android.api.ApiDownloader;
import com.hackbitstudios.taekwondo_time_tracker_android.api.ApiModel;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Robert on 9/1/2014.
 * This class extends the api downloader and is able to download purposes from the db
 */
public abstract class ApiDownloaderPurpose extends ApiDownloader {

    //region MEMBERS
    ArrayList<ApiObjectPurpose> purposeArrayList;
    //endregion

    //region CTOR
    public ApiDownloaderPurpose(ApiModel _apiModel){
        super(_apiModel);

        // Initialize the array list
        purposeArrayList = new ArrayList<ApiObjectPurpose>();
    }

    //endregion

    //region METHOD OVERRIDES
    @Override
    protected void onPostExecute(Void result)
    {
        super.onPostExecute(result);

        onResponse (purposeArrayList, wasSuccessful, error);
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
                purposeArrayList.add(getPurpose(jsonArray.getJSONObject(i)));
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

    /* Invoked the constructor of the ApiObjectPurpose using values in the JSONObject
    * _object:: one item in the JSONArray
    * RETURNS:: the constructed class */
    private ApiObjectPurpose getPurpose (JSONObject _object) {

        try {
            // Try to construct the object
            return new ApiObjectPurpose(_object.getString("name"), _object.getInt("id"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /* Return stats and error information to calling class */
    public abstract void onResponse(ArrayList<ApiObjectPurpose> purpose, boolean wasSuccessful, String error);

    //endregion

}
