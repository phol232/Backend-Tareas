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

    public void insertarCategoria(String nombre, LocalDateTime fecha) {
        categoryRepository.insertarCategoria(nombre, fecha);
    }

    public List<Category> listarCategorias() {
        return categoryRepository.listarCategorias();
    }

    public List<Category> buscarPorNombre(String nombre) {
        return categoryRepository.buscarPorNombre(nombre);
    }

    public void actualizarCategoria(String idCat, String nombre, LocalDateTime fecha) {
        categoryRepository.actualizarCategoria(idCat, nombre, fecha);
    }

    public void eliminarCategoria(String idCat) {
        categoryRepository.eliminarCategoria(idCat);
    }
}
