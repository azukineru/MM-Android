package com.minemeander.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

public class LevelSelectScreen2 extends AbstractScreen {

	private OrthographicCamera camera;
	private SpriteBatch spriteBatch = new SpriteBatch();
	private Stage stage = new Stage();
	private Skin skin = new Skin();
	private Texture titleImage;
	private Sprite sprite;
	private static int selectlevel;
	
	public LevelSelectScreen2() {
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));		
		skin.add("default", Art.bitmapFont);
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);		
		textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);
		Table table = new Table();
		table.padTop(100);
		table.setFillParent(true);
		stage.addActor(table);
	
		titleImage = new Texture(Gdx.files.internal("material/UI/Menu.png"));

		TextButton button = new TextButton("LEVEL 6", skin);
		button.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				selectlevel = 6;
				fadeOut();
			}
		});

		TextButton button2 = new TextButton("LEVEL 7", skin);
		button2.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				selectlevel = 7;
				fadeOut();
			}
		});

		TextButton button3 = new TextButton("LEVEL 8", skin);
		button3.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				selectlevel = 8;
				fadeOut();
			}
		});

		TextButton button4 = new TextButton("LEVEL 9", skin);
		button4.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				selectlevel = 9;
				fadeOut();
			}
		});

		TextButton button5 = new TextButton("LEVEL 10", skin);
		button5.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				selectlevel = 10;
				fadeOut();
			}
		});

		TextButton button6 = new TextButton("PREVIOUS WORLD", skin);
		button6.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				LevelSelectScreen2.this.transitionTo(new LevelSelectScreen());
			}
		});

		TextButton button8 = new TextButton("NEXT WORLD", skin);
		button8.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				LevelSelectScreen2.this.transitionTo(new LevelSelectScreen3());
			}
		});

		table.add();
		table.add(button).pad(20);
		table.add();
		table.row();
		table.add();
		table.add(button2).pad(20);
		table.add();
		table.row();
		table.add();
		table.add(button3).pad(20);
		table.add();
		table.row();
		table.add();
		table.add(button4).pad(20);
		table.add();
		table.row();
		table.add();
		table.add(button5).pad(20);
		table.add();
		table.row();

		table.row().pad(5 ,5 ,5 ,5 );
		table.add(button6);
		table.add();
		table.add(button8);

		table.row().padBottom(5);
		table.add();
		
		TextButton button10 = new TextButton("BACK", skin);
		button10.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				LevelSelectScreen2.this.transitionTo(new MainMenu());
			}
		});
		table.add(button10).pad(20);
		
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
		LevelSelectScreen2.this.transitionTo(new LevelScreen(selectlevel));

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
