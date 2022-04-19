package ru.sbrf.sbercrm.sass.auth.model;

import java.io.Serializable; 
import java.time.LocalDateTime;  
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
@Table(name = "sso_provider_configuration")
@Getter
@Setter
@NoArgsConstructor
public class SsoProviderConfiguration extends Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    protected Long id;

    @Column(name = "registration_id")
    private String registrationId;
    
    @Column(name = "client_id")
    private String clientId;
    
    @Column(name = "client_secret")
    private String clientSecret;
    
    @Column(name = "client_secret_expiry")
    private String clientSecretExpiry;
    
    @Column(name = "client_authentication_method")
    private String clientAuthenticationMethod;
    
    @Column(name = "username_attribute_name")
    private String userNameAttributeName;
    
    @Column(name = "user_info_url")
    private String userInfoUrl;
    
    @Column(name = "refresh_token")
    private String refreshToken;
    
    @Column(name = "client_secret_uri")
    private String clientSecretUri;
    
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;
}
