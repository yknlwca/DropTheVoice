package com.ssafy.a505.global.sse;

import com.ssafy.a505.domain.entity.Member;
import com.ssafy.a505.domain.repository.MemberRepository;
import com.ssafy.a505.global.sse.EmitterRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Optional;

@Service
public class NotificationService {

    private final EmitterRepository emitterRepository;
    private final MemberRepository memberRepository;

    public NotificationService(EmitterRepository emitterRepository, MemberRepository memberRepository) {
        this.emitterRepository = emitterRepository;
        this.memberRepository = memberRepository;
    }

    public SseEmitter subscribe(Long memberId) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        emitterRepository.save(memberId, emitter);

        emitter.onCompletion(() -> emitterRepository.deleteById(memberId));
        emitter.onTimeout(() -> emitterRepository.deleteById(memberId));

        try {
            emitter.send(SseEmitter.event()
//                    .name("INIT")
                    .data("Connected!"));

            Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);
            Member member = optionalMember.orElseThrow(() -> new RuntimeException("Member not found"));

            if (member.isHasNew()){
                emitter.send(SseEmitter.event()
                        .data("hasNew")
                );
                member.setHasNew(false);
            }
        } catch (IOException e) {
            emitterRepository.deleteById(memberId);
        }

        return emitter;
    }

    public void sendNotification(Long memberId, String message) {
        SseEmitter emitter = emitterRepository.get(memberId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
//                        .name("MESSAGE")
                        .data(message));
            } catch (IOException e) {
                emitterRepository.deleteById(memberId);
            }
        }
    }
}
