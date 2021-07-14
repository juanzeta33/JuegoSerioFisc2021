package com.example.juegoejemplo.Requests;

import com.example.juegoejemplo.Entidades.Partida;

import java.util.List;

public class PartidaRequest {
    private String juego;
    private String jugador;
    private int puntaje;
    private String fecha;
    private String hora;
    private int partida;
    private String nivel;
    private List<Partida> detalle;

    public String getJuego() {
        return juego;
    }

    public void setJuego(String juego) {
        this.juego = juego;
    }

    public String getJugador() {
        return jugador;
    }

    public void setJugador(String jugador) {
        this.jugador = jugador;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getPartida() {
        return partida;
    }

    public void setPartida(int partida) {
        this.partida = partida;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public List<Partida> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<Partida> detalle) {
        this.detalle = detalle;
    }
}
