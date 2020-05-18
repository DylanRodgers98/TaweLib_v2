package com.crowvalley.tawelib.dao;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatCode;

public class LoanDAOImplTest {

    private LoanDAOImpl loanDAO = new LoanDAOImpl();

    @Test
    public void testSearch_NoSearchParameters() {
        assertThatCode(() -> loanDAO.search(null, null, null))
                .as("Search method called with no parameters should thrown an exception")
                .isInstanceOf(IllegalArgumentException.class);
    }

}
