package com.odhiambopaul.fileupload.datastore;

import com.odhiambopaul.fileupload.profile.ProfileUser;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FakeUserProfileData {
    private static final List<ProfileUser> USER_LIST = new ArrayList<>();

    static {
        USER_LIST.add(new ProfileUser(UUID.fromString("9a274c26-8c1a-412e-b042-99243536ad99"), "PaulOdhiambo", null));
        USER_LIST.add(new ProfileUser(UUID.randomUUID(), "PeterKevin", null));

    }

    public List<ProfileUser> getUserProfiles() {
        return USER_LIST;
    }
}
