package com.crowvalley.model.resource;

import javax.persistence.*;

@Entity
public class Copy {

    @Id
    @GeneratedValue
    private Long id;

    private Long resourceId;

    private Integer loanDurationAsDays;

    /**
     * Creates a copy.
     * @param id the unique identifier of the copy
     * @param resourceId the resourceId of the copy
     * @param loanDurationAsDays The minimum duration of a loan in days
     */
    public Copy(Long id, Long resourceId, Integer loanDurationAsDays) {
        this.id = id;
        this.resourceId = resourceId;
        this.loanDurationAsDays = loanDurationAsDays;
    }

    public Copy() {
    }

    public Long getCopyId() {
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

}
