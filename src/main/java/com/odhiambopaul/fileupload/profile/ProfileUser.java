package com.odhiambopaul.fileupload.profile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Setter
@AllArgsConstructor
public class ProfileUser {
    private UUID id;
    private String userName;
    private String profileImageLink;

    public UUID getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public Optional<String> getProfileImageLink() {
        return Optional.ofNullable(profileImageLink);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfileUser that = (ProfileUser) o;
        return Objects.equals(id, that.id) && Objects.equals(userName, that.userName)
                && Objects.equals(profileImageLink, that.profileImageLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, profileImageLink);
    }
}
