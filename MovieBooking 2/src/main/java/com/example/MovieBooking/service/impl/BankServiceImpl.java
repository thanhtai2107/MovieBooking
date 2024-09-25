package com.example.MovieBooking.service.impl;

import com.example.MovieBooking.entity.Bank;
import com.example.MovieBooking.repository.BankRepository;
import com.example.MovieBooking.service.IBankService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankServiceImpl implements IBankService {

    private final BankRepository bankRepository;

    public BankServiceImpl(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public List<Bank> getAllBanks(){
        return bankRepository.findAll();
    }

    public Optional<Bank> getBankById (long id){
        return bankRepository.findById(id);
    }
}
