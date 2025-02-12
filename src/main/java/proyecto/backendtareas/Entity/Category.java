package proyecto.backendtareas.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.ServiceLoader;

@Entity
@Table(name = "Categorias")
@Getter
@Setter
public class Category {

    @Id
    @Column(name = "idCat", length = 12, nullable = false)
    private String idCat;

    @Column(name = "nombre", length = 20, nullable = false)
    private String nombre;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    // ✅ Constructor vacío requerido por Jackson
    public Category() {}

    // ✅ Constructor con `@JsonCreator` que permite convertir un `String` en un objeto `Category`
    @JsonCreator
    public Category(@JsonProperty("nombre") String nombre) {
        this.nombre = nombre;
    }

}
