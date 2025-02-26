package gc.unir.actividad2.elastic_buscador.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import gc.unir.actividad2.elastic_buscador.models.Producto;
import gc.unir.actividad2.elastic_buscador.repository.ProductoRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoService {

	private final ElasticsearchClient elasticsearchClient;
	
    @Autowired
    private ProductoRepository productoRepository;
    

    public ProductoService(ElasticsearchClient elasticsearchClient, ProductoRepository productoRepository) {
        this.elasticsearchClient = elasticsearchClient;
        this.productoRepository = productoRepository;
    }

    
    //Búsqueda Full-Text en nombre y descripción
    public List<Producto> buscarProductos(String query) throws IOException {
        SearchResponse<Producto> response = elasticsearchClient.search(s -> s
            .index("productos")
            .query(q -> q
                .multiMatch(m -> m
                    .fields("nombre", "coleccion")
                    .query(query)
                )
            ), Producto.class
        );

        return response.hits().hits().stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }

   // Método para obtener sugerencias de corrección
    public List<Producto> getSugerencias(String queryText) throws IOException {
    	final Query query = new Query.Builder().term(t->t.field("coleccion").value(queryText)) .build();
        final Query bool_query = new Query.Builder().bool(t -> t.must(query)).build();
        List<Producto> productos = new ArrayList<Producto>();
            final SearchResponse<Producto> result = elasticsearchClient.search(s -> s.query(bool_query).index("productos").size(100), Producto.class);
            if (!result.hits().hits().isEmpty()) {
                for (final Hit<Producto> data_result : result.hits().hits()) {
                    Producto producto = data_result.source();
                    productos.add(producto);
                }
            }
        return productos; 
    }
    
    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public Optional<Producto> obtenerProductoPorId(String id) {
        return productoRepository.findById(id);
    }

    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombre(nombre);
    }

    public void eliminarProducto(String id) {
        productoRepository.deleteById(id);
    }
}


