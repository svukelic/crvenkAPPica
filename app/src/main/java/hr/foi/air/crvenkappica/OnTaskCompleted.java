package hr.foi.air.crvenkappica;

import java.util.ArrayList;

import hr.foi.air.crvenkappica.images.ImageItem;

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
