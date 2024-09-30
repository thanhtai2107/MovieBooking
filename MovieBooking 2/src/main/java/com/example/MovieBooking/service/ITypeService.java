package com.example.MovieBooking.service;

import com.example.MovieBooking.entity.Type;
import java.util.List;

public interface ITypeService {
    List<Type> getAllTypes();
    Type getTypeById(Long id);
    Type saveType(Type type);
    void deleteType(Long id);
    List<Type> getTypesByIds(List<Long> ids);
}