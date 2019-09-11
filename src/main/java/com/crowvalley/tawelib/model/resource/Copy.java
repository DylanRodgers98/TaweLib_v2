package com.crowvalley.tawelib.model.resource;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "copy")
public class Copy {

    @Id
    @GeneratedValue
    @Column(name = "copy_id")
    private Long id;

    private Long resourceId;

    private String resourceType;

    private Integer loanDurationAsDays;

    private String currentBorrower;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "copy")
    private List<CopyRequest> copyRequestList = new ArrayList<>();

    /**
     * Creates a copy.
     * @param resourceId the resourceId of the copy
     * @param resourceType The type of resource of the copy
     * @param loanDurationAsDays The minimum duration of a loan in days
     */
    public Copy(Long resourceId, String resourceType, Integer loanDurationAsDays) {
        this.resourceId = resourceId;
        this.resourceType = resourceType;
        this.loanDurationAsDays = loanDurationAsDays;
    }

    public Copy() {
    }

    public void addCopyRequest(CopyRequest copyRequest) {
        List<CopyRequest> newCopyRequestList = new ArrayList<>();
        for (CopyRequest cr : copyRequestList) {
            newCopyRequestList.add(cr);
        }
        newCopyRequestList.add(copyRequest);
        this.copyRequestList = newCopyRequestList;
    }

    public Long getId() {
        return id;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public String getResourceType() { return resourceType; }

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

    public String getCurrentBorrower() {
        return currentBorrower;
    }

    public void setCurrentBorrower(String borrowerUsername) {
        this.currentBorrower = borrowerUsername;
    }
}
