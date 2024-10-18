package com.reservatucancha.backend.rtc_backend.controller;

import com.reservatucancha.backend.rtc_backend.dto.request.PitchRequestDTO;
import com.reservatucancha.backend.rtc_backend.dto.response.PitchResponseDTO;
import com.reservatucancha.backend.rtc_backend.service.IPitchService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PitchControllerTest {

    @Mock
    private IPitchService pitchService;

    @InjectMocks
    private PitchController pitchController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUpdatePitch_Success() {
        Long pitchId = 1L;
        PitchRequestDTO pitchRequest = new PitchRequestDTO();
        // Suponiendo que configuras algunos valores para pitchRequest

        PitchResponseDTO pitchResponse = new PitchResponseDTO();
        // Configura valores para pitchResponse simulada

        // Simula el comportamiento del servicio
        when(pitchService.updatePitch(pitchId, pitchRequest)).thenReturn(pitchResponse);

        // Llama al controlador
        ResponseEntity<PitchResponseDTO> response = pitchController.updatePitch(pitchId, pitchRequest);

        // Verifica la respuesta
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pitchResponse, response.getBody());
    }

    @Test
    public void testUpdatePitch_NotFound() {
        Long pitchId = 1L;
        PitchRequestDTO pitchRequest = new PitchRequestDTO();

        doThrow(new EntityNotFoundException()).when(pitchService).updatePitch(pitchId, pitchRequest);

        ResponseEntity<PitchResponseDTO> response = pitchController.updatePitch(pitchId, pitchRequest);

        // Verificar que se devuelve un estado 404 NOT_FOUND
        assertEquals(404, response.getStatusCodeValue());
        verify(pitchService, times(1)).updatePitch(pitchId, pitchRequest);

        // Simula el comportamiento cuando no se encuentra la cancha
        //when(pitchService.updatePitch(pitchId, pitchRequest)).thenReturn(null);

        // Llama al controlador
        //ResponseEntity<PitchResponseDTO> response = pitchController.updatePitch(pitchId, pitchRequest);

        // Verifica la respuesta
        //assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}