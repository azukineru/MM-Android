package com.minemeander;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import com.minemeander.Art;
import com.minemeander.screen.AbstractScreen;
import com.minemeander.screen.MainMenu;
import com.minemeander.screen.ScreenListener;

public class MyMineMeander extends ApplicationAdapter implements ApplicationListener, ScreenListener{
	SpriteBatch batch;
	Texture img;
	private AbstractScreen currentScreen;

	public MyMineMeander(){

	}

	@Override
	public void render () {
		currentScreen.render(0f);
	}

	@Override
	public void create () {
		loadArt();
		AbstractScreen.listener = this;
		currentScreen = new MainMenu();
	}

	private void loadArt() {
		Art.load(new TextureAtlas(Gdx.files.internal("material/output/pack.atlas")));
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyScreenChange(AbstractScreen newScreen) {
		this.currentScreen = newScreen;
	}
}
