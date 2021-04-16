package sv.cv181237.parcial02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

//import java.util.logging.LogManager;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseUser user = auth.getCurrentUser();
    private TextView  email;

    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        email = findViewById(R.id.email);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        if (user != null) {


            email.setText("Bienvenido de nuevo "+user.getEmail());
        }
        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                LoginManager.getInstance().logOut();
                openLogin();
            }
        });

        findViewById(R.id.btnCuenta).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCuenta();
            }
        });

    }

    private void openCuenta() {
        startActivity(new Intent(this, CrudUsuario.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (user == null) {

            openLogin();
        }
    }

    private void openLogin() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}