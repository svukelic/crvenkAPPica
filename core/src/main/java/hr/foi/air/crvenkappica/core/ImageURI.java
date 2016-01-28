package hr.foi.air.crvenkappica.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;

/**
 * Created by Mario on 28.01.2016..
 */
public class ImageURI {
    public static Uri getImageUri( Context con, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(con.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}
