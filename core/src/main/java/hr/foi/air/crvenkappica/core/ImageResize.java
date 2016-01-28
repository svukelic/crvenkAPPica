package hr.foi.air.crvenkappica.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by Mario on 28.01.2016..
 */
public class ImageResize {
    private static int minWidthQuality = 400;
    public static Bitmap getImageResized(Context context, Uri selectedImage) {
        Bitmap bm = null;
        int[] sampleSizes = new int[]{5, 3, 2, 1};
        int i = 0;
        do {
            bm = BitmapDecode.decodeBitmap(context, selectedImage, sampleSizes[i]);
            //Log.d(TAG, "resizer: new bitmap width = " + bm.getWidth());
            i++;
        } while (bm.getWidth() < minWidthQuality && i < sampleSizes.length);
        return bm;
    }

}
