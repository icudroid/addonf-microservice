package com.adloveyou.ms.service.impl;

import com.adloveyou.ms.service.AdCampaingService;
import com.adloveyou.ms.domain.AdCampaing;
import com.adloveyou.ms.repository.AdCampaingRepository;
import com.adloveyou.ms.repository.search.AdCampaingSearchRepository;
import com.adloveyou.ms.service.dto.AdCampaingDTO;
import com.adloveyou.ms.service.mapper.AdCampaingMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing AdCampaing.
 */
@Service
@Transactional
public class AdCampaingServiceImpl extends GenericServiceImpl<AdCampaing,AdCampaingDTO,Long> implements AdCampaingService{

    public AdCampaingServiceImpl(AdCampaingRepository adCampaingRepository, AdCampaingMapper adCampaingMapper, AdCampaingSearchRepository adCampaingSearchRepository) {
        super(adCampaingMapper,adCampaingSearchRepository,adCampaingRepository);
    }

}
