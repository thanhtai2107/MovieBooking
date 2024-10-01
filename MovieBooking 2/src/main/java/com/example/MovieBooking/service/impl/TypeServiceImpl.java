package com.example.MovieBooking.service.impl;

import com.example.MovieBooking.entity.Type;
import com.example.MovieBooking.repository.TypeRepository;
import com.example.MovieBooking.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements ITypeService {
    @Autowired
    private TypeRepository typeRepository;

    @Override
    public List<Type> getAllTypes() {
        return typeRepository.findAll();
    }

    @Override
    public Type getTypeById(Long id) {
        return typeRepository.findById(id).orElse(null);
    }

    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    @Override
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }

    @Override
    public List<Type> getTypesByIds(List<Long> ids) {
        return typeRepository.findAllById(ids);
    }
}