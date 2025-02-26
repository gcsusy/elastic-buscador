package gc.unir.actividad2.elastic_buscador.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import gc.unir.actividad2.elastic_buscador.models.Producto;
import gc.unir.actividad2.elastic_buscador.repository.ProductoRepository;
import gc.unir.actividad2.elastic_buscador.services.ProductoService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productos")
@CrossOrigin(origins = "https://actividad1-gctiendav1-gxnbd7y83-gcsusys-projects.vercel.app/")
public class ProductoController {

    @Autowired
    private ProductoService productoService;
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @GetMapping("/all")
    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }

    @PostMapping
    public Producto agregarProducto(@RequestBody Producto producto) {
        return productoService.guardarProducto(producto);
    }

    @GetMapping("/{id}")
    public Optional<Producto> obtenerProducto(@PathVariable String id) {
        return productoService.obtenerProductoPorId(id);
    }

    @GetMapping("/buscar/{nombre}")
    public List<Producto> buscarPorNombre(@PathVariable String nombre) {
        return productoService.buscarPorNombre(nombre);
    }
    
    //Endpoint para buscar productos con full-text
    @GetMapping("/buscar")
    public List<Producto> buscarProductos(@RequestParam String q) throws IOException {
        return productoService.buscarProductos(q);
    }
    
    // Endpoint para obtener sugerencias de busqueda
    @GetMapping("/sugerencias")
    public List<Producto> obtenerSugerencias(@RequestParam String q) throws IOException {
        return productoService.getSugerencias(q);
    }

    /*@DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable String id) {
        productoService.eliminarProducto(id);
    }*/
}

