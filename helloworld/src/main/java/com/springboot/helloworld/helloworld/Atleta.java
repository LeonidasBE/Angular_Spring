package com.springboot.helloworld.helloworld;

public class Atleta {
    private String nome;
    int idade;   

    public void addAtleta(String nome, int idade){
        this.nome = nome;
        this.idade = idade;
        if (this.idade <= 12)
               System.out.println("Piscina infantil");
        else if (this.idade > 12 && this.idade < 18)
               System.out.println("Piscina juvenil");
        else
               System.out.println("Idade não permitida para o torneio");
    }
}