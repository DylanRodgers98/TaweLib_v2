package com.crowvalley.tawelib.model.resource;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;

import java.sql.Date;
import java.sql.Timestamp;

public class CopyTest {

    @Rule
    public final JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    public void testCreateCopyRequest() {
        String username = "DylanRodgers98";
        Copy copy = new Copy();
        copy.createCopyRequest(username);

        softly.assertThat(copy.getCopyRequests().get(0).getCopy())
                .as("Copy request list contains copy request for correct copy")
                .isEqualTo(copy);

        softly.assertThat(copy.getCopyRequests().get(0).getUsername())
                .as("Copy request list contains copy request with correct username")
                .isEqualTo(username);

        softly.assertThat(copy.getCopyRequests().get(0).getRequestDate().toLocalDateTime().toLocalDate())
                .as("Copy request list contains copy request with today's date")
                .isEqualTo(new Date(System.currentTimeMillis()));
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
