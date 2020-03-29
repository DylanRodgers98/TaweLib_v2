package com.crowvalley.tawelib.dao;

import com.crowvalley.tawelib.model.resource.Resource;
import com.crowvalley.tawelib.model.resource.ResourceDTO;

import java.util.List;

public interface ResourceDAO extends BaseDAO {

    List<ResourceDTO> getAllResourceDTOs(Class<? extends Resource> clazz);

    List<ResourceDTO> search(String query, Class<? extends Resource> clazz);

}
