package xyz.jzab.oj.ga;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import xyz.jzab.oj.common.ErrorCode;
import xyz.jzab.oj.exception.BusinessException;
import xyz.jzab.oj.model.entity.Question;
import xyz.jzab.oj.service.QuestionService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * @author JZAB
 * @from http://vip.jzab.xyz
 * 遗传算法实现类
 */
@Component
public class GA {

    @Resource
    QuestionService questionService;
    // 种群数量
    private final Integer paperNum = 100;
    // 发生交叉的概率
    private final Double crossP = 0.05;
    // 发生变异的概率
    private final Double mutateP = 0.05;
    // 迭代次数
    private final Integer iterNum = 100;

    private final Random rand = new Random(  );
    private final List<GAQuestion> gaQuestionList = new ArrayList<>();
    private List<GAPaper> gaPaperList = new ArrayList<>();
    // 遗传算法启动类
    public List<GAPaper> startGA(int bankId, int questionNum, double needDifficulty, int resultNum){
        // 每次启动先清空题目和试卷列表
        gaQuestionList.clear();
        gaPaperList.clear();

        // 获取题目
        getQuestions(bankId,questionNum);
        if(questionNum<=0) throw new BusinessException(ErrorCode.PARAMS_ERROR,"题目数量设置错误");
        if(needDifficulty>10 || needDifficulty<=0) throw new BusinessException(ErrorCode.PARAMS_ERROR,"难度设置错误");
        if(resultNum<=0 || resultNum>=paperNum) throw new BusinessException(ErrorCode.PARAMS_ERROR,"结果数量设置错误");
        // 初始化种群(许多试卷,一个试卷就是一个个体)
        initPapers(questionNum);
        updateFit(needDifficulty);
        for (int i = 0; i < iterNum; i++) {
            System.out.println("遗传算法运行中,第["+(i+1)+"]轮" );
            // 随机一些个体进行交叉操作
            // cross();
            // 随机一些个体进行变异操作
            mutation();
            // 计算并更新适应度
            updateFit(needDifficulty);
            // 根据选择算法生成新种群,大小和之前种群一致
            select();
        }
        updateFit(needDifficulty);
        return gaPaperList.subList(0,resultNum);
    }
    private int randomInt(int min, int max){
        // 随机生成一个整数,这个整数在[min,max)
        int raw = rand.nextInt();
        if(raw<0){raw = -raw;}
        return raw%(max-min)+min;
    }

    private void getQuestions(int bankId,int questionNum) {
        LambdaQueryWrapper<Question> wrapper = new LambdaQueryWrapper<>( );
        wrapper.eq(Question::getQuestionBankId, bankId);
        List<Question> questionList = questionService.list(wrapper);
        for (Question question : questionList) {
            gaQuestionList.add(
                    new GAQuestion(
                            question.getId( ),
                            (Integer) question.getContent( ).get("diff")
                    )
            );
        }
        if (gaQuestionList.size( ) < questionNum) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目数量不足");
        }
    }

    // 1.初始化种群,题目数量外面指定,随机选择题目,生成x套试卷
    private void initPapers(int questionNum){
        for (int i = 0; i < paperNum; i++) {
            GAPaper gaPaper = new GAPaper(questionNum);
            HashSet<Integer> selected = new HashSet<>(  );
            for (int j = 0; j < questionNum; j++) {
                int sel = randomInt(0, gaQuestionList.size( ));
                if(selected.contains(sel)){
                    j--;continue;
                }
                selected.add(sel);
                gaPaper.getQuestions()[j] = gaQuestionList.get(sel);
            }
            gaPaperList.add(gaPaper);
        }
    }

    // 计算并更新适应度
    private void updateFit(double needDifficulty){
        for (GAPaper gaPaper:gaPaperList) {
            double fit = 0;
            for (int j = 0; j < gaPaper.getQuestions().length; j++) {
                fit += Math.abs(
                        gaPaper.getQuestions()[j].getDiff() - needDifficulty
                );
            }
            gaPaper.setFit(fit);
            gaPaper.setP(0);
        }
    }

    private void select(){
        List<GAPaper> result = new ArrayList<>(  );
        double sumFit = 0;
        // 对适应度的倒数求和
        for(GAPaper gaPaper: gaPaperList) sumFit+=1/(gaPaper.getFit()+1);
        // 计算概率(适应度的倒数除以倒数的求和)
        for(GAPaper gaPaper: gaPaperList) gaPaper.setP(1/(gaPaper.getFit()+1)/sumFit);
        // 按照p从大到小排序
        gaPaperList.sort((o1, o2) -> {
            double tmp = o1.getP()-o2.getP();
            if(tmp>0){
                return 1;
            }else if(tmp<0){
                return -1;
            }else{
                return 0;
            }
        });
        // 计算累加概率
        double sumP = 0;
        for(GAPaper gaPaper: gaPaperList){
            gaPaper.setP(gaPaper.getP()+sumP);
            sumP = gaPaper.getP();
        }
        for (int i = 0; i < paperNum; i++) {
            int j=0;
            // 随机生成一个0-1的小数
            double randTarget = rand.nextDouble();
            // 遍历列表,找到第一次出现
            while(j<paperNum-1 && gaPaperList.get(j++).getP()<randTarget);
            GAPaper oldPaper = gaPaperList.get(j);
            GAPaper newPaper = new GAPaper( );
            newPaper.setQuestions(new GAQuestion[oldPaper.getQuestions().length]);
            System.arraycopy(oldPaper.getQuestions( ), 0, newPaper.getQuestions( ), 0, oldPaper.getQuestions( ).length);
            result.add(newPaper);
        }
        gaPaperList = result;
    }

    private void cross(){
        // 交叉
        // 4. 重复上述步骤m次,m根据交叉概率获取获取
        for(int i=0;i<paperNum*crossP;i+=1){
            // 1. 随机两个个体
            int sel1 = randomInt(0,paperNum);
            GAPaper gaPaper1 = gaPaperList.get(sel1);
            int sel2 = randomInt(0,paperNum);
            GAPaper gaPaper2 = gaPaperList.get(sel2);
            // 2. 确定交叉点
            int crossPoint = randomInt(0,gaPaper1.getQuestions().length);
            // 3. 交换交叉点之后的内容
            for(int j=crossPoint;j<gaPaper1.getQuestions().length;j++){
                GAQuestion temp;
                temp = gaPaper1.getQuestions()[j];
                gaPaper1.getQuestions()[j] = gaPaper2.getQuestions()[j];
                gaPaper2.getQuestions()[j] = temp;
            }
        }
    }

    private void mutation(){

        // 变异
        // 5.重复m次,m根据变异概率获取
        for(int i=0;i<paperNum*mutateP;i+=1){
            // 1.随机一个个体
            int sel = randomInt(0,paperNum);
            GAPaper gaPaper = gaPaperList.get(sel);
            // 如果题目列表已经包含了所有的基因,则无法变异
            if(gaQuestionList.size()==gaPaper.getQuestions().length){
                return;
            }
            // 2.确定变异点
            int mutatePoint = randomInt(0,gaPaper.getQuestions().length);
            // 3.随机一个题目
            // 4.替换变异点
            GAQuestion newQuestion = gaQuestionList.get(randomInt(0, gaQuestionList.size( )));
            // 查看是否存在重复的
            boolean haveDuplicate = false;
            for (int j=0;j<gaPaper.getQuestions().length;j++) {
                GAQuestion question = gaPaper.getQuestions( )[j];
                if (question.getId( ) == newQuestion.getId( ) && j!=mutatePoint) {
                    haveDuplicate = true;
                    break;
                }
            }
            if(haveDuplicate){
                i--;
            }else{
                gaPaper.getQuestions()[mutatePoint] = newQuestion;
            }
        }
    }
}