package cc.mrbird.febs.server.ding.controller;


import cc.mrbird.febs.common.core.entity.ding.PClass;
import cc.mrbird.febs.server.ding.controller.req.PClassReq;
import cc.mrbird.febs.server.ding.service.IPClassService;
import cc.mrbird.febs.common.core.entity.FebsResponse;
import cc.mrbird.febs.common.core.entity.QueryRequest;
import cc.mrbird.febs.common.core.exception.FebsException;
import cc.mrbird.febs.common.core.utils.FebsUtil;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.Map;

/**
 * Controller
 *
 * @author MrBird
 * @date 2020-11-06 08:35:28
 */
@Slf4j
@Validated
@RestController
@RequestMapping("pClass")
@RequiredArgsConstructor
public class PClassController {

    private final IPClassService pClassService;

    @GetMapping
    @PreAuthorize("hasAuthority('pClass:list')")
    public FebsResponse getAllPClasss(PClass pClass) {
        return new FebsResponse().data(pClassService.findPClasss(pClass));
    }

    @GetMapping("list")
    @PreAuthorize("hasAuthority('pClass:list')")
    public FebsResponse pClassList(QueryRequest request, PClassReq pClassReq) {
        Map<String, Object> dataTable =this.pClassService.findPClasss(request, pClassReq);
        return new FebsResponse().data(dataTable);
    }

//    @GetMapping("nameList")
//    @PreAuthorize("hasAuthority('pClass:list')")
//    public FebsResponse nameList(QueryRequest request, PClass pClass) {
//        pClass.setClassNameOne(true);
//        Map<String, Object> dataTable =this.pClassService.findPClasss(request, pClass);
//        return new FebsResponse().data(dataTable);
//    }

    @PostMapping
    @PreAuthorize("hasAuthority('pClass:add')")
    public void addPClass(@Valid PClass pClass) throws FebsException {
        try {
            this.pClassService.createPClass(pClass);
        } catch (Exception e) {
            String message = "新增PClass失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('pClass:update')")
    public void deletePClass(PClass pClass) throws FebsException {
        try {
            this.pClassService.deletePClass(pClass);
        } catch (Exception e) {
            String message = "删除PClass失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PutMapping
    @PreAuthorize("hasAuthority('pClass:update')")
    public void updatePClass(PClass pClass) throws FebsException {
        try {
            this.pClassService.updatePClass(pClass);
        } catch (Exception e) {
            String message = "修改PClass失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
}
