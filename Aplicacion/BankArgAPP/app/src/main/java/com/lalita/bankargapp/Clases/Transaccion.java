package com.lalita.bankargapp.Clases;

import java.sql.Timestamp;

public class Transaccion {
    private int id_cuenta;
    private int id_tipo_transaccion;
    private float monto;
    private Timestamp fecha_transaccion;
    private String descripcion;

    public Transaccion(int idCuenta, int idTipoTransaccion, float monto, Timestamp fechaTransaccion, String descripcion) {
        id_cuenta = idCuenta;
        id_tipo_transaccion = idTipoTransaccion;
        this.monto = monto;
        fecha_transaccion = fechaTransaccion;
        this.descripcion = descripcion;
    }

    public int getId_cuenta() {
        return id_cuenta;
    }

    public void setId_cuenta(int id_cuenta) {
        this.id_cuenta = id_cuenta;
    }

    public int getId_tipo_transaccion() {
        return id_tipo_transaccion;
    }

    public void setId_tipo_transaccion(int id_tipo_transaccion) {
        this.id_tipo_transaccion = id_tipo_transaccion;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public Timestamp getFecha_transaccion() {
        return fecha_transaccion;
    }

    public void setFecha_transaccion(Timestamp fecha_transaccion) {
        this.fecha_transaccion = fecha_transaccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
