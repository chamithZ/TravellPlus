package com.travelPlus.v1.Service;


import com.travelPlus.v1.DTO.HotelDTO;
import com.travelPlus.v1.DTO.HotelImageDTO;
import com.travelPlus.v1.DTO.RoomTypeDTO;
import com.travelPlus.v1.DTO.UserDTO;
import com.travelPlus.v1.Entity.Hotel;
import com.travelPlus.v1.Entity.HotelImage;
import com.travelPlus.v1.Entity.Season;
import com.travelPlus.v1.Entity.User;
import com.travelPlus.v1.Repo.HotelImageRepo;
import com.travelPlus.v1.Repo.HotelRepo;
import com.travelPlus.v1.Repo.SeasonRepo;
import com.travelPlus.v1.Utill.ImageLoader;
import com.travelPlus.v1.Utill.VarList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
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
    private HotelImageRepo hotelImageRepo;
    @Autowired
    private SeasonRepo seasonRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ImageService imageService;
    @Autowired
    private ImageLoader imageLoader;

    public String addHotel(HotelDTO hotelDTO) {
        if (hotelRepo.existsById(hotelDTO.getHotelId())) {
            return VarList.RSP_DUPLICATED;
        } else {
            // Map HotelDTO to Hotel entity
            Hotel hotel = modelMapper.map(hotelDTO, Hotel.class);
            hotel.setHotelStatus(true);
            // Save the hotel entity
            hotelRepo.save(hotel);

            // If hotelDTO contains images, save them
            List<HotelImageDTO> imageDTOs = hotelDTO.getHotelImages();
            if (imageDTOs != null && !imageDTOs.isEmpty()) {
                for (HotelImageDTO imageDTO : imageDTOs) {
                    // Load image data using ImageLoader
                    try {
                        byte[] imageData = imageLoader.loadImageData(imageDTO.getImagePath());

                        // Create and save HotelImage entity
                        HotelImage hotelImage = new HotelImage();
                        hotelImage.setHotel(hotel);
                        hotelImage.setImageName(imageDTO.getImageName()); // Set image name
                        hotelImage.setImageData(imageData); // Set image data

                        // Save the hotel image entity
                        hotelImageRepo.save(hotelImage);
                    } catch (IOException e) {
                        // Handle error loading image data
                        e.printStackTrace(); // Or log the error
                        return VarList.RSP_FAIL;
                    }
                }
            }

            return VarList.RSP_SUCCESS;
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

    public List<HotelDTO> searchHotel(String destination, String checkInDate, String checkOutDate, int guestCount, int roomCount) {

        int guestsPerRoom=guestCount/roomCount;
        // Parse string dates to LocalDate

        LocalDate parsedCheckInDate = LocalDate.parse(checkInDate);
        LocalDate parsedCheckOutDate = LocalDate.parse(checkOutDate);


        // Call repository method to find available hotels
        List<Hotel> hotels = hotelRepo.findAvailableHotels(destination, parsedCheckInDate, parsedCheckOutDate, guestsPerRoom, roomCount);

        // Map Hotel entities to HotelDTOs

        return modelMapper.map(hotels, new TypeToken<ArrayList<HotelDTO>>() {}.getType());
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

    public List<HotelDTO> getAllHotel(){
        List<Hotel> hotelList=hotelRepo.findAll();
        return modelMapper.map(hotelList,new TypeToken<ArrayList<HotelDTO>>(){
        }.getType());
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
