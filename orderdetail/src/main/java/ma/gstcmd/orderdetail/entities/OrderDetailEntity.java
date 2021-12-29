package ma.gstcmd.orderdetail.entities;

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
import java.io.Serializable;
import java.util.Date;

@Entity(name = "OrderDetails")
@Table(uniqueConstraints = { @UniqueConstraint(name = "UniqueOrderIdAndProductId", columnNames = { "orderId", "productId" }) })
@SQLDelete(sql = "UPDATE Products SET deleted_at=now() WHERE id=?")
@FilterDef(name = "FilterData")
@Filter(name = "FilterData", condition = "deleted_at is null")
@Where(clause = "deleted_at is null")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDetailEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;

    String orderId;

    String productId;

    int orderedQuantity;

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
