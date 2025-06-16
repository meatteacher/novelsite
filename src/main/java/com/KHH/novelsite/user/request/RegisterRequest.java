package com.KHH.novelsite.user.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String email;
    private String pwd;
    private String name;
    private String nickname;
}
