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
        tareaRepository.guardarTarea(userId, titulo, descripcion, categoriaNombre, prioridad, estado, fecha);
    }

    // ✅ Editar tarea existente
    public void editarTarea(String idTarea, String titulo, String descripcion, String categoriaNombre, String prioridad, String estado, LocalDateTime fecha) {
        tareaRepository.editarTarea(idTarea, titulo, descripcion, categoriaNombre, prioridad, estado, fecha);
    }

    // ✅ Eliminar tarea por ID
    public void eliminarTarea(String idTarea) {
        tareaRepository.eliminarTarea(idTarea);
    }

    // ✅ Listar tareas con validación de categoría
    public List<Tarea> listarTareas() {
        return tareaRepository.listarTareas().stream()
                .peek(tarea -> {
                    if (tarea.getNombreCategoria() == null || tarea.getNombreCategoria().isEmpty()) {
                        tarea.setNombreCategoria("Sin Categoría");
                    }
                })
                .collect(Collectors.toList());
    }
}
