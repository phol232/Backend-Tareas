package proyecto.backendtareas.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import proyecto.backendtareas.Entity.Category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class CategoryRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Insertar categoría (el ID se genera automáticamente en la BD)
    public void insertarCategoria(String nombre, LocalDateTime fecha) {
        String sql = "CALL InsertarCategoria(?, ?)";
        jdbcTemplate.update(sql, nombre, fecha);
    }

    // Listar todas las categorías
    public List<Category> listarCategorias() {
        String sql = "CALL ListarCategorias()";
        return jdbcTemplate.query(sql, this::mapRowToCategory);
    }

    // ✅ Buscar una única categoría por nombre (retorna null si no existe)
    public Category buscarPorNombre(String nombre) {
        String sql = "CALL BuscarCategoriaPorNombre(?)";

        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{nombre}, this::mapRowToCategory);
        } catch (Exception e) {
            return null; // 🚨 Si no se encuentra, retorna null en vez de lanzar un error.
        }
    }

    // Actualizar categoría
    public void actualizarCategoria(String idCat, String nombre, LocalDateTime fecha) {
        String sql = "CALL ActualizarCategoria(?, ?, ?)";
        jdbcTemplate.update(sql, idCat, nombre, fecha);
    }

    // Eliminar categoría
    public void eliminarCategoria(String idCat) {
        String sql = "CALL EliminarCategoria(?)";
        jdbcTemplate.update(sql, idCat);
    }

    // Método privado para mapear el resultado de la consulta a un objeto Category
    private Category mapRowToCategory(ResultSet rs, int rowNum) throws SQLException {
        Category category = new Category();
        category.setIdCat(rs.getString("idCat"));
        category.setNombre(rs.getString("nombre"));
        category.setFecha(rs.getTimestamp("fecha") != null ? rs.getTimestamp("fecha").toLocalDateTime() : null);
        return category;
    }
}
