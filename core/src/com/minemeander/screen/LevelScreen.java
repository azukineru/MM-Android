package com.minemeander.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.minemeander.Art;
import com.minemeander.Constant;
import com.minemeander.Controller;
import com.minemeander.Level;
import com.minemeander.objects.Avatar;

public class LevelScreen extends AbstractScreen{
	
	private Level level;
	private Stage stage = new Stage();
	public static SpriteBatch spriteBatch = new SpriteBatch();
	private long lastKeyTime = System.currentTimeMillis();
	private float timer = 0f;	
	private Box2DDebugRenderer box2dDebugRenderer;
	private boolean pause, resume;

	public static Controller controller;

	public LevelScreen(int worldId) {
		level = new Level(this, worldId);		
		controller = new Controller();
		Art.playMusic.setLooping(true);
		Art.playMusic.play();
		fadeIn();
	}
		
	public void handleInput(){
		if(controller.isRightPressed()){
			//System.out.printf("Pressed Right Button\n");
			//avatar.getRightThrust();
		}
		else if(controller.isLeftPressed()){
			//System.out.printf("Pressed Left Button\n");
			//avatar.getLeftThrust();
		}
		if(controller.isUpPressed()){
			//System.out.printf("Pressed Up Button\n");
			//avatar.getRightThrust();
		}
		else if(controller.isDownPressed()){
			//System.out.printf("Pressed Down Button\n");
			//avatar.getLeftThrust();
		}
		if(controller.isBackPressed()){
			Art.playMusic.stop();
			LevelScreen.this.transitionTo(new LevelSelectScreen());
		}
	}
	
	public void update(float dt){
		handleInput();
		//camera.update();
	}
	
	public void reset(){
		fadeOut();
	}

	
	@Override
	protected void onFadeOutTermination() {
		level.objectManager.reset();
		level.camera.focusOnAvatar(level.objectManager.getAvatar());
		controller = new Controller();
		fadeIn();
	}
	
	@Override
	public void render(float delta) {
		update(Gdx.graphics.getDeltaTime());
		if ((Gdx.input.isKeyPressed(Input.Keys.F1) || Gdx.input.isKeyJustPressed(Input.Keys.BACK) ) &&  (System.currentTimeMillis()-lastKeyTime)>100) {
			//level.debugMode = !level.debugMode;
			//lastKeyTime=System.currentTimeMillis();
			LevelScreen.this.transitionTo(new LevelSelectScreen());
		}



		float deltaTime = pause ? 0 : Gdx.graphics.getDeltaTime();
		timer += deltaTime;
		level.step(deltaTime, 8, 3);
		
		level.camera.update(level.objectManager.getAvatar());

		// Render map
		level.tiledMapRenderer.setView(level.camera.parrallax);
		level.tiledMapRenderer.render(Constant.PARALLAX_LAYERS);
		level.tiledMapRenderer.setView(level.camera.front);
		level.tiledMapRenderer.render(Constant.BACKGROUND_LAYERS);
		
		// Render sprites
		spriteBatch.getProjectionMatrix().set(level.camera.front.combined);
		spriteBatch.begin();
		level.objectManager.draw(spriteBatch, timer);		
		spriteBatch.end();
		
		// Render HUD		
		level.hud.draw(level, spriteBatch, timer);
		
		// Render Controller
		controller.draw();
		
		// Display info
		if (level.debugMode) {			
			if (box2dDebugRenderer == null) {
				box2dDebugRenderer = new Box2DDebugRenderer();
				box2dDebugRenderer.setDrawJoints(true);
				box2dDebugRenderer.setDrawBodies(true);
				box2dDebugRenderer.setDrawInactiveBodies(true);
			}
		
			box2dDebugRenderer.render(level.physicalWorld, level.camera.front.combined);
			spriteBatch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			spriteBatch.begin();
			level.font.draw(spriteBatch, getStatusString(), 20, 460);
			spriteBatch.end();			
			if (level.debugMode) {
				level.objectManager.drawDebugInfo();
				}				
		}

		// Select and Render the level
		super.renderCurtain();
	}
	
	private String getStatusString() {
		Avatar avatar = level.objectManager.getAvatar();
		return avatar.toString();
	}

	public void pause() {
		this.pause = true;
	}

	public void resume() { this.resume = true; }
	
}
