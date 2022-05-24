package teamspace.controllers;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.uuid.Generators;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import teamspace.pojo.CompanyPojo;
import teamspace.pojo.UserPojo;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static teamspace.database.rethinkdb.CompanyModel.*;
import static teamspace.database.rethinkdb.UserModel.*;

@RestController
@Slf4j
@RequestMapping("/auth/api/v1/users")
public class UsersController {

    @Value("${app.secrete}")
    private String SecreteKey;
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> login(@RequestBody Map<String, Object> payload) {


        if (!payload.containsKey("email") || !payload.containsKey("password")) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Map.of(
                                    "success", false,
                                    "message", "Missing Fields"
                            )
                    );
        }
        var email = String.valueOf(payload.get("email"));

        if (!checkIfUserExists(email)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                                    "success", false,
                                    "message", "Wrong Details"
                            )
                    );
        }

        UserPojo user = getByEmailUser(email);

        CompanyPojo company = getCompanyById(user.getCompanyClientId());
        if (company == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                                    "success", false,
                                    "message", "Company Account Deleted"
                            )
                    );
        }

        if (!passwordEncoder.matches(String.valueOf(payload.get("password")), user.getPassword())) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Map.of(
                                    "success", false,
                                    "message", "Failed to login user,please check credentials again"
                            )
                    );
        }
        Algorithm algorithm = Algorithm.HMAC256(SecreteKey);

        Map<String, Object> payloadClaims = new HashMap<>();
        payloadClaims.put("role", 1);
        payloadClaims.put("companyId", user.getCompanyClientId());
        payloadClaims.put("fullName", user.getFullName());

        String accessToken = JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + 60L * 60 * 1000 * 24 * 30)) /*30 days */
                .withIssuer(request.getRequestURI().toString())
                .withPayload(payloadClaims)
                .withClaim("userId", user.getId_())


                .sign(algorithm);
        String refreshToken = JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + 60L * 60 * 1000 * 24 * 30)) /*30 days */
                .withIssuer(request.getRequestURI())
                .sign(algorithm);

        var dataObject = Map.of(
                "fullName", user.getFullName() == null ? "" : user.getFullName(),
                "profileImage", user.getProfilePictureUrl() == null ? "null" : user.getProfilePictureUrl(),
                "userId", user.getId_(),
                "role", 1,
                "email", user.getEmail(),
                "companyId", user.getCompanyClientId(),
                "token", Map.of(
                        "accessToken", accessToken,
                        "refreshToken", refreshToken
                )

        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of(
                        "success", true,
                        "message", "User Logged in",
                        "data", dataObject)
                );
    }


    @RequestMapping(value = "/register-company", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> registerCompanyUser(@RequestBody Map<String, Object> payload) throws IOException {
        UUID uuid = Generators.randomBasedGenerator().generate();
        UUID uuid2 = Generators.timeBasedGenerator().generate();
        UUID activationCode = Generators.timeBasedGenerator().generate();


        if (!payload.containsKey("fullName") || !payload.containsKey("emailAddress") || !payload.containsKey("password")) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                                    "success", false,
                                    "message", "Missing Fields"
                            )
                    );
        }

        if (checkIfUserExists(String.valueOf(payload.get("emailAddress")))) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Map.of(
                                    "success", false,
                                    "message", "Duplicate Email details , please Login"
                            )
                    );
        }
        if (checkIfCompanyExists(String.valueOf(payload.get("companyName")))) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Map.of(
                                    "success", false,
                                    "message", "Duplicate Company Name details , please Login"
                            )
                    );
        }

        var password = passwordEncoder.encode(String.valueOf(payload.get("password")));

        saveCompany(String.valueOf(payload.get("companyName")), String.valueOf(payload.get("emailAddress")));
        var company = getCompanyByName(String.valueOf(payload.get("companyName")));

        saveUser(String.valueOf(payload.get("emailAddress")), password, String.valueOf(payload.get("fullName")), null, true, true, company.getId_());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of(
                        "success", true,
                        "message", "Registered new company and registered as a new user .")
                );

    }


}
