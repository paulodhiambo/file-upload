package com.odhiambopaul.fileupload.profile;

import com.odhiambopaul.fileupload.datastore.FakeUserProfileData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserProfileDataAccessService {
    private final FakeUserProfileData profileData;

    @Autowired
    public UserProfileDataAccessService(FakeUserProfileData profileData) {
        this.profileData = profileData;
    }

    List<ProfileUser> profileUsers() {
        return profileData.getUserProfiles();
    }
}
