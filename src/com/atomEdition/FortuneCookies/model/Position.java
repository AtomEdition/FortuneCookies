package com.atomEdition.FortuneCookies.model;

/**
 * Created with IntelliJ IDEA.
 * User: FruityDevil
 * Date: 05.12.14
 * Time: 16:11
 * To change this template use File | Settings | File Templates.
 */
public class Position {
    private Integer x;
    private Integer y;

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }
}
