package ar.edu.uade.compralo.compralo.service;

import ar.edu.uade.compralo.compralo.model.entity.Producto;
import ar.edu.uade.compralo.compralo.utils.DPUtils;
import ar.edu.uade.compralo.compralo.utils.DijkstraUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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

        DPUtils dp = new DPUtils(distancias, n);
        
        return dp.nRecomendaciones();
    }
}