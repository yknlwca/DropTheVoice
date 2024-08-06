package com.ssafy.a505.global;

import com.ssafy.a505.domain.entity.*;
import com.ssafy.a505.domain.repository.HeartRepository;
import com.ssafy.a505.domain.repository.MemberRepository;
import com.ssafy.a505.domain.repository.SpreadRepository;
import com.ssafy.a505.domain.repository.VoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class DataLoader {

    private final MemberRepository memberRepository;
    private final VoiceRepository voiceRepository;

    @Bean
    public CommandLineRunner loadData(HeartRepository heartRepository, SpreadRepository spreadRepository) {
        return args -> {
            // 멤버 데이터 생성 및 저장
            Member member1 = Member.builder()
                    .memberId(1L)
                    .memberEmail("user1@example.com")
                    .memberName("김병관")
                    .memberPassword("password1")
                    .profileImgUrl("")
                    .remainChangeCount(5)
                    .totalSpreadCount(10)
                    .totalUploadCount(15)
                    .build();
            Member member2 = Member.builder()
                    .memberId(2L)
                    .memberEmail("user2@example.com")
                    .memberName("User2")
                    .memberPassword("password2")
                    .profileImgUrl("")
                    .remainChangeCount(3)
                    .totalSpreadCount(8)
                    .totalUploadCount(12)
                    .build();
            memberRepository.save(member1);
            memberRepository.save(member2);

            // 보이스 데이터 생성 및 저장
            Voice voice1 = Voice.builder()
                    .voiceId(1L)
                    .member(member1)
                    .listenCount(200L)
                    .title("1111.프로님들 10층 욕심 이유 찾았다")
                    .imageUrl("https://png.pngtree.com/thumb_back/fw800/background/20231008/pngtree-d-render-of-a-golden-tooth-exploring-dental-and-medical-concepts-image_13574408.png")
                    .originalName("voice1.mp3")
                    .savePath("/voices/voice1.mp3")
                    .saveFolder("/voices/")
                    .latitude(37.5665)
                    .longitude(126.9780)
                    .dateTime(LocalDateTime.now())
                    .voiceType(VoiceType.Processed)
                    .build();
            Voice voice2 = Voice.builder()
                    .voiceId(2L)
                    .member(member2)
                    .listenCount(300L)
                    .title("2222.재용이햄 애플 런칭행사 줄서있는거 봄")
                    .imageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRXobLcA4-7fiJkSmqBaQnd2QL8gY8EFIx4pQ&s")
                    .originalName("voice2.mp3")
                    .savePath("/voices/voice2.mp3")
                    .saveFolder("/voices/")
                    .latitude(37.7749)
                    .longitude(-122.4194)
                    .dateTime(LocalDateTime.now())
                    .voiceType(VoiceType.NormalVoice)
                    .build();
            Voice voice3 = Voice.builder()
                    .voiceId(3L)
                    .member(member1)
                    .listenCount(200L)
                    .title("3333.프로님들 10층 욕심 이유 찾았다")
                    .imageUrl("https://png.pngtree.com/thumb_back/fw800/background/20231008/pngtree-d-render-of-a-golden-tooth-exploring-dental-and-medical-concepts-image_13574408.png")
                    .originalName("voice1.mp3")
                    .savePath("/voices/voice1.mp3")
                    .saveFolder("/voices/")
                    .latitude(37.5665)
                    .longitude(126.9780)
                    .dateTime(LocalDateTime.now())
                    .voiceType(VoiceType.Processed)
                    .build();
            Voice voice4 = Voice.builder()
                    .voiceId(4L)
                    .member(member2)
                    .listenCount(300L)
                    .title("4444.재용이햄 애플 런칭행사 줄서있는거 봄")
                    .imageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRXobLcA4-7fiJkSmqBaQnd2QL8gY8EFIx4pQ&s")
                    .originalName("voice2.mp3")
                    .savePath("/voices/voice2.mp3")
                    .saveFolder("/voices/")
                    .latitude(37.7749)
                    .longitude(-122.4194)
                    .dateTime(LocalDateTime.now())
                    .voiceType(VoiceType.NormalVoice)
                    .build();


            voiceRepository.save(voice1);
            voiceRepository.save(voice2);

            Long count = 0L;
            for (int i = 0; i < 40; i++) {
                Voice voice = new Voice();
                voice.setMember(member1);
                voice.setHeartCount(count++);
                voice.setListenCount(Math.round(Math.random() * 100));
                voiceRepository.save(voice3);
                voiceRepository.save(voice4);
            }

                for (int i = 0; i < 30; i++) {
                    Voice voice = new Voice();
                    voice.setMember(member1);
                    voice.setHeartCount(3L);
                    voice.setListenCount(Math.round(Math.random() * 100000));
                    voice.setLatitude(50);
                    voice.setLongitude(50);
                    voice.setTitle("두부를 왜 좋아함" + i);
                    voice.setVoiceType(VoiceType.NormalVoice);
                    voice.setDateTime(LocalDateTime.now());
                    voice.setSavePath("https://github.com/KoEonYack/Tistory-Coveant/blob/master/Article/JAVA/LocalDateTime_%EC%82%AC%EC%9A%A9%EB%B2%95_%EC%A0%95%EB%A6%AC/img/cover.png?raw=true");
                    voice.setImageUrl("https://github.com/KoEonYack/Tistory-Coveant/blob/master/Article/JAVA/LocalDateTime_%EC%82%AC%EC%9A%A9%EB%B2%95_%EC%A0%95%EB%A6%AC/img/cover.png?raw=true");
                    voiceRepository.save(voice);
                }

                for (int i = 1; i <= 43; i++) {
                    Voice voice = new Voice();
                    voice.setMember(member1);
                    voice.setHeartCount(Math.round(Math.random() * 1));
                    voice.setTitle("spread" + i);
                    voiceRepository.save(voice);
                }
                for (int i = 1; i <= 43; i++) {
                    Voice voice = new Voice();
                    voice.setMember(member1);
                    voice.setHeartCount(Math.round(Math.random() * 10));
                    voice.setListenCount(Math.round(Math.random() * 1000));
                    voice.setTitle("like" + i);
                    voiceRepository.save(voice);
                }

                Heart heart1 = new Heart();
                heart1.setMember(member1);
                heart1.setVoice(voice1);
                heartRepository.save(heart1);

                Heart heart2 = new Heart();
                heart2.setMember(member1);
                heart2.setVoice(voice2);
                heartRepository.save(heart2);

                Spread spread1 = new Spread();
                spread1.setMember(member1);
                spread1.setVoice(voice3);
                spreadRepository.save(spread1);

                Spread spread2 = new Spread();
                spread2.setMember(member1);
                spread2.setVoice(voice4);
                spreadRepository.save(spread2);



        };
    }
}

