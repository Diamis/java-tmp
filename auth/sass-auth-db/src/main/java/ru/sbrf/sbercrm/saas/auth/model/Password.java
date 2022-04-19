package ru.sbrf.sbercrm.sass.auth.model;

import java.io.Serializable; 
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombox.NoArgsConstructor;
import lombox.Getter;
import lombox.Setter; 

@Entity
@Table(name = "mobile_pin_code")
@Getter
@Setter
public class Password extends Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    protected Long id;

    @Column(name = "user_info_id")
    private UUID userInfoId;

    @Column(name = "password", nullable = false)
    private String value;

    @Column(name = "transported")
    private Boolean transported;

    @Embedded
    private CommonAuditFields commonAuditFields = new CommonAuditFields();

    public Password(UUID userInfoID, String value) {
        this.userInfoId = userInfoId;
        this.value = value;
    }
}
