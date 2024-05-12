package xyz.jzab.oj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import xyz.jzab.oj.model.entity.Paper;
import xyz.jzab.oj.service.PaperService;
import xyz.jzab.oj.mapper.PaperMapper;
import org.springframework.stereotype.Service;

/**
* @author 86131
* @description 针对表【paper(试卷表)】的数据库操作Service实现
* @createDate 2024-05-11 09:31:13
*/
@Service
public class PaperServiceImpl extends ServiceImpl<PaperMapper, Paper>
    implements PaperService{

}




