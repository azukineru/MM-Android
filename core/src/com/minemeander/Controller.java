package com.minemeander;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import com.minemeander.screen.LevelScreen;
import com.minemeander.screen.LevelSelectScreen;

public class Controller {
	
	Viewport viewport;
	Stage stage;
	boolean backPressed;
	boolean upPressed, downPressed, leftPressed, rightPressed;
	OrthographicCamera cam;
	
	public Controller(){
		cam = new OrthographicCamera();
		viewport = new FitViewport(1050, 600);
		stage = new Stage(viewport, LevelScreen.spriteBatch);
		
		stage.addListener(new InputListener(){

	            @Override
	            public boolean keyDown(InputEvent event, int keycode) {
	                switch(keycode){
	                    case Input.Keys.UP:
	                        upPressed = true;
	                        break;
	                    case Input.Keys.DOWN:
	                        downPressed = true;
	                        break;
	                    case Input.Keys.LEFT:
	                        leftPressed = true;
	                        break;
	                    case Input.Keys.RIGHT:
	                        rightPressed = true;
	                        break;
	                }
	                return true;
	            }

	            @Override
	            public boolean keyUp(InputEvent event, int keycode) {
	                switch(keycode){
	                    case Input.Keys.UP:
	                        upPressed = false;
	                        break;
	                    case Input.Keys.DOWN:
	                        downPressed = false;
	                        break;
	                    case Input.Keys.LEFT:
	                        leftPressed = false;
	                        break;
	                    case Input.Keys.RIGHT:
	                        rightPressed = false;
	                        break;
	                }
	                return true;
	            }
	        });
		
		Gdx.input.setInputProcessor(stage);
		
		Table table = new Table();
		Table table2 = new Table();
		table2.left().bottom();
		table.left().bottom();

		Image backImg = new Image(new Texture(Gdx.files.internal("material/UI/back.png")));
		backImg.setSize(80, 80);
		backImg.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				backPressed = true;
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				backPressed = false;
			}
		});

		Image upImg = new Image(new Texture(Gdx.files.internal("material/UI/flatDark25.png")));
		upImg.setSize(80, 80);
		upImg.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				upPressed = true;				
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				upPressed = false;				
			}			
		});
		
		Image downImg = new Image(new Texture(Gdx.files.internal("material/UI/flatDark26.png")));
		downImg.setSize(80, 80);
		downImg.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				downPressed = true;
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				downPressed = false;
			}			
		});
		
		Image rightImg = new Image(new Texture(Gdx.files.internal("material/UI/flatDark24.png")));
		rightImg.setSize(80, 80);
		rightImg.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				rightPressed = true;
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				rightPressed = false;
			}			
		});
		
		Image leftImg = new Image(new Texture(Gdx.files.internal("material/UI/flatDark23.png")));
		leftImg.setSize(80, 80);
		leftImg.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				leftPressed = true;
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				leftPressed = false;
			}			
		});

		table2.add();
		table2.add(backImg).size(backImg.getWidth(), backImg.getHeight()).padBottom(450).padLeft(960);


		table.add();
		table.add(upImg).size(upImg.getWidth(), upImg.getHeight());
		table.add();
		//table.row().pad(5,100, 5, 100);
		table.row().pad(5 ,5 ,5 ,5 );
		table.add(leftImg).size(leftImg.getWidth(), leftImg.getHeight());
		table.add();
		table.add(rightImg).size(rightImg.getWidth(), rightImg.getHeight());
		table.row().padBottom(5);
		table.add();
		table.add(downImg).size(downImg.getWidth(), downImg.getHeight());
		table.add();
		
		stage.addActor(table);
		stage.addActor(table2);
	}

	public void draw(){
		stage.draw();
	}
	
	public boolean isUpPressed() {
		return upPressed;
	}

	public boolean isDownPressed() {
		return downPressed;
	}

	public boolean isLeftPressed() {
		return leftPressed;
	}
	
	public boolean isRightPressed() {
		return rightPressed;
	}

	public boolean isBackPressed() { return backPressed; }

	public void resize(int width, int height){
		viewport.update(width, height);
	}
	
}
