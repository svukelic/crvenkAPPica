package hr.foi.air.crvenkappica.cam;

import android.content.Context;
import android.net.Uri;

/**
 * Created by Mario on 28.01.2016..
 */
public class RotationC {
    public static int getRotation(Context context, Uri imageUri, boolean isCamera) {
        int rotation;
        rotation = RotationFromCamera.getRotationFromCamera(context, imageUri);
        return rotation;
    }

}
