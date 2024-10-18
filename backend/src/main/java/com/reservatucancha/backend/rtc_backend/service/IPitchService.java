package com.reservatucancha.backend.rtc_backend.service;

import com.reservatucancha.backend.rtc_backend.dto.request.PitchRequestDTO;
import com.reservatucancha.backend.rtc_backend.dto.response.PitchHomeResponseDTO;
import com.reservatucancha.backend.rtc_backend.dto.response.PitchResponseDTO;
import com.reservatucancha.backend.rtc_backend.entity.Pitch;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IPitchService {
    List<PitchResponseDTO> getAllPitches();
    List<PitchHomeResponseDTO> getAllPitchesForHome();
    PitchResponseDTO getPitchById(Long id);
    PitchResponseDTO createPitch(PitchRequestDTO pitchRequest);
    PitchResponseDTO updatePitch(Long id, PitchRequestDTO pitchRequest);

    List<String> PitchFileUpload(Long id, List<MultipartFile> files);
    List<String> PitchFileUpdate(Long id, List<MultipartFile> files);
    void deletePitch(Long id);
    List<PitchResponseDTO> getPitchesBySurface(Long surfaceId);
    List<PitchResponseDTO> getPitchesByLocation(String location);
    List<PitchResponseDTO> getPitchesBySport(Long sportID);
    List<PitchResponseDTO> getPitchesByLocationAndSport(String location, Long sportID);

}
