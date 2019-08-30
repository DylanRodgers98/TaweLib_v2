package com.crowvalley.service;

import com.crowvalley.dao.ResourceDAO;

import javax.annotation.Resource;

public class BookService {

    @Resource
    private ResourceDAO DAO;

    public void setDAO(ResourceDAO DAO) {
        this.DAO = DAO;
    }
}
