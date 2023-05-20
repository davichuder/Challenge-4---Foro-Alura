package com.david.foro_alura.dto.login;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LoginResponse(@JsonProperty("access_token") String jwtToken) {
}
