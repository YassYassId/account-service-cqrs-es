package com.yassine.accountservicecqrses.commonapi.events;

import lombok.Getter;

public class BaseEvent<T> {
    @Getter
    T id;

    public BaseEvent(T id) {
        this.id = id;
    }
}
