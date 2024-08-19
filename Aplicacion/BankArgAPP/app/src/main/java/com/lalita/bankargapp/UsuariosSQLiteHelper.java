package com.lalita.bankargapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.lalita.bankargapp.Clases.User;
import com.lalita.bankargapp.Clases.Usuario;
import com.lalita.bankargapp.Clases.Usuarios;

import java.util.ArrayList;
import java.util.List;

public class UsuariosSQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "BankArgAPP.db";
    private static final int DATABASE_VERSION = 17;

    // Definir la estructura de la tabla "user".
    private static final String CREATE_TABLE_USER = "CREATE TABLE if not exists User (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "username TEXT NOT NULL," +
            "password TEXT NOT NULL)";
    public UsuariosSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DB_CREATION", "Database created or upgraded");
        // Ejecutar la creación de la tabla "user".
        db.execSQL(CREATE_TABLE_USER);

        // Crear las tablas

        createUsuarioTable(db);
        createTipoCuentaTable(db);
        createCuentaTable(db);
        createTipoTransaccionTable(db);
        createTransaccionTable(db);

//        createTipoContactosTable(db);
        createContactosTable(db);

        Cursor cursor = db.rawQuery("PRAGMA table_info(Usuarios2);", null);
        if (cursor.moveToFirst()) {
            do {
                String columnName = cursor.getString(1); // 1 es el índice del nombre de la columna
                Log.d("DatabaseInfo", "Columna: " + columnName);
            } while (cursor.moveToNext());
        }
        cursor.close();

//        Insertar las tablas
        //insertarDatosIniciales(db);
        insertarDatosIniciales(db);



//        db.execSQL("INSERT INTO User (username, password) VALUES ('admin', '1234')");
//        db.execSQL("INSERT INTO User (username, password) VALUES ('eze', '1234')");
//        db.execSQL("INSERT INTO User (username, password) VALUES ('joaco', '1234')");
//        db.execSQL("INSERT INTO User (username, password) VALUES ('valen', '1234')");
//        db.execSQL("INSERT INTO User (username, password) VALUES ('user', '1234')");
//
//        db.execSQL("CREATE TABLE if not exists Documentos (id_tipo_doc INTEGER PRIMARY KEY AUTOINCREMENT, tipo_doc TEXT);");
//        db.execSQL("CREATE TABLE if not exists Sexos (id_tipo_sexo INTEGER PRIMARY KEY AUTOINCREMENT, tipo TEXT);");
//        db.execSQL("CREATE TABLE if not exists paises (cod_pais INTEGER PRIMARY KEY AUTOINCREMENT, pais TEXT);");
//        db.execSQL("CREATE TABLE if not exists provincias (cod_provincia INTEGER PRIMARY KEY AUTOINCREMENT, provincia TEXT, cod_pais INTEGER, FOREIGN KEY (cod_pais) REFERENCES paises (cod_pais));");
//        db.execSQL("CREATE TABLE if not exists localidades (cod_localidad INTEGER PRIMARY KEY AUTOINCREMENT, localidad TEXT, cod_provincia INTEGER, FOREIGN KEY (cod_provincia) REFERENCES provincias (cod_provincia));");
//        db.execSQL("CREATE TABLE if not exists Usuarios (id_usuario INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, apellido TEXT, password TEXT, id_tipo_doc INTEGER, nro_doc TEXT, cod_localidad INTEGER, nro_calle INTEGER, calle TEXT, fecha_nac TEXT, id_tipo_sexo INTEGER, FOREIGN KEY (id_tipo_doc) REFERENCES Documentos (id_tipo_doc), FOREIGN KEY (cod_localidad) REFERENCES localidades (cod_localidad), FOREIGN KEY (id_tipo_sexo) REFERENCES Sexos (id_tipo_sexo));");
//
//        insertarPais(db, "Argentina");
//        insertarPais(db, "Brasil");
//        insertarPais(db, "Chile");
//        insertarPais(db, "Uruguay");

//
//        // Insertar Provincias
//        // Arg
//        insertarProvincia(db, "Buenos Aires", 1);
//        insertarProvincia(db, "Catamarca", 1);
//        insertarProvincia(db, "Chaco", 1);
//        insertarProvincia(db, "Chubut", 1);
//        insertarProvincia(db, "Cordoba", 1);
//        insertarProvincia(db, "Corrientes", 1);
//        insertarProvincia(db, "Entre Rios", 1);
//        insertarProvincia(db, "Formosa", 1);
//        insertarProvincia(db, "Jujuy", 1);
//        insertarProvincia(db, "La Pampa", 1);
//        insertarProvincia(db, "La Rioja", 1);
//        insertarProvincia(db, "Mendoza", 1);
//        insertarProvincia(db, "Misiones", 1);
//        insertarProvincia(db, "Neuquen", 1);
//        insertarProvincia(db, "Rio Negro", 1);
//        insertarProvincia(db, "Salta", 1);
//        insertarProvincia(db, "San Juan", 1);
//        insertarProvincia(db, "San Luis", 1);
//        insertarProvincia(db, "Santa Cruz", 1);
//        insertarProvincia(db, "Santa Fe", 1);
//        insertarProvincia(db, "Santiago del Estero", 1);
//        insertarProvincia(db, "Tierra del Fuego", 1);
//        insertarProvincia(db, "Tucuman", 1);
//

//
//// Otros
//        insertarProvincia(db, "Otro", 21);
//        insertarProvincia(db, "Otro", 22);
//        insertarProvincia(db, "Otro", 23);
//        insertarProvincia(db, "Otro", 24);
//        insertarProvincia(db, "Otro", 25);
//        insertarProvincia(db, "Otro", 26);
//        insertarProvincia(db, "Otro", 27);
//        insertarProvincia(db, "Otro", 28);
//        insertarProvincia(db, "Otro", 29);
//        insertarProvincia(db, "Otro", 30);
//        insertarProvincia(db, "Otro", 31);
//        insertarProvincia(db, "Otro", 32);
//
//        // Argentina
//        insertarLocalidad(db, "Buenos Aires", 1);
//        insertarLocalidad(db, "La Plata", 1);
//        insertarLocalidad(db, "Mar del Plata", 1);
//        insertarLocalidad(db, "Quilmes", 1);
//        insertarLocalidad(db, "Lomas de Zamora", 1); // ID: 5
//
//        insertarLocalidad(db, "San Fernando del Valle de Catamarca", 2); // Catamarca
//        insertarLocalidad(db, "Resistencia", 3); // Chaco
//        insertarLocalidad(db, "Rawson", 4); // Chubut
//
//        insertarLocalidad(db, "Córdoba", 5);
//        insertarLocalidad(db, "Río Cuarto", 5);
//        insertarLocalidad(db, "Villa María", 5);
//        insertarLocalidad(db, "Villa Carlos Paz", 5);
//        insertarLocalidad(db, "Río Tercero", 5); // ID: 13
//
//        insertarLocalidad(db, "Corrientes", 6);
//        insertarLocalidad(db, "Paraná", 7); // Entre Ríos
//        insertarLocalidad(db, "Formosa", 8);
//        insertarLocalidad(db, "San Salvador de Jujuy", 9); // Jujuy
//        insertarLocalidad(db, "Santa Rosa", 10); // La Pampa
//        insertarLocalidad(db, "La Rioja", 11);
//        insertarLocalidad(db, "Mendoza", 12);
//        insertarLocalidad(db, "Godoy Cruz", 12);
//        insertarLocalidad(db, "Guaymallén", 12);
//        insertarLocalidad(db, "Luján de Cuyo", 12);
//        insertarLocalidad(db, "San Rafael", 12); // ID: 24
//        insertarLocalidad(db, "Posadas", 13); // Misiones
//        insertarLocalidad(db, "Neuquén", 14); // Neuquén
//        insertarLocalidad(db, "Viedma", 15); // Rio Negro
//        insertarLocalidad(db, "Salta", 16);
//        insertarLocalidad(db, "San Juan", 17);
//        insertarLocalidad(db, "San Luis", 18);
//        insertarLocalidad(db, "Río Gallegos", 19); // Santa Cruz
//        insertarLocalidad(db, "Santa Fe", 20);
//        insertarLocalidad(db, "Rosario", 20);
//        insertarLocalidad(db, "Venado Tuerto", 20);
//        insertarLocalidad(db, "San Lorenzo", 20);
//        insertarLocalidad(db, "San Cristóbal", 20); // ID: 36
//        insertarLocalidad(db, "Santiago del Estero", 21);
//        insertarLocalidad(db, "Ushuaia", 22); // Tierra del Fuego
//        insertarLocalidad(db, "San Miguel de Tucumán", 23); // Tucumán
//

//
//        insertarDocumento(db, "Dni");
//        insertarDocumento(db, "Pasaporte");
//        insertarDocumento(db, "Obra social");
//        insertarDocumento(db, "Licencia de conducir");
//
//        insertarSexo(db, "Hombre");
//        insertarSexo(db, "Mujer");
//        insertarSexo(db, "Otro");
//
//        insertarUsuario(db, "Ana", "López", "12345", 1, "12345678", 1, 101, "Calle Principal", "1990-04-15", 2);



    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        // Aquí podrías agregar más condiciones si necesitas manejar futuras actualizaciones
        db.execSQL("DROP TABLE IF EXISTS Usuarios2");
        db.execSQL("DROP TABLE IF EXISTS Tipos_Cuenta");
        db.execSQL("DROP TABLE IF EXISTS Cuentas");
        db.execSQL("DROP TABLE IF EXISTS Tipos_Transaccion");
        db.execSQL("DROP TABLE IF EXISTS Transacciones");
        db.execSQL("DROP TABLE IF EXISTS Contactos");

       // createUsuarioTable(db);
       // createTipoCuentaTable(db);
       // createCuentaTable(db);
      //  createTipoTransaccionTable(db);
       // createTransaccionTable(db);
       // createContactosTable(db);

       onCreate(db);  // Crear la tabla nuevamente
    }




    // Tablas BD 2

    // Método para insertar datos iniciales
    private void insertarDatosIniciales(SQLiteDatabase db) {

        // Insertar 10 usuarios
        insertarUsuario2(db,"Juan", "Perez", "juan.perez@mail.com", "password123");
        insertarUsuario2(db,"Maria", "Gomez", "maria.gomez@mail.com", "password456");
        insertarUsuario2(db,"Luis", "Martinez", "luis.martinez@mail.com", "password789");
        insertarUsuario2(db,"Ana", "Garcia", "ana.garcia@mail.com", "password111");
        insertarUsuario2(db,"Pedro", "Lopez", "pedro.lopez@mail.com", "password222");
        insertarUsuario2(db,"Jose", "Diaz", "jose.diaz@mail.com", "password333");
        insertarUsuario2(db,"Carla", "Sanchez", "carla.sanchez@mail.com", "password444");
        insertarUsuario2(db,"Miguel", "Torres", "miguel.torres@mail.com", "password555");
        insertarUsuario2(db,"Laura", "Cruz", "laura.cruz@mail.com", "password666");
        insertarUsuario2(db,"Diego", "Mendez", "diego.mendez@mail.com", "password777");

        // Insertar 10 tipos de cuenta
        insertarTipoCuenta(db, "Ahorros");
        insertarTipoCuenta(db, "Corriente");
        insertarTipoCuenta(db, "Nomina");
        insertarTipoCuenta(db, "Inversion");
        insertarTipoCuenta(db, "Crédito");
        insertarTipoCuenta(db, "Estudiantil");
        insertarTipoCuenta(db, "Empresarial");
        insertarTipoCuenta(db, "Conjunta");
        insertarTipoCuenta(db, "Infantil");
        insertarTipoCuenta(db, "Fideicomiso");

        // Insertar 10 cuentas
        insertarCuenta(db,1, 1, 5000.50);
        insertarCuenta(db,2, 2, 15000.75);
        insertarCuenta(db,3, 3, 3000.00);
        insertarCuenta(db,4, 4, 7500.20);
        insertarCuenta(db,5, 1, 10000.00);
        insertarCuenta(db,6, 2, 8500.10);
        insertarCuenta(db,7, 5, 9500.00);
        insertarCuenta(db,8, 6, 5000.25);
        insertarCuenta(db,9, 7, 13000.00);
        insertarCuenta(db,10, 8, 17500.60);

        // Insertar 10 tipos de transacción
        insertarTipoTransaccion(db, "Depósito");
        insertarTipoTransaccion(db, "Retiro");
        insertarTipoTransaccion(db, "Transferencia");
        insertarTipoTransaccion(db, "Pago");
        insertarTipoTransaccion(db, "Compra");
        insertarTipoTransaccion(db, "Recarga");
        insertarTipoTransaccion(db, "Devolución");
        insertarTipoTransaccion(db, "Reembolso");
        insertarTipoTransaccion(db, "Intereses");
        insertarTipoTransaccion(db, "Penalización");

        // Insertar 10 transacciones
        insertarTransaccion(db,1, 1, 100.50, "Compra de comida");
        insertarTransaccion(db,2, 2, 200.00, "Pago de servicios");
        insertarTransaccion(db,3, 1, 300.25, "Retiro de cajero");
        insertarTransaccion(db,4, 2, 400.75, "Depósito");
        insertarTransaccion(db,5, 1, 150.00, "Pago de alquiler");
        insertarTransaccion(db,6, 2, 250.50, "Transferencia");
        insertarTransaccion(db,7, 1, 350.30, "Compra de ropa");
        insertarTransaccion(db,8, 2, 450.20, "Pago de factura");
        insertarTransaccion(db,9, 1, 500.00, "Compra de electrodoméstico");
        insertarTransaccion(db,10, 2, 600.00, "Gastos médicos");

        // Insertar 10 tipos de contacto
//        insertarTipoContacto(db, "Email");
//        insertarTipoContacto(db, "Teléfono");
//        insertarTipoContacto(db, "CBU/CVU");

        // Insertar 10 contactos
        insertarContactos(db,1, "1234567894561234567891", "juan A");
        insertarContactos(db,2, "1234567894561234567892", "juan B");
        insertarContactos(db,3, "1234567894561234567894", "juan C");
        insertarContactos(db,4, "1234567894561234567893", "juan D");
        insertarContactos(db,5, "1234567894561234567895", "juan E");
        insertarContactos(db,6, "7894561233214569874563", "juan F");
        insertarContactos(db,7, "1234567894561234567896", "juan G");
        insertarContactos(db,8, "1234567894561234567897", "juan H");
        insertarContactos(db,9, "3698521477412589632587", "juan I");
        insertarContactos(db,10, "1234567894561234567898", "juan J");

    }

    // =================== Métodos para la tabla Usuarios ===================
    private void createUsuarioTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Usuarios2 (" +
                "id_usuario INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT, " +
                "apellido TEXT, " +
                "email TEXT UNIQUE, " +
                "password TEXT)");
    }

    public boolean insertarUsuario2(SQLiteDatabase db, String nombre, String apellido, String email, String password) {
        Log.d("DB_CREATION", "Creating Usuarios2 table");
//        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("apellido", apellido);
        values.put("email", email);
        values.put("password", password);
        long result = db.insert("Usuarios2", null, values);
        return result != -1;
    }

    public boolean eliminarUsuario2(long id_usuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Usuarios2", "id_usuario = ?", new String[]{String.valueOf(id_usuario)}) > 0;
    }

    public boolean actualizarUsuario2(long id_usuario, String nombre, String apellido, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("apellido", apellido);
        values.put("email", email);
        values.put("password", password);
        return db.update("Usuarios2", values, "id_usuario = ?", new String[]{String.valueOf(id_usuario)}) > 0;
    }

    // =================== Métodos para la tabla Tipos_Cuenta ===================
    private void createTipoCuentaTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Tipos_Cuenta (" +
                "id_tipo_cuenta INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tipo_cuenta TEXT)");
    }

    public boolean insertarTipoCuenta(SQLiteDatabase db, String tipo_cuenta) {
        ContentValues values = new ContentValues();
        values.put("tipo_cuenta", tipo_cuenta);
        long result = db.insert("Tipos_Cuenta", null, values);
        return result != -1;
    }

    // =================== Métodos para la tabla Cuentas ===================
    private void createCuentaTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Cuentas (" +
                "id_cuenta INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id_usuario INTEGER NOT NULL, " +
                "id_tipo_cuenta INTEGER NOT NULL, " +
                "saldo DECIMAL(12, 2) DEFAULT 0.00, " +
                "FOREIGN KEY (id_usuario) REFERENCES Usuarios2(id_usuario), " +
                "FOREIGN KEY (id_tipo_cuenta) REFERENCES Tipos_Cuenta(id_tipo_cuenta))");
    }

    public boolean insertarCuenta(SQLiteDatabase db, long id_usuario, long id_tipo_cuenta, double saldo) {
//        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_usuario", id_usuario);
        values.put("id_tipo_cuenta", id_tipo_cuenta);
        values.put("saldo", saldo); // Saldo inicial (0.0)
        long result = db.insert("Cuentas", null, values);
        return result != -1;
    }

    public boolean eliminarCuenta(long id_cuenta) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Cuentas", "id_cuenta = ?", new String[]{String.valueOf(id_cuenta)}) > 0;
    }

    public boolean actualizarCuenta(long id_cuenta, long id_usuario, long id_tipo_cuenta, double saldo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_usuario", id_usuario);
        values.put("id_tipo_cuenta", id_tipo_cuenta);
        values.put("saldo", saldo);
        return db.update("Cuentas", values, "id_cuenta = ?", new String[]{String.valueOf(id_cuenta)}) > 0;
    }

    // =================== Métodos para la tabla Tipos_Transaccion ===================
    private void createTipoTransaccionTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Tipos_Transaccion (" +
                "id_tipo_transaccion INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tipo_transaccion TEXT)");
    }

    public boolean insertarTipoTransaccion(SQLiteDatabase db, String tipo_transaccion) {
        ContentValues values = new ContentValues();
        values.put("tipo_transaccion", tipo_transaccion);
        long result = db.insert("Tipos_Transaccion", null, values);
        return result != -1;
    }

    // =================== Métodos para la tabla Transacciones ===================
    private void createTransaccionTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Transacciones (" +
                "id_transaccion INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id_cuenta INTEGER NOT NULL, " +
                "id_tipo_transaccion INTEGER NOT NULL, " +
                "monto DECIMAL(10, 2), " +
                "fecha_transaccion TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "descripcion TEXT, " +
                "FOREIGN KEY (id_cuenta) REFERENCES Cuentas(id_cuenta), " +
                "FOREIGN KEY (id_tipo_transaccion) REFERENCES Tipos_Transaccion(id_tipo_transaccion))");
    }

    public boolean insertarTransaccion(SQLiteDatabase db, long id_cuenta, long id_tipo_transaccion, double monto, String descripcion) {
//        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_cuenta", id_cuenta);
        values.put("id_tipo_transaccion", id_tipo_transaccion);
        values.put("monto", monto);
        values.put("descripcion", descripcion);
        long result = db.insert("Transacciones", null, values);
        return result != -1;
    }

    public boolean eliminarTransaccion(long id_transaccion) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Transacciones", "id_transaccion = ?", new String[]{String.valueOf(id_transaccion)}) > 0;
    }

    public boolean actualizarTransaccion(long id_transaccion, long id_cuenta, long id_tipo_transaccion, double monto, String descripcion) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_cuenta", id_cuenta);
        values.put("id_tipo_transaccion", id_tipo_transaccion);
        values.put("monto", monto);
        values.put("descripcion", descripcion);
        return db.update("Transacciones", values, "id_transaccion = ?", new String[]{String.valueOf(id_transaccion)}) > 0;
    }

    // =================== Métodos para la tabla Tipos_Contacto ===================\
//    private void createTipoContactosTable(SQLiteDatabase db) {
//        db.execSQL("CREATE TABLE IF NOT EXISTS Tipos_Contacto (" +
//                "id_tipo_contacto INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                "tipo_contacto TEXT)");
//    }
//
//    public boolean insertarTipoContacto(SQLiteDatabase db, String tipo_contacto) {
//        ContentValues values = new ContentValues();
//        values.put("tipo_contacto", tipo_contacto);
//        long result = db.insert("Tipos_Contacto", null, values);
//        return result != -1;
//    }






    // =================== Métodos para la tabla Contactos ===================
    private void createContactosTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Contactos (" +
                "id_contacto INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                "id_tipo_contacto INTEGER NOT NULL, " +
                "id_usuario INTEGER NOT NULL, " +
                "contacto TEXT, " +
                "nombre TEXT, " +
//                "FOREIGN KEY (id_tipo_contacto) REFERENCES Tipos_Contacto(id_tipo_contacto), " +
                "FOREIGN KEY (id_usuario) REFERENCES Usuarios2(id_usuario))");
    }

    public boolean insertarContactos(SQLiteDatabase db,long id_usuario, String contacto, String nombre) {
        ContentValues values = new ContentValues();
        values.put("id_usuario", id_usuario);
        values.put("contacto", contacto);
        values.put("nombre", nombre);
        long result = db.insert("Contactos", null, values);
        return result != -1;
    }

    public boolean eliminarPorCBU(String contacto) {
        SQLiteDatabase db=this.getWritableDatabase();
        int filasEliminadas= db.delete("Contactos","contacto = ?",new String[]{contacto});
        return filasEliminadas > 0; // Retorna true si al menos una fila fue eliminada
    }


    public boolean eliminarContacto(String cbu) {
       SQLiteDatabase db = this.getWritableDatabase();
       return db.delete("Contactos", "contacto = ?", new String[]{cbu}) > 0;
    }


    public boolean actualizarContactos(long id_contacto, long id_usuario, String contacto, String nombre) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_usuario", id_usuario);
        values.put("contacto", contacto);
        values.put("nombre", nombre);
        return db.update("Contactos", values, "id_contacto = ?", new String[]{String.valueOf(id_contacto)}) > 0;
    }

    // Fin Tablas BD 2







    private void insertarPais(SQLiteDatabase db, String pais) {
        ContentValues values = new ContentValues();
        values.put("pais", pais);
        db.insert("paises", null, values);
    }

    private void insertarProvincia(SQLiteDatabase db, String provincia, int codPais) {
        ContentValues values = new ContentValues();
        values.put("provincia", provincia);
        values.put("cod_pais", codPais);
        db.insert("provincias", null, values);
    }

    private void insertarLocalidad(SQLiteDatabase db, String localidad, int codProvincia) {
        ContentValues values = new ContentValues();
        values.put("localidad", localidad);
        values.put("cod_provincia", codProvincia);
        db.insert("localidades", null, values);
    }

    private void insertarDocumento(SQLiteDatabase db, String tipoDocumento) {
        ContentValues values = new ContentValues();
        values.put("tipo_doc", tipoDocumento);
        db.insert("Documentos", null, values);
    }

    private void insertarSexo(SQLiteDatabase db, String tipoSexo) {
        ContentValues values = new ContentValues();
        values.put("tipo", tipoSexo);
        db.insert("Sexos", null, values);
    }

    private void insertarUsuario(SQLiteDatabase db, String nombre, String apellido, String password, int idTipoDoc, String nroDoc, int codLocalidad, int nroCalle, String calle, String fechaNac, int idTipoSexo) {
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("apellido", apellido);
        values.put("password", password);
        values.put("id_tipo_doc", idTipoDoc);
        values.put("nro_doc", nroDoc);
        values.put("cod_localidad", codLocalidad);
        values.put("nro_calle", nroCalle);
        values.put("calle", calle);
        values.put("fecha_nac", fechaNac);
        values.put("id_tipo_sexo", idTipoSexo);
        db.insert("Usuarios", null, values);
    }

    public void insertarUsuario(String nombre, String apellido, String password, int tipoDoc, String nroDoc, int localidad, int nroCalle, String calle, String fechaNac, int tipoSexo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("apellido", apellido);
        values.put("password", password);
        values.put("id_tipo_doc", tipoDoc);
        values.put("nro_doc", nroDoc);
        values.put("cod_localidad", localidad);
        values.put("nro_calle", nroCalle);
        values.put("calle", calle);
        values.put("fecha_nac", fechaNac);
        values.put("id_tipo_sexo", tipoSexo);

        db.insert("Usuarios", null, values);
        db.close();
    }

    public void actualizarUsuario(int id, String nombre, String apellido, String password, int tipoDoc, String nroDoc, int localidad, int nroCalle, String calle, String fechaNac, int tipoSexo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("apellido", apellido);
        values.put("password", password);
        values.put("id_tipo_doc", tipoDoc);
        values.put("nro_doc", nroDoc);
        values.put("cod_localidad", localidad);
        values.put("nro_calle", nroCalle);
        values.put("calle", calle);
        values.put("fecha_nac", fechaNac);
        values.put("id_tipo_sexo", tipoSexo);

        db.update("Usuarios", values, "id_usuario" + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public int buscarUsuarioId(String nombre, String apellido, String nroDoc) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("Usuarios", new String[]{"id_usuario"},
                "nombre" + "=? AND " + "nro_doc" + "=?",
                new String[]{nombre, nroDoc}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int userId = cursor.getInt(cursor.getColumnIndex("id_usuario"));
            cursor.close();
            return userId;
        }

        return -1; // Return -1 if user is not found
    }

    public List<Usuario> getAllUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT u.*, d.tipo_doc AS DocNombre, l.localidad AS LocalidadNombre, s.tipo AS SexoNombre " +
                "FROM Usuarios u " +
                "JOIN Documentos d ON u.id_tipo_doc = d.id_tipo_doc " +
                "JOIN Localidades l ON u.cod_localidad = l.cod_localidad " +
                "JOIN Sexos s ON u.id_tipo_sexo = s.id_tipo_sexo";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int idUsuario = cursor.getInt(cursor.getColumnIndexOrThrow("id_usuario"));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                String apellido = cursor.getString(cursor.getColumnIndexOrThrow("apellido"));
                String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
                String docNombre = cursor.getString(cursor.getColumnIndexOrThrow("DocNombre"));
                String nroDoc = cursor.getString(cursor.getColumnIndexOrThrow("nro_doc"));
                String localidadNombre = cursor.getString(cursor.getColumnIndexOrThrow("LocalidadNombre"));
                int nroCalle = cursor.getInt(cursor.getColumnIndexOrThrow("nro_calle"));
                String calle = cursor.getString(cursor.getColumnIndexOrThrow("calle"));
                String fechaNac = cursor.getString(cursor.getColumnIndexOrThrow("fecha_nac"));
                String sexoNombre = cursor.getString(cursor.getColumnIndexOrThrow("SexoNombre"));

                Usuario usuario = new Usuario(idUsuario, nombre, apellido, password, docNombre, nroDoc,
                        localidadNombre, nroCalle, calle, fechaNac, sexoNombre);
                usuarios.add(usuario);
            } while (cursor.moveToNext());
            cursor.close(); // Asegúrate de cerrar el cursor cuando hayas terminado
        }

        return usuarios;
    }


    public List<String> getAllLabels(String columnName, String tableName) {
        List<String> labels = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + columnName + " FROM " + tableName + " ORDER BY " + columnName, null);

        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return labels;
    }

    public List<Integer> getAllIds(String COLUMN_ID, String tableName) {
        List<Integer> ids = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_ID + " FROM " + tableName, null);

        if (cursor.moveToFirst()) {
            do {
                ids.add(cursor.getInt(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return ids;
    }

    public Usuarios findUserByUsernameAndPassword(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Usuarios usuario = null;

        String[] columns = {
                "id_usuario",
                "nombre",
                "apellido",
                "username",
                "password",
                "id_tipo_doc",
                "nro_doc",
                "cod_localidad",
                "nro_calle",
                "calle",
                "fecha_nac",
                "id_tipo_sexo"
        };

        String selection = "SELECT username, password FROM Usuarios WHERE username='"+
        username+"' and password='"+password+"'";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.rawQuery(selection, null);

        if (cursor != null && cursor.moveToFirst()) {
            usuario = new Usuarios(
                    cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getInt(5),
                    cursor.getString(6), cursor.getInt(7), cursor.getInt(8),
                    cursor.getString(9), cursor.getString(10), cursor.getInt(11)
            );
            cursor.close();
        }

        return usuario;
    }

    public Boolean insertData(String nombre, String apellido, String username, String password, int idTipoDoc, String nroDoc, int codLocalidad, int nroCalle, String calle, String fechaNac, int idTipoSexo) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("nombre", nombre);
        contentValues.put("apellido", apellido);
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("id_tipo_doc", idTipoDoc);
        contentValues.put("nro_doc", nroDoc);
        contentValues.put("cod_localidad", codLocalidad);
        contentValues.put("nro_calle", nroCalle);
        contentValues.put("calle", calle);
        contentValues.put("fecha_nac", fechaNac);
        contentValues.put("id_tipo_sexo", idTipoSexo);

        long result = MyDatabase.insert("Usuarios", null, contentValues);

        if (result == -1) {
            return false; // Insertion failed
        } else {
            return true; // Insertion successful
        }
    }



}