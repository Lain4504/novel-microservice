package com.backend.novelservice.entity;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.backend.enums.NovelStatusEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators.In;

import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "novel")
public class Novel {
    @MongoId
    String id;
    String title;
    @ManyToMany
    Set<NovelCategory> categories;
    String authorId;
    String authorName;
    String description;
    Integer score;
    Integer bookStatus;
    Long visitCount;
    Long wordCount;
    Long commentCount;
    Integer isVip;
    Instant createdDate;
    Instant updateDateTime;
    List<String> volumeIds;
    @DBRef
    Image image;
    Integer chapterCount;
    String latestChapterTitle;
    String latestChapterId;
    Instant latestChapterTime;
    @Enumerated(EnumType.STRING)
    NovelStatusEnum status;
}
