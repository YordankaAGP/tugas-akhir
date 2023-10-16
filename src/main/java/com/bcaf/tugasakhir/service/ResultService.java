package com.bcaf.tugasakhir.service;

import com.bcaf.tugasakhir.configuration.OtherConfiguration;
import com.bcaf.tugasakhir.core.IService;
import com.bcaf.tugasakhir.core.security.JwtUtility;
import com.bcaf.tugasakhir.dto.ResultByAssessmentDTO;
import com.bcaf.tugasakhir.dto.ResultByUserDTO;
import com.bcaf.tugasakhir.handler.RequestCapture;
import com.bcaf.tugasakhir.handler.ResponseHandler;
import com.bcaf.tugasakhir.model.Result;
import com.bcaf.tugasakhir.repo.AssessmentRepo;
import com.bcaf.tugasakhir.repo.ResultRepo;
import com.bcaf.tugasakhir.util.LoggingFile;
import com.bcaf.tugasakhir.util.TransformDataPaging;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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

@Service
@Transactional
public class ResultService implements IService<Result> {
    private AssessmentRepo assessmentRepo;
    private ResultRepo resultRepo;
    private String[] strExceptionArr = new String[2];
    private TransformDataPaging transformDataPaging = new TransformDataPaging();
    private Map<String, Object> mapz = new HashMap<>();

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public ResultService(AssessmentRepo assessmentRepo, ResultRepo resultRepo, ModelMapper modelMapper) {
        strExceptionArr[0] = "ResultService";
        this.assessmentRepo = assessmentRepo;
        this.resultRepo = resultRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<Object> save(Result result, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> update(Long id, Result result, HttpServletRequest request) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> saveBatch(List<Result> lt, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        return null;
    }

    public ResponseEntity<Object> findByUserId(Long id, HttpServletRequest request) {
        List<Result> results;

        try {
            results = resultRepo.findByUserId(id);
            if (results.size() == 0) {
                return new ResponseHandler().generateResponse(
                        "Result tidak Ditemukan",
                        HttpStatus.NOT_FOUND,
                        null,
                        "FV002071",
                        request
                );
            }
        } catch (Exception e) {
            strExceptionArr[1] = " findByAssessmentId(Long id, HttpServletRequest request) --- LINE 93 \n" + RequestCapture.allRequest(request);
            LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfiguration.getFlagLoging());
            return new ResponseHandler().generateResponse(
                    "Data tidak Valid",
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    "FE002071",
                    request
            );
        }

        List<ResultByUserDTO> transformedResults = modelMapper.map(results, new TypeToken<List<ResultByUserDTO>>() {}.getType());


        return new ResponseHandler().generateResponse(
                "Data Ditemukan",
                HttpStatus.OK,
                transformedResults,
                null,
                request
        );
    }

    public ResponseEntity<Object> findByAssessmentId(Long id, HttpServletRequest request) {
        List<Result> results;
        try {
            results = resultRepo.findByAssessmentId(id);
            if (results.size() == 0) {
                return new ResponseHandler().generateResponse(
                        "Result tidak Ditemukan",//message
                        HttpStatus.NOT_FOUND,//httpstatus
                        null,//object
                        "FV002071",//errorCode Fail Validation modul-code 001 sequence 001 range 071 - 080
                        request
                );
            }
        } catch (Exception e) {
            strExceptionArr[1] = " findByAssessmentId(Long id, HttpServletRequest request) --- LINE 93 \n" + RequestCapture.allRequest(request);
            LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfiguration.getFlagLoging());
            return new ResponseHandler().generateResponse(
                    "Data tidak Valid",
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    "FE002071",
                    request
            );
        }

        List<ResultByAssessmentDTO> transformedResults = modelMapper.map(results, new TypeToken<List<ResultByAssessmentDTO>>() {}.getType());


        return new ResponseHandler().generateResponse(
                "Data Ditemukan",
                HttpStatus.OK,
                transformedResults,
                null,
                request
        );
    }


    @Override
    public ResponseEntity<Object> findByPage(Integer page, Integer size, String columFirst, String valueFirst, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> findAllByPage(Integer page, Integer size, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> findAll(HttpServletRequest request) {
        List<Result> results;
        try {
            results = resultRepo.findAll();
            if (results.size() == 0) {
                return new ResponseHandler().generateResponse(
                        "Result tidak Ditemukan",//message
                        HttpStatus.NOT_FOUND,//httpstatus
                        null,//object
                        "FV002071",//errorCode Fail Validation modul-code 001 sequence 001 range 071 - 080
                        request
                );
            }
        } catch (Exception e) {
            strExceptionArr[1] = " findAll(HttpServletRequest request) --- LINE 143 \n" + RequestCapture.allRequest(request);
            LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfiguration.getFlagLoging());
            return new ResponseHandler().generateResponse(
                    "Data tidak Valid",//message
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    "FE002071",
                    request
            );
        }

        List<ResultByAssessmentDTO> transformedResults = modelMapper.map(results, new TypeToken<List<ResultByAssessmentDTO>>() {}.getType());


        return new ResponseHandler().generateResponse(
                "Data Ditemukan",
                HttpStatus.OK,
                transformedResults,
                null,
                request
        );
    }

    @Override
    public ResponseEntity<Object> dataToExport(MultipartFile multipartFile, HttpServletRequest request) {
        return null;
    }
}
