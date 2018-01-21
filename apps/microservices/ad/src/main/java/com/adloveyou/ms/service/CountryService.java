package com.adloveyou.ms.service;

import com.adloveyou.ms.domain.country.Country;
import com.adloveyou.ms.service.dto.CountryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Country.
 */
public interface CountryService extends GenericServiceWithDTO<Country, CountryDTO, Long> {

}
