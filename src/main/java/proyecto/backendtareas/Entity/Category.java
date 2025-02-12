package proyecto.backendtareas.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

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
}
