package com.backend.novelservice.mapper;

import com.backend.novelservice.dto.request.NovelVolumeRequest;
import com.backend.novelservice.dto.response.NovelVolumeResponse;
import com.backend.novelservice.entity.NovelVolume;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface NovelVolumeMapper {
    NovelVolume toNovelVolume(NovelVolumeRequest request);
    NovelVolumeResponse toNovelVolumeResponse(NovelVolume novelVolume);
    void updateNovelVolume(@MappingTarget NovelVolume novelVolume, NovelVolumeRequest request);
}
