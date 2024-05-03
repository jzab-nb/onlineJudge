package xyz.jzab.oj.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import xyz.jzab.oj.common.ErrorCode;
import xyz.jzab.oj.common.PageRequest;
import xyz.jzab.oj.exception.BusinessException;
import xyz.jzab.oj.model.dto.clazz.ClazzAddRequest;
import xyz.jzab.oj.model.dto.clazz.ClazzUpdateRequest;
import xyz.jzab.oj.model.entity.Clazz;
import xyz.jzab.oj.model.entity.User;
import xyz.jzab.oj.model.vo.ClazzVo;
import xyz.jzab.oj.service.ClazzService;
import xyz.jzab.oj.mapper.ClazzMapper;
import org.springframework.stereotype.Service;
import xyz.jzab.oj.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
* @author 86131
* @description 针对表【clazz(班级表)】的数据库操作Service实现
* @createDate 2024-04-29 10:39:18
*/
@Service
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz>
    implements ClazzService{

    @Resource
    UserService userService;

    @Override
    public boolean addClazz(ClazzAddRequest clazzAddRequest, User loginUser) {
        Clazz clazz = new Clazz();
        BeanUtils.copyProperties(clazzAddRequest,clazz);
        clazz.setUpdateUser(loginUser.getId());
        clazz.setCreateUser(loginUser.getId());
        return this.save(clazz);
    }

    @Override
    public boolean delClazz(Integer id, User loginUser) {
        Clazz clazz = this.getById(id);
        if(clazz==null) throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        clazz.setUpdateUser(loginUser.getId());
        this.updateById(clazz);
        return this.removeById(id);
    }

    @Override
    public boolean updateClazz(ClazzUpdateRequest clazzUpdateRequest, User loginUser) {
        Clazz clazz = new Clazz();
        BeanUtils.copyProperties(clazzUpdateRequest,clazz);
        clazz.setUpdateUser(loginUser.getId());
        return this.updateById(clazz);
    }

    @Override
    public Page<ClazzVo> listPage(PageRequest pageRequest) {
        Page<Clazz> page = new Page<>(pageRequest.getCurrent(), pageRequest.getSize());
        this.page(page);
        Page<ClazzVo> voPage = new Page<>(pageRequest.getCurrent(), pageRequest.getSize(), page.getTotal());
        voPage.setRecords(this.getVos(page.getRecords()));
        return voPage;
    }

    @Override
    public ClazzVo getVo(Clazz clazz) {
        ClazzVo vo = new ClazzVo( );
        vo.setTeacherName(userService.getById(
                clazz.getTeacherId()
        ).getName());
        BeanUtils.copyProperties(clazz,vo);
        return vo;
    }

    @Override
    public List<ClazzVo> getVos(List<Clazz> clazzList) {
        List<ClazzVo> voList = new ArrayList<>();
        for (Clazz clazz : clazzList) {
            voList.add(this.getVo(clazz));
        }
        return voList;
    }
}




