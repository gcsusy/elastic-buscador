package gc.unir.actividad2.elastic_buscador.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import gc.unir.actividad2.elastic_buscador.models.Producto;

import java.util.List;

public interface ProductoRepository extends ElasticsearchRepository<Producto, String> {
    List<Producto> findByNombre(String nombre);
    List<Producto> findAll();
}

