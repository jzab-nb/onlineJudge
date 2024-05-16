package xyz.jzab.oj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import xyz.jzab.oj.model.entity.Exam;
import xyz.jzab.oj.service.ExamService;
import xyz.jzab.oj.mapper.ExamMapper;
import org.springframework.stereotype.Service;

/**
* @author 86131
* @description 针对表【exam(考试表)】的数据库操作Service实现
* @createDate 2024-05-13 09:44:47
*/
@Service
public class ExamServiceImpl extends ServiceImpl<ExamMapper, Exam>
    implements ExamService{

}




