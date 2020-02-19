package com.crowvalley.tawelib.dao;

import com.crowvalley.tawelib.model.user.Librarian;
import com.crowvalley.tawelib.model.user.User;
import com.crowvalley.tawelib.util.DatabaseUtils;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Data Access Object for retrieving data about {@link User} objects
 * persisted in a database. This class provides methods to perform CRUD
 * operations on a database table that stores information about {@link User}
 * objects, using a Hibernate {@link SessionFactory} object to access
 * the database.
 *
 * @author Dylan Rodgers
 */
public class UserDAOImpl extends BaseDAOImpl implements UserDAO {

    @Override
    public List<String> getAllUsernames() {
        return DatabaseUtils.castList(
                    sessionFactory.getCurrentSession()
                    .createCriteria(User.class)
                    .setProjection(Projections.property("username"))
                    .list(),
                String.class);
    }

    /**
     * Retrieves a {@link User} from the database using the
     * {@link User}'s {@code username} and returns it wrapped in an
     * {@link Optional}. If a {@link User} with the passed {@code username}
     * doesn't exist within the database, an empty {@link User} is returned.
     *
     * @param username The username of the {@link User} to be retrieved
     * @return The requested {@link User} wrapped in an {@link Optional}
     * if it is found in the database, or an empty {@link Optional} if not.
     */
    @Override
    public Optional<? extends User> getWithUsername(String username) {
        User user = sessionFactory.getCurrentSession().get(User.class, username);
        return Optional.ofNullable(user);
    }

    /**
     * Retrieves a {@link Librarian} from the database using the
     * {@link Librarian}'s {@code staffNum} and returns it wrapped in an
     * {@link Optional}. If a {@link Librarian} with the passed {@code staffNum}
     * doesn't exist within the database, an empty {@link Optional} is returned.
     *
     * @param staffNum The username of the {@link Librarian} to be retrieved
     * @return The requested {@link Librarian} wrapped in an {@link Optional}
     * if it is found in the database, or an empty {@link Optional} if not.
     */
    @Override
    public Optional<Librarian> getLibrarianUserWithStaffNumber(Long staffNum) {
        List<Librarian> librarians = DatabaseUtils.getAll(Librarian.class, sessionFactory, "staffNum", staffNum);

        if (librarians.size() > 1) {
            throw new HibernateException("More than one Librarian retrieved from database with same staff number");
        }

        if (!librarians.isEmpty()) {
            return Optional.of(librarians.get(0));
        } else {
            return Optional.empty();
        }
    }

}
