package com.kasumov.SpringBootApp.service;

import java.util.List;

public interface GenericService<T, ID> {
    T getById(ID id);

    List<T> getAll();

    void deleteById(ID id);
}
