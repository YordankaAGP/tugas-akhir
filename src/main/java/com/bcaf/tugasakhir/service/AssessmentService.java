package com.bcaf.tugasakhir.service;

import com.bcaf.tugasakhir.configuration.OtherConfiguration;
import com.bcaf.tugasakhir.core.IService;
import com.bcaf.tugasakhir.dto.IdDTO;
import com.bcaf.tugasakhir.handler.RequestCapture;
import com.bcaf.tugasakhir.handler.ResponseHandler;
import com.bcaf.tugasakhir.model.Assessment;
import com.bcaf.tugasakhir.model.Usr;
import com.bcaf.tugasakhir.repo.AssessmentRepo;
import com.bcaf.tugasakhir.repo.LogRequestRepo;
import com.bcaf.tugasakhir.repo.UsrRepo;
import com.bcaf.tugasakhir.util.LogTable;
import com.bcaf.tugasakhir.util.LoggingFile;
import com.bcaf.tugasakhir.util.TransformDataPaging;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class AssessmentService implements IService<Assessment> {
    private AssessmentRepo assessmentRepo;
    private UsrRepo usrRepo;
    private String [] strExceptionArr = new String[2];
    private TransformDataPaging transformDataPaging = new TransformDataPaging();
    private Map<String,Object> mapz = new HashMap<>();
    @Autowired
    private LogRequestRepo logRequestRepo;

    @Autowired
    private LogService logService;
    private ModelMapper modelMapper;

    @Autowired
    public AssessmentService(AssessmentRepo assessmentRepo, UsrRepo usrRepo, ModelMapper modelMapper) {
        strExceptionArr[0] = "AssessmentService";
        this.assessmentRepo = assessmentRepo;
        this.usrRepo = usrRepo;
        this.modelMapper = modelMapper;
    }


    @Override
    public ResponseEntity<Object> save(Assessment assessment, HttpServletRequest request) {
        if(assessment==null)
        {
            return new ResponseHandler().generateResponse(
                    "Data tidak Valid",//message
                    HttpStatus.BAD_REQUEST,//httpstatus
                    null,//object
                    "FV002001",//errorCode Fail Validation modul-code 001 sequence 001 range 001 - 010
                    request
            );
        }

        try{
            assessmentRepo.save(assessment);
        }catch (Exception e)
        {
            strExceptionArr[1] = "save(Assessment assessment, HttpServletRequest request) --- LINE 66 \n"+ RequestCapture.allRequest(request);
            LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfiguration.getFlagLoging());
            LogTable.inputLogRequest(logRequestRepo,strExceptionArr,e,OtherConfiguration.getFlagLogTable());
            return new ResponseHandler().generateResponse(
                    "Data Gagal Disimpan",//message
                    HttpStatus.INTERNAL_SERVER_ERROR,//httpstatus
                    null,//object
                    "FE002001",//errorCode Fail Error modul-code 001 sequence 001 range 001 - 010
                    request
            );
        }

        return new ResponseHandler().generateResponse(
                "Data Berhasil Disimpan",//message
                HttpStatus.CREATED,//httpstatus created
                null,//object
                null,//errorCode diisi null ketika data berhasil disimpan
                request
        );
    }

    public ResponseEntity<Object> addParticipant(Long id, IdDTO idDTO, HttpServletRequest request) throws Exception {
        List<Usr> participants = usrRepo.findAllById(idDTO.getIdList());
        Assessment assessment = assessmentRepo.findById(id).orElse(null);
        Boolean isChanged = false;

        if (participants.size() == 0) {
            return new ResponseHandler().generateResponse(
                    "Data tidak Valid",//message
                    HttpStatus.BAD_REQUEST,//httpstatus
                    "User tidak ditemukan!",//object
                    "FV002001",//errorCode Fail Validation modul-code 001 sequence 001 range 001 - 010
                    request
            );
        }


        if (assessment == null) {
            return new ResponseHandler().generateResponse(
                    "Data tidak Valid",//message
                    HttpStatus.BAD_REQUEST,//httpstatus
                    "Assessment tidak ditemukan!",//object
                    "FV002001",//errorCode Fail Validation modul-code 001 sequence 001 range 001 - 010
                    request
            );
        }

        try {
            for (Usr newUser : participants) {
                Long newUserId = newUser.getId();

                // Check if any book with the same ID already exists in the list.
                boolean userExists = assessment.getParticipants().stream().anyMatch(existingUser -> existingUser.getId().equals(newUserId));

                if (!userExists) {
                    // If no book with the same ID is found, add the new book to the list.
                    assessment.getParticipants().add(newUser);
                    isChanged = true;
                }
            }

            if (!isChanged) {
                return new ResponseHandler().generateResponse(
                        "Tidak ada participant yang ditambah",//message
                        HttpStatus.BAD_REQUEST,//httpstatus
                        "Participant sudah didalam assessment!",//object
                        "FV002001",//errorCode Fail Validation modul-code 001 sequence 001 range 001 - 010
                        request
                );
            }

            assessmentRepo.save(assessment);
        } catch (Exception e) {
            strExceptionArr[1] = "addParticipant(Long id, IdDTO idDTO, HttpServletRequest request) --- LINE 109 \n"+ RequestCapture.allRequest(request);
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
                "Participant berhasil ditambahkan",//message
                HttpStatus.CREATED,//httpstatus created
                null,
                null,
                request
        );
    }

    @Override
    public ResponseEntity<Object> update(Long id, Assessment assessment, HttpServletRequest request) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> saveBatch(List<Assessment> lt, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> findByPage(Integer page, Integer size, String columFirst, String valueFirst, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> findAllByPage(Integer page, Integer size, HttpServletRequest request) {
        return null;
    }

    public ResponseEntity<Object> findByUserId(Long id, HttpServletRequest request) {
        List<Assessment> assessment;
        try{
            assessment = assessmentRepo.findByParticipantsId(id);
            if(assessment.size() == 0)
            {
                return new ResponseHandler().generateResponse(
                        "Assessment tidak Ditemukan",//message
                        HttpStatus.NOT_FOUND,//httpstatus
                        null,//object
                        "FV002071",//errorCode Fail Validation modul-code 001 sequence 001 range 071 - 080
                        request
                );
            }
        }catch (Exception e)
        {
            strExceptionArr[1] = " findByUserId(HttpServletRequest request) --- LINE 211 \n"+ RequestCapture.allRequest(request);
            LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfiguration.getFlagLoging());
            return new ResponseHandler().generateResponse(
                    "Data tidak Valid",//message
                    HttpStatus.INTERNAL_SERVER_ERROR,//httpstatus
                    null,//object
                    "FE002071",//errorCode Fail Validation modul-code 001 sequence 001 range 071 - 080
                    request
            );
        }

        return new ResponseHandler().generateResponse(
                "Data Ditemukan",//message
                HttpStatus.OK,//httpstatus OK
                assessment,//object
                null,//errorCode diisi null ketika data berhasil disimpan
                request
        );
    }

    @Override
    public ResponseEntity<Object> findAll(HttpServletRequest request) {
        List<Assessment> assessmentList;
        try{
            assessmentList = assessmentRepo.findAll();
            if(assessmentList.size()==0)
            {
                return new ResponseHandler().generateResponse(
                        "Data tidak Ditemukan",//message
                        HttpStatus.NOT_FOUND,//httpstatus
                        null,//object
                        "FV002071",//errorCode Fail Validation modul-code 001 sequence 001 range 071 - 080
                        request
                );
            }
        }catch (Exception e)
        {
            strExceptionArr[1] = " findAll(HttpServletRequest request) --- LINE 199 \n"+RequestCapture.allRequest(request);
            LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfiguration.getFlagLoging());
            return new ResponseHandler().generateResponse(
                    "Data tidak Valid",//message
                    HttpStatus.INTERNAL_SERVER_ERROR,//httpstatus
                    null,//object
                    "FE002071",//errorCode Fail Validation modul-code 001 sequence 001 range 071 - 080
                    request
            );
        }

        return new ResponseHandler().generateResponse(
                "Data Ditemukan",//message
                HttpStatus.OK,//httpstatus OK
                assessmentList,//object
                null,//errorCode diisi null ketika data berhasil disimpan
                request
        );
    }

    @Override
    public ResponseEntity<Object> dataToExport(MultipartFile multipartFile, HttpServletRequest request) {
        return null;
    }
}
