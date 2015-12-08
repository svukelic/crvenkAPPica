package hr.foi.air.crvenkappica;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by domagoj on 08.12.15..
 */
public class ObavijestiOnClickListener implements RecyclerView.OnItemTouchListener {

    private OnItemClickListener itemClickListener;
    private GestureDetector gestureDetector;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public ObavijestiOnClickListener(Context context, OnItemClickListener listener){
        itemClickListener = listener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View view = rv.findChildViewUnder(e.getX(),e.getY());

        if(view != null && gestureDetector.onTouchEvent(e) && itemClickListener != null){
            itemClickListener.onItemClick(view,rv.getChildPosition(view));
            return true;
        }

        return false;
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }
}
