package com.travelPlus.v1.Service;


import com.travelPlus.v1.DTO.HotelDTO;
import com.travelPlus.v1.DTO.HotelImageDTO;
import com.travelPlus.v1.Entity.Contract;
import com.travelPlus.v1.Entity.Hotel;
import com.travelPlus.v1.Entity.HotelImage;
import com.travelPlus.v1.Repo.*;
import com.travelPlus.v1.Utill.ImageLoader;
import com.travelPlus.v1.Utill.VarList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class HotelService {

    @Autowired
    private HotelRepo hotelRepo;
    @Autowired
    private ContractRepo contractRepo;
    @Autowired
    private HotelImageRepo hotelImageRepo;
    @Autowired
    private SeasonRepo seasonRepo;
    @Autowired
    private RoomTypeRepo roomTypeRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ImageService imageService;
    @Autowired
    private ImageLoader imageLoader;

    public long addHotel(HotelDTO hotelDTO) throws IOException {
        if (hotelRepo.existsById(hotelDTO.getHotelId())) {
            return 0;
        } else {
            // Map HotelDTO to Hotel entity
            Hotel hotel = modelMapper.map(hotelDTO, Hotel.class);
            hotel.setHotelStatus(true);
            // Save the hotel entity
            hotelRepo.save(hotel);
            System.out.println(hotel.getHotelId());
            long hotelId=hotel.getHotelId();
            // If hotelDTO contains images, save them
            List<HotelImageDTO> imageDTOs = hotelDTO.getHotelImages();
            if (imageDTOs != null && !imageDTOs.isEmpty()) {
                for (HotelImageDTO imageDTO : imageDTOs) {
                    // Decode the Base64 encoded image path
                    String decodedImagePath = imageDTO.getImagePath();

                    // Load image data using ImageLoader
                    byte[] images = imageLoader.loadImageData(decodedImagePath);

                    // Create and save HotelImage entity
                    HotelImage hotelImage = new HotelImage();
                    hotelImage.setHotel(hotel);
                    hotelImage.setImageName(imageDTO.getImageName()); // Set image name
                    hotelImage.setImageData(images); // Set image data

                    // Save the hotel image entity
                    hotelImageRepo.save(hotelImage);
                }
            }

            return hotelId;
        }
    }


    public String updateHotel(HotelDTO hotelDTO){
        if(hotelRepo.existsById(hotelDTO.getHotelId())){
            hotelRepo.save(modelMapper.map(hotelDTO,Hotel.class));
            return VarList.RSP_SUCCESS;
        }
        else{
            return VarList.RSP_NO_DATA_FOUND;
        }
    }

    public List<HotelDTO> searchHotel(String destination, String checkInDate, String checkOutDate, int guestCount, int roomCount, Pageable pageable) {
        int guestsPerRoom = guestCount / roomCount;
        // Parse string dates to LocalDate
        LocalDate parsedCheckInDate = LocalDate.parse(checkInDate);
        LocalDate parsedCheckOutDate = LocalDate.parse(checkOutDate);

        // Call repository method to find available hotels with pagination
        Page<Hotel> hotelPage = hotelRepo.findAvailableHotels(destination, parsedCheckInDate, parsedCheckOutDate, guestsPerRoom, roomCount, pageable);
        List<Hotel> hotels = hotelPage.getContent();

        // Filter hotels based on room availability
        List<HotelDTO> availableHotels = new ArrayList<>();
        for (Hotel hotel : hotels) {
            // Check room availability for each hotel
            long hotelId = hotel.getHotelId();
            Contract contract = contractRepo.findByHotelHotelIdAndStartDateBeforeAndEndDateAfter(hotelId, parsedCheckOutDate, parsedCheckInDate);

            if (contract != null) {
                long contractId = contract.getContractId();
                // Fetch available room count for each room type in the contract
                List<Object[]> availableRooms = roomTypeRepo.findAvailableRoomTypes(contractId, parsedCheckInDate, parsedCheckOutDate);

                if (availableRooms.isEmpty()) {
                    // Log an error message to the console
                    System.out.println("No available rooms found for contract ID: " + contractId + " and dates " + parsedCheckInDate + " to " + parsedCheckOutDate);
                } else {
                    // Calculate total available rooms for all room types
                    int totalAvailableRooms = 0;
                    for (Object[] arr : availableRooms) {
                        Object availableRoomsObj = arr[1];
                        System.out.println("Available rooms object: " + availableRoomsObj);
                        if (availableRoomsObj != null) {
                            try {
                                long roomsCount = (Long) availableRoomsObj; // Cast to long instead of int
                                totalAvailableRooms += roomsCount;
                            } catch (ClassCastException e) {
                                System.err.println("Error: Unable to cast available rooms to long.");
                                e.printStackTrace();
                            }
                        }
                    }

                    // Check if the total available rooms are sufficient for the requested room count
                    if (totalAvailableRooms >= roomCount) {
                        availableHotels.add(modelMapper.map(hotel, HotelDTO.class));
                    }
                }
            }
        }
        return availableHotels;
    }





    public String deleteHotel(long hotelId) {
        // Check if the hotel exists
        Hotel hotel = hotelRepo.findById(hotelId).orElse(null);
        if (hotel != null) {
            // Set hotelStatus as false
            hotel.setHotelStatus(false);
            // Save the updated hotel
            hotelRepo.save(hotel);
            return VarList.RSP_SUCCESS;
        } else {
            return VarList.RSP_NO_DATA_FOUND;
        }
    }

    public Page<Hotel> getAllHotel(Pageable pageable) {
        Page<Hotel> hotelPage = hotelRepo.findAllActiveHotels(pageable);
        System.out.println(hotelPage);
        return hotelPage;
    }
    public HotelDTO getHotel(long hotelId) {
        if(hotelRepo.existsById(hotelId)){
            Hotel hotel=hotelRepo.findById(hotelId).orElse(null);
            return modelMapper.map(hotel,HotelDTO.class);
        }
        else{
            return null;
        }
    }
}
