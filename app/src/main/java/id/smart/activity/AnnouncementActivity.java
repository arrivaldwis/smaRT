package id.smart.activity;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateChangedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import id.smart.R;
import id.smart.adapter.EventAdapter;
import id.smart.config.Config;
import id.smart.model.AcaraModel;
import id.smart.model.KartuKeluargaModel;

public class AnnouncementActivity extends AppCompatActivity implements OnDateChangedListener, OnMonthChangedListener,
        DatePickerDialog.OnDateSetListener {

    private MaterialCalendarView calendarView;
    private RecyclerView rvData;
    private FloatingActionButton btnAdd;
    private List<AcaraModel> acara = new ArrayList<>();
    private EventAdapter mAdapter;
    Calendar cal = Calendar.getInstance();
    Calendar now = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);
        setUI();
        loadEvents(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
    }

    private void setUI() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        this.setTitle("Announcement");
        calendarView = findViewById(R.id.calendarView);
        rvData = findViewById(R.id.rvData);
        btnAdd = findViewById(R.id.btnAdd);
        mAdapter = new EventAdapter(this, acara);
        LinearLayoutManager llManager = new LinearLayoutManager(this);
        rvData.setLayoutManager(llManager);
        rvData.setAdapter(mAdapter);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        calendarView.setCurrentDate(cal.getTime());
        calendarView.setSelectedDate(cal.getTime());
        calendarView.setOnDateChangedListener(this);
        calendarView.setOnMonthChangedListener(this);

        if(Config.modelIntent.getRole().equals("warga")) {
            btnAdd.setVisibility(View.GONE);
        }
    }

    TextInputEditText etTanggal;
    private void showDialog() {
        final Dialog d = new Dialog(this);
        d.setContentView(R.layout.dialog_event);
        etTanggal = d.findViewById(R.id.etTanggal);
        final TextInputEditText etNama = d.findViewById(R.id.etEvent);
        Button btnSave = d.findViewById(R.id.btnSave);
        etTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        AnnouncementActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tanggal = etTanggal.getText().toString();
                String nama = etNama.getText().toString();

                String key = Config.mDatabase.child("acara").push().getKey();
                AcaraModel model = new AcaraModel(key, nama, tanggal);
                Config.mDatabase.child("acara").child(key).setValue(model);
                Toast.makeText(AnnouncementActivity.this, "Data berhasil ditambahkan!", Toast.LENGTH_SHORT).show();
                d.dismiss();
            }
        });
        d.show();
    }

    public void loadEvents(final String tanggal) {
        acara.clear();

        Config.refAcara.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                acara.clear();
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    AcaraModel model = ds.getValue(AcaraModel.class);
                    if(tanggal.equals(model.getTanggal())) {
                        acara.add(model);
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onDateChanged(@NonNull MaterialCalendarView widget, @Nullable CalendarDay date) {
        if(date == null) {}
        else {
            String tanggal = new SimpleDateFormat("yyyy-MM-dd").format(date.getDate());
            loadEvents(tanggal);
        }
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

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
}
