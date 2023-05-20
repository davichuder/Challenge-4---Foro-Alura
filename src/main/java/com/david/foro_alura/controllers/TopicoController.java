package com.david.foro_alura.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.david.foro_alura.dto.topico.NuevoTopicoRequest;
import com.david.foro_alura.dto.topico.NuevoTopicoResponse;
import com.david.foro_alura.repository.TopicoRepository;

@RestController
@RequestMapping("/topico")
public class TopicoController {
    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping("/nuevo")
    public ResponseEntity<NuevoTopicoResponse> neuvoTopico(@RequestBody @Validated NuevoTopicoRequest nuevoTopico){
        return null;
    }
}
