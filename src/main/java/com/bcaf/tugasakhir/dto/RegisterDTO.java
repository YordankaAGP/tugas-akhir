package com.bcaf.tugasakhir.dto;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

public class RegisterDTO {

    private Long id;


    @NotNull
    @NotEmpty
    @NotBlank
    @Pattern(regexp = "^\\w{8,15}$",message = "Format user tidak boleh spasi (minimal 8 maksimal 15)")
//    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,15}$",message = "Format user tidak boleh spasi (minimal 8 maksimal 15)")
    private String username;

    @NotNull
    @NotBlank
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$",message = "ex : poll.chihuy@gmail.com")//RFC532
    private String email;

    @NotNull
    @NotEmpty
    @NotBlank
    //    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[*.!@$%^&(){}:;<>,.?/~_+-=|]).{10,20}$",message = "Kombinasi Huruf,Angka dan spesial character !!")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{10,20}$",message = "Minimal 1 simbol & alfanumeric , ex : Paul@123... Kombinasi Huruf dan Angka !!(Password Minimal 10 Maksimal 20 Karakter)")
//    @Pattern(regexp = "",message = "Minimal 1 simbol & alfanumeric , ex : Paul@123...")//MINIMAL 1 SIMBOL & ALFANUMERIK
    private String password;

    @NotNull
    @NotBlank
    @NotEmpty
    @Length(min = 10,max = 60)
    private String name;


    private String token;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }
}

