package ma.gstcmd.client.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "Clients")
// to perform an update instead delete query
@SQLDelete(sql = "UPDATE Clients SET deleted_at = now() where id=?")
// to filter retrieved data
@FilterDef(name = "FilterData")
@Filter(name = "FilterData", condition = "deleted_at is not null")
@Where(clause = "deleted_at is null")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true, length = 20)
    String clientId;

    @Column(nullable = false, length = 50)
    String clientFirstName;

    @Column(nullable = false, length = 50)
    String clientLastName;

    @Column(nullable = false, unique = true, length = 13)
    String clientPhone;

    @Column(nullable = false, unique = true, length = 50)
    String clientEmail;

    @Column(nullable = false, length = 150)
    String clientAddress;

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
