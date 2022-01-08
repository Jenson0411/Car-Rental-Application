package com.example.byblosmobileapplication;

import static org.junit.Assert.assertEquals;
import android.content.ContentValues;
import android.content.Context;
import static org.junit.Assert.*;
import org.junit.Test;
import org.mockito.Mock;

public class TestCases {
    @Mock
    Context context;

    @Test
    public void validTestAddress(){
        CreateServiceRequest createServiceRequest= new CreateServiceRequest();
        boolean flag = createServiceRequest.validateAddress("123, Test");
        assertEquals(flag, true);
    }
    @Test
    public void invalidTestAddress(){
        CreateServiceRequest createServiceRequest= new CreateServiceRequest();
        boolean flag = createServiceRequest.validateAddress("123, Te12st");
        assertEquals(flag, false);
    }
    @Test
    public void validTestDriverLicenses(){
        CreateServiceRequest createServiceRequest= new CreateServiceRequest();
        boolean flag = createServiceRequest.validateDriverLisence("S9819-90101-25161");
        assertEquals(flag, true);
    }

    @Test
    public void invalidTestDriverLicenses(){
        CreateServiceRequest createServiceRequest= new CreateServiceRequest();
        boolean flag = createServiceRequest.validateDriverLisence("S98198-1234-25161");
        assertEquals(flag, false );
    }
    @Test
    public void invalidTestReturnDate(){
        CreateServiceRequest createServiceRequest= new CreateServiceRequest();
        boolean flag = createServiceRequest.validateDate("12/05/2021");
        assertEquals(flag, false );

    }

    @Test
    public void validTestReturnDate(){
        CreateServiceRequest createServiceRequest= new CreateServiceRequest();
        boolean flag = createServiceRequest.validateDate("12/25/2021");
        assertEquals(flag, true );
    }
    @Test
    public void validTestTime(){
        CreateServiceRequest createServiceRequest= new CreateServiceRequest();
        boolean flag = createServiceRequest.validateTime("21:00");
        assertEquals(flag, true);
    }
    @Test
    public void invalidTestTime(){
        CreateServiceRequest createServiceRequest= new CreateServiceRequest();
        boolean flag = createServiceRequest.validateTime("27:00");
        assertEquals(flag, false);
    }
    @Test
    public void validTestBranchProfileInfo(){
        ProfileManager profileManager = new ProfileManager();
        String numStreet = "123";
        String streetName = "Test";
        String phoneNumber = "613123456";
        boolean flag = profileManager.validation(streetName,phoneNumber,numStreet);
        assertEquals(flag, true);
    }

    @Test
    public void validTestPassword(){
        SignUpPage signUpPage = new SignUpPage();
        boolean flag = signUpPage.matchPassword("123", "123");
        assertEquals(flag, true);
    }

}
