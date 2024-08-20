package com.lalita.bankargapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.MenuItem;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.navigation.NavigationView;

public class IngresarDineroActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    private SQLiteDatabase db;
    private EditText dineroIngresarEditText;
    private Button ingresarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_dinero);

        dineroIngresarEditText = findViewById(R.id.Dineroingresar);
        ingresarButton = findViewById(R.id.Ingresar);


        // Inicializar la base de datos
        UsuariosSQLiteHelper dbHelper = new UsuariosSQLiteHelper(this);
        db = dbHelper.getReadableDatabase();

        // Agregar la funcionalidad al botón Ingresar
        ingresarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingresarDinero();
            }
        });

        /*--- Boton en el tool bar que lleva al perfil---*/

        View btnPerfil = findViewById(R.id.account_cir);
        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IngresarDineroActivity.this, PerfilActivity.class);
                startActivity(intent);
            }
        });

        /*--- lleva al home ---*/
        View btnHome = findViewById(R.id.rectangle_2);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IngresarDineroActivity.this, ProductActivity.class);
                startActivity(intent);
            }
        });

        // Referencia al Spinner
        Spinner miSpinner = findViewById(R.id.miSpinner);

        // Opciones del Spinner
        String[] opciones = {"Pago Fácil", "Rappi Pago"};

        // Adaptador para el Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Asigna el adaptador al Spinner
        miSpinner.setAdapter(adapter);

        // Maneja la selección de una opción
        miSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Obtén la opción seleccionada
                String seleccion = (String) parent.getItemAtPosition(position);

                // Muestra un mensaje con la opción seleccionada
                Toast.makeText(IngresarDineroActivity.this, "Seleccionaste: " + seleccion, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        /*---------------------Hooks------------------------*/
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
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

                IngresarDineroActivity activity = IngresarDineroActivity.this;
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
                } else if (itemId == R.id.nav_contact) {
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

    private void ingresarDinero() {
        // Obtener el monto ingresado
        String montoIngresadoStr = dineroIngresarEditText.getText().toString().trim();
        if (montoIngresadoStr.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese un monto válido", Toast.LENGTH_SHORT).show();
            return;
        }

        double montoIngresado = Double.parseDouble(montoIngresadoStr);

        // Aquí debes obtener el ID del usuario actualmente logueado.
        // Supongamos que lo tenemos almacenado en una variable llamada idUsuarioLogueado.
        int idUsuarioLogueado = obtenerIdUsuarioLogueado();

        // Consultar el saldo actual
        double saldoActual = obtenerSaldoActual(idUsuarioLogueado);

        // Sumar el monto ingresado al saldo actual
        double nuevoSaldo = saldoActual + montoIngresado;

        // Actualizar el saldo en la base de datos
        ContentValues values = new ContentValues();
        values.put("saldo", nuevoSaldo);
        int rowsUpdated = db.update("Cuentas", values, "id_usuario = ?", new String[]{String.valueOf(idUsuarioLogueado)});

        if (rowsUpdated > 0) {
            Toast.makeText(this, "Saldo actualizado correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al actualizar el saldo", Toast.LENGTH_SHORT).show();
        }

        // Registrar la transacción en la tabla Transacciones (Opcional)
        registrarTransaccion(idUsuarioLogueado, montoIngresado);
    }

    private int obtenerIdUsuarioLogueado() {
        // Recuperar id_usuario de SharedPreferences
        SharedPreferences preferences = getSharedPreferences("user_session", MODE_PRIVATE);
        int idUsuario = preferences.getInt("id_usuario", -1);
        return idUsuario;
    }

    private double obtenerSaldoActual(int idUsuarioLogueado) {
        double saldo = 0.0;
        Cursor cursor = db.rawQuery("SELECT saldo FROM Cuentas WHERE id_usuario = ?", new String[]{String.valueOf(idUsuarioLogueado)});
        if (cursor.moveToFirst()) {
            saldo = cursor.getDouble(cursor.getColumnIndex("saldo"));
        }
        cursor.close();
        return saldo;
    }

    private void registrarTransaccion(int idUsuarioLogueado, double monto) {
        ContentValues values = new ContentValues();
        values.put("id_cuenta", idUsuarioLogueado);
        values.put("id_tipo_transaccion", 1); // Suponiendo 1 es para ingreso de dinero
        values.put("monto", monto);
        values.put("descripcion", "Ingreso de dinero");
        db.insert("Transacciones", null, values);
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