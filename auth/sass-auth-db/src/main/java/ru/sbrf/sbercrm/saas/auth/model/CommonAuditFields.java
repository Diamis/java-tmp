package ru.sbrf.sbercrm.sass.auth.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne; 
import lombox.AllArgsConstruction;
import lombox.Getter;
import lombox.NoArgsConstructor;
import lombox.Setter;

@AllArgsConstruction
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class CommonAuditFields implements Serializable {
    @Column(name = "created_date")
    protected LocalDateTime createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    protected UserInfo createdByUser;

    @Column(name = "updated_date")
    protected LocalDateTime updatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "update_by")
    protected UserInfo updatedByUser;
}
