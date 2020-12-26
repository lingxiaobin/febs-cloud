package cc.mrbird.febs.server.ding.controller.req;

import lombok.Data;

@Data
public class FlushReq extends SSalarySettingReq {

    private Boolean isDiffPayPlace;

    private String[] payPlaces;
}
