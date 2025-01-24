package com.example.feeservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.feeservice.dto.StudentDTO;

@FeignClient(name = "student-service", url = "http://localhost:8080")
public interface StudentServiceClient {

	 @GetMapping("/students/{id}")
    StudentDTO getStudentById(@PathVariable("id") Long studentId);
}

