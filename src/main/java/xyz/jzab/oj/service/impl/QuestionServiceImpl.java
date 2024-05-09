package xyz.jzab.oj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import xyz.jzab.oj.model.entity.Question;
import xyz.jzab.oj.service.QuestionService;
import xyz.jzab.oj.mapper.QuestionMapper;
import org.springframework.stereotype.Service;

/**
* @author 86131
* @description 针对表【question(题目表)】的数据库操作Service实现
* @createDate 2024-05-09 14:21:41
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService{

}




