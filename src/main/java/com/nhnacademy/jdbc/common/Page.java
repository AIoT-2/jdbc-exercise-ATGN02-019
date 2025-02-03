package com.nhnacademy.jdbc.common;

import java.util.List;

public class Page<T> {

    // 페이징 처리된 content
    private final List<T> content;

    // 전체 행 갯수
    private final long totalCount;

    public Page(List<T> content, long totalCount) {
        this.content = content;
        this.totalCount = totalCount;
    }

    public List<T> getContent() {
        return content;
    }

    public long getTotalCount() {
        return totalCount;
    }
}