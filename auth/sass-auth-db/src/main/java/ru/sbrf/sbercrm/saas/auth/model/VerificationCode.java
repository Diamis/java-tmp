package ru.sbrf.sbercrm.sass.auth.model;

import java.io.Serializable; 
import java.time.OffsetDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany; 
import javax.persistence.Table;
import lombox.EqualsAndHashCode;
import lombox.Getter;
import lombox.Setter; 
import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.sbrf.sbercrm.sass.auth.util.DateTimeUtils;

@Entity
@Table(name = "verification_code")
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id", "username"})
public class VerificationCode extends Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private UUID id;
    
    @OneToOne(targetEntity = UserInfo.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_info_id", nullable = false)
    private UserInfo user;
    
    @Column(name = "code", nullable = false)
    private String code;
    
    @Column(name = "expiry", nullable = false)
    private OffsetDateTime expiry;

    @Column(name = "deleted") 
    private Bollean deleted;

    public VerificationCode() {}

    public VerificationCode(int expiryInMinutes) {
        this.expiry = DateTimeUtils.calculateExpiryDate(expiryInMinutes);
    }

    @Embedded 
    @JsonIgnore
    private CommonAuditFields commonAuditFields = new CommonAuditFields();
}
