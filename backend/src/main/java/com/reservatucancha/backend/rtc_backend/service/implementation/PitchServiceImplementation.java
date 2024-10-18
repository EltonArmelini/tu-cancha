package com.reservatucancha.backend.rtc_backend.service.implementation;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.reservatucancha.backend.rtc_backend.dto.request.PitchRequestDTO;
import com.reservatucancha.backend.rtc_backend.dto.response.*;
import com.reservatucancha.backend.rtc_backend.entity.*;
import com.reservatucancha.backend.rtc_backend.entity.Schedule.ScheduleEntity;
import com.reservatucancha.backend.rtc_backend.exception.FileUploadLimitExceededException;
import com.reservatucancha.backend.rtc_backend.exception.ResourceNotFoundException;
import com.reservatucancha.backend.rtc_backend.repository.*;
import com.reservatucancha.backend.rtc_backend.service.IMapService;
import com.reservatucancha.backend.rtc_backend.service.IPitchService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Implementación del servicio de gestión de canchas.
 *
 * Esta clase proporciona la implementación de los métodos definidos en la interfaz {@link IPitchService}.
 * Maneja las operaciones relacionadas con la creación, actualización, eliminación y recuperación de canchas.
 */


@Service
public class PitchServiceImplementation implements IPitchService {

    private final IPitchRepository IPitchRepository;
    private final ISurfaceRepository ISurfaceRepository;
    private final ISportRepository ISportRepository;
    private final IServicesRepository iServicesRepository;
    private final MapServiceImplementation mapServiceImplementation;
    private final ScheduleServiceImplementation scheduleServiceImplementation;
    private final ModelMapper modelMapper;
    private final AmazonS3 s3Client;
    @Value("${aws.bucketName}")
    private String BUCKET_NAME;

    @Autowired
    public PitchServiceImplementation(com.reservatucancha.backend.rtc_backend.repository.IPitchRepository IPitchRepository, com.reservatucancha.backend.rtc_backend.repository.ISurfaceRepository ISurfaceRepository, com.reservatucancha.backend.rtc_backend.repository.ISportRepository ISportRepository, IServicesRepository iServicesRepository, MapServiceImplementation mapServiceImplementation, ScheduleServiceImplementation scheduleServiceImplementation, ModelMapper modelMapper, AmazonS3 s3Client) {
        this.IPitchRepository = IPitchRepository;
        this.ISurfaceRepository = ISurfaceRepository;
        this.ISportRepository = ISportRepository;
        this.iServicesRepository = iServicesRepository;
        this.mapServiceImplementation = mapServiceImplementation;
        this.scheduleServiceImplementation = scheduleServiceImplementation;
        this.modelMapper = modelMapper;
        this.s3Client = s3Client;
    }

    @Override
    public List<PitchResponseDTO> getAllPitches() {
        return IPitchRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PitchHomeResponseDTO> getAllPitchesForHome() {
        return IPitchRepository.findAll().stream()
                .map( pitch -> modelMapper.map(pitch,PitchHomeResponseDTO.class)).collect(Collectors.toList());
    }

    @Override
    public PitchResponseDTO getPitchById(Long id) {
        Pitch pitch = IPitchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pitch with id " + id + " not found"));
        return modelMapper.map(pitch,PitchResponseDTO.class);
    }


    /**
     * Crea una nueva cancha
     *
     * @param pitchRequest
     * @return {@link PitchResponseDTO} con la información de la cancha actualizada, o {@code null} si no se encuentra.
     * @throws ResourceNotFoundException Si no se encuentra algo
     */
    @Transactional
    @Override
    public PitchResponseDTO createPitch(PitchRequestDTO pitchRequest) {
        // check data provided exist
        Surface surface = ISurfaceRepository.findById(pitchRequest.getIdSurface())
                .orElseThrow(() -> new ResourceNotFoundException("Surface not found"));
        Sport sport = ISportRepository.findById(pitchRequest.getIdSport())
                .orElseThrow(() -> new ResourceNotFoundException("Sport not found"));
        Set<Services> services = pitchRequest.getIdServices().stream()
                .map(serviceId -> iServicesRepository.findById(serviceId)
                        .orElseThrow(() -> new ResourceNotFoundException("Service with ID " + serviceId + " not found")))
                .collect(Collectors.toSet());
        Pitch pitch = new Pitch();
        pitch.setSurface(surface);
        pitch.setSport(sport);
        pitch.setServices(services);
        pitch.setPitchMapUrl(mapServiceImplementation.createMap(pitchRequest.getPitchMapUrl()));
        pitch.setPitchDescription(pitchRequest.getPitchDescription());
        pitch.setPitchName(pitchRequest.getPitchName());
        pitch.setPitchAddress(pitchRequest.getPitchAddress());
        pitch.setPitchLocation(pitchRequest.getPitchLocation());
        pitch.setPitchPricePerHour(pitchRequest.getPitchPricePerHour());
        Pitch savedPitch = IPitchRepository.save(pitch);
        Set<ScheduleEntity> schedules = pitchRequest.getSchedules().stream()
                .map(scheduleRequestDTO -> scheduleServiceImplementation.createSchedule(scheduleRequestDTO, savedPitch.getId()))
                .collect(Collectors.toSet());
        savedPitch.setSchedules(schedules);
        return mapToDto(savedPitch);
    }

    /**
     * Actualiza una cancha existente con la información proporcionada.
     *
     * @param id Identificador de la cancha a actualizar.
     * @param pitchRequest Datos de la cancha a actualizar.
     * @return {@link PitchResponseDTO} con la información de la cancha actualizada, o {@code null} si no se encuentra.
     * @throws RuntimeException Si no se encuentra la superficie o el deporte especificado.
     */
    @Override
    public PitchResponseDTO updatePitch(Long id, PitchRequestDTO pitchRequest) {
        return IPitchRepository.findById(id)
                .map(pitch -> {
                    pitch.setPitchName(pitchRequest.getPitchName());
                    pitch.setPitchLocation(pitchRequest.getPitchLocation());
                    pitch.setPitchPricePerHour(pitchRequest.getPitchPricePerHour());
                    pitch.setPitchDescription(pitchRequest.getPitchDescription());
                    pitch.setPitchAddress(pitchRequest.getPitchAddress());
                    Surface surface = ISurfaceRepository.findById(pitchRequest.getIdSurface())
                            .orElseThrow(() -> new RuntimeException("Surface not found"));
                    pitch.setSurface(surface);
                    Sport sport = ISportRepository.findById(pitchRequest.getIdSport())
                            .orElseThrow(() -> new RuntimeException("Sport not found"));
                    pitch.setSport(sport);
                    Set<Services> services = pitchRequest.getIdServices().stream()
                            .map(serviceId -> iServicesRepository.findById(serviceId)
                                    .orElseThrow(() -> new ResourceNotFoundException("Service with ID " + serviceId + " not found")))
                            .collect(Collectors.toSet());
                    pitch.setServices(services);
                    MapsEntity map = mapServiceImplementation.updateMap(pitchRequest.getPitchMapUrl());
                    pitch.setPitchMapUrl(map);
                    Pitch updatedPitch = IPitchRepository.save(pitch);
                    Set<ScheduleEntity> schedules = pitchRequest.getSchedules().stream()
                            .map(scheduleRequestDTO -> scheduleServiceImplementation.createSchedule(scheduleRequestDTO, id))
                            .collect(Collectors.toSet());
                    pitch.setSchedules(schedules);
                    return mapToDto(updatedPitch);
                })
                .orElse(null); // O lanzar una excepción
    }

    @Override
    public void deletePitch(Long id) {
        Pitch pitch = IPitchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pitch not found"));
        IPitchRepository.delete(pitch);
    }

    @Override
    public List<String> PitchFileUpdate(Long id, List<MultipartFile> files) {
        if (files.size() > 4 || files.size() < 2) {
            throw new FileUploadLimitExceededException("Cannot upload more than 4 images.");
        }

        Pitch pitch = IPitchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pitch not found"));


        List<String> updatedImagePaths = new ArrayList<>();


        for (MultipartFile file : files) {
            try {

                String fileName = file.getOriginalFilename();
                String encodedFileName = encodeUrl(fileName);

                PutObjectRequest putObjectRequest = new PutObjectRequest(
                        BUCKET_NAME,
                        encodedFileName,
                        file.getInputStream(),
                        null
                );

                s3Client.putObject(putObjectRequest);


                updatedImagePaths.add(encodedFileName);
            } catch (IOException e) {
                throw new RuntimeException("Error uploading file to S3: " + e.getMessage(), e);
            }
        }


        pitch.setImagePaths(updatedImagePaths);
        IPitchRepository.save(pitch);


        return updatedImagePaths;

    }

    public List<String> PitchFileUpload(Long id, List<MultipartFile> files){

        if (files.size() > 4 || files.size() < 2 ) {
            throw new FileUploadLimitExceededException("Cannot upload more than 4 images.");
        }

        Pitch pitch = IPitchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pitch not found"));

        List<String> imagePaths = new ArrayList<>();

        for (MultipartFile file : files) {
            try{
                String fileName = file.getOriginalFilename();
                String encodedFileName = encodeUrl(fileName);

                PutObjectRequest putObjectRequest = new PutObjectRequest(
                        BUCKET_NAME,
                        file.getOriginalFilename(),
                        file.getInputStream(),
                        null);

                imagePaths.add(encodedFileName);
                s3Client.putObject(putObjectRequest);
            } catch (IOException e) {
                throw new RuntimeException("Error uploading file to S3: " + e.getMessage(), e);
            }
        }
        pitch.getImagePaths().addAll(imagePaths);
        IPitchRepository.save(pitch);

        return imagePaths;
    }

    @Override
    public List<PitchResponseDTO> getPitchesBySurface(Long surfaceId) {
        return IPitchRepository.findById(surfaceId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public String encodeUrl(String fileName) {
        try { //Se precisa el reemplazo de de espacion con "%20" para uso Bucket
            String encodedFileName = URLEncoder.encode(fileName, "UTF-8").replace("+", "%20");
            return String.format("https://%s.s3.amazonaws.com/%s", BUCKET_NAME, encodedFileName);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Encoding error: " + e.getMessage(), e);
        }
    }

    private PitchResponseDTO mapToDto(Pitch pitch) {
        return modelMapper.map(pitch, PitchResponseDTO.class);
    }

    @Override
    public List<PitchResponseDTO> getPitchesByLocation(String location) {
        return IPitchRepository.findByPitchLocationContaining(location).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PitchResponseDTO> getPitchesBySport(Long sportId) {
        Sport sport = ISportRepository.findById(sportId)
                .orElseThrow(() -> new ResourceNotFoundException("Sport not found"));
        return IPitchRepository.findBySport(sport).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PitchResponseDTO> getPitchesByLocationAndSport(String location, Long sportId) {
        Sport sport = ISportRepository.findById(sportId)
                .orElseThrow(() -> new ResourceNotFoundException("Sport not found"));
        return IPitchRepository.findByPitchLocationAndSport(location, sport).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
