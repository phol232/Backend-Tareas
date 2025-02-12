package proyecto.backendtareas.Controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            jdbcTemplate.update("CALL InsertUser(?, ?, ?, ?)",
                    request.get("id"),
                    request.get("name"),
                    request.get("email"),
                    request.get("password"));

            response.put("success", true);
            response.put("message", "Usuario registrado exitosamente.");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllUsers() {
        List<Map<String, Object>> users = jdbcTemplate.queryForList("SELECT * FROM users");
        return ResponseEntity.ok(users);
    }
}
