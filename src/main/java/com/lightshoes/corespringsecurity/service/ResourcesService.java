package com.lightshoes.corespringsecurity.service;

import com.lightshoes.corespringsecurity.domain.dto.ResourcesDto;
import com.lightshoes.corespringsecurity.domain.dto.ResourcesResponseDto;
import com.lightshoes.corespringsecurity.domain.entity.Resources;

import java.util.List;

public interface ResourcesService {

    ResourcesResponseDto getResources(Long id);

    List<ResourcesResponseDto> getResources();

    Long createResources(ResourcesDto resources);

    Long deleteResources(Long id);

}
