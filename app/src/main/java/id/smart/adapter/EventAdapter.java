package id.smart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.smart.R;
import id.smart.model.AcaraModel;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {
    private List<AcaraModel> moviesList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvEvent;
        public ImageView btnEdit, btnDelete;

        public MyViewHolder(View view) {
            super(view);
            tvEvent = (TextView) view.findViewById(R.id.tvEvent);
            btnEdit = (ImageView) view.findViewById(R.id.btnEdit);
            btnDelete = (ImageView) view.findViewById(R.id.btnDelete);
        }
    }


    public EventAdapter(Context context, List<AcaraModel> moviesList) {
        this.moviesList = moviesList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_announcement, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final AcaraModel movie = moviesList.get(position);
        holder.tvEvent.setText(movie.getNama());
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, movie.getTanggal(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
