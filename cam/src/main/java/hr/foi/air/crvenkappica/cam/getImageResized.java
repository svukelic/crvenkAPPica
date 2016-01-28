package hr.foi.air.crvenkappica.cam;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import hr.foi.air.crvenkappica.core.OnImageReturn;

/**
 * Created by Mario on 28.01.2016..
 */
public class getImageResized implements OnImageReturn {
    private static int minWidthQuality = 400;
    public static Bitmap getImageResized(Context context, Uri selectedImage) {
        Bitmap bm = null;
        int[] sampleSizes = new int[]{5, 3, 2, 1};
        int i = 0;
        do {
            bm = decodeBitmap.decodeBitmap(context, selectedImage, sampleSizes[i]);
            //Log.d(TAG, "resizer: new bitmap width = " + bm.getWidth());
            i++;
        } while (bm.getWidth() < minWidthQuality && i < sampleSizes.length);
        return bm;
    }
    @Override
    public String GetPath() {
        return null;
    }
}
