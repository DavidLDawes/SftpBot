package com.virtualsoundnw.sftpbot.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Sftproot.
 */
@Entity
@Table(name = "sftproot")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Sftproot implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "incoming_directory")
    private String incomingDirectory;

    @Column(name = "outgoing_directory")
    private String outgoingDirectory;

    @Column(name = "error_directory")
    private String errorDirectory;

    @OneToMany(mappedBy = "sftproot")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SftpTestCase> sftpTestCases = new HashSet<>();

    public Sftproot() {
    }

    public Sftproot (String incomingDirectory, String outgoingDirectory, String errorDirectory) {
        this.incomingDirectory = incomingDirectory;
        this.outgoingDirectory = outgoingDirectory;
        this.errorDirectory = errorDirectory;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIncomingDirectory() {
        return incomingDirectory;
    }

    public Sftproot incomingDirectory(String incomingDirectory) {
        this.incomingDirectory = incomingDirectory;
        return this;
    }

    public void setIncomingDirectory(String incomingDirectory) {
        this.incomingDirectory = incomingDirectory;
    }

    public String getOutgoingDirectory() {
        return outgoingDirectory;
    }

    public Sftproot outgoingDirectory(String outgoingDirectory) {
        this.outgoingDirectory = outgoingDirectory;
        return this;
    }

    public void setOutgoingDirectory(String outgoingDirectory) {
        this.outgoingDirectory = outgoingDirectory;
    }

    public String getErrorDirectory() {
        return errorDirectory;
    }

    public Sftproot errorDirectory(String errorDirectory) {
        this.errorDirectory = errorDirectory;
        return this;
    }

    public void setErrorDirectory(String errorDirectory) {
        this.errorDirectory = errorDirectory;
    }

    public Set<SftpTestCase> getSftpTestCases() {
        return sftpTestCases;
    }

    public Sftproot sftpTestCases(Set<SftpTestCase> sftpTestCases) {
        this.sftpTestCases = sftpTestCases;
        return this;
    }

    public Sftproot addSftpTestCase(SftpTestCase sftpTestCase) {
        this.sftpTestCases.add(sftpTestCase);
        sftpTestCase.setSftproot(this);
        return this;
    }

    public Sftproot removeSftpTestCase(SftpTestCase sftpTestCase) {
        this.sftpTestCases.remove(sftpTestCase);
        sftpTestCase.setSftproot(null);
        return this;
    }

    public void setSftpTestCases(Set<SftpTestCase> sftpTestCases) {
        this.sftpTestCases = sftpTestCases;
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
        Sftproot sftproot = (Sftproot) o;
        if (sftproot.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sftproot.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Sftproot{" +
            "id=" + getId() +
            ", incomingDirectory='" + getIncomingDirectory() + "'" +
            ", outgoingDirectory='" + getOutgoingDirectory() + "'" +
            ", errorDirectory='" + getErrorDirectory() + "'" +
            "}";
    }
}
