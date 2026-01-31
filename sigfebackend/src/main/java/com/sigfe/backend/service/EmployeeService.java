package com.sigfe.backend.service;

import com.sigfe.backend.repository.EmployeeRepository;
import com.sigfe.backend.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;


    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
}
