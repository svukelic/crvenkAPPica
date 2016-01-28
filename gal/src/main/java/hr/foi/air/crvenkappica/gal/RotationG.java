package hr.foi.air.crvenkappica.gal;

import android.content.Context;
import android.net.Uri;

/**
 * Created by Mario on 28.01.2016..
 */
public class RotationG {
    public static int getRotation(Context context, Uri imageUri, boolean isCamera) {
        int rotation;
        rotation = RotationGallery.getRotationFromGallery(context, imageUri);
        return rotation;
    }

}
