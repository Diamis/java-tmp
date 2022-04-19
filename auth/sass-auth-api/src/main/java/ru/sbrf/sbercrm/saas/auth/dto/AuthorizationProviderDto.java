package ru.sbrf.sbercrm.sass.auth.dto;

import lombok.AllArgsConstructor; 
import lombok.Data; 

@Data
@AllArgsConstructor 
public class AuthorizationProviderDto {
    private String provider;
}
