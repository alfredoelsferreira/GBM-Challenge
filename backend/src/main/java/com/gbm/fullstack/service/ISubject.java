package com.gbm.fullstack.service;

public interface ISubject {
    void addObserver(IObserver ob);
    void removeObserver(IObserver ob);
    void notifyObservers();
}