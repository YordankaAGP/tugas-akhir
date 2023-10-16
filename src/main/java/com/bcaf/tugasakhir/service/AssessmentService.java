package com.bcaf.tugasakhir.service;

import com.bcaf.tugasakhir.configuration.OtherConfiguration;
import com.bcaf.tugasakhir.core.IService;
import com.bcaf.tugasakhir.core.security.JwtUtility;
import com.bcaf.tugasakhir.dto.*;
import com.bcaf.tugasakhir.handler.RequestCapture;
import com.bcaf.tugasakhir.handler.ResponseHandler;
import com.bcaf.tugasakhir.model.*;
import com.bcaf.tugasakhir.repo.AssessmentRepo;
import com.bcaf.tugasakhir.repo.LogRequestRepo;
import com.bcaf.tugasakhir.repo.UsrRepo;
import com.bcaf.tugasakhir.util.LogTable;
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
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class AssessmentService implements IService<Assessment> {
    private AssessmentRepo assessmentRepo;
    private UsrRepo usrRepo;
    private String[] strExceptionArr = new String[2];
    private TransformDataPaging transformDataPaging = new TransformDataPaging();
    private Map<String, Object> mapz = new HashMap<>();
    @Autowired
    private LogRequestRepo logRequestRepo;

    @Autowired
    private JwtUtility jwtUtility;

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
        if (assessment == null) {
            return new ResponseHandler().generateResponse(
                    "Data tidak Valid",//message
                    HttpStatus.BAD_REQUEST,//httpstatus
                    null,//object
                    "FV002001",//errorCode Fail Validation modul-code 001 sequence 001 range 001 - 010
                    request
            );
        }

        try {
            List<Question> questions = assessment.getQuestions();
            if (questions != null) {
                for (Question question : questions) {
                    List<Choice> choices = question.getChoices();
                    question.setAssessment(assessment);
                    if (choices != null) {
                        for (Choice choice : choices) {
                            choice.setQuestion(question);
                        }
                    }
                }
            }
            assessment.getQuestions().addAll(questions);
            assessmentRepo.save(assessment);
        } catch (Exception e) {
            strExceptionArr[1] = "save(Assessment assessment, HttpServletRequest request) --- LINE 66 \n" + RequestCapture.allRequest(request);
            LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfiguration.getFlagLoging());
            LogTable.inputLogRequest(logRequestRepo, strExceptionArr, e, OtherConfiguration.getFlagLogTable());
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
            strExceptionArr[1] = "addParticipant(Long id, IdDTO idDTO, HttpServletRequest request) --- LINE 109 \n" + RequestCapture.allRequest(request);
            LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfiguration.getFlagLoging());
            LogTable.inputLogRequest(logRequestRepo, strExceptionArr, e, OtherConfiguration.getFlagLogTable());
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

    public ResponseEntity<Object> addQuestion(Long id, AddQuestionDTO body, HttpServletRequest request) throws Exception {
        List<QuestionDTO> questionsBody = body.getQuestions();
        List<Question> questions = modelMapper.map(questionsBody, new TypeToken<List<Question>>() {
        }.getType());
        Assessment assessment = assessmentRepo.findById(id).orElse(null);

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
            for (Question question : questions) {
                question.setAssessment(assessment);
            }
            assessment.getQuestions().addAll(questions);
            assessmentRepo.save(assessment);
        } catch (Exception e) {
            strExceptionArr[1] = "addQuestion(Long id, AddQuestionDTO addQuestionDTO, HttpServletRequest request) --- LINE 189 \n" + RequestCapture.allRequest(request);
            LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfiguration.getFlagLoging());
            LogTable.inputLogRequest(logRequestRepo, strExceptionArr, e, OtherConfiguration.getFlagLogTable());
            return new ResponseHandler().generateResponse(
                    "Data Gagal Disimpan",//message
                    HttpStatus.INTERNAL_SERVER_ERROR,//httpstatus
                    null,//object
                    "FE-Auth001",//errorCode Fail Error modul-code 001 sequence 001 range 001 - 010
                    request
            );
        }

        return new ResponseHandler().generateResponse(
                "Question berhasil ditambahkan",//message
                HttpStatus.CREATED,//httpstatus created
                null,
                null,
                request
        );
    }

    public ResponseEntity<Object> addResult(Long id, ResultByAssessmentDTO resultByAssessmentDTO, HttpServletRequest request) throws Exception {
        System.out.println("asdasdsadsadasdads");
        String authorization = request.getHeader("Authorization");
        String token = authorization.substring(7);//memotong setelah kata Bearer+spasi = 7 digit
        String username = jwtUtility.getUsernameFromToken(token);
        Usr currentUser = usrRepo.findByUsername(username).orElse(null);

        Result result = modelMapper.map(resultByAssessmentDTO, new TypeToken<Result>() {}.getType());
        Assessment assessment = assessmentRepo.findById(id).orElse(null);

        if (assessment == null) {
            return new ResponseHandler().generateResponse(
                    "Data tidak Valid",//message
                    HttpStatus.BAD_REQUEST,//httpstatus
                    "Assessment tidak ditemukan!",//object
                    "FV002001",//errorCode Fail Validation modul-code 001 sequence 001 range 001 - 010
                    request
            );
        }

        boolean isParticipant = assessment.getParticipants().stream().anyMatch(
                p -> p.getId().equals(currentUser.getId())
        );

        System.out.println(isParticipant);

        if (!isParticipant) {
            return new ResponseHandler().generateResponse(
                    "User bukan bagian dari assessment!",
                    HttpStatus.UNAUTHORIZED,
                    "User tidak memiliki akses",
                    "FV002001",
                    request
            );
        }

        boolean isAlreadyAnswered = assessment.getResults().stream().anyMatch(
                r -> r.getUser().getId().equals(currentUser.getId())
        );

        if (isAlreadyAnswered) {
            return new ResponseHandler().generateResponse(
                    "Attempt hanya boleh 1 kali",
                    HttpStatus.UNAUTHORIZED,
                    "User sudah mengisi tes",
                    "FV002001",
                    request
            );
        }

        List<Question> questionBank = assessment.getQuestions();

        if (questionBank.size() < 1) {
            return new ResponseHandler().generateResponse(
                    "Assessment tidak memiliki questions!",
                    HttpStatus.BAD_REQUEST,
                    "Question kosong!",
                    "FV002001",
                    request
            );
        }

        try {
            List<Answer> answers = result.getAnswers();
            result.setAssessment(assessment);
            result.setUser(currentUser);
            Float totalScore = 0F;
            HashSet<Long> AnswerQuestionIds = new HashSet<>();

            for (Answer answer : answers) {
                Boolean isAnswerDuplicate = !AnswerQuestionIds.add(answer.getQuestion().getId());

                if (isAnswerDuplicate) {
                    return new ResponseHandler().generateResponse(
                            "Ada question yang duplikat",
                            HttpStatus.BAD_REQUEST,
                            "Jawaban tidak boleh sama!",
                            "FV002001",
                            request
                    );
                }

                Long idToFind = answer.getQuestion().getId();
                Question foundQuestion = questionBank.stream()
                        .filter(q -> q.getId().equals(idToFind))
                        .findFirst().orElse(null);

                if (foundQuestion == null) {
                    return new ResponseHandler().generateResponse(
                            "Question id:" + answer.getQuestion().getId().toString() + " tidak ditemukan dalam Assessment",
                            HttpStatus.BAD_REQUEST,
                            "Question tidak ditemukan!",
                            "FV002001",
                            request
                    );
                }

                Choice foundChoice = foundQuestion.getChoices().stream().filter(
                        q -> q.getId().equals(answer.getChoice().getId())).findFirst().orElse(null);

                if (foundChoice == null) {
                    return new ResponseHandler().generateResponse(
                            "Choice id:"
                                    + answer.getChoice().getId().toString()
                                    + " tidak ditemukan dalam Question dengan id "
                                    + answer.getQuestion().getId().toString(),
                            HttpStatus.BAD_REQUEST,
                            "Choice tidak ditemukan!",
                            "FV002001",
                            request
                    );
                }

                if (foundChoice.isTrue()) {
                    answer.setScore(1F);
                    totalScore += 1;
                }
                answer.setResult(result);
            }
            result.setFinalScore(totalScore / assessment.getQuestions().size() * 100);
            assessment.getResults().add(result);
            assessmentRepo.save(assessment);
        } catch (Exception e) {
            strExceptionArr[1] = "addResult(Long id, ResultDTO resultDTO, HttpServletRequest request) --- LINE 189 \n" + RequestCapture.allRequest(request);
            LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfiguration.getFlagLoging());
            LogTable.inputLogRequest(logRequestRepo, strExceptionArr, e, OtherConfiguration.getFlagLogTable());
            return new ResponseHandler().generateResponse(
                    "Data Gagal Disimpan",//message
                    HttpStatus.INTERNAL_SERVER_ERROR,//httpstatus
                    null,//object
                    "FE-Auth001",//errorCode Fail Error modul-code 001 sequence 001 range 001 - 010
                    request
            );
        }

        return new ResponseHandler().generateResponse(
                "Result berhasil ditambahkan",//message
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
        Assessment assessment;
        try {
            assessment = assessmentRepo.findById(id).orElse(null);
            if (assessment == null) {
                return new ResponseHandler().generateResponse(
                        "Data tidak Ditemukan",//message
                        HttpStatus.NOT_FOUND,//httpstatus
                        null,//object
                        "FV002071",//errorCode Fail Validation modul-code 001 sequence 001 range 071 - 080
                        request
                );
            }
        } catch (Exception e) {
            strExceptionArr[1] = " findById(HttpServletRequest request) --- LINE 198 \n" + RequestCapture.allRequest(request);
            LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfiguration.getFlagLoging());
            return new ResponseHandler().generateResponse(
                    "Data tidak Valid",//message
                    HttpStatus.INTERNAL_SERVER_ERROR,//httpstatus
                    null,//object
                    "FE002071",//errorCode Fail Validation modul-code 001 sequence 001 range 071 - 080
                    request
            );
        }

        GetAssessmentDTO transformedAssessment = modelMapper.map(assessment, new TypeToken<GetAssessmentDTO>() {
        }.getType());

        return new ResponseHandler().generateResponse(
                "Data Ditemukan",//message
                HttpStatus.OK,//httpstatus OK
                transformedAssessment,//object
                null,//errorCode diisi null ketika data berhasil disimpan
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

    public ResponseEntity<Object> findByUserId(Long id, HttpServletRequest request) {
        List<Assessment> assessments;
        try {
            assessments = assessmentRepo.findByParticipantsId(id);
            if (assessments.size() == 0) {
                return new ResponseHandler().generateResponse(
                        "Assessment tidak Ditemukan",//message
                        HttpStatus.NOT_FOUND,//httpstatus
                        null,//object
                        "FV002071",//errorCode Fail Validation modul-code 001 sequence 001 range 071 - 080
                        request
                );
            }
        } catch (Exception e) {
            strExceptionArr[1] = " findByUserId(HttpServletRequest request) --- LINE 211 \n" + RequestCapture.allRequest(request);
            LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfiguration.getFlagLoging());
            return new ResponseHandler().generateResponse(
                    "Data tidak Valid",//message
                    HttpStatus.INTERNAL_SERVER_ERROR,//httpstatus
                    null,//object
                    "FE002071",//errorCode Fail Validation modul-code 001 sequence 001 range 071 - 080
                    request
            );
        }
        List<GetAssessmentDTO> transformedAssessments = modelMapper.map(assessments, new TypeToken<List<GetAssessmentDTO>>() {
        }.getType());


        return new ResponseHandler().generateResponse(
                "Data Ditemukan",//message
                HttpStatus.OK,//httpstatus OK
                transformedAssessments,//object
                null,//errorCode diisi null ketika data berhasil disimpan
                request
        );
    }

    public ResponseEntity<Object> findCompleteByUserId(Long id, HttpServletRequest request) {
        List<Assessment> assessments;
        try {
            assessments = assessmentRepo.findByParticipantsId(id);
            if (assessments.size() == 0) {
                return new ResponseHandler().generateResponse(
                        "Assessment tidak Ditemukan",//message
                        HttpStatus.NOT_FOUND,//httpstatus
                        null,//object
                        "FV002071",//errorCode Fail Validation modul-code 001 sequence 001 range 071 - 080
                        request
                );
            }
        } catch (Exception e) {
            strExceptionArr[1] = " findCompleteByUserId(HttpServletRequest request) --- LINE 480 \n" + RequestCapture.allRequest(request);
            LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfiguration.getFlagLoging());
            return new ResponseHandler().generateResponse(
                    "Data tidak Valid",//message
                    HttpStatus.INTERNAL_SERVER_ERROR,//httpstatus
                    null,//object
                    "FE002071",//errorCode Fail Validation modul-code 001 sequence 001 range 071 - 080
                    request
            );
        }

        List<Assessment> filteredAssessments = assessments.stream().filter(
                assessment -> assessment.getResults().stream().anyMatch(
                        result -> result.getUser().getId().equals(id)
                )
        ).collect(Collectors.toList());


        List<GetAssessmentDTO> transformedAssessments = modelMapper.map(filteredAssessments, new TypeToken<List<GetAssessmentDTO>>() {}.getType());

        return new ResponseHandler().generateResponse(
                "Data Ditemukan",
                HttpStatus.OK,
                transformedAssessments,
                null,
                request
        );
    }

    public ResponseEntity<Object> findIncompleteByUserId(Long id, HttpServletRequest request) {
        List<Assessment> assessments;
        try {
            assessments = assessmentRepo.findByParticipantsId(id);
            if (assessments.size() == 0) {
                return new ResponseHandler().generateResponse(
                        "Assessment tidak Ditemukan",//message
                        HttpStatus.NOT_FOUND,//httpstatus
                        null,//object
                        "FV002071",//errorCode Fail Validation modul-code 001 sequence 001 range 071 - 080
                        request
                );
            }
        } catch (Exception e) {
            strExceptionArr[1] = " findIncompleteByUserId(HttpServletRequest request) --- LINE 211 \n" + RequestCapture.allRequest(request);
            LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfiguration.getFlagLoging());
            return new ResponseHandler().generateResponse(
                    "Data tidak Valid",//message
                    HttpStatus.INTERNAL_SERVER_ERROR,//httpstatus
                    null,//object
                    "FE002071",//errorCode Fail Validation modul-code 001 sequence 001 range 071 - 080
                    request
            );
        }

        List<Assessment> filteredAssessments = assessments.stream().filter(
                assessment -> !assessment.getResults().stream().anyMatch(
                        result -> result.getUser().getId().equals(id)
                    )
                ).collect(Collectors.toList());


        List<GetAssessmentDTO> transformedAssessments = modelMapper.map(filteredAssessments, new TypeToken<List<GetAssessmentDTO>>() {}.getType());

        return new ResponseHandler().generateResponse(
                "Data Ditemukan",
                HttpStatus.OK,
                transformedAssessments,
                null,
                request
        );
    }

    @Override
    public ResponseEntity<Object> findAll(HttpServletRequest request) {
        List<Assessment> assessmentList;
        try {
            assessmentList = assessmentRepo.findAll();
            if (assessmentList.size() == 0) {
                return new ResponseHandler().generateResponse(
                        "Data tidak Ditemukan",//message
                        HttpStatus.NOT_FOUND,//httpstatus
                        null,//object
                        "FV002071",//errorCode Fail Validation modul-code 001 sequence 001 range 071 - 080
                        request
                );
            }
        } catch (Exception e) {
            strExceptionArr[1] = " findAll(HttpServletRequest request) --- LINE 199 \n" + RequestCapture.allRequest(request);
            LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfiguration.getFlagLoging());
            return new ResponseHandler().generateResponse(
                    "Data tidak Valid",//message
                    HttpStatus.INTERNAL_SERVER_ERROR,//httpstatus
                    null,//object
                    "FE002071",//errorCode Fail Validation modul-code 001 sequence 001 range 071 - 080
                    request
            );
        }
        List<GetAssessmentDTO> transformedAssessments = modelMapper.map(assessmentList, new TypeToken<List<GetAssessmentDTO>>() {}.getType());

        return new ResponseHandler().generateResponse(
                "Data Ditemukan",//message
                HttpStatus.OK,//httpstatus OK
                transformedAssessments,//object
                null,//errorCode diisi null ketika data berhasil disimpan
                request
        );
    }

    @Override
    public ResponseEntity<Object> dataToExport(MultipartFile multipartFile, HttpServletRequest request) {
        return null;
    }
}
