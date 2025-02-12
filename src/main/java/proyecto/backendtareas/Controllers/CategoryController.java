package proyecto.backendtareas.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proyecto.backendtareas.Entity.Category;
import proyecto.backendtareas.Service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin("*")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // ✅ Insertar nueva categoría (verifica si ya existe antes de insertarla)
    @PostMapping
    public ResponseEntity<String> crearCategoria(@RequestBody Category categoria) {
        if (categoria.getNombre() == null || categoria.getNombre().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("❌ Error: El nombre de la categoría no puede estar vacío.");
        }

        Category nuevaCategoria = categoryService.insertarCategoria(categoria.getNombre(), categoria.getFecha());

        if (nuevaCategoria != null) {
            return ResponseEntity.ok("✅ Categoría '" + nuevaCategoria.getNombre() + "' creada exitosamente.");
        } else {
            return ResponseEntity.badRequest().body("❌ Error al crear la categoría.");
        }
    }

    // ✅ Listar todas las categorías
    @GetMapping
    public ResponseEntity<List<Category>> obtenerCategorias() {
        List<Category> categorias = categoryService.listarCategorias();
        if (categorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categorias);
    }

    // ✅ Buscar categoría por nombre (devuelve una sola categoría en lugar de lista)
    @GetMapping("/buscar")
    public ResponseEntity<Category> buscarPorNombre(@RequestParam String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Category categoria = categoryService.buscarPorNombre(nombre);
        if (categoria != null) {
            return ResponseEntity.ok(categoria);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ✅ Actualizar categoría (verifica si existe antes de actualizar)
    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarCategoria(@PathVariable String id, @RequestBody Category categoria) {
        if (categoria.getNombre() == null || categoria.getNombre().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("❌ Error: El nombre de la categoría no puede estar vacío.");
        }

        boolean actualizado = categoryService.actualizarCategoria(id, categoria.getNombre());
        if (actualizado) {
            return ResponseEntity.ok("✅ Categoría actualizada correctamente.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ✅ Eliminar categoría (verifica si existe antes de eliminar)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCategoria(@PathVariable String id) {
        boolean eliminado = categoryService.eliminarCategoria(id);
        if (eliminado) {
            return ResponseEntity.ok("✅ Categoría eliminada correctamente.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
