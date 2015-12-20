package hr.foi.air.crvenkappica;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import hr.foi.air.crvenkappica.web.AsyncResponse;
import hr.foi.air.crvenkappica.web.WebParams;
import hr.foi.air.crvenkappica.web.WebRequest;

/**
 * Created by Mario on 20/12/2015.
 */
public class AlbumFragment extends Fragment {
    private static final int PICK_IMAGE_ID = 234;
    private Button b;
    private GridView gridView;
    private GridViewAdapter gridAdapter;
    private String selectedImagePath;
    String upLoadServerUri = "http://www.redtesseract.sexy/crvenkappica/upload_images.php";
    int serverResponseCode = 0;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_album,container,false);
        b = (Button) view.findViewById(R.id.odabir);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPickImage(view);
            }
        });
        gridView = (GridView) view.findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(getActivity().getApplicationContext(), R.layout.grid_item_layout, getData());
        gridView.setAdapter(gridAdapter);
        return view;
    }
    public void onPickImage(View view){
        Intent chooseImageIntent = ImagePicker.getPickImageIntent(getActivity());
        startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
    }
    AsyncResponse response = new AsyncResponse() {
        @Override
        public void processFinish(String output) {
            if (output.equals("uspjeh")) {
                //dialog.hide();
                Toast.makeText(getActivity().getApplicationContext(), "Image upload done.", Toast.LENGTH_LONG).show();
                //getActivity().finish();
            }
            if (output.equals("greska prilikom upisa")) {
               // dialog.hide();
                Toast.makeText(getActivity().getApplicationContext(), "Error during image upload.", Toast.LENGTH_LONG).show();
            }
            if(output == null || output.isEmpty()) Toast.makeText(getActivity().getApplicationContext(), "Problem with internet connection", Toast.LENGTH_LONG).show();
        }
    };
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        switch(requestCode){
            case PICK_IMAGE_ID:
               // Bitmap bitmab = ImagePicker.getImageFromResult(getActivity(),resultCode,data);
                //String id = LoginStatus.LoginInfo.getLoginID();
                Toast.makeText(getActivity(), "Image chosen.", Toast.LENGTH_LONG).show();
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                File file = new File(selectedImagePath);
                String nesto = file.getName();
                ImageItem i = new ImageItem();
                i.setId("6");
                i.setTitle(nesto);

                System.out.println(i.getId() +  i.getTitle());
                JSONParser j = new JSONParser(i);
                System.out.println(selectedImagePath);
                System.out.println(nesto);
                System.out.println(j.getString());
                new Thread(new Runnable() {
                    public void run() {
                        uploadFile(selectedImagePath);
                    }
                }).start();
                WebParams webParamsReg = new WebParams();
                webParamsReg.service = "image_db.php";
                webParamsReg.params = j.getString();
                webParamsReg.listener = response;
                new WebRequest().execute(webParamsReg);
                break;
            default:
                super.onActivityResult(requestCode,resultCode,data);
        }
    }
    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        //TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
        //for (int i = 0; i < imgs.length(); i++) {
        //    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
       //     imageItems.add(new ImageItem(bitmap, "Image#" + i));
      //  }
        return imageItems;
    }
    public String getPath(Uri uri) {
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().getContentResolver().query(uri, proj, null, null, null);
        if(cursor.moveToFirst()){;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }
    public int uploadFile(String sourceFileUri) {
        String fileName = sourceFileUri;
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        if (!sourceFile.isFile()) {
            //pg2.hide();
            Toast.makeText(getActivity(), "Error, choose picture again.", Toast.LENGTH_LONG).show();
            Log.e("uploadFile", "Source File not exist :" + sourceFile);
            return 0;
        }
        else
        {
            try {
                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(upLoadServerUri);
                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);
                dos = new DataOutputStream(conn.getOutputStream());
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename="+ fileName + "" + lineEnd);
                dos.writeBytes(lineEnd);
                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];
                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                while (bytesRead > 0) {
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }
                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();
                Log.i("uploadFile", "HTTP Response is : "+ serverResponseMessage + ": " + serverResponseCode);

                if(serverResponseCode == 200){
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            //    pg2.hide();
                            Toast.makeText(getActivity(), "Image upload complete.", Toast.LENGTH_LONG).show();
                        }
                    });
                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {

                //dialog.dismiss();
                ex.printStackTrace();

                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        //  pg2.hide();
                        Toast.makeText(getActivity(), "Error.", Toast.LENGTH_LONG).show();
                    }
                });

                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {

                //dialog.dismiss();
                e.printStackTrace();

                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        //   pg2.hide();
                        Toast.makeText(getActivity(), "Error, check logcat.", Toast.LENGTH_LONG).show();
                        // Toast.makeText(New_annonce_act_step3.this, "Got Exception : see logcat ",
                        //       Toast.LENGTH_SHORT).show();
                    }
                });
                Log.e("Upload file to server Exception", "Exception : "
                        + e.getMessage(), e);
            }
            // dialog.dismiss();
            return serverResponseCode;

        } // End else block
    }

}
