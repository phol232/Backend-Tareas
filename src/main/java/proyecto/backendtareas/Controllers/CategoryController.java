package proyecto.backendtareas.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proyecto.backendtareas.Entity.Category;
import proyecto.backendtareas.Service.CategoryService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin("*")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Insertar nueva categoría
    @PostMapping
    public ResponseEntity<String> crearCategoria(@RequestBody Category categoria) {
        categoryService.insertarCategoria(categoria.getNombre(), categoria.getFecha());
        return ResponseEntity.ok("Categoría insertada correctamente.");
    }

    // Listar todas las categorías
    @GetMapping
    public ResponseEntity<List<Category>> obtenerCategorias() {
        return ResponseEntity.ok(categoryService.listarCategorias());
    }

    // Buscar categoría por nombre
    @GetMapping("/buscar")
    public ResponseEntity<List<Category>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(categoryService.buscarPorNombre(nombre));
    }

    // Actualizar categoría
    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarCategoria(@PathVariable String id, @RequestBody Category categoria) {
        categoryService.actualizarCategoria(id, categoria.getNombre(), categoria.getFecha());
        return ResponseEntity.ok("Categoría actualizada correctamente.");
    }

    // Eliminar categoría
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCategoria(@PathVariable String id) {
        categoryService.eliminarCategoria(id);
        return ResponseEntity.ok("Categoría eliminada correctamente.");
    }
}
