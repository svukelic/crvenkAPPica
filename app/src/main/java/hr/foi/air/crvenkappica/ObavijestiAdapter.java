package hr.foi.air.crvenkappica;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by domagoj on 06.12.15..
 */
public class ObavijestiAdapter extends RecyclerView.Adapter<ObavijestiAdapter.ViewHolder> {

    private ArrayList<Obavijesti_item> items;
    private Context context;

    public ObavijestiAdapter(ArrayList<Obavijesti_item> items, Context context) {
        Log.d("CONSTRUCTOR","---------->in");
        this.items = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.obavijesti_items, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.e("ONBIND","UNUTRA SAM");
        Glide.with(context)
                .load(items.get(position).getThumbnail())
                .into(holder.imageView);
        holder.textView.setText(items.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        Log.d("ITEMCOUNT",items.size() + "");
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            Log.d("VIEWHOLDER","UNUTRA SAM");
            imageView = (ImageView) imageView.findViewById(R.id.image_hls);
            textView = (TextView) textView.findViewById(R.id.textView_hls);
        }

    }
}
