package proyecto.backendtareas;

import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        try {
            // Llamada al procedimiento almacenado en MySQL
            List<Map<String, Object>> result = jdbcTemplate.queryForList(
                    "CALL ValidateLogin(?, ?)", email, password
            );

            if (result.isEmpty()) {
                return ResponseEntity.status(401).body(Map.of("message", "Correo no encontrado"));
            }

            // ✅ Agregar el campo "message" en la respuesta
            Map<String, Object> response = new HashMap<>(result.get(0));
            response.put("message", "Inicio de sesión exitoso");

            return ResponseEntity.ok(response); // Devuelve los datos del usuario + mensaje
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("error", e.getMessage()));
        }
    }
}
