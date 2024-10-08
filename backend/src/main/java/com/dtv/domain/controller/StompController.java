package com.dtv.domain.controller;

import com.dtv.domain.entity.Coordinate;
import com.dtv.domain.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;


@RestController
@RequiredArgsConstructor
@RequestMapping("/ws")
@Slf4j
public class StompController {

    private final RedisService redisService;

    /**
     * @param coordinate
     *
     * 매 10초마다 프론트엔드에서 호출되는 메서드.
     * 사용자 위치정보를 계속해서 업데이트한다
     */
    @MessageMapping(value = "/position")
    public void message(@Header("simpSessionId") String sessionId, @Payload Coordinate coordinate) {
        redisService.addSessionId(sessionId, coordinate.getName());
        redisService.addSessionIdV2(coordinate.getName());

        log.info("Member Info. sessionId: {}, memberId: {}, X: {}, y: {}", sessionId, coordinate.getName(), coordinate.getX(), coordinate.getY());

        //레디스에 위경도 좌표와 세션ID를 포함해서 저장하자. -> 위경도 좌표와 memberId를 저장하도록 변경
        redisService.addLocation(RedisService.MEMBER_KEY, RedisService.MEMBER_TIME_KEY, Long.valueOf(coordinate.getName()), coordinate.getX(), coordinate.getY(), 1);
    }

    //클라이언트가 사전에 전달받은 상대 세션ID 리스트를 통해 mySessionId와 otherSessionId를 명시함으로써
    //WebRTC연결을 특정할 수 있다.
    @MessageMapping("/peer/offer/{mySessionId}/{otherSessionId}")
    @SendTo("/topic/peer/offer/{otherSessionId}")
    public Object PeerHandleOffer(@Payload String offer, @DestinationVariable(value = "mySessionId") String mySessionId,
                                  @DestinationVariable(value = "otherSessionId") String otherSessionId) {
        log.info("[OFFER] {} : {}", mySessionId+" : "+otherSessionId, offer);
        return offer;
    }

    @MessageMapping("/peer/answer/{mySessionId}/{otherSessionId}")
    @SendTo("/topic/peer/answer/{otherSessionId}")
    public String PeerHandleAnswer(@Payload String answer, @DestinationVariable(value = "mySessionId") String mySessionId,
                                   @DestinationVariable(value = "otherSessionId") String otherSessionId) {
        log.info("[ANSWER] {} : {}", mySessionId+" : "+otherSessionId, answer);
        return answer;
    }

    @MessageMapping("/peer/iceCandidate/{mySessionId}/{otherSessionId}")
    @SendTo("/topic/peer/iceCandidate/{otherSessionId}")
    public String PeerHandleIceCandidate(@Payload String candidate, @DestinationVariable(value = "mySessionId") String mySessionId,
                                         @DestinationVariable(value = "otherSessionId") String otherSessionId) {
        log.info("[ICECANDIDATE] {} : {}", mySessionId+" : "+otherSessionId, candidate);

        return candidate;
    }

    //웹소켓 세션이 종료되었을때 해당 세션 종료에 대한 후처리 기능이 적용되어야함
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        String userId = redisService.removeSessionIdAndGetUserId(sessionId);
        redisService.removeSessionIdAndGetUserIdV2(userId);
    }
}
