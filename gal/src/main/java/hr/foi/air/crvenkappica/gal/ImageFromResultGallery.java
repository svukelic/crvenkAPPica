package hr.foi.air.crvenkappica.gal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import java.io.File;

import hr.foi.air.crvenkappica.core.ImageResize;
import hr.foi.air.crvenkappica.core.ImageURI;
import hr.foi.air.crvenkappica.core.OnImageReturn;
import hr.foi.air.crvenkappica.core.Path;
import hr.foi.air.crvenkappica.core.TempFile;
import hr.foi.air.crvenkappica.core.rotate;

/**
 * Created by Mario on 28.01.2016..
 */
public class ImageFromResultGallery implements OnImageReturn {
    public static Bitmap getImageFromResult(Context context, int resultCode,
                                            Intent imageReturnedIntent) {
        Bitmap bm = null;
        File imageFile = TempFile.getTempFile(context);
        if (resultCode == Activity.RESULT_OK) {
            Uri selectedImage;
            boolean isCamera = (imageReturnedIntent == null ||
                    imageReturnedIntent.getData() == null  ||
                    imageReturnedIntent.getData().equals(Uri.fromFile(imageFile)));
            selectedImage = imageReturnedIntent.getData();
            bm = ImageResize.getImageResized(context, selectedImage);
            int rotation = RotationG.getRotation(context, selectedImage, isCamera);
            bm = rotate.rotate(bm, rotation);

        }
        return bm;
    }
    @Override
    public String GetPath(Bitmap b, Context c) {
        Uri image = ImageURI.getImageUri(c, b);
        String selectedImagePath = Path.getPath(c, image);
        return selectedImagePath;
    }
}
