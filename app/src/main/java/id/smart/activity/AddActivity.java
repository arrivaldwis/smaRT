package id.smart.activity;

import android.app.ProgressDialog;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.List;

import id.smart.R;
import id.smart.config.Config;
import id.smart.model.KartuKeluargaModel;

public class AddActivity extends AppCompatActivity implements Validator.ValidationListener,
        DatePickerDialog.OnDateSetListener {

    @NotEmpty
    private TextInputEditText etNIK;
    @NotEmpty
    private TextInputEditText etNoKK;
    @NotEmpty
    private TextInputEditText etNama;
    @NotEmpty
    private TextInputEditText etNamaPanggilan;
    @NotEmpty
    private TextInputEditText etTTL;
    @NotEmpty
    private TextInputEditText etJK;
    @NotEmpty
    private TextInputEditText etAlamat;
    @NotEmpty
    private TextInputEditText etAgama;
    @NotEmpty
    private TextInputEditText etStatus;
    @NotEmpty
    private TextInputEditText etPekerjaan;
    @NotEmpty
    private TextInputEditText etKeterkaitan;

    private ImageView imgDelete;
    private ImageView imgSave;
    private Validator validator;
    private ProgressDialog pDialog;
    private String menu;
    Calendar now = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        setUI();
        loadExtras();
    }

    private void loadExtras() {
        menu = getIntent().getStringExtra("menu");
        if(menu.equals("tambah")) {
            imgDelete.setVisibility(View.GONE);
        }
    }

    private void setUI() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        etNama = findViewById(R.id.etNama);
        etNoKK = findViewById(R.id.etKK);
        etNIK = findViewById(R.id.etNIK);
        etNamaPanggilan = findViewById(R.id.etNamaPanggilan);
        etTTL = findViewById(R.id.etTTL);
        etJK = findViewById(R.id.etJK);
        etAlamat = findViewById(R.id.etAlamat);
        etAgama = findViewById(R.id.etAgama);
        etStatus = findViewById(R.id.etStatus);
        etPekerjaan = findViewById(R.id.etPekerjaan);
        etKeterkaitan = findViewById(R.id.etKeterkaitan);
        imgDelete = findViewById(R.id.imgDelete);
        imgSave = findViewById(R.id.imgSave);
        validator = new Validator(this);
        validator.setValidationListener(this);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait..");

        imgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.validate();
            }
        });
    }

    public void setTanggal(View v) {
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onValidationSucceeded() {
        //Sukses
        pDialog.show();
        String nik = etNIK.getText().toString();
        String nokk = etNoKK.getText().toString();
        String nama = etNama.getText().toString();
        String panggilan = etNamaPanggilan.getText().toString();
        String ttl = etTTL.getText().toString();
        String jk = etJK.getText().toString();
        String alamat = etAlamat.getText().toString();
        String agama = etAgama.getText().toString();
        String status = etStatus.getText().toString();
        String pekerjaan = etPekerjaan.getText().toString();
        String keterkaitan = etKeterkaitan.getText().toString();

        KartuKeluargaModel model = new KartuKeluargaModel(nik, nokk, nama, panggilan, ttl, jk,
                alamat, agama, status, pekerjaan, keterkaitan);
        Config.mDatabase.child("kartu_keluarga").push().setValue(model);
        Toast.makeText(this, "Data berhasil ditambahkan!", Toast.LENGTH_SHORT).show();
        pDialog.dismiss();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
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
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        now.set(Calendar.YEAR,year);
        now.set(Calendar.MONTH,monthOfYear);
        now.set(Calendar.DATE,dayOfMonth);

        String date = ""+dayOfMonth;
        String month = ""+monthOfYear;

        if(dayOfMonth<10) {
            date = "0"+dayOfMonth;
        }
        if(monthOfYear<10) {
            month = "0"+monthOfYear;
        }

        etTTL.setText(year+"-"+month+"-"+date);
    }
}
