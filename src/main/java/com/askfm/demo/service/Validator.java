package com.askfm.demo.service;

import com.askfm.demo.entity.User;

public interface Validator<T> {
  void validate(T t);
}
