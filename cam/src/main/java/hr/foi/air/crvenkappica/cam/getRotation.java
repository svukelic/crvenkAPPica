package hr.foi.air.crvenkappica.cam;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import hr.foi.air.crvenkappica.core.OnImageReturn;

/**
 * Created by Mario on 28.01.2016..
 */
public class getRotation implements OnImageReturn {
    public static int getRotation(Context context, Uri imageUri, boolean isCamera) {
        int rotation;
        if (isCamera) {
            rotation = getRotationFromCamera.getRotationFromCamera(context, imageUri);
        } else {
            rotation = getRotationFromGallery.getRotationFromGallery(context, imageUri);
        }
       // Log.d(TAG, "Image rotation: " + rotation);
        return rotation;
    }
    @Override
    public String GetPath() {
        return null;
    }
}
