package dev.alisonluiz.cadastro.controller;

import dev.alisonluiz.cadastro.modelo.Pessoa;
import dev.alisonluiz.cadastro.repositorio.PessoaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void incluir(@RequestBody Pessoa pessoa) {
        pessoaRepositorio.save(pessoa);
    }

}
