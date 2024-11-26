package xyz.jzab.oj.model.enums;

public enum UserRoleEnum {
    ADMIN("admin",3),
    TEACHER("teacher",2),
    STUDENT("student",1),
    USER("loginUser",0);


    private final String desc;
    private final int code;
    UserRoleEnum(String desc, int code){
        this.desc = desc;
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public int getCode() {
        return code;
    }
}
