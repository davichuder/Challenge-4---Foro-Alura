package com.david.foro_alura.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.david.foro_alura.dto.topico.EliminarTopicoRequest;
import com.david.foro_alura.dto.topico.ModificarTopicoRequest;
import com.david.foro_alura.dto.topico.NuevoTopicoRequest;
import com.david.foro_alura.dto.topico.TopicoResponse;
import com.david.foro_alura.exceptions.ExisteException;
import com.david.foro_alura.services.TopicoService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/topicos")
public class TopicoController {
    @Autowired
    private TopicoService topicoService;

    @PostMapping
    @Transactional
    public ResponseEntity<TopicoResponse> nuevaTopico(
            @RequestBody @Valid NuevoTopicoRequest nuevaTopico) {
        return ResponseEntity.ok(new TopicoResponse(topicoService.nuevo(nuevaTopico)));
    }

    @RequestMapping
    public ResponseEntity<Page<TopicoResponse>> listadoTopicos(@PageableDefault(size = 10) Pageable paginacion) {
        return ResponseEntity.ok(topicoService.listado(paginacion));
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<Object> eliminarTopico(@RequestBody @Valid EliminarTopicoRequest eliminarTopico) throws ExisteException {
        topicoService.eliminar(eliminarTopico);
        return ResponseEntity.noContent().build();
    }

    // @PutMapping
    // @Transactional
    // public ResponseEntity<TopicoResponse> modificarTopico(
    //         @RequestBody @Valid ModificarTopicoRequest modificarTopico) throws ExisteException {
    //     return ResponseEntity.ok(new TopicoResponse(topicoService.modificar(modificarTopico)));
    // }

    // @GetMapping("/{id}")
    // public ResponseEntity<TopicoResponse> verTopico(@PathVariable Long id) throws ExisteException {
    //     return ResponseEntity.ok(new TopicoResponse(topicoService.ver(id)));
    // }
}
