package com.minemeander.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.minemeander.Art;

/**
 * Created by tex on 6/5/2016.
 */
public class CompletionScreen extends AbstractScreen{

    private SpriteBatch spriteBatch = new SpriteBatch();
    private Stage stage = new Stage();
    private Skin skin = new Skin();
    private Texture titleImage;
    private int worldId;
    public int currentLevel;

    public CompletionScreen(int worldId) {
        this.worldId = worldId;
        currentLevel = worldId;
        Art.winMusic.play();

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
        table.setFillParent(true);
        stage.addActor(table);

        titleImage = new Texture(Gdx.files.internal("material/UI/LevelComplete.png"));

        TextButton button6 = new TextButton("BACK TO LEVEL MENU", skin);
        button6.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor){
                Art.buttonSound.play();
                Art.winMusic.stop();
                CompletionScreen.this.transitionTo(new LevelSelectScreen());
            }
        });

        TextButton button8 = new TextButton("NEXT LEVEL", skin);
        button8.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor){
                //CompletionScreen.this.transitionTo(new LevelSelectScreen());
                Art.winMusic.stop();
                Art.startSound.play();
                fadeOut();
            }
        });

        table.row();
        table.add(button6).pad(60).padTop(50);
        table.row();
        table.add(button8).pad(20).padTop(50);
        table.row();
        table.layout();

        Gdx.input.setInputProcessor(stage);
        fadeIn();
    }

    @Override
    protected void onFadeOutTermination() {
        System.out.printf("Select Level %d\n", currentLevel+1);
        CompletionScreen.this.transitionTo(new LevelScreen(currentLevel+1));

    }

    @Override
    public void render(float delta) {

        spriteBatch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        spriteBatch.begin();
        spriteBatch.draw(titleImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //Art.bitmapFont.draw(spriteBatch, "LEVEL COMPLETE", (Gdx.graphics.getWidth()/2)-100, Gdx.graphics.getHeight()/2);
        spriteBatch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        super.renderCurtain();
    }

}
