package com.virtualsoundnw.sftpbot.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the SftpTestCase entity.
 */
public class SftpTestCaseDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 13, max = 36)
    private String incomingFileName;

    @Size(min = 13, max = 36)
    private String resultFileName;

    @Size(min = 13, max = 36)
    private String errorFileName;

    @NotNull
    private String fileContents;

    private Long sftprootId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIncomingFileName() {
        return incomingFileName;
    }

    public void setIncomingFileName(String incomingFileName) {
        this.incomingFileName = incomingFileName;
    }

    public String getResultFileName() {
        return resultFileName;
    }

    public void setResultFileName(String resultFileName) {
        this.resultFileName = resultFileName;
    }

    public String getErrorFileName() {
        return errorFileName;
    }

    public void setErrorFileName(String errorFileName) {
        this.errorFileName = errorFileName;
    }

    public String getFileContents() {
        return fileContents;
    }

    public void setFileContents(String fileContents) {
        this.fileContents = fileContents;
    }

    public Long getSftprootId() {
        return sftprootId;
    }

    public void setSftprootId(Long sftprootId) {
        this.sftprootId = sftprootId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SftpTestCaseDTO sftpTestCaseDTO = (SftpTestCaseDTO) o;
        if(sftpTestCaseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sftpTestCaseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SftpTestCaseDTO{" +
            "id=" + getId() +
            ", incomingFileName='" + getIncomingFileName() + "'" +
            ", resultFileName='" + getResultFileName() + "'" +
            ", errorFileName='" + getErrorFileName() + "'" +
            ", fileContents='" + getFileContents() + "'" +
            "}";
    }
}
