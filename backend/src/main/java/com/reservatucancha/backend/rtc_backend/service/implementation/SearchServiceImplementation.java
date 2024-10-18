package com.reservatucancha.backend.rtc_backend.service.implementation;


import com.reservatucancha.backend.rtc_backend.dto.response.PitchResponseDTO;
import com.reservatucancha.backend.rtc_backend.exception.InvalidSearchParameterException;
import com.reservatucancha.backend.rtc_backend.exception.ResourceNotFoundException;
import com.reservatucancha.backend.rtc_backend.repository.IPitchRepository;
import com.reservatucancha.backend.rtc_backend.repository.ISportRepository;
import com.reservatucancha.backend.rtc_backend.service.ISearchService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class SearchServiceImplementation implements ISearchService {

    private final IPitchRepository pitchRepository;
    private final ModelMapper modelMapper;
    private final ISportRepository sportRepository;

    @Autowired
    public SearchServiceImplementation(IPitchRepository pitchRepository, ModelMapper modelMapper, ISportRepository sportRepository) {
        this.pitchRepository = pitchRepository;
        this.modelMapper = modelMapper;
        this.sportRepository = sportRepository;
    }

    @Override
    public List<PitchResponseDTO> search(String location, String sportName) {
        if (location == null || location.trim().isEmpty()) {
            throw new InvalidSearchParameterException("Location cannot be null or empty");
        }
        if (sportName == null || sportName.trim().isEmpty()) {
            return pitchRepository.findByPitchLocationContaining(location).stream()
                    .map(pitch ->modelMapper.map(pitch, PitchResponseDTO.class))
                    .collect(Collectors.toList());
        }
        sportRepository.findBySportName(sportName).orElseThrow(()->new ResourceNotFoundException("Sport not found: " + sportName));

        return pitchRepository.findByPitchLocationContainingAndSportSportName(location,sportName).stream()
                .map(pitch ->modelMapper.map(pitch, PitchResponseDTO.class))
                .collect(Collectors.toList());
    }
}
