package id.smart.activity;

import android.app.Dialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import id.smart.R;
import id.smart.adapter.EventAdapter;
import id.smart.config.Config;
import id.smart.model.AcaraModel;
import id.smart.model.KartuKeluargaModel;

public class AnnouncementActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private RecyclerView rvData;
    private FloatingActionButton btnAdd;
    private List<EventDay> events = new ArrayList<>();
    private List<AcaraModel> acara = new ArrayList<>();
    private EventAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);
        setUI();
        loadEvents();
    }

    private void setUI() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        calendarView = findViewById(R.id.calendarView);
        rvData = findViewById(R.id.rvData);
        btnAdd = findViewById(R.id.btnAdd);
        mAdapter = new EventAdapter(this, acara);
        LinearLayoutManager llManager = new LinearLayoutManager(this);
        rvData.setLayoutManager(llManager);
        rvData.setAdapter(mAdapter);
        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {

            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }

    private void showDialog() {
        final Dialog d = new Dialog(this);
        d.setContentView(R.layout.dialog_event);
        final TextInputEditText etTanggal = d.findViewById(R.id.etTanggal);
        final TextInputEditText etNama = d.findViewById(R.id.etEvent);
        Button btnSave = d.findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tanggal = etTanggal.getText().toString();
                String nama = etNama.getText().toString();

                AcaraModel model = new AcaraModel(nama, tanggal);
                Config.mDatabase.child("acara").push().setValue(model);
                Toast.makeText(AnnouncementActivity.this, "Data berhasil ditambahkan!", Toast.LENGTH_SHORT).show();
                d.dismiss();
            }
        });
        d.show();
    }

    private void loadEvents() {
        events.clear();
        acara.clear();

        Config.refAcara.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    AcaraModel model = ds.getValue(AcaraModel.class);

                    Integer tahun = Integer.parseInt(model.getTanggal().split("-")[0]);
                    Integer bulan = Integer.parseInt(model.getTanggal().split("-")[1]);
                    Integer tanggal = Integer.parseInt(model.getTanggal().split("-")[2]);

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(tahun, bulan, tanggal);
                    events.add(new EventDay(calendar, R.drawable.ic_menu_share));
                    acara.add(model);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setEvents(events);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
