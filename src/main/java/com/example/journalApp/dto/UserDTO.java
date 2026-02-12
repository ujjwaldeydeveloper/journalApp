package com.example.journalApp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotEmpty
    private String name;
    private String email;
    @Schema(description = "set true, to get sentiment Analysis mail")
    private boolean sentimentAnalysis;
    @NotEmpty
    private String password;

}
