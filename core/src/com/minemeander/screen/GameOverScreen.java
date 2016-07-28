package com.minemeander.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.minemeander.Level;
import com.minemeander.Art;
import com.minemeander.objects.Avatar;

public class GameOverScreen extends AbstractScreen{
	SpriteBatch spriteBatch = new SpriteBatch();
	private Texture titleImage;
	private Skin skin = new Skin();
	private Stage stage = new Stage();
	private int score;
	private String scoreTag;
	
	public GameOverScreen(int score){
		this.score = score;
		//System.out.printf("Score Final: %d\n", score);
		Art.loseMusic.play();

		Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));
		skin.add("default", Art.menuFont);
		TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);

		Table table = new Table();
		table.padTop(100);
		//table.padLeft(-900);
		table.setFillParent(true);
		stage.addActor(table);

		titleImage = new Texture(Gdx.files.internal("material/UI/GameOver.png"));
		titleImage.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

		scoreTag = "TOTAL SCORE :" + Integer.toString(score) ;

		TextButton button7 = new TextButton("BACK TO MAIN MENU", skin);
		button7.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				Art.buttonSound.play();
				Art.loseMusic.stop();
				GameOverScreen.this.transitionTo(new MainMenu());
			}
		});
		table.add(button7).padTop(400);
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
		Art.menuFont.draw(spriteBatch, scoreTag, (Gdx.graphics.getWidth()/2)-250, Gdx.graphics.getHeight()/2 - 100);
		spriteBatch.end();

		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();

		super.renderCurtain();
	}
	
	@Override
	protected void onFadeInTermination() {

	}
	
	@Override
	protected void onFadeOutTermination() {
		transitionTo(new MainMenu());
	}
}
