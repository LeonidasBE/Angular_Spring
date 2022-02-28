package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;

@RestController
public class StudentController {

	@Autowired
	StudentRepository studentRepository;

	@RequestMapping(value = "/student", method = RequestMethod.GET)
	public List<Student> listStudent() {
		return this.studentRepository.findAll();
	}

	@RequestMapping(value = "/student", method = RequestMethod.POST)
	public Student saveStudent(@RequestBody Student student) {
		return this.studentRepository.save(student);
	}

	@RequestMapping(value = "/student/{id}", method = RequestMethod.GET)
	public Optional<Student> findById(@PathVariable String id) {
		return this.studentRepository.findById(id);
	}

	@RequestMapping(value = "/student/{name}/name", method = RequestMethod.GET)
	public List<Student> findByName(@PathVariable String name) {
		return this.studentRepository.findByNameLikeIgnoreCase(name);
	}
}
