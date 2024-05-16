package xyz.jzab.oj.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import xyz.jzab.oj.common.BaseResponse;
import xyz.jzab.oj.common.PageRequest;
import xyz.jzab.oj.common.ResultUtils;
import xyz.jzab.oj.ga.GA;
import xyz.jzab.oj.ga.GAPaper;
import xyz.jzab.oj.model.dto.paper.GARequest;
import xyz.jzab.oj.model.dto.paper.PaperAddRequest;
import xyz.jzab.oj.model.dto.paper.PaperUpdateRequest;
import xyz.jzab.oj.model.entity.Paper;
import xyz.jzab.oj.model.entity.User;
import xyz.jzab.oj.model.vo.GAVo;
import xyz.jzab.oj.model.vo.PaperVo;
import xyz.jzab.oj.service.PaperService;
import xyz.jzab.oj.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JZAB
 * @from http://vip.jzab.xyz
 */
@RestController
@RequestMapping("/paper")
public class PaperController {
    @Resource
    GA ga;
    @Resource
    UserService userService;
    @Resource
    PaperService paperService;
    @PostMapping("/ga")
    public BaseResponse<GAVo> startGA(@RequestBody GARequest gaRequest){
        List<GAPaper> gaPapers = ga.startGA(gaRequest.getBankId( ), gaRequest.getQuestionNum( ), gaRequest.getNeedDifficulty( ), gaRequest.getResultNum( ));
        GAVo gaVo = new GAVo( );
        gaVo.setBankId(gaRequest.getBankId( ));
        gaVo.setNeedDifficulty(gaRequest.getNeedDifficulty( ));
        gaVo.setQuestionNum(gaRequest.getQuestionNum( ));
        gaVo.setResult(gaPapers);
        return ResultUtils.success(gaVo);
    }

    // 生成试卷
    @PostMapping("/create")
    public BaseResponse<Boolean> createPaper(@RequestBody PaperAddRequest paperAddRequest, HttpServletRequest request){
        User loginUser = userService.getLoginUser(request);
        Paper paper = new Paper();
        BeanUtils.copyProperties(paperAddRequest,paper);
        paper.setCreateUser(loginUser.getId());
        paper.setUpdateUser(loginUser.getId());
        return ResultUtils.success(paperService.save(paper));
    }

    @PostMapping("/update")
    public BaseResponse<Boolean> updatePaper(@RequestBody PaperUpdateRequest paperUpdateRequest, HttpServletRequest request){
        User loginUser = userService.getLoginUser(request);
        Paper paper = new Paper();
        BeanUtils.copyProperties(paperUpdateRequest,paper);
        paper.setUpdateUser(loginUser.getId());
        return ResultUtils.success(paperService.updateById(paper));
    }

    // 获取试卷列表
    @PostMapping("/list/page/ByTeacher/{id}")
    public BaseResponse<Page<PaperVo>> pagePaper(@PathVariable Integer id, @RequestBody PageRequest pageRequest){
        LambdaQueryWrapper<Paper> wrapper = new LambdaQueryWrapper<>(  );
        wrapper.eq(Paper::getCreateUser,id);
        Page<Paper> page = new Page<>(pageRequest.getCurrent( ), pageRequest.getSize( ));
        paperService.page(page, wrapper);
        Page<PaperVo> voPage = new Page<>(pageRequest.getCurrent(),pageRequest.getSize(),page.getTotal());
        voPage.setRecords(getVos(page.getRecords()));
        return ResultUtils.success(voPage);
    }

    @PostMapping("/del/{id}")
    public BaseResponse<Boolean> delPaper(@PathVariable Integer id){
        return ResultUtils.success(paperService.removeById(id));
    }

    public List<PaperVo> getVos(List<Paper> paperList){
        ArrayList<PaperVo> voList = new ArrayList<>( );
        for (Paper paper : paperList) {
            PaperVo vo = new PaperVo(  );
            BeanUtils.copyProperties(paper,vo);
            voList.add(vo);
        }
        return voList;
    }

}
