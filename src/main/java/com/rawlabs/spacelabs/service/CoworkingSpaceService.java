package com.rawlabs.spacelabs.service;

import com.rawlabs.spacelabs.domain.dao.CoworkingSpace;
import com.rawlabs.spacelabs.repository.CoworkingSpaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CoworkingSpaceService  {
    private final CoworkingSpaceRepository coworkingSpaceRepository;

    @Autowired
    public CoworkingSpaceService(CoworkingSpaceRepository coworkingSpaceRepository) {
        this.coworkingSpaceRepository = coworkingSpaceRepository;
    }


    public CoworkingSpace getById(Long id){
        Optional<CoworkingSpace> coworkingSpaceOptional = coworkingSpaceRepository.findById(id);
        if (coworkingSpaceOptional.isEmpty()) return null;
        return coworkingSpaceOptional.get();
    }

    public void deleteById (Long id){
        coworkingSpaceRepository.deleteById(id);
    }

    public List<CoworkingSpace> getCoworkingSpaces(String address, String name){
        if(StringUtils.isNotEmpty(address)){
            return coworkingSpaceRepository.findCoworkingSpaceByAddressIgnoreCase(address);
        }
        if(StringUtils.isNotEmpty(name)){
            return coworkingSpaceRepository.findCoworkingSpaceByNameIgnoreCase(name);
        }
        if(StringUtils.isNotEmpty(address) & StringUtils.isNoneEmpty(name)){
            return coworkingSpaceRepository.findCoworkingSpaceByNameAndAddressIgnoreCase(name, address);
        } else {
            return coworkingSpaceRepository.findAll();
        }

    }


}
