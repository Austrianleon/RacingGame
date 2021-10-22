package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import javax.swing.*;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class MyGdxGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture carTexture;
	private Sprite car;
	private float carX = 150f;
	private float carY ;
	private float scalecarX = 1;
	private float scalecarY = 1;
	private float origincarX = 17;
	private float origincarY = 32;
	private float rotation = 0;
	private float rotationspeed = 180f;
	private float carSpeed = 200f;
	private float oldX;
	private float oldY;
	private boolean collides = false;
	private OrthogonalTiledMapRenderer renderer;
	private TiledMap tm;
	private OrthographicCamera camera;
	private TiledMapTileLayer collisionLayer;
	private float tilehight, tilewidth;

	@Override
	public void create() {
		batch = new SpriteBatch();
		FileHandle carFileHandle = Gdx.files.external("/Downloads/car.png");
		carTexture = new Texture(carFileHandle);
		car = new Sprite(carTexture, 0 ,0, 35, 63);
		tm = new TmxMapLoader().load("C:/Users/leon/OneDrive/Desktop/Racinggame/Racemap.tmx" );
		renderer = new OrthogonalTiledMapRenderer(tm);
		camera = new OrthographicCamera();
		collisionLayer = (TiledMapTileLayer) tm.getLayers().get(0);
		tilehight = collisionLayer.getTileHeight();
		tilewidth = collisionLayer.getWidth();
	}

	@Override
	public void render() {
		oldX = carX;
		oldY = carY;

		if(Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT))
			rotation += 40;
		if(Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT))
			rotation -= 40;
		if(Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT) & Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)){
			carX += Gdx.graphics.getDeltaTime() * 200f;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT) & Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)){
			carX -= Gdx.graphics.getDeltaTime() *200f;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT) & Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)){
			carX -= Gdx.graphics.getDeltaTime() * 200f;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT) & Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)){
			carX += Gdx.graphics.getDeltaTime() * 200f;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DPAD_UP))
			carY += Gdx.graphics.getDeltaTime() * carSpeed;
		if(Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN))
			carY -= Gdx.graphics.getDeltaTime() * carSpeed;


		if(collidesLeft()){
			carX = oldX;
			carY = oldY;
		}
		if(collidesTop()){
			carX = oldX;
			carY = oldY;
		}
		if(collidesBottom()){
			carX = oldX;
			carY = oldY;
		}
		if(collidesRight()){
			carX = oldX;
			carY = oldY;
		}
		Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
		camera.position.set(carX + car.getWidth() / 2, carY + car.getHeight()/2, 0);
		camera.update();
		renderer.setView(camera);
		renderer.render();
		batch.begin();
		batch.draw(car, carX, carY, origincarX, origincarY, 35, 63, scalecarX, scalecarY, rotation);
		batch.end();
		rotation = 0;
	}


	@Override
	public void resume() {
	}

	public boolean collidesRight() {
		boolean collides = false;
		for(float step = 0; step < car.getHeight(); step += collisionLayer.getTileHeight() / 2)
			if(collides = isCellBlocked(carX + car.getWidth(), carY + step))
				break;
		return collides;
	}

	public boolean collidesLeft() {
		boolean collides = false;
		for(float step = 0; step < car.getHeight(); step += collisionLayer.getTileHeight() / 2)
			if(collides = isCellBlocked(carX, carY + step))
				break;

		return collides;}

	public boolean collidesTop() {
		boolean collides = false;
		for(float step = 0; step < car.getWidth(); step += collisionLayer.getTileWidth() / 2)
			if(collides = isCellBlocked(carX + step, carY + car.getHeight()))
				break;
		return collides;
	}
	public boolean collidesBottom() {
		boolean collides = false;
		for(float step = 0; step < car.getWidth(); step += collisionLayer.getTileWidth() / 2)
			if(collides = isCellBlocked(carX + step, carY))
				break;
		return collides;}


	private boolean isCellBlocked(float x, float y)
	{
		TiledMapTileLayer.Cell cell = collisionLayer.getCell(((int)((x/collisionLayer.getTileWidth()))), (int)(y / collisionLayer.getTileHeight()));
		return cell != null && cell.getTile() !=null && cell.getTile().getProperties().containsKey("blocked");
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportHeight = height;
		camera.viewportWidth = width;
	}

	@Override
	public void pause() {
	}

	@Override
	public void dispose() {
		tm.dispose();
		renderer.dispose();
	}
}
