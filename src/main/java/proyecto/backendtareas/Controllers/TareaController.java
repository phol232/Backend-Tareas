package proyecto.backendtareas.Controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proyecto.backendtareas.Entity.Tarea;
import proyecto.backendtareas.Service.TareaService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tareas")
@CrossOrigin("*")
public class TareaController {

    @Autowired
    private TareaService tareaService;

    // ✅ Guardar nueva tarea y devolver la lista actualizada
    @PostMapping
    public ResponseEntity<List<Tarea>> agregarTarea(@RequestBody Map<String, String> request, HttpSession session) {
        String userId = (String) session.getAttribute("userId");

        // 🚨 Verificar que el usuario esté autenticado
        if (userId == null) {
            return ResponseEntity.status(401).build(); // No autorizado
        }

        // 🛠 Extraer los datos del request
        String titulo = request.get("titulo");
        String descripcion = request.get("descripcion");
        String categoriaNombre = request.getOrDefault("categoria", "Sin Categoría");
        String prioridad = request.get("prioridad");
        String estado = request.get("estado");
        LocalDateTime fecha = LocalDateTime.now();

        // 🚀 Guardar tarea en BD
        tareaService.guardarTarea(userId, titulo, descripcion, categoriaNombre, prioridad, estado, fecha);

        // ✅ Devolver la lista de tareas actualizada
        return ResponseEntity.ok(tareaService.listarTareas());
    }

    // ✅ Editar una tarea existente y devolver la lista actualizada
    @PutMapping("/{id}")
    public ResponseEntity<List<Tarea>> editarTarea(@PathVariable String id, @RequestBody Map<String, String> request, HttpSession session) {
        String userId = (String) session.getAttribute("userId");

        // 🚨 Verificar que el usuario esté autenticado
        if (userId == null) {
            return ResponseEntity.status(401).build(); // No autorizado
        }

        // 🛠 Extraer los datos del request
        String titulo = request.get("titulo");
        String descripcion = request.get("descripcion");
        String categoriaNombre = request.getOrDefault("categoria", "Sin Categoría");
        String prioridad = request.get("prioridad");
        String estado = request.get("estado");
        LocalDateTime fecha = LocalDateTime.now();

        // 🚀 Editar tarea en BD
        tareaService.editarTarea(id, titulo, descripcion, categoriaNombre, prioridad, estado, fecha);

        // ✅ Devolver la lista de tareas actualizada
        return ResponseEntity.ok(tareaService.listarTareas());
    }

    // ✅ Eliminar una tarea y devolver la lista actualizada
    @DeleteMapping("/{id}")
    public ResponseEntity<List<Tarea>> eliminarTarea(@PathVariable String id, HttpSession session) {
        String userId = (String) session.getAttribute("userId");

        // 🚨 Verificar que el usuario esté autenticado
        if (userId == null) {
            return ResponseEntity.status(401).build(); // No autorizado
        }

        // 🚀 Eliminar tarea
        tareaService.eliminarTarea(id);

        // ✅ Devolver la lista de tareas actualizada
        return ResponseEntity.ok(tareaService.listarTareas());
    }


    @GetMapping
    public ResponseEntity<List<Tarea>> obtenerTareas() {
        return ResponseEntity.ok(tareaService.listarTareas());
    }
}
