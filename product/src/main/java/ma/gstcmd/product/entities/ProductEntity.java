package ma.gstcmd.product.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Date;

@Entity(name = "Products")
@SQLDelete(sql = "UPDATE Products SET deleted_at=now() WHERE id=?")
@FilterDef(name = "FilterData")
@Filter(name = "FilterData", condition = "deleted_at is null")
@Where(clause = "deleted_at is null")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductEntity {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;

    @Column(nullable = false, unique = true, length = 20)
    String productId;

    @Column(nullable = false, length = 100)
    String productName;

    @Column(nullable = false, length = 20)
    String productImage;

    @Column(nullable = false, length = 20)
    Double productPrice;

    @Column(nullable = false)
    int productQuantity;

    float productRate = 5;

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
