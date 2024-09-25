package br.com.java01.rocketseat_projeto_java_1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.java01.rocketseat_projeto_java_1.dto.CurseDTO;
import br.com.java01.rocketseat_projeto_java_1.model.Curse;
import br.com.java01.rocketseat_projeto_java_1.repository.CurseRepository;

@Service
public class CurseService {

    @Autowired
    private CurseRepository curseRepository;

    public Curse createCurse(CurseDTO curseDTO) {
        Curse curse = new Curse();
        curse.setName(curseDTO.getName());
        curse.setCategory(curseDTO.getCategory());
        return curseRepository.save(curse);
    }
}

