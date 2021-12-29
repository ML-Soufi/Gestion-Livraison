package ma.gstcmd.apigateway.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class ExceptionTemplate {
    int  errorStatus;
    Object errorMessage;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+1")
    Date errorTimestamps;
}
