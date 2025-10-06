package ar.edu.uade.compralo.compralo.utils;

import ar.edu.uade.compralo.compralo.model.entity.Producto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

import static java.lang.Math.min;

@Getter
@Setter
public class DPUtils {
    Map<Producto, Double> vector;
    int n;
    int m;
    Double[][] tabla;
    List<Producto> productos = new ArrayList<>();
    Set<Producto> combinacion = new HashSet<>();

    public DPUtils(Map<Producto, Double> vector, int m) {
        this.vector = vector;
        this.n = vector.size();
        this.m = m;
        this.tabla = new Double[n][m+1];

        productos.addAll(vector.keySet());
    }

    public Set<Producto> nRecomendaciones() {
        if (n <= m) {
            combinacion.addAll(productos);
        } else {
            construirSolucion();
            reconstruirCamino();
        }
        return combinacion;
    }

    private void construirSolucion() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (j == 0) tabla[i][j] = 0.0;
                else if (i == 0) tabla[i][j] = vector.get(productos.get(i));
                else if (i <= j) tabla[i][j] = tabla[i-1][j] + vector.get(productos.get(i));
                else tabla[i][j] = min(tabla[i-1][j], tabla[i-1][j-1] + vector.get(productos.get(i)));
            }
        }
    }

    private void reconstruirCamino() {
        int j = m;

        for (int i = tabla.length - 1; i >= 0; i--) {
            if (Double.compare(tabla[i][j], 0) == 0) break;
            if (i == 0) combinacion.add(productos.get(i));
            else if (!Objects.equals(tabla[i][j], tabla[i - 1][j])) {
                combinacion.add(productos.get(i));
                j--;
            }
        }
    }
}