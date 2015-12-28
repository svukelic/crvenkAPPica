package hr.foi.air.crvenkappica;

import android.content.Context;
import android.media.Image;

import java.util.ArrayList;

/**
 * Created by domagoj on 05.12.15..
 */

/**
 * Callback interface za rad sa asynctask-om
 */
public interface OnTaskCompleted {
    void onTaskCompleted(ArrayList<String> result);
    void onTaskCompleted2(ArrayList<ImageItem> result);
}
