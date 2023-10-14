package com.bcaf.tugasakhir.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class LoginDTO {

    private Long id;

    @NotNull
    @NotEmpty
    @NotBlank
//    @Pattern(regexp = "^\\w{8,15}$",message = "Format user tidak boleh spasi (minimal 8 maksimal 15)")
//    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,15}$",message = "Format user tidak boleh spasi (minimal 8 maksimal 15)")
    private String username;

    @NotNull
    @NotEmpty
    @NotBlank
    //    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[*.!@$%^&(){}:;<>,.?/~_+-=|]).{10,20}$",message = "Kombinasi Huruf,Angka dan spesial character !!")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{10,20}$",message = "Minimal 1 simbol & alfanumeric , ex : Paul@123... Kombinasi Huruf dan Angka !!(Password Minimal 10 Maksimal 20 Karakter)")
//    @Pattern(regexp = "",message = "Minimal 1 simbol & alfanumeric , ex : Paul@123...")//MINIMAL 1 SIMBOL & ALFANUMERIK
    private String password;

    public Long getIdUser() {
        return id;
    }

    public void setIdUser(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
