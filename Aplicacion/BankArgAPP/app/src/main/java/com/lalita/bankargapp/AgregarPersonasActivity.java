package com.lalita.bankargapp;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import com.lalita.bankargapp.Clases.Contactos;
import com.lalita.bankargapp.Clases.ContactosAdapter;

import java.util.ArrayList;
import java.util.List;

public class AgregarPersonasActivity extends AppCompatActivity {

    private EditText editTextCBU;
    //private EditText editTextAlias;
    private EditText nombrePers;
    private Button buttonAddPerson;

    UsuariosSQLiteHelper miBase;


    RecyclerView recyclerViewContactos;
    ContactosAdapter contactoAdapter;
    List<Contactos> contactoList;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);

        // Inicializar la base de datos
        miBase = new UsuariosSQLiteHelper(this);
        recyclerViewContactos = findViewById(R.id.recyclerViewContactos);


        // Recuperar el id_usuario de SharedPreferences
        SharedPreferences preferences = getSharedPreferences("user_session", MODE_PRIVATE);
        int idUsuario = preferences.getInt("id_usuario", -1); // -1 es el valor por defecto si no se encuentra

        if (idUsuario != -1) {
            // el id_usuario se encontro
        } else {
            // No se encontró un id_usuario
            Toast.makeText(this, "No se encontró un usuario logueado", Toast.LENGTH_SHORT).show();
            // Puedes redirigir al usuario a la pantalla de inicio de sesión o manejarlo de otra forma
        }



        editTextCBU = findViewById(R.id.editTextCBU);
        nombrePers = findViewById(R.id.nombrePers);
        buttonAddPerson = findViewById(R.id.buttonAddPerson);


        // Configurar el RecyclerView
        recyclerViewContactos.setLayoutManager(new LinearLayoutManager(this));
        contactoList = getAllContactos();

        contactoAdapter = new ContactosAdapter(contactoList, this::eliminarContacto);
        recyclerViewContactos.setAdapter(contactoAdapter);




        buttonAddPerson.setOnClickListener(v -> addPerson());


        /*--- Boton en el tool bar que lleva al perfil---*/

        View btnPerfil = findViewById(R.id.account_cir);
        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AgregarPersonasActivity.this, PerfilActivity.class);
                startActivity(intent);
            }
        });

        /*--- lleva al home ---*/
        View btnHome = findViewById(R.id.rectangle_2);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AgregarPersonasActivity.this, ProductActivity.class);
                startActivity(intent);
            }
        });

        /*---------------------Hooks------------------------*/
        drawerLayout=findViewById(R.id.drawer_layout);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                AgregarPersonasActivity activity = AgregarPersonasActivity.this;
                if (itemId == R.id.nav_product) {
                    Intent intent = new Intent(activity, ProductActivity.class);
                    Log.i("MENU_DRAWER_TAG", "Home is selected");
                    startActivities(new Intent[]{intent});
                    drawerLayout.closeDrawer(GravityCompat.START);

                } else if (itemId == R.id.nav_banking) {
                    Intent intent = new Intent(activity, BankingActivity.class);
                    Log.i("MENU_DRAWER_TAG", "Banking is selected");
                    startActivities(new Intent[]{intent});
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else if (itemId == R.id.nav_contact) {
                    Intent intent = new Intent(activity, ContactActivity.class);
                    Log.i("MENU_DRAWER_TAG", "Contact is selected");
                    startActivities(new Intent[]{intent});
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (itemId == R.id.nav_support) {
                    Intent intent = new Intent(activity, SupportActivity.class);
                    Log.i("MENU_DRAWER_TAG", "Support is selected");
                    startActivities(new Intent[]{intent});
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (itemId == R.id.nav_logout) {
                    Toast.makeText(activity, "Singing Out", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(activity, MainActivity.class);
                    Log.i("MENU_DRAWER_TAG", "Logout is selected");
                    startActivities(new Intent[]{intent});
                    drawerLayout.closeDrawer(GravityCompat.START);
                }

                return true;
            }
        });
    }


    // Aca arranca la funcion Para Agregar un contacto


    //reviso si existe un cbu con el mismo numero
    private boolean existeCBU(String contacto) {
        SQLiteDatabase db = miBase.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM Contactos WHERE contacto = ?";
        Cursor cursor = db.rawQuery(query, new String[]{contacto});

        if (cursor != null) {
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            cursor.close();
            return count > 0;
        }
        return false;
    }


    private void addPerson() {
        // Recuperar el id_usuario desde SharedPreferences
        SharedPreferences preferences = getSharedPreferences("user_session", MODE_PRIVATE);
        int idUsuario = preferences.getInt("id_usuario", -1);

        if (idUsuario == -1) {
            // Manejar el caso donde no se encontró un id_usuario
            Toast.makeText(this, "No se encontró un usuario logueado", Toast.LENGTH_SHORT).show();
            return;
        }

        String contactoA = editTextCBU.getText().toString().trim();
        String nombre = nombrePers.getText().toString().trim();

        if (TextUtils.isEmpty(contactoA)) {
            editTextCBU.setError("CBU es requerido");
            return;
        }
        if (TextUtils.isEmpty(nombre)) {
            nombrePers.setError("Nombre es requerido");
            return;
        }

        // Verificar si ya existe un contacto con el mismo CBU
        if (existeCBU(contactoA)) {
            Toast.makeText(this, "Ya existe un contacto con este CBU/CVU", Toast.LENGTH_SHORT).show();
            return;
        }

        if (validarCampos(contactoA, nombre)) {

            // Pasar id_usuario junto con contacto y nombre
            agregarContacto(idUsuario, contactoA, nombre);
        }
    }




    //Verifico los campos

    private boolean validarCampos(String contacto, String nombre) {
        // Validar que el nombre solo contenga letras
        if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
            Toast.makeText(this, "El nombre solo debe contener letras", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Valido que el contacto (CBU) contenga exactamente 22 números
        if (!contacto.matches("\\d{22}")) {
            Toast.makeText(this, "CBU/CVU debe contener 22 dígitos", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    //Agrego el contacto

    private void agregarContacto( int idUsuario, String contacto, String nombre) {
        boolean insertar = miBase.insertarContactos(idUsuario, contacto, nombre);
        if (insertar) {
            contactoList.add(new Contactos(idUsuario,contacto, nombre));
            contactoAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Contacto agregado", Toast.LENGTH_SHORT).show();
            // Limpia los campos del formulario
            editTextCBU.setText("");
            nombrePers.setText("");
        } else {
            Toast.makeText(this, "Error al agregar el contacto", Toast.LENGTH_SHORT).show();
        }
    }

// Traer los contactos agendados

    public List<Contactos> getAllContactos() {
        List<Contactos> contactos = new ArrayList<>();
        SQLiteDatabase db = miBase.getReadableDatabase();

        Cursor cursor= db.query(
        "Contactos",new String[]{"id_contacto", "id_usuario", "contacto", "nombre"},null,null,null,null,null);

        // Procesa el cursor para obtener los datos
        if (cursor != null && cursor.moveToFirst()) {
        do {
            long idContacto= cursor.getLong(cursor.getColumnIndexOrThrow("id_contacto"));
            long idUsuario= cursor.getLong(cursor.getColumnIndexOrThrow("id_usuario"));
            String contacto= cursor.getString(cursor.getColumnIndexOrThrow("contacto"));
            String nombre= cursor.getString(cursor.getColumnIndexOrThrow("nombre"));

            // Crea un objeto Contactos y añade a la lista
            Contactos contactoItem= new Contactos((int) idUsuario, contacto, nombre);
            contactos.add(contactoItem);
        } while (cursor.moveToNext());
        cursor.close(); // Cierra el cursor para liberar recursos
    }

    return contactos; // Retorna la lista de contactos
}



    //Borrar contacto

    private void eliminarContacto(Contactos contacto) {

        String contactoValor= contacto.getContacto();
        // / Eliminar el contacto usando el valor del contacto
        boolean eliminado= miBase.eliminarPorCBU(contactoValor);

        if (eliminado) {
            // Actualizar la lista y la vista
            contactoList.remove(contacto);
            contactoAdapter.notifyDataSetChanged();
            Toast.makeText(this, " Contacto eliminado", Toast.LENGTH_SHORT).show();
        } else {
            // Mostrar mensaje de error
            Toast.makeText(this, "Error al eliminar el contacto", Toast.LENGTH_SHORT).show();
        }
    }





    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}



