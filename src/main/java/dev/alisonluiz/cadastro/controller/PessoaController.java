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

    @GetMapping("/buscar")
    public ResponseEntity<Object> listarPessoas(@RequestParam(required = false) String nome,@RequestParam(required = false) String cpf) {
        List<Pessoa> pessoas = pessoaRepositorio.findAllByParams(nome, cpf);
        return ResponseEntity.ok(pessoas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> buscarPorId(@PathVariable Long id) {
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

        if (pessoaRepositorio.existsByCpf(pessoa.getCpf())) {
            return ResponseEntity.badRequest().body("Este CPF já esta cadastrado.");
        }

        Pessoa salvarPessoa = pessoaRepositorio.save(pessoa);
        return ResponseEntity.ok(salvarPessoa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarPessoa(@PathVariable Long id, @Valid @RequestBody Pessoa pessoa) {
        if (pessoa.getContatos() == null || pessoa.getContatos().isEmpty()) {
            return ResponseEntity.badRequest().body("É necessário fornecer pelo menos um contato.");
        }

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

    @DeleteMapping("/{id}")
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
