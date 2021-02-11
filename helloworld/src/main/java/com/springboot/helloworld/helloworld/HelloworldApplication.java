package com.springboot.helloworld.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloworldApplication {

	public static void main(String[] args) {
		Atleta[] nadador = new Atleta[50];
		nadador[0] = new Atleta();
		nadador[0].addAtleta("Murilo", 12);
	 }

}
