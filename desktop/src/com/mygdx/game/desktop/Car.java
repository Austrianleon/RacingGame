package com.mygdx.game.desktop;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Car extends Sprite {
    private TiledMapTileLayer Collisonlayer;

    public Car(TiledMapTileLayer collisonlayer) {
        Collisonlayer = collisonlayer;
    }

    public Car(Sprite sprite, TiledMapTileLayer collisonlayer) {
        super(sprite);
        Collisonlayer = collisonlayer;
    }
}
