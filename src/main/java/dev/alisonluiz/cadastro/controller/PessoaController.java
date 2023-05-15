package dev.alisonluiz.cadastro.controller;

import dev.alisonluiz.cadastro.modelo.Contato;
import dev.alisonluiz.cadastro.modelo.Pessoa;
import dev.alisonluiz.cadastro.repositorio.PessoaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaRepositorio pessoaRepositorio;

    @GetMapping
    public List<Pessoa> listar() {
        return pessoaRepositorio.findAll();
    }

    @PostMapping
    public ResponseEntity<Pessoa> incluir(@RequestBody Pessoa pessoa) {
        for ( Contato contato : pessoa.getContatos() ) {
            contato.setPessoa(pessoa);
        }
        pessoaRepositorio.save(pessoa);
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoa);
    }

}
