package com.hackbitstudios.taekwondo_time_tracker_android.api.taekwondo.post;

import com.hackbitstudios.taekwondo_time_tracker_android.api.ApiDownloader;
import com.hackbitstudios.taekwondo_time_tracker_android.api.ApiModel;

import org.apache.http.HttpEntity;

/**
 * Created by Robert on 9/2/2014.
 * The following file performs a post method to store a punchcard. It does not do much processing.
 */
public abstract class ApiDownloaderPost extends ApiDownloader{

    //region CTOR
    public ApiDownloaderPost (ApiModel _apiModel) {
        super(_apiModel);
    }
    //endregion

    //region METHOD OVERRIDES
    @Override
    protected void onPostExecute(Void result)
    {
        super.onPostExecute(result);

        onResponse (wasSuccessful, error);
    }

    /* Processes the HTTP Entity and spits out the result in the form of an Array List */
    @Override
    protected void processHttpEntity (HttpEntity _entity) {

        // Nothing to process, if we got this far then there were no http errors
        wasSuccessful = true;
    }

    //endregion

    /* Return stats and error information to calling class */
    public abstract void onResponse(boolean wasSuccessful, String error);

    //endregion


}
