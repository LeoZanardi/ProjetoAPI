package org.example.controllers;

import org.example.constant.Constant;
import org.example.model.Aluno;
import org.example.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class AlunoController {


    @Autowired
    private AlunoService alunoService;


    @PostMapping(Constant.API_ALUNO)
    public ResponseEntity<Aluno> createAluno(@RequestBody Aluno aluno) {
      Aluno savedAluno = alunoService.save(aluno);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAluno);
    }

    @PutMapping(Constant.API_ALUNO + "/{id}")
    public ResponseEntity<Aluno>  update(@PathVariable String id,  @RequestBody Aluno aluno){

        Optional<Aluno> existingAluno = alunoService.findById(id);

        if(existingAluno.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Aluno alunoUpdate = existingAluno.get();
        alunoUpdate.setNome(aluno.getNome());
        alunoUpdate.setEmail(aluno.getEmail());
        alunoUpdate.setTelefone(aluno.getTelefone());
        alunoUpdate.setEndereco(aluno.getEndereco());

        Aluno savedAluno = alunoService.save(alunoUpdate);
        return ResponseEntity.ok(savedAluno);

    }
    @DeleteMapping(Constant.API_ALUNO + "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") String id){
        alunoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping(Constant.API_ALUNO)
    public ResponseEntity<List<Aluno>> findAll(){
        return ResponseEntity.ok(alunoService.findAll());
    }
    @GetMapping(Constant.API_ALUNO + "/{id}")
    public ResponseEntity<Optional<Aluno>> findById(@PathVariable("id") String id){
        return ResponseEntity.ok(alunoService.findById(id));
    }
}
