package dev.alisonluiz.cadastro.controller;

import dev.alisonluiz.cadastro.modelo.Pessoa;
import dev.alisonluiz.cadastro.repositorio.PessoaRepositorio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaController {

    @Autowired
    private final PessoaRepositorio pessoaRepositorio;

    public PessoaController(PessoaRepositorio pessoaRepositorio) {
        this.pessoaRepositorio = pessoaRepositorio;
    }

    @GetMapping
    public ResponseEntity<Object> listarPessoas() {
        List<Pessoa> pessoas = pessoaRepositorio.findAll();
        return ResponseEntity.ok(pessoas);
    }

    @GetMapping("/id={id}")
    public ResponseEntity<Pessoa> lerId(@PathVariable Long id) {
        Optional<Pessoa> pessoa = pessoaRepositorio.findById(id);
        if (pessoa.isPresent()) {
            return ResponseEntity.ok(pessoa.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Object> incluirPessoa(@Valid @RequestBody Pessoa pessoa, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> erros = new ArrayList<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                erros.add(error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(erros);
        }

        if (pessoa.getContatos() == null || pessoa.getContatos().isEmpty()) {
            return ResponseEntity.badRequest().body("É necessário fornecer pelo menos um contato.");
        }

        Pessoa salvarPessoa = pessoaRepositorio.save(pessoa);
        return ResponseEntity.ok(salvarPessoa);
    }

    @PutMapping("/id={id}")
    public ResponseEntity<Pessoa> atualizarPessoa(@PathVariable Long id, @RequestBody Pessoa pessoa) {
        Optional<Pessoa> temPessoa = pessoaRepositorio.findById(id);
        if (temPessoa.isPresent()) {
            Pessoa atualizarPessoa = temPessoa.get();
            atualizarPessoa.setNome(pessoa.getNome());
            atualizarPessoa.setCpf(pessoa.getCpf());
            atualizarPessoa.setDataNascimento(pessoa.getDataNascimento());

            atualizarPessoa.getContatos().clear();
            atualizarPessoa.getContatos().addAll(pessoa.getContatos());

            pessoaRepositorio.save(atualizarPessoa);
            return ResponseEntity.ok(atualizarPessoa);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/id={id}")
    public ResponseEntity<Void> deletarPessoa(@PathVariable Long id) {
        Optional<Pessoa> optionalPessoa = pessoaRepositorio.findById(id);
        if (optionalPessoa.isPresent()) {
            pessoaRepositorio.delete(optionalPessoa.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
