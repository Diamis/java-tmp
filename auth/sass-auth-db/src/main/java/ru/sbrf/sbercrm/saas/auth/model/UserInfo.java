package ru.sbrf.sbercrm.sass.auth.model;

import java.io.Serializable; 
import java.time.LocalDateTime;
import java.util.Collections;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombox.EqualsAndHashCode;
import lombox.Getter;
import lombox.Setter; 
import lombox.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user_info")
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id", "username"})
public class UserInfo extends UserDetails, Serializable {
    private static final long serialVersionUID = -6194224311469788002L;
    
    @Id
    @Column(name = "id", unique = true, nullable = false)
    protected UUID id;
    
    @Column(name = "tenant_id")
    protected UUID tenantId;
    
    @Column(name = "tenant_creator")
    protected Boolean tenantCreator;
    
    @Column(name = "application_name")
    protected String applicationName;
    
    @Column(name = "username")
    protected String userName;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JsonColumn(name = "current_password_id", nullable = false)
    private Password currentPasswordId;
    
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JsonColumn(name = "current_mobile_pin_code_id")
    private MobilePinCode currentMobilePinCode;

    @Column(name = "enabled")
    private boolean enable;
    
    @Column(name = "account_non_expired")
    private boolean accountNonExpired = true;
    
    @Column(name = "credentials_non_expired")
    private boolean credentialsNonExpired = false;
    
    @Column(name = "account_non_locked")
    private boolean accountNonLocked = true;
    
    @Column(name = "login_atempts")
    private Long loginAtempts;
    
    @Column(name = "increase_blocking_time")
    private Long increaseBlockingTime;
    
    @Column(name = "temporary_blocking_time")
    private OffsetDateTime temporaryBlockingTime;
    
    @Column(name = "last_login")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private OffsetDateTime lastLogin;
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "second_name")
    private String secondName;
    
    @Column(name = "middle_name")
    private String middleName;
    
    @Column(name = "full_name")
    private String fullName;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "last_login_sso_provider")
    private String lastLoginSsoProvider;
    
    @Embedded
    @JsonIgnore
    private CommonAuditFields = commonAuditFields = new CommonAuditFields();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.EMPTY_LIST;
    }
    
    @Override
    public String getPassword() {
        return currentPassword.getValue();
    }
}
