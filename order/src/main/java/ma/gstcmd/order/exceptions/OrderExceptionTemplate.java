package ma.gstcmd.order.exceptions;

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
public class OrderExceptionTemplate {
    int errorStatus;
    Object errorMessage;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+1")
    Date timesStamps;
}
