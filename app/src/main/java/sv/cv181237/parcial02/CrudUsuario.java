package sv.cv181237.parcial02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class CrudUsuario extends AppCompatActivity {
    private EditText nombre, apellido, carnet, telefono, edad;
    private String _nombre, _apellido, _carnet, _telefono, _edad;

    DatabaseReference mDatabase;

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseUser user = auth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_usuario);

        nombre = findViewById(R.id.txtNombre);
        apellido = findViewById(R.id.txtApellido);
        carnet = findViewById(R.id.txtCarnet);
        telefono = findViewById(R.id.txtTelefono);
        edad = findViewById(R.id.txtEdad);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        if (user != null) {

            String idUser = user.getUid();
            cargarUsuario(idUser);
        }




        findViewById(R.id.btnGuardar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarUsuario();
            }
        });

        findViewById(R.id.btnRegresar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regresar();
            }
        });




    }

    private void guardarUsuario() {

        if (user != null) {
            String idUser = user.getUid();
            _nombre = nombre.getText().toString();
            _apellido = apellido.getText().toString();
            _carnet = carnet.getText().toString();
            _telefono = telefono.getText().toString();
            _edad = edad.getText().toString();

            Map<String, Object> map = new HashMap<>();
            map.put("nombre", _nombre);
            map.put("apellidos", _apellido);
            map.put("carnet", _carnet);
            map.put("telefono", _telefono);
            map.put("edad", _edad);

            mDatabase.child("usuario").child(idUser).setValue(map);

            Toast.makeText(CrudUsuario.this, "DATOS GUARDADOS: ",
                    Toast.LENGTH_SHORT).show();

            cargarUsuario(idUser);

        }
    }

    private void cargarUsuario(String idUser) {

        mDatabase.child("usuario").child(idUser).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {


                    Usuarios usuario = new Usuarios();

                    usuario.setNombre(String.valueOf(task.getResult().child("nombre").getValue()));
                    usuario.setApellidos(String.valueOf(task.getResult().child("apellidos").getValue()));
                    usuario.setCarnet(String.valueOf(task.getResult().child("carnet").getValue()));
                    usuario.setEdad(String.valueOf(task.getResult().child("edad").getValue()));
                    usuario.setTelefono(String.valueOf(task.getResult().child("telefono").getValue()));
                    nombre.setText(usuario.getNombre());
                    apellido.setText(usuario.getApellidos());
                    carnet.setText(usuario.getCarnet());
                    edad.setText(usuario.getEdad());
                    telefono.setText(usuario.getTelefono());

                    Toast.makeText(CrudUsuario.this, "DATOS CARGADOS: ",
                            Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void regresar(){
        startActivity(new Intent(this, ProfileActivity.class));
        finish();
    }
}