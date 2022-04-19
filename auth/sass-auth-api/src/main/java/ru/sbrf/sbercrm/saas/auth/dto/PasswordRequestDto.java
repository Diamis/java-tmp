package ru.sbrf.sbercrm.sass.auth.dto;

import java.util.UUID;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor; 
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для запроса обновления пароля пользователя
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordRequestDto {
    private String oldPassword;
    
    @NotBlank
    private String newPassword;

    /**
     * Токен выданный пользователю
     * для смены пароля
     */
    private UUID token;

    /**
     * Флаг отвечающий за отправку email 
     * пользователю после успешного обновления пароля
     */
    private Boolean sendEmail;
}
