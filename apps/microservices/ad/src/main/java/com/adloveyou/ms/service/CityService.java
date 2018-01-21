package com.adloveyou.ms.service;

import com.adloveyou.ms.domain.country.City;
import com.adloveyou.ms.service.dto.CityDTO;
import com.adloveyou.ms.service.impl.GenericServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing City.
 */
public interface CityService extends GenericServiceWithDTO<City, CityDTO, Long> {

}
