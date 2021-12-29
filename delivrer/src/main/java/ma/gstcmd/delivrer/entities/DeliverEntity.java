package ma.gstcmd.delivrer.entities;

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

@Entity(name = "Delivers")
@SQLDelete(sql = "UPDATE Delivers SET deleted_at=now() WHERE id=?")
@FilterDef(name = "FilterData")
@Filter(name = "FilterData", condition = "deleted_at is null")
@Where(clause = "deleted_at is null")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeliverEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true, length = 20)
    String deliverId;

    @Column(nullable = false, unique = true, length = 10)
    String deliverCni;

    @Column(nullable = false, length = 50)
    String firstName;

    @Column(nullable = true, length = 50)
    String lastName;

    @Column(nullable = false, length = 1)
    String deliverSex;

    @Column(nullable = false, unique = true, length = 13)
    String deliverPhone;

    @Column(nullable = false, unique = true, length = 50)
    String deliverEmail;

    @Column(nullable = false)
    Boolean deliverState = true;

    @Column(nullable = false)
    float deliverRate = 5;

    //this column refers to inserted occurrence
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+1")
    @Temporal(TemporalType.TIMESTAMP)
    Date insertedAt = new Date();

    //this column refers to modified occurrence
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+1")
    @Temporal(TemporalType.TIMESTAMP)
    Date modifiedAt;

    //this column refers to deleted occurrence
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+1")
    @Temporal(TemporalType.TIMESTAMP)
    Date deletedAt;
}
