package gc.unir.actividad2.elastic_buscador.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.core.suggest.Completion;

import lombok.Data;

@Data
@Document(indexName = "productos")
public class Producto {
    @Id
    private String id;
    
    @Field(type = FieldType.Text)
    private String nombre;
    
    @Field(type = FieldType.Text)
    private String coleccion;
    
    @Field(type = FieldType.Float)
    private Double precio;
    
    @Field(type = FieldType.Integer)
    private int idimage;
	
    @Field(type = FieldType.Match_Only_Text)
    private Completion nombreSugerencia;
    
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	public String getColeccion() {
		return coleccion;
	}
	public void setColeccion(String coleccion) {
		this.coleccion = coleccion;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getIdimage() {
		return idimage;
	}
	public void setIdimage(int idimage) {
		this.idimage = idimage;
	}	
}

