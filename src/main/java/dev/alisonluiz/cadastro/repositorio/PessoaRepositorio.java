package dev.alisonluiz.cadastro.repositorio;

import dev.alisonluiz.cadastro.modelo.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PessoaRepositorio extends JpaRepository<Pessoa, Long> {

    @Query("SELECT COUNT(p) > 0 FROM Pessoa p WHERE p.cpf = :cpf")
    boolean existsByCpf(@Param("cpf") String cpf);

    @Query("SELECT p FROM Pessoa p WHERE " +
            "(:nome IS NULL OR lower(p.nome) LIKE lower(concat('%', :nome, '%'))) AND " +
            "(:cpf IS NULL OR lower(p.cpf) LIKE lower(concat('%', :cpf, '%')))")
    List<Pessoa> findAllByParams(
            @Param("nome") String nome,
            @Param("cpf") String cpf
    );

}
