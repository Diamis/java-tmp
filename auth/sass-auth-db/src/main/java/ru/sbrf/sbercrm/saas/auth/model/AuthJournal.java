package ru.sbrf.sbercrm.sass.auth.model;

import java.io.Serializable;
import java.lang.annotation.Inherited;
import java.time.OffsetDateTime;
import javax.persistence.*;
import lombox.Data;

@Entity
@Data
@Table(name = "auth_journal")
public class AuthJournal implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @MenyToOne
    @JoinColumn(name = "user_id")
    private UerInfo user;
    
    @Column(name = "auth_date")
    private OffsetDateTime authDate;
    
    @Column(name = "ip_address")
    private String ipAddress;
    
    @Column(name = "sessing_id")
    private String sessionId;
    
    @MenyToOne(targetEntity = UserSsoInfo.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_sso_info_id")
    private UerSsoInfo userSsoInfo;
}
