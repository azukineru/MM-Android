package com.minemeander.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

import com.minemeander.Art;
import com.minemeander.Constant;
import com.minemeander.MyMineMeander;

public class MainMenu extends AbstractScreen{

	private SpriteBatch spriteBatch = new SpriteBatch();
	private Stage stage = new Stage();
	private Skin skin = new Skin();
	private Texture titleImage;
	private BitmapFont fontMenu;
	
	public MainMenu(){
		fontMenu  = Art.menuFont;
		//fontMenu.getData().setScale(1f);
		Art.menuMusic.setLooping(true);
		Art.menuMusic.play();

		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		skin.add("white", new Texture(pixmap));
		skin.add("default", fontMenu);
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);
		Table table = new Table();
		table.padTop(40);
		table.setFillParent(true);
		stage.addActor(table);
	
		titleImage = new Texture(Gdx.files.internal("material/UI/Menu.png"));
		titleImage.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

		TextButton buttonx = new TextButton("",skin);
		table.add(buttonx).pad(20).padLeft(1000);
		table.row();
		table.add(buttonx).pad(20).padLeft(1000);
		table.row();

		table.add(buttonx).pad(20).padLeft(1000);
		table.row();
		table.add(buttonx).pad(20).padLeft(1000);
		table.row();

		TextButton button = new TextButton("START", skin);
		button.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				Art.buttonSound.play();
				Art.menuMusic.pause();
				MainMenu.this.transitionTo(new LevelSelectScreen());
			}
		});
		table.add(button).pad(20).padLeft(1000);
		table.row();

		table.add(buttonx).pad(20).padLeft(1000);
		table.row();
		table.add(buttonx).pad(20).padLeft(1000);
		table.row();

		TextButton button2 = new TextButton("HELP", skin);
		button2.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				Art.buttonSound.play();
				MainMenu.this.transitionTo(new HelpScreen());
			}
		});
		table.add(button2).pad(20).padLeft(1000);
		table.row();

		table.add(buttonx).pad(20).padLeft(1000);
		table.row();
		table.add(buttonx).pad(20).padLeft(1000);
		table.row();

		TextButton button3 = new TextButton("CREDITS", skin);
		button3.addListener(new ChangeListener() {			
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				Art.buttonSound.play();
				MainMenu.this.transitionTo(new CreditsScreen());
			}
		});
		table.add(button3).pad(20).padLeft(1000);
		table.row();

		table.add(buttonx).pad(20).padLeft(1000);
		table.row();
		table.add(buttonx).pad(20).padLeft(1000);
		table.row();

		TextButton button4 = new TextButton("EXIT", skin);
		button4.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				//Art.buttonSound.play();
				Gdx.app.exit();
			}
		});
		table.add(button4).pad(20).padLeft(1000);
		table.row();

		table.add(buttonx).pad(20).padLeft(1000);
		table.row();
		table.add(buttonx).pad(20).padLeft(1000);
		table.row();
		
		table.layout();

		
		Gdx.input.setInputProcessor(stage);
		
		fadeIn();
		
	}
	
	
	@Override
	public void render(float delta) {

		spriteBatch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		spriteBatch.begin();
		spriteBatch.draw(titleImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		spriteBatch.end();
		
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
		
		super.renderCurtain();
	}
	
		
}
