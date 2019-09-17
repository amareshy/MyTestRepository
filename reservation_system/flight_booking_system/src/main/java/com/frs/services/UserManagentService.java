package com.frs.services;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frs.dao.UserManagementDAO;
import com.frs.entities.User;
import com.frs.generated.common.CommonTypes;
import com.frs.generated.usermanagement.CreateUserRequest;
import com.frs.generated.usermanagement.CreateUserResponse;
import com.frs.generated.usermanagement.GetUserProfileRequest;
import com.frs.generated.usermanagement.GetUserProfileResponse;
import com.frs.generated.usermanagement.UserManagementServiceGrpc.UserManagementServiceImplBase;
import com.frs.generated.usermanagement.VerifyCredentialsRequest;
import com.frs.generated.usermanagement.VerifyCredentialsResponse;

import io.grpc.stub.StreamObserver;

@Service
public class UserManagentService extends UserManagementServiceImplBase
{
    private static final Logger LOGGER = LoggerFactory
        .getLogger(UserManagentService.class);

    @Autowired
    private UserManagementDAO<User> userManagementDAO;

    @Override
    public void createUser(CreateUserRequest request,
        StreamObserver<CreateUserResponse> responseObserver)
    {
	LOGGER.debug("Create user started!!!");
	CommonTypes.Uuid userId = CommonTypes.Uuid.newBuilder()
	    .setValue(UUID.randomUUID().toString()).build();
	User user = new User();
	user.setUserId(userId.getValue());
	user.setUserName(request.getEmail());
	user.setFirstName(request.getFirstName());
	user.setLastName(request.getLastName());

	userManagementDAO.create(user);

	responseObserver
	    .onNext(CreateUserResponse.newBuilder().setUserId(userId).build());
	responseObserver.onCompleted();
    }

    @Override
    public void verifyCredentials(VerifyCredentialsRequest request,
        StreamObserver<VerifyCredentialsResponse> responseObserver)
    {

	responseObserver
	    .onNext(
	        VerifyCredentialsResponse.newBuilder()
	            .setUserId(CommonTypes.Uuid.newBuilder()
	                .setValue(UUID.randomUUID().toString()).build())
	            .build());

	responseObserver.onCompleted();
    }

    @Override
    public void getUserProfile(GetUserProfileRequest request,
        StreamObserver<GetUserProfileResponse> responseObserver)
    {
	super.getUserProfile(request, responseObserver);
    }
}
