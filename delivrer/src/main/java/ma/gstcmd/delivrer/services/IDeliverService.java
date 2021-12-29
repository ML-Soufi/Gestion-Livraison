package ma.gstcmd.delivrer.services;

import ma.gstcmd.delivrer.dtos.DeliverDto;
import ma.gstcmd.delivrer.dtos.DeliverDto1;
import ma.gstcmd.delivrer.requests.DeliverRequest;

public interface IDeliverService {
    DeliverDto1 getDelivers(int page);
    DeliverDto1 getDelivers(String firstName);
    DeliverDto getDeliver(String deliverId);
    Boolean existDeliver(String deliverId);
    DeliverDto addDeliver(DeliverRequest request);
    DeliverDto updateDeliver(String deliverId, DeliverRequest request);
    void deleteDeliver(String deliverId);
}
