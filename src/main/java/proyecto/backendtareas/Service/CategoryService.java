package proyecto.backendtareas.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proyecto.backendtareas.Entity.Category;
import proyecto.backendtareas.Repository.CategoryRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // ✅ Insertar categoría (validando si ya existe antes de insertar)
    public Category insertarCategoria(String nombre, LocalDateTime fecha) {
        Category categoriaExistente = categoryRepository.buscarPorNombre(nombre);

        if (categoriaExistente != null) {
            return categoriaExistente; // 📌 Si la categoría ya existe, la devuelve.
        }

        LocalDateTime fechaActual = LocalDateTime.now();
        categoryRepository.insertarCategoria(nombre, fechaActual);
        return categoryRepository.buscarPorNombre(nombre);
    }

    // ✅ Listar todas las categorías
    public List<Category> listarCategorias() {
        return categoryRepository.listarCategorias();
    }

    // ✅ Buscar una sola categoría por nombre (devuelve null si no existe)
    public Category buscarPorNombre(String nombre) {
        return categoryRepository.buscarPorNombre(nombre);
    }

    // ✅ Actualizar categoría (verifica si existe antes de actualizar)
    public boolean actualizarCategoria(String idCat, String nombre) {
        Category categoria = categoryRepository.buscarPorNombre(nombre);

        if (categoria == null) {
            return false; // 📌 Retorna false si la categoría no existe.
        }

        LocalDateTime fechaActual = LocalDateTime.now();
        categoryRepository.actualizarCategoria(idCat, nombre, fechaActual);
        return true; // 📌 Retorna true si la actualización fue exitosa.
    }

    // ✅ Eliminar categoría (verifica si existe antes de eliminar)
    public boolean eliminarCategoria(String idCat) {
        Category categoria = categoryRepository.buscarPorNombre(idCat);

        if (categoria == null) {
            return false; // 📌 Retorna false si la categoría no existe.
        }

        categoryRepository.eliminarCategoria(idCat);
        return true; // 📌 Retorna true si la eliminación fue exitosa.
    }
}
