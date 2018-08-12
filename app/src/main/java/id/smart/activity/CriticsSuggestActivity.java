package id.smart.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import id.smart.R;
import id.smart.adapter.ChatAdapter;
import id.smart.adapter.EventAdapter;
import id.smart.config.Config;
import id.smart.model.CriticsModel;

public class CriticsSuggestActivity extends AppCompatActivity implements Validator.ValidationListener {

    private RecyclerView rvData;
    @NotEmpty
    private EditText etMsg;
    private ChatAdapter mAdapter;
    private ArrayList<CriticsModel> chatList = new ArrayList<>();
    private Validator validator;
    private ImageView btnSend;
    private Calendar cal = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_critics_suggest);
        setUI();
        loadChat();
    }

    private void setUI() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("Critics and Suggestion");
        validator = new Validator(this);
        validator.setValidationListener(this);
        rvData = findViewById(R.id.chat_message);
        etMsg = findViewById(R.id.chat_input);
        btnSend = findViewById(R.id.btnSend);
        mAdapter = new ChatAdapter(this, chatList);
        LinearLayoutManager llManager = new LinearLayoutManager(this);
        rvData.setLayoutManager(llManager);
        rvData.setAdapter(mAdapter);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendChat();
            }
        });
    }

    private void loadChat() {

        Config.refCritics.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatList.clear();
                for (DataSnapshot ds:dataSnapshot.getChildren()) {
                    CriticsModel model = ds.getValue(CriticsModel.class);
                    chatList.add(model);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendChat() {
        validator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        String key = Config.mDatabase.child("critics").push().getKey();
        String nama = Config.modelIntent.getNama();
        String email = Config.modelIntent.getEmail();
        String pesan = etMsg.getText().toString();
        String tanggal = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        CriticsModel model = new CriticsModel(key, nama, email, pesan, tanggal);
        Config.mDatabase.child("critics").push().setValue(model);
        etMsg.setText("");
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
}
