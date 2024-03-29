package com.tidev.service;

import com.tidev.database.dao.repositories.UserAddressRepository;
import com.tidev.database.dao.repositories.UserRepository;
import com.tidev.dto.UserAddressCreateEditDto;
import com.tidev.dto.UserAddressReadDto;
import com.tidev.mapper.UserAddressCreateEditMapper;
import com.tidev.mapper.UserAddressReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserAddressService {

    private final UserAddressReadMapper userAddressReadMapper;
    private final UserAddressCreateEditMapper userAddressCreateEditMapper;
    private final UserAddressRepository userAddressRepository;
    private final UserRepository userRepository;

    public Optional<UserAddressReadDto> findByUserId(Integer userId) {
        return userAddressRepository.findByUserId(userId)
                .map(userAddressReadMapper::map);
    }

    @Transactional
    public UserAddressReadDto create(UserAddressCreateEditDto userAddressDto) {
        return Optional.of(userAddressDto)
                .map(userAddressCreateEditMapper::map)
                .map(userAddress -> {
                    userRepository.saveAndFlush(userAddress.getUser());
                    return userAddress;
                })
//                .map(userAddressRepository::save)
                .map(userAddressReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<UserAddressReadDto> updateByUserId(Integer userId, UserAddressCreateEditDto userAddressDto) {
        return userAddressRepository.findByUserId(userId)
                .map(userAddress -> userAddressCreateEditMapper.map(userAddressDto, userAddress))
                .map(userAddressRepository::saveAndFlush)
                .map(userAddressReadMapper::map);
    }

    @Transactional
    public boolean deleteByUserId(Integer userId) {
        return userAddressRepository.findByUserId(userId)
                .map(entity -> {
                    userAddressRepository.delete(entity);
                    userAddressRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
