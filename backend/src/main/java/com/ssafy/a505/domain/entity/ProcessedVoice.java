package com.ssafy.a505.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ProcessedVoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "processed_voice_id")
    private Long processedVoiceId;

    private String processedPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voice_id")
    private Voice voice;

    @Enumerated(EnumType.STRING)
    private VoiceType voiceType;
}
