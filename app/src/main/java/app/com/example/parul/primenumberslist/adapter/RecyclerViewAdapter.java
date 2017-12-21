package app.com.example.parul.primenumberslist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import app.com.example.parul.primenumberslist.R;

/**
 * Created by Parul on 12/19/2017.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private List<Integer> mData;

    // data is passed into the constructor
    public RecyclerViewAdapter(Context context, List<Integer> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    /**
     *
     * @param parent
     * @param viewType
     * @return viewHolder
     */
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    /**
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {

        int number=mData.get(position);
        holder.myTextView.setText(String.valueOf(number));

    }

    /**
     *
     * @return int
     */
    @Override
    public int getItemCount() {
        return (mData==null) ? 0 : mData.size();
    }

    /**
     * stores and recycles views as they are scrolled off screen
     *
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView myTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            myTextView = (TextView) itemView.findViewById(R.id.tvNumber);
        }
    }
}
