package ru.sbrf.sbercrm.sass.auth.model;
 
import java.time.OffsetDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany; 
import javax.persistence.Table;
import lombox.EqualsAndHashCode;
import lombox.Getter;
import lombox.Setter; 
import lombox.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.sbrf.sbercrm.sass.auth.util.DateTimeUtils;

@Entity
@Table(name = "verification_token")
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id", "token"})
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    
    @OneToOne(targetEntity = UserInfo.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_info_id", nullable = false)
    private UserInfo user;
    
    @Column(name = "hash", unique = true, nullable = false)
    private UUID hash;
    
    @Column(name = "expiry", nullable = false)
    private OffsetDateTime expiry;

    @Column(name = "type") 
    @Enumerated(value = EnumType.STRING)
    private TokenType type;

    public VerificationToken() {}

    public VerificationToken(int expiryInMinutes) {
        this.expiry = DateTimeUtils.calculateExpiryDate(expiryInMinutes);
    }

    @Embedded 
    @JsonIgnore
    private CommonAuditFields commonAuditFields = new CommonAuditFields();
}
