package cc.mrbird.febs.server.ding.controller;

import cc.mrbird.febs.common.core.entity.ding.ESaleMonth;
import cc.mrbird.febs.server.ding.service.IESaleMonthService;
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
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * Controller
 *
 * @author MrBird
 * @date 2020-10-26 10:43:46
 */
@Slf4j
@Validated
@RestController
@RequestMapping("eSaleMonth")
@RequiredArgsConstructor
public class ESaleMonthController {

    private final IESaleMonthService eSaleMonthService;

    @GetMapping
    @PreAuthorize("hasAuthority('eSaleMonth:list')")
    public FebsResponse getAllESaleMonths(ESaleMonth eSaleMonth) {
        return new FebsResponse().data(eSaleMonthService.findESaleMonths(eSaleMonth));
    }

    @GetMapping("list")
    @PreAuthorize("hasAuthority('eSaleMonth:list')")
    public FebsResponse eSaleMonthList(QueryRequest request, ESaleMonth eSaleMonth) {
        Map<String, Object> dataTable = FebsUtil.getDataTable(this.eSaleMonthService.findESaleMonths(request, eSaleMonth));
        return new FebsResponse().data(dataTable);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('eSaleMonth:add')")
    public void addESaleMonth(@Valid ESaleMonth eSaleMonth) throws FebsException {
        try {
            this.eSaleMonthService.createESaleMonth(eSaleMonth);
        } catch (Exception e) {
            String message = "新增ESaleMonth失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('eSaleMonth:delete')")
    public void deleteESaleMonth(ESaleMonth eSaleMonth) throws FebsException {
        try {
            this.eSaleMonthService.deleteESaleMonth(eSaleMonth);
        } catch (Exception e) {
            String message = "删除ESaleMonth失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PutMapping
    @PreAuthorize("hasAuthority('eSaleMonth:update')")
    public void updateESaleMonth(ESaleMonth eSaleMonth) throws FebsException {
        try {
            this.eSaleMonthService.updateESaleMonth(eSaleMonth);
        } catch (Exception e) {
            String message = "修改ESaleMonth失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @PostMapping("flushSaleMonth")
    public void flushSaleMonth(String str) throws FebsException {
        try {
            Thread.sleep(5000);
            ESaleMonth eSaleMonth = new ESaleMonth();
            eSaleMonth.setMonth(new Date());
            eSaleMonth.setValue(BigDecimal.ZERO);
            this.eSaleMonthService.createESaleMonth(eSaleMonth);
        } catch (Exception e) {
            String message = "新增ESaleMonth失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

}
