package com.baiwangmaoyi.luckydraw.service;


import java.util.List;

public interface RandomProvider {
    public <T> List<T> pickRandomly(List<T> sourceList, int itemsToSelect);
}
