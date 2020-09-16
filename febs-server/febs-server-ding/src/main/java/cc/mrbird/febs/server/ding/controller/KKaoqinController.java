package cc.mrbird.febs.server.ding.controller;

import cc.mrbird.febs.common.core.entity.FebsResponse;
import cc.mrbird.febs.common.core.entity.QueryRequest;
import cc.mrbird.febs.common.core.entity.ding.KUser;
import cc.mrbird.febs.common.core.utils.FebsUtil;
import cc.mrbird.febs.server.ding.annotation.ControllerEndpoint;
import cc.mrbird.febs.server.ding.controller.req.MyReq;
import cc.mrbird.febs.server.ding.service.IKKaoqinService;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author MrBird
 * @date 2020-09-07 11:15:39
 */
@Slf4j
@Validated
@RestController
@RequestMapping("kKaoqin")
@RequiredArgsConstructor
public class KKaoqinController {

    private final IKKaoqinService kUserService;


    @GetMapping("my")
//    @PreAuthorize("hasAuthority('kKaoqin:list')")
    public FebsResponse kUserList(MyReq myReq) {
        return new FebsResponse().data( this.kUserService.findKDayDetails(myReq));
    }

    @GetMapping("selectClass")
//    @PreAuthorize("hasAuthority('kKaoqin:list')")
    public FebsResponse selectClass(MyReq myReq) {
        return new FebsResponse().data( this.kUserService.selectClass(myReq));
    }

    @GetMapping("selectLeave")
//    @PreAuthorize("hasAuthority('kKaoqin:list')")
    public FebsResponse selectLeave(MyReq myReq) {
        return new FebsResponse().data( this.kUserService.selectLeave(myReq));
    }
}
