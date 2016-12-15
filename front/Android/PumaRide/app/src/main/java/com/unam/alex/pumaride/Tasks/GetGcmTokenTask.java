package com.unam.alex.pumaride.Tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.unam.alex.pumaride.utils.Statics;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Priyabrat on 21-07-2015.
 */
public class GetGcmTokenTask extends AsyncTask<Void,Void,String> {

    ProgressDialog progressDialog;
    SweetAlertDialog pDialog;
    Activity activity;
    Context context;
    String TAG = getClass().getName();
    private GoogleCloudMessaging gcm;

    public GetGcmTokenTask(Activity activity, Context context){
        this.context = context;
        this.activity = activity;
        //progressDialog = new ProgressDialog(activity);
        //progressDialog.setMessage("Registrando...");
        //pDialog = new SweetAlertDialog(activity, SweetAlertDialog.PROGRESS_TYPE);
        //pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        //pDialog.setTitleText("Registrando...");
        //pDialog.setCancelable(false);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //pDialog.show();
    }

    @Override
    protected String doInBackground(Void... voids) {
        String responseStr = null;
        if(gcm == null){
            gcm = GoogleCloudMessaging.getInstance(activity);
        }
        InstanceID instanceID = InstanceID.getInstance(activity);
        String tokedId = null;
        try {
            tokedId = instanceID.getToken(Statics.GCM_SENDER_ID, GoogleCloudMessaging.INSTANCE_ID_SCOPE,null);

            Log.d(TAG,"GCM token is "+tokedId);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "Error in fetching GCM token due to "+e);
        }
        if(tokedId != null)
        {
            SharedPreferences sp = context.getSharedPreferences("pumaride", Activity.MODE_PRIVATE);
            int id = sp.getInt("userid",1);
            String dataUrlParameters = "email="+Statics.getDeviceGmail(context)+"&"+"email name regId="+id+"&"+"&regId="+tokedId;
            URL url;
            HttpURLConnection connection = null;
            try {
                // Create connection
                url = new URL(Statics.URL_SERVER_REGISTRATION);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                connection.setRequestProperty("Content-Length","" + Integer.toString(dataUrlParameters.getBytes().length));
                connection.setRequestProperty("Content-Language", "en-US");
                connection.setUseCaches(false);
                connection.setDoInput(true);
                connection.setDoOutput(true);
                // Send request
                DataOutputStream wr = new DataOutputStream(
                        connection.getOutputStream());
                wr.writeBytes(dataUrlParameters);
                wr.flush();
                wr.close();
                // Get Response
                InputStream is = connection.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                String line;
                StringBuffer response = new StringBuffer();
                while ((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
                rd.close();
                responseStr = response.toString();
                Log.d("Server response",responseStr);

            } catch (Exception e) {

                e.printStackTrace();
                responseStr = "error";
                return responseStr;

            } finally {

                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
        return responseStr;
    }

    @Override
    protected void onPostExecute(String resp) {
        if(resp != null)
        {
            //pDialog.dismissWithAnimation();
            if(resp.equals("error")){
                Toast.makeText(activity,"Hubo un error con el servidor", Toast.LENGTH_LONG).show();
                SharedPreferences settings = context.getSharedPreferences(Statics.PREFERENCES, 0);
                SharedPreferences.Editor e = settings.edit();
                e.putBoolean(Statics.NOTIFICATIONS, true);
                e.commit();
            }else {
                //Toast.makeText(activity, "Registrado con éxito", Toast.LENGTH_LONG).show();
            }
            }
    }

}
