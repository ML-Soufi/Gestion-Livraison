package ma.gstcmd.delivrer.exceptions;

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
public class DeliverExceptionTemplate {
    int errorStatus;
    Object errorMessage;
    Date errorTimesStamps;
}
