package com.reservatucancha.backend.rtc_backend.bootstrap;

import com.reservatucancha.backend.rtc_backend.dto.request.AuthLoginRequest;
import com.reservatucancha.backend.rtc_backend.entity.DBTasker;
import com.reservatucancha.backend.rtc_backend.repository.IDBTaskerRepository;
import com.reservatucancha.backend.rtc_backend.repository.IRoleRepository;
import com.reservatucancha.backend.rtc_backend.repository.IUserRepository;
import com.reservatucancha.backend.rtc_backend.service.implementation.UserDetailServiceImplementation;
import com.reservatucancha.backend.rtc_backend.service.implementation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private IDBTaskerRepository idbTaskerRepository;


    @Override
    public void run(String... args) throws Exception {
        // Lista de scripts SQL
        String[] scripts = {
                "prod_sql/users.sql",
                "prod_sql/sports.sql",
                "prod_sql/surfaces.sql",
                "prod_sql/mapInfo.sql",
                "prod_sql/pitches.sql",
                "prod_sql/images.sql",
                "prod_sql/services.sql",
                "prod_sql/schedules.sql"
        };

        // Ejecutar scripts
        for (String script : scripts) {
            if (shouldExecuteScript(script)) {
                executeSqlScript(script);
                recordScriptExecution(script);
                if (script.equals("prod_sql/users.sql")) {
                    userService.putUserPassword(new AuthLoginRequest("admin@reservatucancha.com", "12345678"));
                    userService.putUserPassword(new AuthLoginRequest("user@reservatucancha.com", "12345678"));
                    userService.putUserPassword(new AuthLoginRequest("operator@reservatucancha.com", "12345678"));
                }
            }
        }
    }
    private boolean shouldExecuteScript(String scriptPath) {
        String scriptName = scriptPath.substring(scriptPath.lastIndexOf('/') + 1);
        Optional<DBTasker> dbTaskerOpt = idbTaskerRepository.findByScriptName(scriptName);
        return dbTaskerOpt.isEmpty() || !dbTaskerOpt.get().getExecuted();
    }

    private void executeSqlScript(String scriptPath) throws Exception {
        Resource resource = new ClassPathResource(scriptPath);
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            String sql = reader.lines().collect(Collectors.joining("\n"));


            String[] statements = sql.split(";");
            for (String statement : statements) {
                // Limpiar espacios y nuevas líneas
                String trimmedStatement = statement.trim();
                if (!trimmedStatement.isEmpty()) {
                    jdbcTemplate.execute(trimmedStatement);
                }
            }
        }
    }
    private void recordScriptExecution(String scriptPath) {
        String scriptName = scriptPath.substring(scriptPath.lastIndexOf('/') + 1);
        DBTasker dbTasker = new DBTasker();
        dbTasker.setScriptName(scriptName);
        dbTasker.setDate(LocalDateTime.now());
        dbTasker.setExecuted(true);
        idbTaskerRepository.save(dbTasker);
    }
}
