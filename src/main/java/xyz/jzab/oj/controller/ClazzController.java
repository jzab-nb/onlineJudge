package xyz.jzab.oj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import xyz.jzab.oj.common.BaseResponse;
import xyz.jzab.oj.common.PageRequest;
import xyz.jzab.oj.common.ResultUtils;
import xyz.jzab.oj.model.dto.clazz.ClazzAddRequest;
import xyz.jzab.oj.model.dto.clazz.ClazzUpdateRequest;
import xyz.jzab.oj.model.vo.ClazzVo;
import xyz.jzab.oj.service.ClazzService;
import xyz.jzab.oj.service.UserService;

/**
 * @author JZAB
 * @from http://vip.jzab.xyz
 */
@RestController
@RequestMapping("/clazz")
public class ClazzController {
    @Resource
    ClazzService clazzService;

    @Resource
    UserService userService;

    // 增
    @PostMapping("/add")
    public BaseResponse<Boolean> addClazz(@RequestBody ClazzAddRequest clazzAddRequest, HttpServletRequest request){
        return ResultUtils.success(clazzService.addClazz(clazzAddRequest, userService.getLoginUser(request)));
    }

    // 删
    @PostMapping("/del/{id}")
    public BaseResponse<Boolean> delClazz(@PathVariable Integer id, HttpServletRequest request){
        return ResultUtils.success(clazzService.delClazz(id, userService.getLoginUser(request)));
    }

    // 改
    @PostMapping("/update")
    public BaseResponse<Boolean> updateClazz(@RequestBody ClazzUpdateRequest clazzUpdateRequest, HttpServletRequest request){
        return ResultUtils.success(clazzService.updateClazz(clazzUpdateRequest, userService.getLoginUser(request)));
    }

    // 查
    @GetMapping("/list/page")
    public BaseResponse<Page<ClazzVo>> pageClazz(PageRequest pageRequest){
        return ResultUtils.success(clazzService.listPage(pageRequest));
    }
}
