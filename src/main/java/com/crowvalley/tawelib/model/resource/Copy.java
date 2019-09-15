package com.crowvalley.tawelib.model.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "copy")
public class Copy {

    private static final Logger LOGGER = LoggerFactory.getLogger(Copy.class);

    public static final String BOOK_TYPE = "Book";

    public static final String DVD_TYPE = "Dvd";

    public static final String LAPTOP_TYPE = "Laptop";

    @Id
    @GeneratedValue
    @Column(name = "copy_id")
    private Long id;

    private Long resourceId;

    private String resourceType;

    private Integer loanDurationAsDays;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "copy")
    private List<CopyRequest> copyRequests = new ArrayList<>();

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

    public void createCopyRequest(String username) {
        boolean containsCopyRequestFromUser = false;

        for (CopyRequest copyRequest : copyRequests) {
            if (copyRequest.getUsername().equals(username)) {
                containsCopyRequestFromUser = true;
                LOGGER.info("User {} has already requested this copy (ID: {})", username, id);
            }
        }

        if (!containsCopyRequestFromUser) {
            CopyRequest copyRequest = new CopyRequest(this, username, new Timestamp(System.currentTimeMillis()));
            copyRequests.add(copyRequest);
            LOGGER.info("Request made for copy (ID: {}) by user {}", id, username);
        }
    }

    public void deleteCopyRequestForUser(String username) {
        boolean containsCopyRequestFromUser = false;
        List<CopyRequest> newCopyRequests = new ArrayList<>();

        for (CopyRequest copyRequest : copyRequests) {
            String usernameOfCurrentCopyRequest = copyRequest.getUsername();
            if (!usernameOfCurrentCopyRequest.equals(username)) {
                newCopyRequests.add(copyRequest);
            }
            if (usernameOfCurrentCopyRequest.equals(username)) {
                containsCopyRequestFromUser = true;
            }
        }
        this.copyRequests = newCopyRequests;

        if (containsCopyRequestFromUser) {
            LOGGER.info("Copy request made by user {} deleted from copy (ID: {})", username, id);
        } else {
            LOGGER.info("No copy request found for user {} for copy (ID: {})", username, id);
        }
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

    public List<CopyRequest> getCopyRequests() {
        return copyRequests;
    }

}
