package com.backend.commentservice.entity;

import com.backend.commentservice.repository.httpclient.UserProfileResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
@Document(value = "post_comment")
public class PostComment {
    @MongoId
    String id;
    String postId;
    String postName;
    String userId;
    String content;
    Integer replyCount;
    LocalDateTime createdDate;
    LocalDateTime updateDateTime;
    String username;
    String userAvatar;
    String ownerId;
}
