package id.smart.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import id.smart.R;
import id.smart.activity.AnnouncementActivity;
import id.smart.config.Config;
import id.smart.model.AcaraModel;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> implements DatePickerDialog.OnDateSetListener {
    private List<AcaraModel> moviesList;
    private Context context;
    Calendar now = Calendar.getInstance();

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        now.set(Calendar.YEAR,year);
        now.set(Calendar.MONTH,monthOfYear);
        now.set(Calendar.DATE,dayOfMonth);

        String date = ""+dayOfMonth;
        String month = ""+(monthOfYear++);

        if(dayOfMonth<10) {
            date = "0"+dayOfMonth;
        }
        if(monthOfYear<10) {
            month = "0"+monthOfYear;
        }

        etTanggal.setText(year+"-"+month+"-"+date);
    }

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
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final AcaraModel movie = moviesList.get(position);
        holder.tvEvent.setText(movie.getNama());

        if(Config.modelIntent.getRole().equals("warga")) {
            holder.btnEdit.setVisibility(View.GONE);
            holder.btnDelete.setVisibility(View.GONE);
        }

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(movie.getKey(), movie);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Config.mDatabase.child("acara").child(movie.getKey()).removeValue();
                moviesList.remove(position);
                notifyItemRemoved(position);
            }
        });
    }

    TextInputEditText etTanggal;
    private void showDialog(final String key, AcaraModel model) {
        final Dialog d = new Dialog(context);
        d.setContentView(R.layout.dialog_event);
        etTanggal = d.findViewById(R.id.etTanggal);
        final TextInputEditText etNama = d.findViewById(R.id.etEvent);
        Button btnSave = d.findViewById(R.id.btnSave);

        etTanggal.setText(model.getTanggal());
        etNama.setText(model.getNama());

        etTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        EventAdapter.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(((AnnouncementActivity)context).getFragmentManager(), "Datepickerdialog");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tanggal = etTanggal.getText().toString();
                String nama = etNama.getText().toString();

                AcaraModel model = new AcaraModel(key, nama, tanggal);
                Config.mDatabase.child("acara").child(key).setValue(model);
                Toast.makeText(context, "Data berhasil diperbaharui!", Toast.LENGTH_SHORT).show();
                d.dismiss();
            }
        });
        d.show();
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
