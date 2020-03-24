package com.crowvalley.tawelib.dao;

import com.crowvalley.tawelib.model.resource.Copy;

import java.util.List;

/**
 * Data Access Object interface for classes to implement that deal with
 * {@link Copy} objects persisted in a database.
 *
 * @author Dylan Rodgers
 */
public interface CopyDAO extends BaseDAO {

    List<Copy> getAllCopiesForResource(Long resourceId);

}