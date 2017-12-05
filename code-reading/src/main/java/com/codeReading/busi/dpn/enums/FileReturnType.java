package com.codeReading.busi.dpn.enums;



public enum FileReturnType {
	/**dir**/
	DIR("dir"),
	/** xrefed - line numbered context */
    PLAIN("plain"),
    /** xrefed - summarizer context */
    XREFABLE("xrefable"),
    /** not xrefed - no context - used by diff/list */
    IMAGE("image"),
    /** not xrefed - no context */
    DATA("data"),
    /** not xrefed - summarizer context from original file */
    HTML("html"),
    OTHER("other"),
    ERROR("error")
	;
    private String typeName;
    private FileReturnType(String typename) {
        this.typeName = typename;
    }
    
    /**
     * Get the type name value used to tag lucene documents.
     * @return a none-null string.
     */
    public String typeName() {
        return typeName;
    }
    
    /**
     * Get the Genre for the given type name.
     * @param typeName name to check
     * @return {@code null} if it doesn't match any genre, the genre otherwise.
     * @see #typeName()
     */
    public static FileReturnType get(String typeName) {
        if (typeName == null) {
            return null;
        }
        for (FileReturnType g : values()) {
            if (g.typeName.equals(typeName)) {
                return g;
            }
        }
        return null;
    }
}
