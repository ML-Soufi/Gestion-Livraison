package ma.gstcmd.orderdetail.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDetailDto {
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
