package com.bcaf.tugasakhir.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class IdDTO {
    @NotNull
    @NotEmpty
    private List<Long> idList;
    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }
}
