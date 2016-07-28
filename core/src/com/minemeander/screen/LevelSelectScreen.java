package com.minemeander.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import com.minemeander.Level;

public class LevelSelectScreen extends AbstractScreen {

	private OrthographicCamera camera;
	private SpriteBatch spriteBatch = new SpriteBatch();
	private Stage stage = new Stage();
	private Skin skin = new Skin();
	private Texture titleImage;
	private static int selectlevel;
	private Level level;
	public static Preferences pref;
	public static int levelState, levelState2, levelState3, levelState4, levelState5,
			levelState6, levelState7, levelState8, levelState9, levelState10,
			levelState11, levelState12, levelState13, levelState14, levelState15;
	
	public LevelSelectScreen() {
		Art.menuMusic.setLooping(true);
		Art.menuMusic.play();

		pref = Gdx.app.getPreferences("com.minemeander.profile");
		/*
		pref.putInteger("level1", 0);
		pref.putInteger("level2", 0);
		pref.putInteger("level3", 0);
		pref.putInteger("level4", 0);
		pref.putInteger("level5", 0);
		pref.flush();*/

		levelState = pref.getInteger("level1");
		levelState2 = pref.getInteger("level2");
		levelState3 = pref.getInteger("level3");
		levelState4 = pref.getInteger("level4");
		levelState5 = pref.getInteger("level5");
		levelState6 = pref.getInteger("level6");
		levelState7 = pref.getInteger("level7");
		levelState8 = pref.getInteger("level8");
		levelState9 = pref.getInteger("level9");
		levelState10 = pref.getInteger("level10");
		levelState11 = pref.getInteger("level11");
		levelState12 = pref.getInteger("level12");
		levelState13 = pref.getInteger("level13");
		levelState14 = pref.getInteger("level14");
		levelState15 = pref.getInteger("level15");
		System.out.printf("levelstate1 in Menu = %d\n", levelState);
		System.out.printf("levelstate2 in Menu = %d\n", levelState2);
		System.out.printf("levelstate3 in Menu = %d\n", levelState3);
		System.out.printf("levelstate4 in Menu = %d\n", levelState4);
		System.out.printf("levelstate5 in Menu = %d\n", levelState5);
		System.out.printf("levelstate6 in Menu = %d\n", levelState6);
		System.out.printf("levelstate7 in Menu = %d\n", levelState7);
		System.out.printf("levelstate8 in Menu = %d\n", levelState8);
		System.out.printf("levelstate9 in Menu = %d\n", levelState9);
		System.out.printf("levelstate10 in Menu = %d\n", levelState10);
		System.out.printf("levelstate11 in Menu = %d\n", levelState11);
		System.out.printf("levelstate12 in Menu = %d\n", levelState12);
		System.out.printf("levelstate13 in Menu = %d\n", levelState13);
		System.out.printf("levelstate14 in Menu = %d\n", levelState14);
		System.out.printf("levelstate15 in Menu = %d\n", levelState15);

		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));		
		skin.add("default", Art.menuFont);
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);

		Table table = new Table();
		Table table2 = new Table();

		table.padTop(50);
		table.setFillParent(true);

		//table2.left().bottom();
		table2.padTop(900);
		table2.setFillParent(true);
		//table2.padBottom(70);

		stage.addActor(table);
		stage.addActor(table2);

		titleImage = new Texture(Gdx.files.internal("material/UI/BackgroundMenu.jpg"));

		Image backbutton = new Image(new Texture(Gdx.files.internal("material/UI/button/back_button.png")));
		backbutton.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Art.buttonSound.play();
				LevelSelectScreen.this.transitionTo(new MainMenu());
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		table2.add(backbutton);
		table2.row();

		Image level1 = new Image(new Texture(Gdx.files.internal("material/UI/button/level1_active.png")));
		level1.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				selectlevel = 1;
				Art.startSound.play();
				Art.menuMusic.stop();
				fadeOut();
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		Image level2 = new Image(new Texture(Gdx.files.internal("material/UI/button/level2_active.png")));
		Image level2_d = new Image(new Texture(Gdx.files.internal("material/UI/button/level2_disabled.png")));
		level2.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				selectlevel = 2;
				Art.startSound.play();
				Art.menuMusic.stop();
				fadeOut();
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		Image level3 = new Image(new Texture(Gdx.files.internal("material/UI/button/level3_active.png")));
		Image level3_d = new Image(new Texture(Gdx.files.internal("material/UI/button/level3_disabled.png")));
		level3.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				selectlevel = 3;
				Art.startSound.play();
				Art.menuMusic.stop();
				fadeOut();
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		Image level4 = new Image(new Texture(Gdx.files.internal("material/UI/button/level4_active.png")));
		Image level4_d = new Image(new Texture(Gdx.files.internal("material/UI/button/level4_disabled.png")));
		level4.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				selectlevel = 4;
				Art.startSound.play();
				Art.menuMusic.stop();
				fadeOut();
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		Image level5 = new Image(new Texture(Gdx.files.internal("material/UI/button/level5_active.png")));
		Image level5_d = new Image(new Texture(Gdx.files.internal("material/UI/button/level5_disabled.png")));
		level5.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				selectlevel = 5;
				Art.startSound.play();
				Art.menuMusic.stop();
				fadeOut();
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		Image level6 = new Image(new Texture(Gdx.files.internal("material/UI/button/level6_active.png")));
		Image level6_d = new Image(new Texture(Gdx.files.internal("material/UI/button/level6_disabled.png")));
		level6.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				selectlevel = 6;
				Art.startSound.play();
				Art.menuMusic.stop();
				fadeOut();
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		Image level7 = new Image(new Texture(Gdx.files.internal("material/UI/button/level7_active.png")));
		Image level7_d = new Image(new Texture(Gdx.files.internal("material/UI/button/level7_disabled.png")));
		level7.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				selectlevel = 7;
				Art.startSound.play();
				Art.menuMusic.stop();
				fadeOut();
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		Image level8 = new Image(new Texture(Gdx.files.internal("material/UI/button/level8_active.png")));
		Image level8_d = new Image(new Texture(Gdx.files.internal("material/UI/button/level8_disabled.png")));
		level8.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				selectlevel = 8;
				Art.startSound.play();
				Art.menuMusic.stop();
				fadeOut();
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		Image level9 = new Image(new Texture(Gdx.files.internal("material/UI/button/level9_active.png")));
		Image level9_d = new Image(new Texture(Gdx.files.internal("material/UI/button/level9_disabled.png")));
		level9.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				selectlevel = 9;
				Art.startSound.play();
				Art.menuMusic.stop();
				fadeOut();
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		Image level10 = new Image(new Texture(Gdx.files.internal("material/UI/button/level10_active.png")));
		Image level10_d = new Image(new Texture(Gdx.files.internal("material/UI/button/level10_disabled.png")));
		level10.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				selectlevel = 10;
				Art.startSound.play();
				Art.menuMusic.stop();
				fadeOut();
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		Image level11 = new Image(new Texture(Gdx.files.internal("material/UI/button/level11_active.png")));
		Image level11_d = new Image(new Texture(Gdx.files.internal("material/UI/button/level11_disabled.png")));
		level11.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				selectlevel = 11;
				Art.startSound.play();
				Art.menuMusic.stop();
				fadeOut();
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		Image level12 = new Image(new Texture(Gdx.files.internal("material/UI/button/level12_active.png")));
		Image level12_d = new Image(new Texture(Gdx.files.internal("material/UI/button/level12_disabled.png")));
		level12.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				selectlevel = 12;
				Art.startSound.play();
				Art.menuMusic.stop();
				fadeOut();
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		Image level13 = new Image(new Texture(Gdx.files.internal("material/UI/button/level13_active.png")));
		Image level13_d = new Image(new Texture(Gdx.files.internal("material/UI/button/level13_disabled.png")));
		level13.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				selectlevel = 13;
				Art.startSound.play();
				Art.menuMusic.stop();
				fadeOut();
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		Image level14 = new Image(new Texture(Gdx.files.internal("material/UI/button/level14_active.png")));
		Image level14_d = new Image(new Texture(Gdx.files.internal("material/UI/button/level14_disabled.png")));
		level14.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				selectlevel = 14;
				Art.startSound.play();
				Art.menuMusic.stop();
				fadeOut();
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		Image level15 = new Image(new Texture(Gdx.files.internal("material/UI/button/level15_active.png")));
		Image level15_d = new Image(new Texture(Gdx.files.internal("material/UI/button/level15_disabled.png")));
		level15.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				selectlevel = 15;
				Art.startSound.play();
				Art.menuMusic.stop();
				fadeOut();
				return super.touchDown(event, x, y, pointer, button);
			}
		});



		table.add();
		table.add(level1).pad(20);
		table.add();

		table.add();
		if(levelState == 1) {
			table.add(level2).pad(20);
		}
		else if(levelState == 0){
			table.add(level2_d).pad(20);
		}
		table.add();

		table.add();
		if( levelState2 == 1 ) {
			table.add(level3).pad(20);
		}
		else if( levelState2 == 0 ){
			table.add(level3_d).pad(20);
		}
		table.add();

		table.add();
		if( levelState3 == 1 ){
			table.add(level4).pad(20);
		}
		else if( levelState3 == 0 ){
			table.add(level4_d).pad(20);
		}
		table.add();

		table.add();
		if( levelState4 == 1 ) {
			table.add(level5).pad(20);
		}
		else if( levelState4 == 0 ){
			table.add(level5_d).pad(20);
		}
		table.add();

		table.row();

		table.add();
		if( levelState5 == 1) {
			table.add(level6).pad(20);
		}
		else if( levelState5 == 0){
			table.add(level6_d).pad(20);
		}
		table.add();

		table.add();
		if(levelState6 == 1) {
			table.add(level7).pad(20);
		}
		else if(levelState6 == 0){
			table.add(level7_d).pad(20);
		}
		table.add();

		table.add();
		if( levelState7 == 1) {
			table.add(level8).pad(20);
		}
		else if( levelState7 == 0 ){
			table.add(level8_d).pad(20);
		}
		table.add();

		table.add();
		if( levelState8 == 1 ){
			table.add(level9).pad(20);
		}
		else if( levelState8 == 0 ){
			table.add(level9_d).pad(20);
		}
		table.add();

		table.add();
		if( levelState9 == 1 ) {
			table.add(level10).pad(20);
		}
		else if( levelState9 == 0 ){
			table.add(level10_d).pad(20);
		}
		table.add();

		table.row();

		table.add();
		if( levelState10 == 1) {
			table.add(level11).pad(20);
		}
		else if( levelState10 == 0 ){
			table.add(level11_d).pad(20);
		}
		table.add();

		table.add();
		if(levelState11 == 1) {
			table.add(level12).pad(20);
		}
		else if(levelState11 == 0){
			table.add(level12_d).pad(20);
		}
		table.add();

		table.add();
		if( levelState12 == 1 ) {
			table.add(level13).pad(20);
		}
		else if( levelState12 == 0 ){
			table.add(level13_d).pad(20);
		}
		table.add();

		table.add();
		if( levelState13 == 1 ){
			table.add(level14).pad(20);
		}
		else if( levelState13 == 0 ){
			table.add(level14_d).pad(20);
		}
		table.add();

		table.add();
		if( levelState15 == 1 ) {
			table.add(level15).pad(20);
		}
		else if( levelState15 == 0 ){
			table.add(level15_d).pad(20);
		}
		table.add();

		table.layout();


		int viewPortWidthInMeters = (int) ((Gdx.graphics.getWidth() / 32) * Constant.METERS_PER_TILE);
		int viewPortHeightInMeters = (int) ((Gdx.graphics.getHeight() / 32) * Constant.METERS_PER_TILE);

		camera = new OrthographicCamera(viewPortWidthInMeters, viewPortHeightInMeters);
		camera.position.x = viewPortWidthInMeters / 2;
		camera.position.y = viewPortHeightInMeters / 2;
		camera.update();
		
		Gdx.input.setInputProcessor(stage);
		
		fadeIn();

	}
	
	@Override
	protected void onFadeOutTermination() {
		System.out.printf("Select Level %d\n", selectlevel);
		LevelSelectScreen.this.transitionTo(new LevelScreen(selectlevel));
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
