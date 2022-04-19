package ru.sbrf.sbercrm.sass.auth.model;

import java.io.Serializable; 
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne; 
import javax.persistence.Table;
import lombox.EqualsAndHashCode;
import lombox.Getter;
import lombox.Setter; 
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user_info")
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id", "username"})
public class UserSsoInfo extends Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    
    @Column(name = "username", nullable = false)
    private String userName;

    @Column(name = "code", nullable = false)
    private String code;
    
    @Column(name = "refresh_token")
    private String refreshToken;
    
    @Column(name = "refresh_token_expiry")
    private OffsetDateTime refreshTokenExpiry;

    @ManyToOne(targetEntity = UserInfo.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_info_id", nullable = false)
    private UserInfo user;

    @Embedded
    @JsonIgnore
    private CommonAuditFields commonAuditFields = new CommonAuditFields();
}
