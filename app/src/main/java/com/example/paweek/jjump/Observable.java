package com.example.paweek.jjump;

public interface Observable {
    void registerObserver(Observer observer);
    void unregisterObserver(Observer observer);
    void notifyObservers(Object args);
    void notifyObservers();
}
