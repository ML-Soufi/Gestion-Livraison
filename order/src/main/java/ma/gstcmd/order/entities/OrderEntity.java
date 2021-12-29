package ma.gstcmd.order.entities;

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

@Entity(name = "Orders")
@SQLDelete(sql = "UPDATE Orders SET deleted_at=now() WHERE id=?")
@FilterDef(name = "FilterData")
@Filter(name = "FilterData", condition = "deleted_at is null")
@Where(clause = "deleted_at is null")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(unique = true, nullable = false)
    String orderId;

    @Column(nullable = false, length = 20)
    String clientId;

    @Column(nullable = true, length = 20)
    String deliverId;

    @Column(nullable = false, length = 150)
    String orderAddress;

    @Column(nullable = false, length = 1)
    String orderState;

    @Column(nullable = false)
    Double orderPrice;

    @Column(nullable = false)
    Double orderDeliveryPrice;

    @Column(nullable = true)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+1")
    @Temporal(TemporalType.TIMESTAMP)
    Date deliveryDate = null;

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
