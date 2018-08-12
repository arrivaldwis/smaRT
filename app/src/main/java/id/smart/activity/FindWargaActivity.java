package id.smart.activity;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import id.smart.R;
import id.smart.config.Config;
import id.smart.model.KartuKeluargaModel;

public class FindWargaActivity extends AppCompatActivity {

    private TextInputEditText etNama;
    private TextInputEditText etNoPanggilan;
    private Button btnSearch;

    private TextView tvNama;
    private TextView tvNamaPanggilan;
    private TextView tvJK;
    private TextView tvAlamat;

    private RelativeLayout rlDelete;
    private String menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_warga);
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

        tvNama = findViewById(R.id.tvNama);
        tvNamaPanggilan = findViewById(R.id.tvNamaPanggilan);
        tvJK = findViewById(R.id.tvJK);
        tvAlamat = findViewById(R.id.tvAlamat);
        etNama = findViewById(R.id.etNama);
        etNoPanggilan = findViewById(R.id.etNamaPanggilan);
        btnSearch = findViewById(R.id.btnSearch);
        rlDelete = findViewById(R.id.rlDelete);
        rlDelete.setVisibility(View.GONE);

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

                    if (model.getNama().contains(etNama.getText().toString()) ||
                            model.getNama_panggilan().contains(etNoPanggilan.getText().toString())) {
                        resultData(model);
                        checkRole();
                        break;
                    }

                    Toast.makeText(FindWargaActivity.this, "Data tidak ditemukan!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }

    private void checkRole() {
        if(Config.modelIntent.getRole().equals("ketua")) {
            rlDelete.setVisibility(View.VISIBLE);
        }
    }

    private void resultData(KartuKeluargaModel model) {
        tvNama.setText(model.getNama());
        tvNamaPanggilan.setText(model.getNama_panggilan());
        tvJK.setText(model.getJenis_kelamin());
        tvAlamat.setText(model.getAlamat());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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
}
