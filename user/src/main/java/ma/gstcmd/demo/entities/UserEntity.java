package ma.gstcmd.demo.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "Users")
@SQLDelete(sql = "UPDATE Users SET deleted_at=now() WHERE id=?")
@Where(clause = "deleted_at is null")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue
    Long id;

    @Column(nullable = false, unique = true)
    String userId;

    @Column(nullable = false)
    String userFirstName;

    @Column(nullable = false)
    String userLastName;

    @Column(nullable = false, unique = true)
    String userPassword;

    @Column(nullable = false)
    Boolean userState = false;

    @Column(nullable = false)
    String userRole;

    //this column refers to inserted occurrence
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+1")
    @Temporal(TemporalType.TIMESTAMP)
    Date insertedAt = new Date();

    //this column refers to modified occurrence
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+1")
    @Temporal(TemporalType.TIMESTAMP)
    Date updatedAt;

    //this column refers to deleted occurrence
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+1")
    @Temporal(TemporalType.TIMESTAMP)
    Date deletedAt;
}
