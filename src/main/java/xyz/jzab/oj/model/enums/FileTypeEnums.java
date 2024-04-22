package xyz.jzab.oj.model.enums;

/**
 * @author JZAB
 * @from http://vip.jzab.xyz
 */
public enum FileTypeEnums {
    USER_AVATAR("USER_AVATAR","image"),
    HOUSE_IMG("HOUSE_IMG","image");

    private FileTypeEnums(String type, String contentType){
        this.type = type;
        this.contentType = contentType;
    }
    private final String type;
    private final String contentType;

    public String getType() {
        return type;
    }

    public String getContentType() {
        return contentType;
    }

    public static FileTypeEnums getEnumFromValue(String type){
        for (FileTypeEnums value : FileTypeEnums.values( )) {
            if(value.getType().equals(type)) return value;
        }
        return null;
    }
}
