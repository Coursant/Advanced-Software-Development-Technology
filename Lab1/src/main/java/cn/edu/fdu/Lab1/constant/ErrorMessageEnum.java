package cn.edu.fdu.Lab1.constant;

public enum ErrorMessageEnum {
    /**
     * 未找到相应HTMLElement
     */
    PARNET_ELEMENT_NOTFOUND_ERROR("ELEMENT_NOTFOUND", "Error: Parent HTMLElement not found."),

    /**
     * 未找到相应HTMLElement
     */
    ELEMENT_NOTFOUND_ERROR("ELEMENT_NOTFOUND", "Error: Parent HTMLElement not found."),

    /**
     * ID不唯一
     */
    ID_UNIQUE_ERROR("ID_UNIQUE", "Error: ID must be unique."),

    /**
     * 不能删除根节点
     */
    ROOT_DELETE_ERROR("ROOT_DELETE_ERROR", "Error: Cannot delete root HTMLElements."),

    /**
     * 无效参数
     */
    INVALID_ARGUMENT("INVALID_ARGUMENT", "Invalid argument provided."),

    /**
     * 不能撤销
     */
    CANNOT_UNDO("CANNOT_UNDO" ,"This operation cannot be undone."),

    /**
     * 未知命令
     */
    UNKNOWN_COMMAND("UNKNOWN_COMMAND_ERROR", "Unknown command.");

    ErrorMessageEnum(String typeCode, String type) {
        this.typeCode = typeCode;
        this.type = type;
    }

    private String typeCode;
    private String type;

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
