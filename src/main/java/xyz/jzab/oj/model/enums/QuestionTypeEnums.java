package xyz.jzab.oj.model.enums;

/**
 * @author JZAB
 * @from http://vip.jzab.xyz
 */
public enum QuestionTypeEnums {
    singleChoice("singleChoice"),
    multiChoice("multiChoice"),
    shortAnswer("shortAnswer");

    private QuestionTypeEnums(String type){
        this.type = type;
    }
    private final String type;

    public String getType() {
        return type;
    }

    public static QuestionTypeEnums getEnumFromValue(String type){
        for (QuestionTypeEnums value : QuestionTypeEnums.values()) {
            if(value.getType().equals(type)) return value;
        }
        return null;
    }
}
