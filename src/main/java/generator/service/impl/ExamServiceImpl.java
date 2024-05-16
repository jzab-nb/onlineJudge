package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.Exam;
import generator.service.ExamService;
import generator.mapper.ExamMapper;
import org.springframework.stereotype.Service;

/**
* @author 86131
* @description 针对表【exam(考试表)】的数据库操作Service实现
* @createDate 2024-05-13 11:37:08
*/
@Service
public class ExamServiceImpl extends ServiceImpl<ExamMapper, Exam>
    implements ExamService{

}




