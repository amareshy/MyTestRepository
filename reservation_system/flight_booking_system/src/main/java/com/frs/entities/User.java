package com.frs.entities;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(name = "user")
public class User
{
    @PartitionKey(0)
    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "dob")
    private int dob;

    public String getUserId()
    {
	return userId;
    }

    public void setUserId(String userId)
    {
	this.userId = userId;
    }

    public String getUserName()
    {
	return userName;
    }

    public void setUserName(String userName)
    {
	this.userName = userName;
    }

    public int getDob()
    {
	return dob;
    }

    public void setDob(int dob)
    {
	this.dob = dob;
    }

    public String getFirstName()
    {
	return firstName;
    }

    public void setFirstName(String firstName)
    {
	this.firstName = firstName;
    }

    public String getLastName()
    {
	return lastName;
    }

    public void setLastName(String lastName)
    {
	this.lastName = lastName;
    }

}
