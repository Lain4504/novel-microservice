package com.backend.novelservice.mapper;

import com.backend.novelservice.dto.request.NovelVolumeRequest;
import com.backend.novelservice.dto.response.NovelVolumeResponse;
import com.backend.novelservice.entity.NovelVolume;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface NovelVolumeMapper {
    // Bỏ qua chapters khi ánh xạ NovelVolumeRequest sang NovelVolume
    @Mapping(target = "chapters", ignore = true)
    NovelVolume toNovelVolume(NovelVolumeRequest request);

    // Không ánh xạ chapters khi trả về NovelVolumeResponse
    @Mapping(target = "chapters", ignore = true)
    NovelVolumeResponse toNovelVolumeResponse(NovelVolume novelVolume);

    // Cập nhật NovelVolume với NovelVolumeRequest, bỏ qua chapters
    @Mapping(target = "chapters", ignore = true)
    void updateNovelVolume(@MappingTarget NovelVolume novelVolume, NovelVolumeRequest request);
}
