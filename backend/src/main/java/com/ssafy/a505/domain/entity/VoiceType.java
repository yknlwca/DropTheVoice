package com.ssafy.a505.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum VoiceType {
    pokemon,
    virus,
}
