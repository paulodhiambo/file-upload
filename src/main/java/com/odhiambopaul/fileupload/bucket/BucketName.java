package com.odhiambopaul.fileupload.bucket;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BucketName {
    PROFILE_IMAGE("odhiambopaul-upload-app");
    private final String bucketName;
}
