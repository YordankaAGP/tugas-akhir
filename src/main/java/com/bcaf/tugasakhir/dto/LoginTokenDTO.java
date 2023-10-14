package com.bcaf.tugasakhir.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class LoginTokenDTO {
    @NotNull
    @NotEmpty
    @NotBlank
    @Pattern(regexp = "^\\w{8,15}$",message = "Format user tidak boleh spasi (minimal 8 maksimal 15)")
//    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,15}$",message = "Format user tidak boleh spasi (minimal 8 maksimal 15)")
    private String username;

    @NotNull
    @NotBlank
    @NotEmpty
    @Pattern(regexp = "^[0-9]{6}$",message = "Format Token Tidak Valid")
    private String token;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
