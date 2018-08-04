package id.smart.activity;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import id.smart.R;
import id.smart.config.Config;
import id.smart.model.KartuKeluargaModel;
import id.smart.model.SlideshowModel;

public class FindActivity extends AppCompatActivity {

    private TextInputEditText etNIK;
    private TextInputEditText etNoKK;
    private TextInputEditText etNama;
    private TextInputEditText etNoPanggilan;
    private Button btnSearch;

    private TextView tvNIK;
    private TextView tvNoKK;
    private TextView tvNama;
    private TextView tvNamaPanggilan;
    private TextView tvTTL;
    private TextView tvJK;
    private TextView tvAlamat;
    private TextView tvAgama;
    private TextView tvStatus;
    private TextView tvPekerjaan;
    private TextView tvKeterkaitan;

    private RelativeLayout rlDelete;
    private String menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        setUI();
        loadExtras();
    }

    private void loadExtras() {
        menu = getIntent().getStringExtra("menu");
        if(menu.equals("tambah")) {
            rlDelete.setVisibility(View.GONE);
        }
    }

    private void setUI() {
        this.setTitle("Find");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tvNIK = findViewById(R.id.tvNIK);
        tvNama = findViewById(R.id.tvNama);
        tvNamaPanggilan = findViewById(R.id.tvNamaPanggilan);
        tvTTL = findViewById(R.id.tvTTL);
        tvJK = findViewById(R.id.tvJK);
        tvAlamat = findViewById(R.id.tvAlamat);
        tvAgama = findViewById(R.id.tvAgama);
        tvStatus = findViewById(R.id.tvStatus);
        tvPekerjaan = findViewById(R.id.tvPekerjaan);
        tvKeterkaitan = findViewById(R.id.tvKeterkaitan);
        tvNoKK = findViewById(R.id.tvKK);
        etNama = findViewById(R.id.etNama);
        etNoKK = findViewById(R.id.etKK);
        etNIK = findViewById(R.id.etNIK);
        etNoPanggilan = findViewById(R.id.etNamaPanggilan);
        btnSearch = findViewById(R.id.btnSearch);
        rlDelete = findViewById(R.id.rlDelete);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchData();
            }
        });
    }

    private void searchData() {
        Config.refKartuKeluarga.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    KartuKeluargaModel model = ds.getValue(KartuKeluargaModel.class);

                    if (!etNIK.getText().toString().isEmpty()) {
                        if (model.getNik().equals(etNIK.getText().toString())) {
                            resultData(model);
                            break;
                        }
                    }

                    if (!etNoKK.getText().toString().isEmpty()) {
                        if (model.getNo_kk().equals(etNoKK.getText().toString())) {
                            resultData(model);
                            break;
                        }
                    }

                    if (model.getNama().contains(etNama.getText().toString()) ||
                            model.getNama_panggilan().contains(etNoPanggilan.getText().toString())) {
                        resultData(model);
                        break;
                    }

                    Toast.makeText(FindActivity.this, "Data tidak ditemukan!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }

    private void resultData(KartuKeluargaModel model) {
        tvNIK.setText(model.getNik());
        tvNoKK.setText(model.getNo_kk());
        tvNama.setText(model.getNama());
        tvNamaPanggilan.setText(model.getNama_panggilan());
        tvTTL.setText(model.getTtl());
        tvJK.setText(model.getJenis_kelamin());
        tvAlamat.setText(model.getAlamat());
        tvAgama.setText(model.getAgama());
        tvStatus.setText(model.getStatus());
        tvPekerjaan.setText(model.getPekerjaan());
        tvKeterkaitan.setText(model.getKeterkaitan());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
