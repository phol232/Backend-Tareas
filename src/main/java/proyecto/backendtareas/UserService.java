package proyecto.backendtareas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String registerUser(String id, String name, String email, String passwordHash) {
        try {
            jdbcTemplate.update("CALL InsertUser(?, ?, ?, ?)", id, name, email, passwordHash);
            return "Usuario registrado correctamente.";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
