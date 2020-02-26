package com.crowvalley.tawelib.model.resource;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Optional;

public class CopyTest {

    @Rule
    public final JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    public void testCreateCopyRequest() {
        String username = "DylanRodgers98";
        Copy copy = new Copy();
        copy.createCopyRequest(username);

        CopyRequest copyRequest = copy.getCopyRequests().stream().findFirst().get();

        softly.assertThat(copyRequest.getCopy())
                .as("Copy request list contains copy request for correct copy")
                .isEqualTo(copy);

        softly.assertThat(copyRequest.getUsername())
                .as("Copy request list contains copy request with correct username")
                .isEqualTo(username);

        softly.assertThat(copyRequest.getRequestDate().toLocalDateTime().toLocalDate())
                .as("Copy request list contains copy request with today's date")
                .isEqualTo(new Date(System.currentTimeMillis()).toLocalDate());
    }

    @Test
    public void testDeleteCopyRequest() {
        String username = "DylanRodgers98";
        Copy copy = new Copy();
        CopyRequest copyRequest = new CopyRequest(copy, username, new Timestamp(System.currentTimeMillis()));
        copy.getCopyRequests().add(copyRequest);

        softly.assertThat(copy.getCopyRequests())
                .as("Copy's copy request list containing new copy request")
                .contains(copyRequest);

        copy.deleteCopyRequestForUser(username);
        softly.assertThat(copy.getCopyRequests()).
                as("Copy's copy request list after deleting the copy request from the list")
                .doesNotContain(copyRequest);
    }

}
