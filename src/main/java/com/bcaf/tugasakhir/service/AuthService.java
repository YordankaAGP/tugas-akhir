package com.bcaf.tugasakhir.service;


import com.bcaf.tugasakhir.configuration.OtherConfiguration;
import com.bcaf.tugasakhir.core.security.JwtUtility;
import com.bcaf.tugasakhir.dto.LoginDTO;
//import com.bcaf.tugasakhir.dto.UsrDTO;
import com.bcaf.tugasakhir.handler.RequestCapture;
import com.bcaf.tugasakhir.handler.ResponseHandler;
import com.bcaf.tugasakhir.model.Usr;
import com.bcaf.tugasakhir.repo.LogRequestRepo;
import com.bcaf.tugasakhir.repo.UsrRepo;
import com.bcaf.tugasakhir.util.LogTable;
import com.bcaf.tugasakhir.util.LoggingFile;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
@Transactional
public class AuthService implements UserDetailsService{

    private UsrRepo usrRepo;
    private JwtUtility jwtUtility;
    private String [] strExceptionArr = new String[2];
    @Autowired
    private LogRequestRepo logRequestRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthService(UsrRepo usrRepo, JwtUtility jwtUtility) {
        strExceptionArr[0] = "AuthService";
        this.usrRepo = usrRepo;
        this.jwtUtility = jwtUtility;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
        Optional<Usr> optionalUsr = usrRepo.findByUsernameOrEmail(username, username);

        if (optionalUsr.isEmpty()) {
            return user;
        }
        Usr usr = optionalUsr.get();
//        $2a$11$Owf6JAbkUwLBisLGnmmD8u41FRk/Hs5oEt2byIHz9ENOk00oqU4ii
        return new User(usr.getUsername(), usr.getPassword(), new ArrayList<>());
    }

    public ResponseEntity<Object> registrationUser(Usr usr, HttpServletRequest request){//RANGE 001-005
        try{
            if(usr==null)
            {
                return new ResponseHandler().generateResponse(
                        "Data tidak Valid",//message
                        HttpStatus.BAD_REQUEST,//httpstatus
                        null,//object
                        "FV-Auth001",
                        request
                );
            }
            Random random = new Random();
            Integer intToken = random.nextInt(100000,999999);
            String strToken = String.valueOf(intToken);
            Usr usrNext = null; //untuk penampungan jika proses update
            /*
                pengecekan untuk memastikan user registrasi pertama kali atau sudah pernah dan melakukan registrasi lagi
                tetapi belum selesai melakukan otentikasi verifikasi email
             */
            Optional<Usr> optionalUsr = usrRepo.findByUsername(usr.getUsername());
            if(optionalUsr.isEmpty())
            {
                /*m
                    Jika user baru maka informasi nya akan langsung di save
                 */
                usr.setPassword(bCryptPasswordEncoder.encode(usr.getPassword()+OtherConfiguration.getFlagPwdTrap()));//encrypt password sebelum ke database
                usr.setToken(bCryptPasswordEncoder.encode(strToken));
                usrRepo.save(usr);
            } else {
                /*
                    Jika user sudah pernah registrasi tetapi gagal maka informasi sebelumnya akan ditiban
                    Proses update
                 */
                usrNext = optionalUsr.get();
                usrNext.setEmail(usr.getEmail());
                usrNext.setPassword(bCryptPasswordEncoder.encode(usr.getPassword()+OtherConfiguration.getFlagPwdTrap()));
                usrNext.setName(usr.getName());
                usrNext.setUsername(usr.getUsername());
                usrNext.setToken(bCryptPasswordEncoder.encode(strToken));
            }
        }
        catch (Exception e)
        {
            strExceptionArr[1] = "registrationUser(Usr usr, HttpServletRequest request) --- LINE 109 \n"+ RequestCapture.allRequest(request);
            LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfiguration.getFlagLoging());
            LogTable.inputLogRequest(logRequestRepo,strExceptionArr,e,OtherConfiguration.getFlagLogTable());
            return new ResponseHandler().generateResponse(
                    "Data Gagal Disimpan",//message
                    HttpStatus.INTERNAL_SERVER_ERROR,//httpstatus
                    null,//object
                    "FE-Auth001",//errorCode Fail Error modul-code 001 sequence 001 range 001 - 010
                    request
            );
        }

        return new ResponseHandler().generateResponse(
                "Registrasi Berhasil Diproses",//message
                HttpStatus.CREATED,//httpstatus created
                null,
                null,
                request
        );
    }

    public ResponseEntity<Object> authManager(LoginDTO loginDTO, HttpServletRequest request)//RANGE 006-010
    {
        /*
            Untuk memasukkan user ke dalam
         */
        UserDetails userDetails = loadUserByUsername(loginDTO.getUsername());

        if(userDetails==null)
        {
            return new ResponseHandler().generateResponse(
                    "OTENTIKASI GAGAL",//message
                    HttpStatus.UNAUTHORIZED,//httpstatus
                    null,//object
                    "FV-Auth006",
                    request
            );
        }
        /*
            Isi apapun yang perlu diisi disini !!
         */
        Optional<Usr> usr = usrRepo.findByUsername(loginDTO.getUsername());
        Usr usrNext = usr.get();
        Map<String,Object> mapz = new HashMap<>();
        mapz.put("email",usrNext.getEmail());
        mapz.put("username",usrNext.getUsername());
        mapz.put("name",usrNext.getName());


        if (bCryptPasswordEncoder.matches( loginDTO.getPassword()+OtherConfiguration.getFlagPwdTrap(),userDetails.getPassword())){

            String token = jwtUtility.generateToken(userDetails,mapz);
            mapz.put("token", token);
            mapz.put("isAdmin", usrNext.getAdmin());
            mapz.put("id", usrNext.getId());
            mapz.remove("iat");
            mapz.remove("exp");
            mapz.remove("sub");

            return new ResponseHandler().generateResponse(
                    "Otentikasi Berhasil",//message
                    HttpStatus.OK,//httpstatus created
                    mapz,//object
                    null,//errorCode diisi null ketika data berhasil disimpan
                    request
            );
        }
        return new ResponseHandler().generateResponse(
            "OTENTIKASI GAGAL",//message
            HttpStatus.UNAUTHORIZED,//httpstatus
            null,//object
            "FV-Auth006",
            request
    );
    }

    public Map<String,Object> checkAuthorization(String token,String modulName,
                                                 HttpServletRequest request)
    {
        /*
            Cek otorisasi berdasarkan informasi yang dikirim di JWT
            lalu dinformasi diambil menjadi object Java Map
         */
//        Map<String,Object> map =jwtUtility.getApiAuthorization(token,modulName,idxPrivilledge,new HashMap<String,Object>());
        Map<String,Object> map =jwtUtility.getApiAuthorization(token,modulName,new HashMap<String,Object>());
        return map;
    }


}