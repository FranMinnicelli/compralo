package ar.edu.uade.compralo.compralo.utils;

import ar.edu.uade.compralo.compralo.model.entity.Producto;
import ar.edu.uade.compralo.compralo.model.entity.Relacion;

import java.util.*;

public class DijkstraUtils {

    /**
     * Este método utiliza Dijkstra para calcular el potencial interés hacia cada producto, respecto de un
     * producto raiz.
     *
     * @param productos el conjunto de productos relacionados al producto raiz.
     * @param raiz el nodo desde el cual se calculará la distancia a cada producto.
     * @return un mapa con la distancia para llegar a cada producto desde el producto raiz.
     */
    public static Map<Producto, Double> calcularCaminos(Set<Producto> productos, Producto raiz) {
        PriorityQueue<Producto> pendiente = new PriorityQueue<>();
        Map<Producto, Double> distancias = new HashMap<>();
        Set<Producto> visitados = new HashSet<>();

        pendiente.add(raiz);

        for (Producto p : productos) {
            distancias.put(p, Double.POSITIVE_INFINITY);
        }
        distancias.put(raiz, 0.0);

        while (!pendiente.isEmpty()) {
            Producto producto = pendiente.poll();

            if (producto != null) {
                if (!visitados.contains(producto)) {
                    visitados.add(producto);

                    for (Relacion relacion : producto.getRelacionados()) {
                        Producto relacionado = relacion.getProducto();
                        Double nuevaDistancia = distancias.get(producto) + relacion.getPeso();
                        if (nuevaDistancia < distancias.get(relacionado)) {
                            distancias.put(relacionado, nuevaDistancia);
                            pendiente.add(relacionado);
                        }
                    }
                }
            }
        }

        return distancias;
    }
}
