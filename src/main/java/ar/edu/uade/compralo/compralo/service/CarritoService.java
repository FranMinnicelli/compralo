package ar.edu.uade.compralo.compralo.service;

import ar.edu.uade.compralo.compralo.model.entity.Producto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class CarritoService {
    private final RecomendacionService recomendacionService;
    
    /**
     * Agrega un producto a la lista de descarte.
     */
    public void descartar(Producto producto) {
        if (producto != null) {
            recomendacionService.descartarRecomendacion(producto);
        }
    }


}
