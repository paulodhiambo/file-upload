package com.odhiambopaul.fileupload.profile;

import com.odhiambopaul.fileupload.bucket.BucketName;
import com.odhiambopaul.fileupload.filestore.FileStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.*;

@Service
public class UserProfileService {
    private final UserProfileDataAccessService userProfileDataAccessService;
    private final FileStore fileStore;

    @Autowired
    public UserProfileService(UserProfileDataAccessService userProfileDataAccessService, FileStore fileStore) {
        this.userProfileDataAccessService = userProfileDataAccessService;
        this.fileStore = fileStore;
    }

    List<ProfileUser> profileUsers() {
        return userProfileDataAccessService.profileUsers();
    }

    public void uploadUserProfileImage(UUID id, MultipartFile file) {
        //1. Check if the image is empty
        isFileEmpty(file);
        //2. check if the file is an image
        isImage(file);
        //3. check if the user exist in the database
        ProfileUser user = isUserPresent(id);
        //4. Grab user metadata from the user
        Map<String, String> metadata = getStringStringMap(file);
        //5. Save image in S3 and save the image path in the database
        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), user.getId());
        String fileName = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());
        try {
            fileStore.save(path, fileName, Optional.of(metadata), file.getInputStream());
            user.setProfileImageLink(fileName);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private Map<String, String> getStringStringMap(MultipartFile file) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        return metadata;
    }

    private ProfileUser isUserPresent(UUID id) {
        return userProfileDataAccessService.profileUsers()
                .stream()
                .filter(profileUser -> profileUser.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("User does not exist"));
    }

    private void isImage(MultipartFile file) {
        if (!Arrays.asList(IMAGE_PNG.getMimeType(), IMAGE_BMP.getMimeType(), IMAGE_GIF.getMimeType(), IMAGE_JPEG.getMimeType())
                .contains(file.getContentType())) {
            throw new IllegalStateException("Uploaded file is not an image");
        }
    }

    private void isFileEmpty(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot save an empty file");
        }
    }

    public byte[] downloadUserProfileImage(UUID id) {
        ProfileUser user = isUserPresent(id);
        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), user.getId());
        return user.getProfileImageLink()
                .map(key -> fileStore.download(path, key))
                .orElse(new byte[0]);
    }
}
