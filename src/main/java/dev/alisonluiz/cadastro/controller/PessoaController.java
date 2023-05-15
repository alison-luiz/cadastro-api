package dev.alisonluiz.cadastro.controller;

import dev.alisonluiz.cadastro.modelo.Pessoa;
import dev.alisonluiz.cadastro.repositorio.PessoaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private final PessoaRepositorio pessoaRepositorio;

    public PessoaController(PessoaRepositorio pessoaRepositorio) {
        this.pessoaRepositorio = pessoaRepositorio;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> getPessoa(@PathVariable Long id) {
        Optional<Pessoa> pessoa = pessoaRepositorio.findById(id);
        if (pessoa.isPresent()) {
            return ResponseEntity.ok(pessoa.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<Object> getPessoas() {
        List<Pessoa> pessoas = pessoaRepositorio.findAll();
        return ResponseEntity.ok(pessoas);
    }

    @PostMapping
    public ResponseEntity<Pessoa> createPessoa(@RequestBody Pessoa pessoa) {
        Pessoa salvarPessoa = pessoaRepositorio.save(pessoa);
        return ResponseEntity.ok(salvarPessoa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> updatePessoa(@PathVariable Long id, @RequestBody Pessoa pessoa) {
        Optional<Pessoa> optionalPessoa = pessoaRepositorio.findById(id);
        if (optionalPessoa.isPresent()) {
            Pessoa updatedPessoa = optionalPessoa.get();
            updatedPessoa.setNome(pessoa.getNome());
            updatedPessoa.setCpf(pessoa.getCpf());
            updatedPessoa.setDataNascimento(pessoa.getDataNascimento());

            updatedPessoa.getContatos().clear();
            updatedPessoa.getContatos().addAll(pessoa.getContatos());

            pessoaRepositorio.save(updatedPessoa);
            return ResponseEntity.ok(updatedPessoa);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePessoa(@PathVariable Long id) {
        Optional<Pessoa> optionalPessoa = pessoaRepositorio.findById(id);
        if (optionalPessoa.isPresent()) {
            pessoaRepositorio.delete(optionalPessoa.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
