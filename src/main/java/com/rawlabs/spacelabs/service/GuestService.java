package com.rawlabs.spacelabs.service;

import com.rawlabs.spacelabs.constant.ErrorCode;
import com.rawlabs.spacelabs.domain.dao.Guest;
import com.rawlabs.spacelabs.domain.dto.GuestRequestDto;
import com.rawlabs.spacelabs.exception.SpaceLabsException;
import com.rawlabs.spacelabs.repository.GuestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GuestService {
    private final GuestRepository guestRepository;

    @Autowired
    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    public Guest saveGuest(GuestRequestDto request){

        log.info("Begin do Save Guest with request :: {}", request);
        Guest guest;

        try {
            guest = Guest.builder()
                    .fullName(request.getFullName())
                    .phoneNumber(request.getPhoneNumber())
                    .email(request.getEmail())
                    .date(request.getDate())
                    .timeStart(request.getTimeStart())
                    .timeEnd(request.getTimeEnd())
                    .isDeleted(Boolean.FALSE)
                    .build();



            log.info("Register response :: ", guest.getId(), guest.getFullName());
        } catch (Exception e){
            log.error("Error Save Guest {}", e);
            throw new SpaceLabsException("Error Save Guest", ErrorCode.UNAUTHORIZED.name());
        }



        return  guestRepository.save(guest);
    }
}
