package ru.sbrf.sbercrm.sass.auth.model;

import lombok.AllArgsConstruction;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Информация о источнике пользователя
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor

public class MarketingDate {
    /**
     * Идентификатор клиента Google
     */
    private String cid;

    /**
     * Показывает, с какого URL поступил трафик на сайт.
     * Это значение используется для определения источиника трафика.
     * Формат значения URL.
     */
    private String referer;

    /**
     * Название кампании
     */
    private String utmCompaign;
    
    /**
     * Источник кампании
     */
    private String utmSource;
    
    /**
     * Канал кампании
     */
    private String utmMedium;
    
    /**
     * Содержание кампании
     */
    private String utmTerm;
}
