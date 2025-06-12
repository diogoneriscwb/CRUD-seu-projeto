package br.com.seuprojeto.model;

public class Usuario {
    private int id;
    private String nome;
    private String email;

//  Constructor
//    public Usuario(int id, String nome, String email) {
//        this.id = id;
//        this.nome = nome;
//        this.email = email;
//    } //constructor montado foi desativado por gerar alguns conflitos no código a solução dada pela IDE
//        foi de criar um constructor vazio.

    public Usuario() {

    }

    //  Getters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }
//  Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
