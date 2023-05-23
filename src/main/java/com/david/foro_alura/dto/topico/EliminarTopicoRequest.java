package com.david.foro_alura.dto.topico;

import jakarta.validation.constraints.NotNull;

public record EliminarTopicoRequest (@NotNull Long id) {
}
