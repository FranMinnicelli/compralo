package ar.edu.uade.compralo.compralo.service;

import ar.edu.uade.compralo.compralo.model.entity.Distancia;
import ar.edu.uade.compralo.compralo.model.entity.Producto;
import ar.edu.uade.compralo.compralo.utils.DijkstraUtils;
import ar.edu.uade.compralo.compralo.utils.MapaUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@AllArgsConstructor
public class RecomendacionService {
    private static final int MAXIMA_PROFUNDIDAD = 2;
    private final ProductoService productoService;

    public Set<Producto> obtenerRecomendaciones(Producto producto, int n) {
        Set<Producto> productos = productoService.encontrarProductosRelacionados(producto, MAXIMA_PROFUNDIDAD);

        Map<Producto, Double> distancias = DijkstraUtils.calcularCaminos(productos, producto);
        List<Distancia> distanciasOrdenadas = MapaUtils.ordenarMapa(distancias);
        //algoritmo greedy
        //Lo vamos a buscar por distancia, el producto con mas probabilidad de ser comprado es el mas cercano, entonces greedy
        //va a recibir el map de distnacias que esta arriba y una cantidad de recomendaciones n (cuantas le paso al front, 1,2,3,4)
        //Esto me va a devolver un set con los n productos mas cercanos, seria del tipo de dato listado de productos
        //Entonces, recibe "distancias" y n y retorna productos mas cercanos
        return obtenerRecomendacionesGreedy(distanciasOrdenadas, n);
    }

    public Set<Producto> obtenerRecomendacionesGreedy(List<Distancia> distancias, int n){
        Set<Producto> recomendaciones = new HashSet<>();
        Double distanciaMenor = Double.POSITIVE_INFINITY;
        for(Distancia d : distancias){
            if(recomendaciones.size() == n) break;
            if(d.getDistancia() < distanciaMenor){
                distanciaMenor = d.getDistancia();
                recomendaciones.add(d.getProducto());
            }
        }   
        return recomendaciones;
    }


    
}
