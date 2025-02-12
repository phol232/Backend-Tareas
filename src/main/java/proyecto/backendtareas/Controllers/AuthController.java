package proyecto.backendtareas.Controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*") // Permite acceso desde el frontend
public class AuthController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request, HttpSession session) {
        String email = request.get("email");
        String password = request.get("password");

        try {
            // Llamada al procedimiento almacenado en MySQL
            List<Map<String, Object>> result = jdbcTemplate.queryForList(
                    "CALL ValidateLogin(?, ?)", email, password
            );

            if (result.isEmpty()) {
                return ResponseEntity.status(401).body(Map.of("message", "Correo no encontrado o contraseña incorrecta"));
            }

            // Obtener el ID del usuario autenticado
            Map<String, Object> userData = result.get(0);
            String userId = (String) userData.get("id"); // Asegúrate de que la columna en la BD se llama "id"

            // Guardar el ID en la sesión
            session.setAttribute("userId", userId);

            // ✅ Respuesta con el mensaje y datos del usuario
            Map<String, Object> response = new HashMap<>(userData);
            response.put("message", "Inicio de sesión exitoso");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate(); // Cerrar sesión
        return ResponseEntity.ok("Sesión cerrada con éxito.");
    }

    @GetMapping("/me")
    public ResponseEntity<?> getUserSession(HttpSession session) {
        String userId = (String) session.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.status(401).body(Map.of("message", "No hay usuario autenticado"));
        }

        return ResponseEntity.ok(Map.of("userId", userId));
    }
}
