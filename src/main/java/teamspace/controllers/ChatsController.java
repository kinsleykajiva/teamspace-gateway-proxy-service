package teamspace.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import teamspace.pojo.JWTDataPojo;
import teamspace.pojo.UserPojo;
import teamspace.utils.Utils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import static teamspace.database.rethinkdb.ChatMessageModel.saveChatMessage;
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



    @RequestMapping(value = "/chat/send-event", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> sendEvent(@RequestBody Map<String, Object> payload) {
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
            //TODO: send event to all users
            // 1. TypingEvent
            // 2. StoppedTypingEvent
            // 3. MessageEvent
            // 4. DeletedMessageEvent
            // 5. EditedMessageEvent
            // 6. PinnedMessageEvent
            // 7. UnpinnedMessageEvent
            // 8. MentionEvent
            // 9. UnreadEvent
            // 10. ReadEvent
            // 11. ReactionEvent
            // 12. UnreactionEvent
            // 13. CallEvent
            // 14. CallEndedEvent
            // 15. CallMissedEvent
            // 16. CallStartedEvent
            // 17. CallTransferredEvent
            // 18. CallUpdatedEvent
            // 19. CallVoicemailEvent
            // 20. CallVoicemailUpdatedEvent
            // 21. CallVoicemailTransferredEvent
            // 22. CallVoicemailTransferredEvent

            var eventType = String.valueOf(payload.get("eventType"));
            var toUserId = String.valueOf(payload.get("toUserId"));
            saveChatMessage( decoded.getUserId() ,toUserId , decoded.getUserId(), eventType ,eventType,decoded.getCompanyId() ,true);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Map.of(
                                    "success", true,
                                    "message", "EventSent"

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
    @RequestMapping(value = "/chat/save-message", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> saveMessage(@RequestBody Map<String, Object> payload) {
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

 System.out.println("payload "+payload);
            var message = String.valueOf(payload.get("message"));
            var messageType = String.valueOf(payload.get("messageType"));
            var toUserId = String.valueOf(payload.get("toUserId"));
            saveChatMessage( decoded.getUserId() ,toUserId , decoded.getUserId(), message ,messageType,decoded.getCompanyId() ,true);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Map.of(
                                    "success", true,
                                    "message", "EventSent"

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
