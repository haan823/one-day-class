package com.one.domain.file.domain;

//TODO enum 말고 상수로 선언한다면?
public enum ImageFileType {
    USER(0, "심사", "user"),
    REVIEW(1, "후기", "review"),
    CATEGORY(2, "카테고리", "category"),
    CLASS(3, "클래스", "class")
    ;

    private final int value;
    private final String detail;
    private final String path;

    ImageFileType(int value, String detail, String path) {
        this.value = value;
        this.detail = detail;
        this.path = path;
    }

    public int getValue() {
        return this.value;
    }

    public String getDetail() {
        return this.detail;
    }

    public String getPath() {
        return this.path;
    }
}
