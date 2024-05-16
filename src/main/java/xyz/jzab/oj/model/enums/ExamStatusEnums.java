package xyz.jzab.oj.model.enums;

/**
 * @author JZAB
 * @from http://vip.jzab.xyz
 */
public enum ExamStatusEnums {
    CREATE("创建"),
    START("开始"),
    END("结束");

    private final String status;

    ExamStatusEnums(String status){
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
