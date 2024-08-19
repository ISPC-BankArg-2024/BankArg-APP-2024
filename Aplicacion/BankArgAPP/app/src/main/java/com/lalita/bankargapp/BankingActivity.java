package com.lalita.bankargapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.MenuItem;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.lalita.bankargapp.Clases.Transaccion;
import com.lalita.bankargapp.Clases.TransaccionAdapter;

import java.util.ArrayList;
import java.util.List;

public class BankingActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    TextView saldo;
    RecyclerView recyclerViewTransacciones;
    SQLiteDatabase db;
    TransaccionAdapter transaccionAdapter;
    List<Transaccion> transaccionList;

    Button btnTransferencia, btnPagos, btnPerfil, btnLoan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banking);

        recyclerViewTransacciones = findViewById(R.id.recyclerViewTransacciones);
        recyclerViewTransacciones.setLayoutManager(new LinearLayoutManager(this));

        /*--- Boton en el tool bar que lleva al perfil---*/

        View btnPerfil = findViewById(R.id.account_cir);

        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BankingActivity.this, PerfilActivity.class);
                startActivity(intent);
            }
        });

        saldo = findViewById(R.id.saldo);

        // Inicializar la base de datos
        UsuariosSQLiteHelper dbHelper = new UsuariosSQLiteHelper(this);
        db = dbHelper.getReadableDatabase();

        // Recuperar id_usuario de SharedPreferences
        SharedPreferences preferences = getSharedPreferences("user_session", MODE_PRIVATE);
        int idUsuario = preferences.getInt("id_usuario", -1);

        // Verificar si id_usuario es válido
        if (idUsuario != -1) {
            // Obtener los datos del usuario de la base de datos
            cargarTransacciones(idUsuario);
            obtenerDatosUsuario(idUsuario);
        } else {
            Toast.makeText(this, "Error: Usuario no logueado", Toast.LENGTH_SHORT).show();
        }


        Button btn_transferir = findViewById(R.id.button13);
        btn_transferir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BankingActivity.this, TransferActivity.class);
                startActivity(intent);
            }
        });

        Button btn_pagos = findViewById(R.id.button12);
        btn_pagos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BankingActivity.this, PagosActivity.class);
                startActivity(intent);
            }
        });

        Button btn_ingresar = findViewById(R.id.button14);
        btn_ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BankingActivity.this, IngresarDineroActivity.class);
                startActivity(intent);
            }
        });

        /*--- lleva al home ---*/
        View btnHome = findViewById(R.id.view10);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BankingActivity.this, ProductActivity.class);
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

                BankingActivity activity = BankingActivity.this;
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

    private void cargarTransacciones(int idUsuario) {
        List<Transaccion> transaccionList = new ArrayList<>();

        // Consulta para obtener las transacciones del usuario, ordenadas por fecha_transaccion DESC (de más reciente a más antigua)
        String query = "SELECT t.id_transaccion, t.id_cuenta, t.id_tipo_transaccion, t.monto, t.fecha_transaccion, t.descripcion " +
                "FROM Transacciones t " +
                "JOIN Cuentas c ON t.id_cuenta = c.id_cuenta " +
                "WHERE c.id_usuario = ? " +
                "ORDER BY t.fecha_transaccion DESC " + // Ordenar por fecha DESC
                "LIMIT 10"; // Limite 10


        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idUsuario)});

        if (cursor.moveToFirst()) {
            do {
                // Recuperar los datos del cursor
                int idTransaccion = cursor.getInt(0);
                int idCuenta = cursor.getInt(1);
                int idTipoTransaccion = cursor.getInt(2);
                float monto = cursor.getFloat(3);
                String fechaTransaccion = cursor.getString(4);  // Ahora la fecha es un String
                String descripcion = cursor.getString(5);

                // Crear un objeto Transaccion con los datos
                Transaccion transaccion = new Transaccion(idTransaccion, idCuenta, idTipoTransaccion, monto, fechaTransaccion, descripcion);
                transaccionList.add(transaccion);
            } while (cursor.moveToNext());
        }
        cursor.close();

        // Configurar el adaptador del RecyclerView
        transaccionAdapter = new TransaccionAdapter(transaccionList, this); // 'this' es el Context
        recyclerViewTransacciones.setAdapter(transaccionAdapter);
    }


    private void obtenerDatosUsuario(int idUsuario) {
        // Consulta para obtener la información del usuario desde la base de datos
        String query = "SELECT c.saldo " +
                "FROM Usuarios2 u " +
                "LEFT JOIN Cuentas c ON u.id_usuario = c.id_usuario " +
                "WHERE u.id_usuario = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idUsuario)});

        if (cursor.moveToFirst()) {
            // Obtener datos del cursor y actualizar las vistas
            double saldoValue = cursor.getDouble(0);

            // Actualizar los TextView con los datos del usuario
            saldo.setText("$" + String.format("%.2f", saldoValue));
        } else {
            Toast.makeText(this, "No se encontraron datos para el usuario", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
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