package ru.sbrf.sbercrm.sass.auth.model;
 
import java.io.Serializable;
import java.time.LocalDateTime; 
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
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "mobile_pin_code")
@Getter
@Setter
public class MobilePinCode extends Serializable{
    private static final long serialVersionUID = 2280699294759030316L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    protected Long id;

    @Column(name = "user_info_id")
    private UUID userInfoId;

    @Column(name = "mobile_pin_code", nullable = false)
    private String value;

    @Column(name = "created_date")
    @CreationTimestamp
    protected LocalDateTime createdDate;
}
