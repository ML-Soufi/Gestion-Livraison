package ma.gstcmd.order.shareds;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeliverDto {
    String deliverId;
    String deliverCni;
    String firstName;
    String lastName;
    String deliverSex;
    String deliverPhone;
    String deliverEmail;
    int deliverRate = 0;
    //this column refers to inserted occurrence
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+1")
    Date insertedAt = new Date();
    //this column refers to modified occurrence
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+1")
    Date modifiedAt;
    //this column refers to deleted occurrence
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+1")
    Date deletedAt;
}