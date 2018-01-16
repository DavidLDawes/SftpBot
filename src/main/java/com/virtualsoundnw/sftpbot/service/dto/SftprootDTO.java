package com.virtualsoundnw.sftpbot.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Sftproot entity.
 */
public class SftprootDTO implements Serializable {

    private Long id;

    private String incomingDirectory;

    private String outgoingDirectory;

    private String errorDirectory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIncomingDirectory() {
        return incomingDirectory;
    }

    public void setIncomingDirectory(String incomingDirectory) {
        this.incomingDirectory = incomingDirectory;
    }

    public String getOutgoingDirectory() {
        return outgoingDirectory;
    }

    public void setOutgoingDirectory(String outgoingDirectory) {
        this.outgoingDirectory = outgoingDirectory;
    }

    public String getErrorDirectory() {
        return errorDirectory;
    }

    public void setErrorDirectory(String errorDirectory) {
        this.errorDirectory = errorDirectory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SftprootDTO sftprootDTO = (SftprootDTO) o;
        if(sftprootDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sftprootDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SftprootDTO{" +
            "id=" + getId() +
            ", incomingDirectory='" + getIncomingDirectory() + "'" +
            ", outgoingDirectory='" + getOutgoingDirectory() + "'" +
            ", errorDirectory='" + getErrorDirectory() + "'" +
            "}";
    }
}
