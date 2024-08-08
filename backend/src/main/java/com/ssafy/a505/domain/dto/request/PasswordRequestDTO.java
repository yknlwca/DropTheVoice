package com.ssafy.a505.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordRequestDTO {

    String memberName;
    String oldPassword;
    String newPassword;

}
