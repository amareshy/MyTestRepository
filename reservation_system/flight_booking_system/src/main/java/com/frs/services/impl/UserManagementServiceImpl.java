package com.frs.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frs.dao.UserManagementDAO;
import com.frs.entities.User;
import com.frs.services.UserManagentService;

@Service("userManagementService")
public class UserManagementServiceImpl implements UserManagentService
{
    @Autowired
    private UserManagementDAO<User> userMgmtDAo;

    @Override
    public void createUser()
    {
	User user = new User();
	user.setDob(1985);
	user.setUserId("123456");
	user.setUserName("Amaresh");
	userMgmtDAo.create(user);
    }

}
