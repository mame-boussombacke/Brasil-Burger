package com.brasilburger.core.abstracts;

import com.brasilburger.core.interfaces.IEntity;

public abstract class BaseEntity implements IEntity {
    private int id;

    public BaseEntity() {}

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }
}
