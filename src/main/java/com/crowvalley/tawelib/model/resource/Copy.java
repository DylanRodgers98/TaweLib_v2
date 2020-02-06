package com.crowvalley.tawelib.model.resource;

import com.crowvalley.tawelib.model.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Copy class for creating objects to store information about copies
 * to be persisted in a database.
 *
 * @author Dylan Rodgers
 */
@Entity
@Table(name = "copy")
public class Copy {

    private static final Logger LOGGER = LoggerFactory.getLogger(Copy.class);

    @Id
    @GeneratedValue
    @Column(name = "copy_id")
    private Long id;

    private Long resourceId;

    private ResourceType resourceType;

    private Integer loanDurationAsDays;

    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER, mappedBy = "copy")
    private List<CopyRequest> copyRequests = new ArrayList<>();

    public Copy(Long resourceId, ResourceType resourceType, Integer loanDurationAsDays) {
        this.resourceId = resourceId;
        this.resourceType = resourceType;
        this.loanDurationAsDays = loanDurationAsDays;
    }

    public Copy() {
    }

    /**
     * Creates a {@link CopyRequest} for this copy for a {@link User}
     * identified by the {@code username} passed in.
     *
     * @param username The username of the user for which to create a
     *                 {@link CopyRequest} for this Copy for.
     */
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

    /**
     * Deletes a {@link CopyRequest} for this copy created by the
     * {@link User} identified by the {@code username} passed in.
     *
     * @param username The username of the user for which to delete the
     *                 {@link CopyRequest} for this Copy for.
     */
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

    @Override
    public String toString() {
        return loanDurationAsDays + " day loan";
    }

    public Long getId() {
        return id;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

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
