package proyecto.backendtareas.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "Tareas")
@Getter
@Setter
public class Tarea {

    @Id
    @Column(name = "idTarea", length = 12, nullable = false)
    private String idTarea;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_Tarea_Usuario"))
    private User user;

    @Column(name = "titulo", length = 12, nullable = false)
    private String titulo;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "catId", referencedColumnName = "idCat", foreignKey = @ForeignKey(name = "FK_Tarea_Categoria"), nullable = true)
    private Category categoria;

    @Column(name = "prioridad", length = 13, nullable = false)
    private String prioridad;

    @Column(name = "estado", length = 12, nullable = false)
    private String estado;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    // 🔥 Campo transitorio para devolver el nombre de la categoría en JSON
    @Transient
    private String nombreCategoria;

    @PostLoad
    private void asignarNombreCategoria() {
        this.nombreCategoria = (categoria != null) ? categoria.getNombre() : "Sin Categoría";
    }
}
