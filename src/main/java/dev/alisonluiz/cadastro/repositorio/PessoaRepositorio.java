package dev.alisonluiz.cadastro.repositorio;

import dev.alisonluiz.cadastro.modelo.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepositorio extends JpaRepository<Pessoa, Long> {

}
