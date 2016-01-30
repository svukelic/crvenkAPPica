package hr.foi.air.crvenkappica.core;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

/**
 * Created by domagoj on 28.01.16..
 */
public interface OnImageReturn {

    public String GetPath(Context context, int resultCode,
                          Intent imageReturnedIntent);
}
