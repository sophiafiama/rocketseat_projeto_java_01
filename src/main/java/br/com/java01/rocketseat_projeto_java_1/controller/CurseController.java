package br.com.java01.rocketseat_projeto_java_1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.java01.rocketseat_projeto_java_1.dto.CurseDTO;
import br.com.java01.rocketseat_projeto_java_1.model.Curse;
import br.com.java01.rocketseat_projeto_java_1.service.CurseService;

@RestController
@RequestMapping("/cursos")
public class CurseController {

    @Autowired
    private CurseService curseService;

    @PostMapping
    public ResponseEntity<Curse> createCurse(@RequestBody CurseDTO curseDTO) {
        Curse createdCurse = curseService.createCurse(curseDTO);
        return new ResponseEntity<>(createdCurse, HttpStatus.CREATED);
    }
}

