package dev.alisonluiz.cadastro.modelo;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.List;

@Entity
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    private String cpf;

    private Instant dataNascimento;

    @OneToMany(targetEntity = Pessoa.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "pessoa_id")
    private List<Contato> contatos;

}
