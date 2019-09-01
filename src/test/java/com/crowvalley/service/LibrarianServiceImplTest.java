package com.crowvalley.service;

import com.crowvalley.dao.LibrarianDAOImpl;
import com.crowvalley.model.user.Librarian;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LibrarianServiceImplTest {

    @Mock
    private LibrarianDAOImpl DAO;

    @InjectMocks
    private LibrarianServiceImpl librarianService;

    private Librarian librarian1;

    private Librarian librarian2;

    private Librarian librarian3;

    private Optional<Librarian> optionalLibrarian1;

    private Optional<Librarian> optionalLibrarian2;

    private Optional<Librarian> optionalLibrarian3;

    private List<Librarian> librarians;

    @Before
    public void setup() {
        librarian1 = new Librarian("Librarian 1", "", "", "", "", "", "", "", "", "", new Date(System.currentTimeMillis()), Long.valueOf(1));
        librarian2 = new Librarian("Librarian 2", "", "", "", "", "", "", "", "", "", new Date(System.currentTimeMillis()), Long.valueOf(2));
        librarian3 = new Librarian("Librarian 3", "", "", "", "", "", "", "", "", "", new Date(System.currentTimeMillis()), Long.valueOf(3));
        optionalLibrarian1 = Optional.of(librarian1);
        optionalLibrarian2 = Optional.of(librarian2);
        optionalLibrarian3 = Optional.of(librarian3);
        librarians = new ArrayList<>();
        librarians.add(librarian1);
        librarians.add(librarian2);
        librarians.add(librarian3);
    }

    @Test
    public void testGetWithUsername() {
        String username = "Librarian 1";

        when(DAO.getWithUsername(username)).thenReturn(optionalLibrarian1);

        assertThat(librarianService.getWithUsername(username)).as("Retrieve user from database with username 'Librarian 1'").isEqualTo(optionalLibrarian1);
    }

    @Test
    public void testGetWithStaffNumber() {
        Long staffNum = Long.valueOf(2);

        when(DAO.getWithStaffNumber(staffNum)).thenReturn(optionalLibrarian2);

        assertThat(librarianService.getWithStaffNumber(staffNum)).as("Retrieve user from database with staff number 2").isEqualTo(optionalLibrarian2);
    }

    @Test
    public void testGetWithUsername_noUserFromDAO() {
        String username = "User 4";

        when(DAO.getWithUsername(username)).thenReturn(Optional.empty());

        assertThat(librarianService.getWithUsername(username)).as("Retrieve empty Optional from DAO").isEqualTo(Optional.empty());
    }

    @Test
    public void testGetWithStaffNumber_noUserFromDAO() {
        Long staffNum = Long.valueOf(4);

        when(DAO.getWithStaffNumber(staffNum)).thenReturn(Optional.empty());

        assertThat(librarianService.getWithStaffNumber(staffNum)).as("Retrieve empty Optional from DAO").isEqualTo(Optional.empty());
    }

    @Test
    public void testGetAll() {
        when(DAO.getAll()).thenReturn(librarians);

        assertThat(librarianService.getAll()).as("Retrieve all librarians stored in the database").isEqualTo(librarians);
    }

    @Test
    public void testGetAll_noUsersFromDAO() {
        when(DAO.getAll()).thenReturn(new ArrayList<>());

        assertThat(librarianService.getAll().isEmpty()).as("Retrieve no librarians from DAO").isTrue();
    }

    @Test
    public void test_verifySave() {
        librarianService.save(librarian1);
        verify(DAO).save(eq(librarian1));
    }

    @Test
    public void test_verifyUpdate() {
        librarianService.update(librarian2);
        verify(DAO).update(eq(librarian2));
    }

    @Test
    public void test_verifyDelete() {
        librarianService.delete(librarian3);
        verify(DAO).delete(eq(librarian3));
    }
}
