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
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.minemeander.Art;
import com.minemeander.Constant;
import com.minemeander.MyMineMeander;

public class MainMenu extends AbstractScreen{

	private SpriteBatch spriteBatch = new SpriteBatch();
	private Stage stage = new Stage();
	private Skin skin = new Skin();
	private Texture titleImage, settingImage;
	private BitmapFont fontMenu;
	private TextButton buttonPlay, buttonHelp, buttonCredit, buttonExit;
	
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
		//textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);

		Table table = new Table();
		Table table2 = new Table();

		table.padTop(40);
		table.setFillParent(true);

		table2.padTop(500);
		table2.setFillParent(true);

		stage.addActor(table);
		stage.addActor(table2);
	
		titleImage = new Texture(Gdx.files.internal("material/UI/BackgroundMenu.jpg"));
		titleImage.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

		TextButton buttonx = new TextButton("",skin);
		table.add(buttonx).pad(20);
		table.row();
		table.add(buttonx).pad(20);
		table.row();

		table.add(buttonx).pad(20);
		table.row();
		table.add(buttonx).pad(20);
		table.row();

		Image startbutton = new Image(new Texture(Gdx.files.internal("material/UI/button/start_button.png")));
		startbutton.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Art.buttonSound.play();
				MainMenu.this.transitionTo(new LevelSelectScreen());
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		table.add(startbutton).pad(20);
		table.row();

		table.add(buttonx).pad(20);
		table.row();

		Image helpbutton = new Image(new Texture(Gdx.files.internal("material/UI/button/help_button.png")));
		helpbutton.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Art.buttonSound.play();
				MainMenu.this.transitionTo(new HelpScreen());
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		table.add(helpbutton).pad(20);
		table.row();

		table.add(buttonx).pad(20);
		table.row();

		Image creditsbutton = new Image(new Texture(Gdx.files.internal("material/UI/button/credits_button.png")));
		creditsbutton.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Art.buttonSound.play();
				MainMenu.this.transitionTo(new CreditsScreen());
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		table.add(creditsbutton).pad(20);
		table.row();

		table.add(buttonx).pad(20);
		table.row();

		Image exitbutton = new Image(new Texture(Gdx.files.internal("material/UI/button/exit_button.png")));
		exitbutton.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.exit();
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		table.add(exitbutton).pad(20);
		table.row();

		table.add(buttonx).pad(20);
		table.row();
		table.layout();

		Image settingbutton = new Image(new Texture(Gdx.files.internal("material/UI/button/setting_button.png")));
		settingbutton.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Art.buttonSound.play();
				MainMenu.this.transitionTo(new SettingsScreen());
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		table2.add(settingbutton).pad(20).padLeft(1700).padTop(350);

		Gdx.input.setInputProcessor(stage);
		
		fadeIn();
		
	}
	
	
	@Override
	public void render(float delta) {

		spriteBatch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		spriteBatch.begin();
		spriteBatch.draw(titleImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//spriteBatch.draw(settingImage, (Gdx.graphics.getWidth())-250, (Gdx.graphics.getHeight())-(Gdx.graphics.getHeight()-60), 170, 170);
		spriteBatch.end();
		
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
		
		super.renderCurtain();
	}
	
		
}
