package proyecto.backendtareas.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import proyecto.backendtareas.Entity.Tarea;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class TareaRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // ✅ Guardar nueva tarea
    public void guardarTarea(String userId, String titulo, String descripcion, String categoriaNombre, String prioridad, String estado, LocalDateTime fecha) {
        String sql = "CALL GuardarTarea(?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, userId, titulo, descripcion, categoriaNombre, prioridad, estado, fecha);
    }


    public int editarTarea(String idTarea, String titulo, String descripcion, String categoriaNombre, String prioridad, String estado, LocalDateTime fecha) {
        String sql = "CALL EditarTarea(?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, idTarea, titulo, descripcion, categoriaNombre, prioridad, estado, fecha);
    }

    public int eliminarTarea(String idTarea) {
        String sql = "CALL EliminarTarea(?)";
        return jdbcTemplate.update(sql, idTarea); // 🔹 Retorna cantidad de filas eliminadas
    }


    // ✅ Listar todas las tareas con ID y nombre de la categoría
    public List<Tarea> listarTareas() {
        String sql = "CALL ListarTareas()";
        return jdbcTemplate.query(sql, this::mapRowToTarea);
    }

    private Tarea mapRowToTarea(ResultSet rs, int rowNum) throws SQLException {
        Tarea tarea = new Tarea();
        tarea.setIdTarea(rs.getString("idTarea"));
        tarea.setTitulo(rs.getString("titulo"));
        tarea.setDescripcion(rs.getString("descripcion"));
        tarea.setPrioridad(rs.getString("prioridad"));
        tarea.setEstado(rs.getString("estado"));
        tarea.setFecha(rs.getTimestamp("fecha") != null ? rs.getTimestamp("fecha").toLocalDateTime() : null);


        tarea.setNombreCategoria(rs.getString("categoria"));

        return tarea;
    }

}
