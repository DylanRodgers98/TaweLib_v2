package com.crowvalley.model.resource;

import com.crowvalley.model.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "copy")
public class Copy {

    @Id
    @GeneratedValue
    private Long id;

    private Long resourceId;

    private Integer loanDurationAsDays;

    @OneToMany(mappedBy = "copy", cascade = CascadeType.ALL)
    private List<CopyRequest> copyRequestList = new ArrayList<>();

    /**
     * Creates a copy.
     * @param resourceId the resourceId of the copy
     * @param loanDurationAsDays The minimum duration of a loan in days
     */
    public Copy(Long resourceId, Integer loanDurationAsDays) {
        this.resourceId = resourceId;
        this.loanDurationAsDays = loanDurationAsDays;
    }

    public Copy() {
    }

    public Long getId() {
        return id;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public Integer getLoanDurationAsDays() {
        return loanDurationAsDays;
    }

    public void setLoanDurationAsDays(Integer loanDurationAsDays) {
        this.loanDurationAsDays = loanDurationAsDays;
    }

    public List<CopyRequest> getCopyRequestList() {
        return copyRequestList;
    }

    public void setCopyRequestList(List<CopyRequest> copyRequestList) {
        this.copyRequestList = copyRequestList;
    }
}
