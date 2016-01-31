package hr.foi.air.crvenkappica.images;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import hr.foi.air.crvenkappica.R;
import hr.foi.air.crvenkappica.images.ImageItem;

/**
 * Created by Mario on 20/12/2015.
 */
//Klasa za adapter, koji prima podatke (u nasem slucaju slike), te ih postavlja na gridview
public class GridViewAdapter extends ArrayAdapter {
    private Context context;
    private int layoutResourceId;
    private ArrayList data = new ArrayList();

    //Konstruktor prima context, layoutResId, te data - slike
    public GridViewAdapter(Context context, int layoutResourceId, ArrayList data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    //Metoda vraća view (postavljene slike na zeljena mjesta u gridview-u)
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) row.findViewById(R.id.image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        ImageItem item = (ImageItem) data.get(position);
        holder.image.setImageBitmap(item.getImage());
        return row;
    }
    static class ViewHolder {
        ImageView image;
        TextView imageTitle;
    }
}
