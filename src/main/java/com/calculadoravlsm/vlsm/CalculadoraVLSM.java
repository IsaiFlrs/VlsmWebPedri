package com.calculadoravlsm.vlsm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CalculadoraVLSM {

    public List<Subred> calcular(String ipBase, int mascaraBase, List<Integer> tamanos) {
        List<Subred> lista = new ArrayList<>();
        Collections.sort(tamanos, Comparator.reverseOrder());

        long ipActual = ipADecimal(ipBase);

        for (int hosts : tamanos) {
            int bits = 0;
            while (Math.pow(2, bits) - 2 < hosts) {
                bits++;
            }
            int nuevaMascara = 32 - bits;
            int total = (int) Math.pow(2, bits);

            Subred s = new Subred();
            s.setIpDecimal(decimalAIP(ipActual));
            s.setIpBinaria(decimalABinario(ipActual));
            s.setMascara(nuevaMascara);
            s.setHostsRequeridos(hosts);
            s.setHostsDisponibles(total - 2);
            s.setPrimeraIp(decimalAIP(ipActual + 1));
            s.setUltimaIp(decimalAIP(ipActual + total - 2));
            s.setBroadcast(decimalAIP(ipActual + total - 1));

            lista.add(s);
            ipActual += total;
        }

        return lista;
    }

    // --- Métodos auxiliares ---

    private long ipADecimal(String ip) {
        String[] partes = ip.split("\\.");
        long res = 0;
        for (String p : partes) {
            res = (res << 8) + Integer.parseInt(p);
        }
        return res;
    }

    private String decimalAIP(long ip) {
        return ((ip >> 24) & 255) + "." + ((ip >> 16) & 255) + "." + ((ip >> 8) & 255) + "." + (ip & 255);
    }

    private String decimalABinario(long ip) {
        StringBuilder binario = new StringBuilder();
        for (int i = 3; i >= 0; i--) {
            long octeto = (ip >> (8 * i)) & 255;
            String bin = String.format("%8s", Long.toBinaryString(octeto)).replace(' ', '0');
            binario.append(bin);
            if (i > 0) binario.append(".");
        }
        return binario.toString();
    }
}
