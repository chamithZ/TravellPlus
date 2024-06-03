package com.travelPlus.UnitTests;

import com.travelPlus.v1.DTO.OfferDTO;
import com.travelPlus.v1.Entity.Offer;
import com.travelPlus.v1.Repo.ContractRepo;
import com.travelPlus.v1.Repo.OfferRepo;
import com.travelPlus.v1.Service.OfferService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OfferServiceTest {

    @Mock
    private OfferRepo offerRepo;

    @Mock
    private ContractRepo contractRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private OfferService offerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Order(1)
    public void testAddOffer() {
        // Mock data
        OfferDTO offerDTO = new OfferDTO();
        offerDTO.setOfferId(1L);
        when(offerRepo.save(any(Offer.class))).thenReturn(new Offer());

        // Call the service method
        String result = offerService.addOffer(offerDTO);

        // Assert the result
        assertEquals("011", result);
    }

    @Test
    @Order(2)
    public void testUpdateOffer() {
        // Mock data
        OfferDTO offerDTO = new OfferDTO();
        offerDTO.setOfferId(1L);
        when(offerRepo.existsById(anyLong())).thenReturn(true);
        when(offerRepo.save(any(Offer.class))).thenReturn(new Offer());

        // Call the service method
        String result = offerService.updateOffer(offerDTO);

        // Assert the result
        assertEquals("000", result);
    }

    @Test
    @Order(3)
    public void testDeleteOffer() {
        // Mock data
        long offerId = 1L;
        when(offerRepo.existsById(anyLong())).thenReturn(true);

        // Call the service method
        String result = offerService.deleteOffer(offerId);

        // Assert the result
        assertEquals("000", result);
    }


}
