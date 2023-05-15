package dev.alisonluiz.cadastro.controller;

import dev.alisonluiz.cadastro.modelo.Pessoa;
import dev.alisonluiz.cadastro.repositorio.PessoaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private final PessoaRepositorio pessoaRepositorio;

    public PessoaController(PessoaRepositorio pessoaRepositorio) {
        this.pessoaRepositorio = pessoaRepositorio;
    }

    @PostMapping
    public ResponseEntity<Pessoa> createPessoa(@RequestBody Pessoa pessoa) {
        Pessoa salvarPessoa = pessoaRepositorio.save(pessoa);
        return ResponseEntity.ok(salvarPessoa);
    }

}
