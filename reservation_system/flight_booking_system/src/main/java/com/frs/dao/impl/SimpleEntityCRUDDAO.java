package com.frs.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.cassandradb.client.client.service.DBCluster;
import com.frs.dao.SimpleEntityDAO;

public abstract class SimpleEntityCRUDDAO<T> implements SimpleEntityDAO<T>
{
    @Autowired
    DBCluster myDbCluster;

    @Override
    public void create(T t)
    {
	myDbCluster.getConnectionRequestHandler().insert();
    }
}
