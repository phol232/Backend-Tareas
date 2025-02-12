package proyecto.backendtareas.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proyecto.backendtareas.Entity.Tarea;
import proyecto.backendtareas.Repository.TareaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TareaService {

    @Autowired
    private TareaRepository tareaRepository;

    // ✅ Guardar nueva tarea
    public void guardarTarea(String userId, String titulo, String descripcion, String categoriaNombre, String prioridad, String estado, LocalDateTime fecha) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID del usuario no puede estar vacío.");
        }
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío.");
        }
        if (estado == null || estado.trim().isEmpty()) {
            throw new IllegalArgumentException("El estado no puede estar vacío.");
        }
        if (fecha == null) {
            fecha = LocalDateTime.now();
        }

        tareaRepository.guardarTarea(userId, titulo, descripcion, categoriaNombre, prioridad, estado, fecha);
    }

    // ✅ Editar tarea existente
    public boolean editarTarea(String idTarea, String titulo, String descripcion, String categoriaNombre, String prioridad, String estado, LocalDateTime fecha) {
        if (idTarea == null || idTarea.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID de la tarea no puede estar vacío.");
        }
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío.");
        }
        if (estado == null || estado.trim().isEmpty()) {
            throw new IllegalArgumentException("El estado no puede estar vacío.");
        }
        if (fecha == null) {
            fecha = LocalDateTime.now();
        }

        return tareaRepository.editarTarea(idTarea, titulo, descripcion, categoriaNombre, prioridad, estado, fecha) > 0;
    }

    // ✅ Eliminar tarea por ID
    public boolean eliminarTarea(String idTarea) {
        if (idTarea == null || idTarea.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID de la tarea no puede estar vacío.");
        }
        return tareaRepository.eliminarTarea(idTarea) > 0;
    }

    // ✅ Listar tareas con validación de categoría
    public List<Tarea> listarTareas() {
        return tareaRepository.listarTareas().stream()
                .peek(tarea -> {
                    if (tarea.getNombreCategoria() == null || tarea.getNombreCategoria().trim().isEmpty()) {
                        tarea.setNombreCategoria("Sin Categoría");
                    }
                })
                .collect(Collectors.toList());
    }
}
