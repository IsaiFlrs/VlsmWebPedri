package com.calculadoravlsm.vlsm;

import java.util.ArrayList;
import java.util.List;

public class Subred {
    private String ipDecimal;         // Ej: 192.168.1.0
    private String ipBinaria;         // Ej: 11000000.10101000.00000001.00000000
    private int mascara;              // Ej: 26
    private String primeraIp;
    private String ultimaIp;
    private String broadcast;
    private int hostsRequeridos;
    private int hostsDisponibles;
    private List<Subred> subredesHijas; // Para anidar subredes (jerarquía)

    public Subred() {
        subredesHijas = new ArrayList<>();
    }

    // Getters y setters
    public String getIpDecimal() {
        return ipDecimal;
    }

    public void setIpDecimal(String ipDecimal) {
        this.ipDecimal = ipDecimal;
    }

    public String getIpBinaria() {
        return ipBinaria;
    }

    public void setIpBinaria(String ipBinaria) {
        this.ipBinaria = ipBinaria;
    }

    public int getMascara() {
        return mascara;
    }

    public void setMascara(int mascara) {
        this.mascara = mascara;
    }

    public String getPrimeraIp() {
        return primeraIp;
    }

    public void setPrimeraIp(String primeraIp) {
        this.primeraIp = primeraIp;
    }

    public String getUltimaIp() {
        return ultimaIp;
    }

    public void setUltimaIp(String ultimaIp) {
        this.ultimaIp = ultimaIp;
    }

    public String getBroadcast() {
        return broadcast;
    }

    public void setBroadcast(String broadcast) {
        this.broadcast = broadcast;
    }

    public int getHostsRequeridos() {
        return hostsRequeridos;
    }

    public void setHostsRequeridos(int hostsRequeridos) {
        this.hostsRequeridos = hostsRequeridos;
    }

    public int getHostsDisponibles() {
        return hostsDisponibles;
    }

    public void setHostsDisponibles(int hostsDisponibles) {
        this.hostsDisponibles = hostsDisponibles;
    }

    public List<Subred> getSubredesHijas() {
        return subredesHijas;
    }

    public void setSubredesHijas(List<Subred> subredesHijas) {
        this.subredesHijas = subredesHijas;
    }

    public void agregarSubredHija(Subred s) {
        this.subredesHijas.add(s);
    }
}

