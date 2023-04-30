package com.hostfully.booking.config;

import org.springframework.data.domain.Page;

import java.util.List;

public class CustomPage<T> {
  List<T> data;
  CustomPageable pageable;

  public List<T> getData() {
    return data;
  }

  public void setData(List<T> data) {
    this.data = data;
  }

  public CustomPageable getPageable() {
    return pageable;
  }

  public void setPageable(CustomPageable pageable) {
    this.pageable = pageable;
  }

  public CustomPage(Page<T> page) {
    this.data = page.getContent();
    this.pageable = new CustomPageable(page.getPageable().getPageNumber(),
            page.getPageable().getPageSize(), page.getTotalElements());


  }

  static class CustomPageable {
    int pageNumber;
    int pageSize;
    long totalElements;

    public CustomPageable(int pageNumber, int pageSize, long totalElements) {

      this.pageNumber = pageNumber;
      this.pageSize = pageSize;
      this.totalElements = totalElements;
    }

    public int getPageNumber() {
      return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
      this.pageNumber = pageNumber;
    }

    public int getPageSize() {
      return pageSize;
    }

    public void setPageSize(int pageSize) {
      this.pageSize = pageSize;
    }

    public long getTotalElements() {
      return totalElements;
    }

    public void setTotalElements(long totalElements) {
      this.totalElements = totalElements;
    }
  }
}