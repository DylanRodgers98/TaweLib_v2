package com.crowvalley.tawelib.model.resource;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Test;

public class CopyTest {

    @Test
    public void testCreateCopyRequest() {
        JUnitSoftAssertions softly = new JUnitSoftAssertions();

        String username = "DylanRodgers98";
        Copy copy = new Copy();
        copy.createCopyRequest(username);

        softly.assertThat(copy.getCopyRequestList().get(0).getCopy())
                .as("Copy request list contains copy request for correct copy")
                .isEqualTo(copy);

        softly.assertThat(copy.getCopyRequestList().get(0).getUsername())
                .as("Copy request list contains copy request with correct username")
                .isEqualTo(username);
    }

    @Test
    public void testAddCopyRequest() {
        Copy copy = new Copy();
        CopyRequest copyRequest = new CopyRequest();

        copy.addCopyRequest(copyRequest);

        assertThat(copy.getCopyRequestList())
                .as("Copy request list contains new copy request")
                .contains(copyRequest);
    }
}
