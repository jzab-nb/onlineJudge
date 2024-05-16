package xyz.jzab.oj.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

/**
 * 学生考试记录表
 * @TableName stuexamrecord
 */
@TableName(value ="stuexamrecord")
@Data
public class Stuexamrecord implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 学生ID
     */
    private Integer stuId;

    /**
     * 考试ID
     */
    private Integer examId;

    /**
     * 考试答案
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<Map<String,Object>> answerList;

    /**
     * 考试分数
     */
    private Integer score;

    /**
     * 是否删除
     */
    private Integer isDelete;

    /**
     * 创建者
     */
    private Integer createUser;

    /**
     * 更新者
     */
    private Integer updateUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Stuexamrecord other = (Stuexamrecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getStuId() == null ? other.getStuId() == null : this.getStuId().equals(other.getStuId()))
                && (this.getExamId() == null ? other.getExamId() == null : this.getExamId().equals(other.getExamId()))
                && (this.getAnswerList() == null ? other.getAnswerList() == null : this.getAnswerList().equals(other.getAnswerList()))
                && (this.getScore() == null ? other.getScore() == null : this.getScore().equals(other.getScore()))
                && (this.getIsDelete() == null ? other.getIsDelete() == null : this.getIsDelete().equals(other.getIsDelete()))
                && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
                && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
                && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getStuId() == null) ? 0 : getStuId().hashCode());
        result = prime * result + ((getExamId() == null) ? 0 : getExamId().hashCode());
        result = prime * result + ((getAnswerList() == null) ? 0 : getAnswerList().hashCode());
        result = prime * result + ((getScore() == null) ? 0 : getScore().hashCode());
        result = prime * result + ((getIsDelete() == null) ? 0 : getIsDelete().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", stuId=").append(stuId);
        sb.append(", examId=").append(examId);
        sb.append(", answerList=").append(answerList);
        sb.append(", score=").append(score);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", createUser=").append(createUser);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}