package teamspace.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import teamspace.pojo.JWTDataPojo;
import teamspace.pojo.UserPojo;
import teamspace.utils.Utils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import static teamspace.database.rethinkdb.UserModel.getAllUsersByCompanyId;

@RestController
@Slf4j
@RequestMapping("/auth/api/v1/users/chats")
public class ChatsController {


    @Value("${app.secrete}")
    private String SecreteKey;

    @Autowired
    private HttpServletRequest request;


    @RequestMapping(value = "/company-all-users", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getRoles() {
        JWTDataPojo decoded;

        try {
            decoded = Utils.tokenDecoder(request, SecreteKey);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Map.of(
                                    "success", false,
                                    "access", false,
                                    "message", "Failed to access resource "
                            )
                    );
        }
        try {
            List<UserPojo> usersList = getAllUsersByCompanyId(decoded.getCompanyId());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Map.of(
                                    "success", true,
                                    "data", Map.of(
                                            "users", usersList
                                    )
                            )
                    );


        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Map.of(
                                    "success", false,
                                    "message", "Failed to access resource ",
                                    "errors", e.getMessage()
                            )
                    );
        }
    }

}
