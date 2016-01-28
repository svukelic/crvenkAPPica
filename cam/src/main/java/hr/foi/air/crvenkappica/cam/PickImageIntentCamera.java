package hr.foi.air.crvenkappica.cam;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

import hr.foi.air.crvenkappica.core.IntentsToList;
import hr.foi.air.crvenkappica.core.TempFile;


/**
 * Created by Mario on 28.01.2016..
 */
public  class PickImageIntentCamera {
    public static Intent getPickImageIntent(Context context) {
        Intent chooserIntent = null;
        List<Intent> intentList = new ArrayList<>();
        //Intent pickIntent = new Intent(Intent.ACTION_PICK,
     //           MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePhotoIntent.putExtra("return-data", true);
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(TempFile.getTempFile(context)));
       // intentList = IntentsToList.IntentsToList(context,intentList,pickIntent);
        intentList = IntentsToList.addIntentsToList(context, intentList, takePhotoIntent);
        if (intentList.size() > 0) {
            chooserIntent = Intent.createChooser(intentList.remove(intentList.size() - 1),
                    context.getString(android.R.string.untitled));
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentList.toArray(new Parcelable[]{}));
        }
        return chooserIntent;
    }

}
