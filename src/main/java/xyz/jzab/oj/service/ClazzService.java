package xyz.jzab.oj.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import xyz.jzab.oj.common.PageRequest;
import xyz.jzab.oj.model.dto.clazz.ClazzAddRequest;
import xyz.jzab.oj.model.dto.clazz.ClazzUpdateRequest;
import xyz.jzab.oj.model.entity.Clazz;
import com.baomidou.mybatisplus.extension.service.IService;
import xyz.jzab.oj.model.entity.User;
import xyz.jzab.oj.model.vo.ClazzVo;

import java.util.List;

/**
* @author 86131
* @description 针对表【clazz(班级表)】的数据库操作Service
* @createDate 2024-04-29 10:39:18
*/
public interface ClazzService extends IService<Clazz> {
    boolean addClazz(ClazzAddRequest clazzAddRequest, User loginUser);
    boolean delClazz(Integer id, User loginUser);
    boolean updateClazz(ClazzUpdateRequest clazzUpdateRequest, User loginUser);
    Page<ClazzVo> listPage(PageRequest pageRequest);
    ClazzVo getVo(Clazz clazz);
    List<ClazzVo> getVos(List<Clazz> clazzList);
}
