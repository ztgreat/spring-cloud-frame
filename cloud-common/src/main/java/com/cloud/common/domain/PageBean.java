package com.cloud.common.domain;

import com.github.pagehelper.Page;

import java.io.Serializable;
import java.util.List;

public class PageBean<T> implements Serializable {
    private static final long serialVersionUID = 8656597559014685635L;
    /**
     * 总记录数
     */
    private long totalElements;
    /**
     * 结果集
     */
    private List<T> content;
    /**
     * 第几页
     */
    private int number;
    /**
     * 每页记录数
     */
    private int size;

    /**
     * 总页数
     */
    private int totalPages;

    /**
     * 当前页的数量 <= pageSize，该属性来自ArrayList的size属性
     */
    private int numberOfElements;


    public PageBean() {

    }

    /**
     * 包装Page对象，因为直接返回Page对象，在JSON处理以及其他情况下会被当成List来处理，
     * 而出现一些问题。
     *
     * @param list page结果
     */
    public PageBean(List<T> list) {
        if (list instanceof Page) {
            Page<T> page = (Page<T>) list;
            //当前页, 这里要注意下，jpa分页是从第0页开始的，mybatis从第1页开始，现在这个信息前端不会用到，会在查询体现
            this.number = page.getPageNum();
            // 每页的数量
            this.size = page.getPageSize();
            // 总记录数
            this.totalElements = page.getTotal();
            // 总页数
            this.totalPages = page.getPages();
            // 结果集
            this.content = page;
            // 当前页的数量
            this.numberOfElements = page.size();
        }
    }


    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }
}
