package hr.foi.air.crvenkappica.cam;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import hr.foi.air.crvenkappica.core.OnImageReturn;

/**
 * Created by Mario on 28.01.2016..
 */
public class getRotationFromGallery implements OnImageReturn {
    public static int getRotationFromGallery(Context context, Uri imageUri) {
        String[] columns = {MediaStore.Images.Media.ORIENTATION};
        Cursor cursor = context.getContentResolver().query(imageUri, columns, null, null, null);
        if (cursor == null) return 0;

        cursor.moveToFirst();

        int orientationColumnIndex = cursor.getColumnIndex(columns[0]);
        return cursor.getInt(orientationColumnIndex);
    }

    @Override
    public String GetPath() {
        return null;
    }
}
