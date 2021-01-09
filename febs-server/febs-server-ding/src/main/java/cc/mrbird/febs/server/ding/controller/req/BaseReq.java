package cc.mrbird.febs.server.ding.controller.req;

import lombok.Data;

@Data
public class BaseReq {

    private String[] payPlaces;

    private String[] payComputeTypes;

    private String workDate;

    private String deptsAndUsers;

    private Boolean isDiffPayPlace;



}
