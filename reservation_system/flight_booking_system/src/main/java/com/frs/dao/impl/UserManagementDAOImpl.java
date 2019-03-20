package com.frs.dao.impl;

import org.springframework.stereotype.Repository;

import com.frs.dao.UserManagementDAO;

@Repository("userManagementDAO")
public class UserManagementDAOImpl extends SimpleEntityCRUDDAO
    implements UserManagementDAO
{

}
