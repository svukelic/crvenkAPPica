package hr.foi.air.crvenkappica.web;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WebRequest extends AsyncTask<WebParams, Void, TaskResult>{

    public AsyncResponse delegate = null;
    private Dialog progressdialog;

    @Override
    protected TaskResult doInBackground(WebParams... params) {
        TaskResult taskResult = new TaskResult();
        taskResult.webResult = "";

        String url = WebSite.WebAdress.getAdresa();
        url += params[0].service;
        url += params[0].params;

        delegate = params[0].listener;

        byte[] postData = url.getBytes();

        HttpURLConnection c = null;

        try {
            c = (HttpURLConnection)(new URL(url)).openConnection();
            c.setDoOutput(true);
            c.setDoInput(true);
            c.setUseCaches(false);
            c.setRequestMethod("GET");
            c.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            c.setRequestProperty("charset", "utf-8");
            c.setRequestProperty("Content-Length", ""+postData.length);

            //sending post parameters
            DataOutputStream dw = new DataOutputStream(c.getOutputStream());
            dw.write(postData);

            //reading web service response
            InputStream is = new BufferedInputStream(c.getInputStream());
            BufferedReader dr = new BufferedReader(new InputStreamReader(is));
            String line;
            while((line = dr.readLine()) != null)
                taskResult.webResult += line;

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(c != null)
                c.disconnect();
        }

        return taskResult;
    }

    @Override
    protected void onPostExecute(TaskResult taskResult) {
        super.onPostExecute(taskResult);
        delegate.processFinish(taskResult.webResult);
    }
}
