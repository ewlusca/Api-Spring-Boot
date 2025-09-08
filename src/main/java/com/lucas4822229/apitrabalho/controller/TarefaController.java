package com.lucas4822229.apitrabalho.controller;

import com.lucas4822229.apitrabalho.model.Tarefa;
import com.lucas4822229.apitrabalho.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaRepository tarefaRepository;

    // criar uma nova tarefa
    @PostMapping
    public Tarefa criarTarefa(@RequestBody Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    // listar todas as tarefas
    @GetMapping
    public List<Tarefa> listarTarefas() {
        return tarefaRepository.findAll();
    }

    // buscar tarefa por ID
    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarPorId(@PathVariable Long id) {
        Optional<Tarefa> tarefa = tarefaRepository.findById(id);
        return tarefa.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // atualizar uma tarefa
    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizarTarefa(@PathVariable Long id, @RequestBody Tarefa tarefaAtualizada) {
        return tarefaRepository.findById(id).map(tarefa -> {
            tarefa.setNome(tarefaAtualizada.getNome());
            tarefa.setDataEntrega(tarefaAtualizada.getDataEntrega());
            tarefa.setResponsavel(tarefaAtualizada.getResponsavel());
            return ResponseEntity.ok(tarefaRepository.save(tarefa));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // remover uma tarefa
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Long id) {
        return tarefaRepository.findById(id).map(tarefa -> {
            tarefaRepository.delete(tarefa);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
