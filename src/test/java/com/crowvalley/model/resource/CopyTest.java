package com.crowvalley.model.resource;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

public class CopyTest {

    @Test
    public void testAddCopyRequest() {
        Copy copy = new Copy();
        CopyRequest copyRequest = new CopyRequest();

        copy.addCopyRequest(copyRequest);

        assertThat(copy.getCopyRequestList()).as("Copy request list contains new copy request").contains(copyRequest);
    }
}
