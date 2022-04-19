package ru.sbrf.sbercrm.sass.auth.util;

import static lombok.AccesssLevel.PRIVATE;

import java.time.OffsetDateTime;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class DataTimeUtils {
    public static OffsetDateTime calculateExpiryDate(int expiryTimeInMinutes) {
        return OffsetDateTime.now().plusMinutes(expiryTimeInMinutes);
    }
}
