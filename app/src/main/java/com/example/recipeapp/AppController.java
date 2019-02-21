package com.example.recipeapp;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * This class is singleton class that encapsulates RequestQueue and other Volley functionality
 * This class is available on Android Developers' website.
 * This code is taken from that website.
 *
 * Key concept here is that the RequestQueue must be instantiated with the Application context, not an
 * Activity context. This ensures that the RequestQueue will last for the lifetime of your app, instead
 * of being recreated every time the activity is recreated.
 *
 * @date 21-Feb-2019
 */
public class AppController extends Application
{
    public static final String TAG = AppController.class.getSimpleName();

    private RequestQueue mRequestQueue;

    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized AppController getInstance()
    {
        return mInstance;
    }

    public RequestQueue getmRequestQueue()
    {
        if(mRequestQueue == null)
        {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag)
    {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getmRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req)
    {
        req.setTag(TAG);
        getmRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag)
    {
        if(mRequestQueue != null)
        {
            mRequestQueue.cancelAll(tag);
        }
    }
}
