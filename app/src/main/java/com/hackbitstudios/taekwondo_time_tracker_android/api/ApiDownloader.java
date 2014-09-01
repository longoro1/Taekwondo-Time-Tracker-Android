package com.hackbitstudios.taekwondo_time_tracker_android.api;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

/**
 * This abstract class will use ApiModel to get the uri and query the db.
 * It extends AsyncTask<TypeOfVarArgParams , ProgressValue , ResultValue>
 */
public abstract class ApiDownloader extends AsyncTask<Void, Void, Void> {

    //region PRIVATE MEMBERS

    // The api model instance
    private ApiModel apiModel;

    // This member will perform the http request and report the result
    private HttpClient httpClient;
    //endregion

    //region PROTECTED MEMBERS

    // Whether or not an error was found
    protected boolean wasSuccessful = false;

    // Error Message
    protected String error = "Something went wrong";

    //endregion

    //region CTOR

    public ApiDownloader(ApiModel _apiModel) {
        apiModel = _apiModel;
    }

    //endregion

    //region ABSTRACT METHODS

    /* This function must be implemented by child classes */
    protected abstract void processHttpEntity (HttpEntity _entity);

    //endregion


    // region ASYNC METHODS

    // The setup function
    /*@Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }*/

    @Override
    protected Void doInBackground(Void... params)
    {
        // The response from the http request
        HttpResponse httpResponse;

        // Gets the body of the http response
        HttpEntity httpEntity;

        try {

            // Create a new HTTP client
            httpClient = new DefaultHttpClient();

            // Get the response from the web
            httpResponse = httpClient.execute(new HttpGet(apiModel.getURI()));

            // Check if it was successful
            if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {

                // Was an error, set necessary flags
                wasSuccessful = false;
                error = httpResponse.getStatusLine().getReasonPhrase();

                return null;
            }

            // No errors, so process the response
            else {
                // Get the HttpEntity and process it
                httpEntity = httpResponse.getEntity();
                processHttpEntity (httpEntity);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result)
    {
        System.out.println("Finished async task for ApiDownloader.");

        //super.onPostExecute(result);
    }

    //endregion

}
