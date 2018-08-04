package id.smart.config;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Config {
    // Write a message to the database
    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static DatabaseReference refSlideshow = database.getReference("slideshow");
    public static DatabaseReference refKartuKeluarga = database.getReference("kartu_keluarga");
    public static DatabaseReference refAcara = database.getReference("acara");
    public static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
}
