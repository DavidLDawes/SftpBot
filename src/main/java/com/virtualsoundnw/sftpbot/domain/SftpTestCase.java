package com.virtualsoundnw.sftpbot.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A SftpTestCase.
 */
@Entity
@Table(name = "sftp_test_case")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SftpTestCase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 13, max = 36)
    @Column(name = "incoming_file_name", length = 36, nullable = false)
    private String incomingFileName;

    @Size(min = 13, max = 36)
    @Column(name = "result_file_name", length = 36)
    private String resultFileName;

    @Size(min = 13, max = 36)
    @Column(name = "error_file_name", length = 36)
    private String errorFileName;

    @NotNull
    @Size(max = 4096)
    @Column(name = "file_contents", length = 4096, nullable = false)
    private String fileContents;

    @Column(name = "delay")
    private Integer delay;

    @ManyToOne(optional = false)
    @NotNull
    private Sftproot sftproot;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIncomingFileName() {
        return incomingFileName;
    }

    public SftpTestCase incomingFileName(String incomingFileName) {
        this.incomingFileName = incomingFileName;
        return this;
    }

    public void setIncomingFileName(String incomingFileName) {
        this.incomingFileName = incomingFileName;
    }

    public String getResultFileName() {
        return resultFileName;
    }

    public SftpTestCase resultFileName(String resultFileName) {
        this.resultFileName = resultFileName;
        return this;
    }

    public void setResultFileName(String resultFileName) {
        this.resultFileName = resultFileName;
    }

    public String getErrorFileName() {
        return errorFileName;
    }

    public SftpTestCase errorFileName(String errorFileName) {
        this.errorFileName = errorFileName;
        return this;
    }

    public void setErrorFileName(String errorFileName) {
        this.errorFileName = errorFileName;
    }

    public String getFileContents() {
        return fileContents;
    }

    public SftpTestCase fileContents(String fileContents) {
        this.fileContents = fileContents;
        return this;
    }

    public void setFileContents(String fileContents) {
        this.fileContents = fileContents;
    }

    public Integer getDelay() {
        return delay;
    }

    public SftpTestCase delay(Integer delay) {
        this.delay = delay;
        return this;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    public Sftproot getSftproot() {
        return sftproot;
    }

    public SftpTestCase sftproot(Sftproot sftproot) {
        this.sftproot = sftproot;
        return this;
    }

    public void setSftproot(Sftproot sftproot) {
        this.sftproot = sftproot;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SftpTestCase sftpTestCase = (SftpTestCase) o;
        if (sftpTestCase.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sftpTestCase.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SftpTestCase{" +
            "id=" + getId() +
            ", incomingFileName='" + getIncomingFileName() + "'" +
            ", resultFileName='" + getResultFileName() + "'" +
            ", errorFileName='" + getErrorFileName() + "'" +
            ", fileContents='" + getFileContents() + "'" +
            ", delay=" + getDelay() +
            "}";
    }
}
