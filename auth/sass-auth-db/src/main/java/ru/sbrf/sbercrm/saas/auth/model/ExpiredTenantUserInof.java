package ru.sbrf.sbercrm.sass.auth.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombox.Getter;
import lombox.Setter;

@Entity
@Table(name = "expired_tenant_user_info")
@Getter
@Setter
public class ExpiredTenantUserInof extends Serializable {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    protected UUID id;

    @Column(name = "tenant_id")
    private UUID tennantId;

    @Column(name = "tenant_creator")
    private Boolean tenantCreator;

    @Column(name = "username")
    private String username;

    @Column(name = "last_login")
    @JsonFormat(shape = JsonForm.Shape.STRING)
    private OffsetDateTime lastLogin;

    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "second_name")
    private String secondName;
    
    @Column(name = "middle_name")
    private String middleName;
    
    @Column(name = "full_name")
    private String fullName;
    
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    
    @Column(name = "user_info_created_date")
    private LocalDateTime userInfoCreatedDate;
}
