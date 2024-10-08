package com.dtv.domain.controller;

import com.dtv.domain.dto.response.VoiceResponseDTO;
import com.dtv.domain.entity.Member;
import com.dtv.domain.entity.Voice;
import com.dtv.domain.repository.VoiceRepository;
import com.dtv.domain.service.SpreadService;
import com.dtv.domain.service.VoiceService;
import com.dtv.global.execption.CustomException;
import com.dtv.global.execption.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api-voice")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class VoiceController {

    private final VoiceService voiceService;
    private final VoiceRepository voiceRepository;
    private final SpreadService spreadService;


    @GetMapping("/search/{page}/{size}")
    public ResponseEntity<?> searchVoices(@RequestParam String keyword,
                                          @RequestParam String sort,
                                          @PathVariable int page,
                                          @PathVariable int size) {
        Page<VoiceResponseDTO> responseDTOS = voiceService.searchVoices(keyword, page - 1, size, sort);
        return new ResponseEntity<>(responseDTOS, HttpStatus.OK);
    }

    @GetMapping("/best-heart-voice/{page}/{size}")
    public ResponseEntity<?> getHeartsVoices(@PathVariable("page") int page, @PathVariable("size") int size) {
        List<Voice> result = voiceService.getVoiceOrderByHeartCountDesc(page, size);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping("/best-voice/{voice_id}")
    public ResponseEntity<?> getVoiceDetail(@PathVariable("voice_id") Long voiceId, @AuthenticationPrincipal Member member) {
        Voice voice = voiceService.getVoiceById(voiceId);
        VoiceResponseDTO result = VoiceResponseDTO.fromEntity(voice, member);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/all-voices")
    public ResponseEntity<?> getAllVoices() {
        return new ResponseEntity<>(voiceService.getAllVoice(), HttpStatus.OK);
    }


    @PostMapping("{voiceId}/like")
    public ResponseEntity<?> toggleLike(@PathVariable("voiceId") Long voiceId, @RequestParam(value = "latitude", required = false) Double latitude, @RequestParam(value = "longitude", required = false) Double longitude, @AuthenticationPrincipal Member member) {
        log.info("latitude: {} longitude: {}", latitude, longitude);
        if (member == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
        }

        boolean isLiked = voiceService.toggleLike(voiceId, member);
        Voice voice = voiceRepository.findById(voiceId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_VOICE));

        Long memberId = member.getMemberId();

        spreadService.spreadLogic(longitude, latitude, voiceId, memberId);

        Map<String, Object> response = new HashMap<>();
        response.put("isLiked", isLiked);
        response.put("likeCount", voice.getHeartCount());  // 현재의 heartCount 반환
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{voiceId}/pick")
    public ResponseEntity<?> togglePick(@PathVariable("voiceId") Long voiceId, @AuthenticationPrincipal Member member) {
        if (member == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
        }
        boolean isPicked = voiceService.togglePick(voiceId, member);
        voiceRepository.findById(voiceId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_VOICE));

        Map<String, Object> response = new HashMap<>();
        response.put("isPicked", isPicked);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/nearby")
    public ResponseEntity<?> getNearbyVoices(@RequestParam("latitude") double latitude,
                                             @RequestParam("longitude") double longitude,
                                             @RequestParam("radius") double radius,
                                             @AuthenticationPrincipal Member member) {
        List<VoiceResponseDTO> voices = voiceService.getNearbyVoices(latitude, longitude, radius, member);
        return ResponseEntity.ok(voices);
    }
}
