package proyecto.backendtareas.Controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proyecto.backendtareas.Entity.Category;
import proyecto.backendtareas.Entity.Tarea;
import proyecto.backendtareas.Service.CategoryService;
import proyecto.backendtareas.Service.TareaService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tareas")
@CrossOrigin("*")
public class TareaController {

    @Autowired
    private TareaService tareaService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<String> agregarTarea(@RequestBody Tarea tarea, HttpSession session) {
        String userId = (String) session.getAttribute("userId");

        // 🚨 Verificar autenticación
        if (userId == null) {
            return ResponseEntity.status(401).body("Usuario no autenticado");
        }

        // 📌 Asegurar que la fecha se establezca si no se envió
        if (tarea.getFecha() == null) {
            tarea.setFecha(LocalDateTime.now());
        }

        // 📌 Verificar si la categoría está presente y válida
        if (tarea.getCategoria() == null || tarea.getCategoria().getNombre() == null || tarea.getCategoria().getNombre().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("❌ Error: La categoría no está bien formada");
        }

        Category categoria = categoryService.buscarPorNombre(tarea.getCategoria().getNombre());

        if (categoria == null) {
            return ResponseEntity.badRequest().body("❌ Error: La categoría '" + tarea.getCategoria().getNombre() + "' no existe.");
        }

        // ✅ Asociar la categoría encontrada a la tarea
        tarea.setCategoria(categoria);

        // 🚀 Guardar tarea en BD
        tareaService.guardarTarea(userId, tarea.getTitulo(), tarea.getDescripcion(),
                categoria.getNombre(), tarea.getPrioridad(),
                tarea.getEstado(), tarea.getFecha());

        // ✅ Devolver mensaje de éxito
        return ResponseEntity.ok("✅ Tarea creada correctamente.");
    }


    // ✅ Editar tarea existente
    @PutMapping("/{id}")
    public ResponseEntity<List<Tarea>> editarTarea(@PathVariable String id, @RequestBody Tarea tarea, HttpSession session) {
        String userId = (String) session.getAttribute("userId");

        // 🚨 Verificar autenticación del usuario
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }

        // 🚨 Validar que la tarea a actualizar tiene título y estado
        if (tarea.getTitulo() == null || tarea.getTitulo().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(null);  // ❌ Error si el título está vacío
        }
        if (tarea.getEstado() == null || tarea.getEstado().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(null);  // ❌ Error si el estado está vacío
        }

        // 📌 Si no se envió una fecha, usar la fecha actual
        if (tarea.getFecha() == null) {
            tarea.setFecha(LocalDateTime.now());
        }

        // 📌 Verificar si la categoría está presente y válida
        if (tarea.getCategoria() == null || tarea.getCategoria().getNombre() == null || tarea.getCategoria().getNombre().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(null);  // ❌ Error si la categoría no está bien formada
        }

        // 📌 Buscar la categoría en la BD o crear una nueva si no existe
        Category categoria = categoryService.buscarPorNombre(tarea.getCategoria().getNombre());

        if (categoria == null) {
            // ✅ Si la categoría no existe, crearla y guardarla en la BD
            categoria = new Category();
            categoria.setNombre(tarea.getCategoria().getNombre());
            categoria.setFecha(LocalDateTime.now());
            categoryService.insertarCategoria(categoria.getNombre(), categoria.getFecha()); // 🚀 Guardar en BD
        }

        // ✅ Asociar la categoría encontrada/creada a la tarea
        tarea.setCategoria(categoria);

        // 📌 Llamar al servicio para actualizar la tarea
        boolean actualizada = tareaService.editarTarea(
                id, tarea.getTitulo(), tarea.getDescripcion(),
                categoria.getNombre(), // ✅ Pasamos `String` en lugar de `Category`
                tarea.getPrioridad(), tarea.getEstado(), tarea.getFecha()
        );

        if (actualizada) {
            return ResponseEntity.ok(tareaService.listarTareas()); // ✅ Devuelve la lista actualizada si la tarea fue editada
        } else {
            return ResponseEntity.notFound().build(); // ❌ Devuelve 404 si la tarea no existe
        }
    }



    // ✅ Eliminar tarea y devolver la lista actualizada
    @DeleteMapping("/{id}")
    public ResponseEntity<List<Tarea>> eliminarTarea(@PathVariable String id, HttpSession session) {
        String userId = (String) session.getAttribute("userId");

        // 🚨 Verificar autenticación
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }

        // 🚀 Intentar eliminar la tarea
        boolean eliminada = tareaService.eliminarTarea(id);

        if (eliminada) {
            return ResponseEntity.ok(tareaService.listarTareas());  // ✅ Devuelve la lista actualizada si la tarea fue eliminada
        } else {
            return ResponseEntity.notFound().build();  // ❌ Devuelve 404 si la tarea no existe
        }
    }


    @GetMapping
    public ResponseEntity<List<Tarea>> obtenerTareas() {
        return ResponseEntity.ok(tareaService.listarTareas());
    }
}
