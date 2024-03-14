package com.todo.app.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.todo.app.ApplicationClass;


public class SessionManager {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;
    private int PRIVATE_MODE = 0;
    private static SessionManager sessionManagerInstance = new SessionManager();

    private static final String PREF_NAME = "AvdeshTodo";

    private SessionManager() {
        this.context = ApplicationClass.mcontext;
        pref = this.context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }



    public static SessionManager getInstance(){
        if(sessionManagerInstance!=null){
            return sessionManagerInstance;
        }else {
            sessionManagerInstance = new SessionManager();
            return sessionManagerInstance;
        }
    }



    //----------------------------------- Store Signup data-------------------------------------------


    public void setSignUpData(String data) {
        editor.putString("SignUpData", data);
        editor.commit();
    }


    public String getSignUpData() {
        return pref.getString("SignUpData", "");
    }



    //----------------------------------- Store sessionUser data-------------------------------------------


    public void setUserData(String data) {
        editor.putString("sessionUser", data);
        editor.commit();
    }


    public String getUserData() {
        return pref.getString("sessionUser", "");
    }


    //----------------------------------- Store firebase msg token -------------------------------------------


    public void setFirebaseMsgToken(String token) {
        editor.putString("FirebaseMsgToken", token);
        editor.commit();
    }


    public String getFirebaseMsgToken() {
        return pref.getString("FirebaseMsgToken", "");
    }





    //----------------------------------- Store token -------------------------------------------


    public void setToken(String token) {
        editor.putString("Token", token);
        editor.commit();
    }


    public String getToken() {
        return pref.getString("Token", "");
    }



    //----------------------------------- Store firebase token -------------------------------------------


    public void setFirebaseToken(String token) {
        editor.putString("FirebaseToken", token);
        editor.commit();
    }


    public String getFirebaseToken() {
        return pref.getString("FirebaseToken", "");
    }





    //----------------------------------- Store latitude data-------------------------------------------


    public void setLatitude(String data) {
        editor.putString("latitude", data);
        editor.commit();
    }


    public String getLatitude() {
        return pref.getString("latitude", "");
    }

    //----------------------------------- Store tasks data-------------------------------------------


    public void setTasks(String data) {
        editor.putString("tasks", data);
        editor.commit();
    }


    public String getTasks() {
        return pref.getString("tasks", "");
    }


    //----------------------------------- Store longitude data-------------------------------------------


    public void setLongitude(String data) {
        editor.putString("longitude", data);
        editor.commit();
    }


    public String getLongitude() {
        return pref.getString("longitude", "");
    }






}
