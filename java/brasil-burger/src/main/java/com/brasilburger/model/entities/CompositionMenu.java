package com.brasilburger.model.entities;

import com.brasilburger.core.interfaces.IEntity;

public class CompositionMenu implements IEntity {
    private int id;
    private int menuId;
    private int burgerId;      // ou complementId selon le type
    private String type;       // "burger" ou "complement"

    public CompositionMenu() { }

    public CompositionMenu(int id, int menuId, int burgerId, String type) {
        this.id = id;
        this.menuId = menuId;
        this.burgerId = burgerId;
        this.type = type;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getMenuId() { return menuId; }
    public void setMenuId(int menuId) { this.menuId = menuId; }

    public int getBurgerId() { return burgerId; }
    public void setBurgerId(int burgerId) { this.burgerId = burgerId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

        public Integer getComplementId() { 
            return "complement".equals(type) ? burgerId : null; 
        }
        public void setComplementId(int complementId) { 
            this.burgerId = complementId;
            this.type = "complement";
        }
}
