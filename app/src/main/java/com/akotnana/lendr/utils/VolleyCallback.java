package com.akotnana.lendr.utils;

/**
 * Created by anees on 3/3/2018.
 */

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by anees on 11/11/2017.
 */

public interface VolleyCallback{
    void onSuccess(String result);
    void onError(VolleyError error);
}
