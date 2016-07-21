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

public class LevelSelectScreen extends AbstractScreen {

	private OrthographicCamera camera;
	private SpriteBatch spriteBatch = new SpriteBatch();
	private Stage stage = new Stage();
	private Skin skin = new Skin();
	private Texture titleImage;
	private static int selectlevel;
	
	public LevelSelectScreen() {
		Art.menuMusic.setLooping(true);
		Art.menuMusic.play();

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
		table.padTop(200);
		table.setFillParent(true);
		stage.addActor(table);

		titleImage = new Texture(Gdx.files.internal("material/UI/Menu.png"));

		TextButton button = new TextButton("LEVEL 1", skin);
		button.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				selectlevel = 1;
				Art.startSound.play();
				Art.menuMusic.stop();
				fadeOut();
			}
		});

		TextButton button2 = new TextButton("LEVEL 2", skin);
		button2.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				selectlevel = 2;
				Art.startSound.play();
				Art.menuMusic.stop();
				fadeOut();
			}
		});

		TextButton button3 = new TextButton("LEVEL 3", skin);
		button3.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				selectlevel = 3;
				Art.startSound.play();
				Art.menuMusic.stop();
				fadeOut();
			}
		});

		TextButton button4 = new TextButton("LEVEL 4", skin);
		button4.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				selectlevel = 4;
				Art.startSound.play();
				Art.menuMusic.stop();
				fadeOut();
			}
		});

		TextButton button5 = new TextButton("LEVEL 5", skin);
		button5.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				selectlevel = 5;
				Art.startSound.play();
				Art.menuMusic.stop();
				fadeOut();
			}
		});

		Image nextWorld = new Image(new Texture(Gdx.files.internal("material/UI/nextworld.png")));
		nextWorld.setSize(240,240);
		nextWorld.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Art.buttonSound.play();
				LevelSelectScreen.this.transitionTo(new LevelSelectScreen2());
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		Image prevWorld = new Image(new Texture(Gdx.files.internal("material/UI/disabled_prevworld.png")));
		prevWorld.setSize(240,240);

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
		table.add(prevWorld);
		table.add();
		table.add(nextWorld);

		table.row().padBottom(5);
		table.add();
		
		TextButton button10 = new TextButton("BACK", skin);
		button10.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				Art.buttonSound.play();
				LevelSelectScreen.this.transitionTo(new MainMenu());
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
