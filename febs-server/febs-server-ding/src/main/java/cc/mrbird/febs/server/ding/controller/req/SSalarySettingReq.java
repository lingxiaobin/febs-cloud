package cc.mrbird.febs.server.ding.controller.req;

import lombok.Data;

@Data
public class SSalarySettingReq {

    private String workDate;
    private String type;
    private String key;
    private Integer value;

}
