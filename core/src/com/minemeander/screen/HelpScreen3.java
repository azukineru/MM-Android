package com.minemeander.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
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
public class HelpScreen3 extends AbstractScreen{
    private SpriteBatch spriteBatch = new SpriteBatch();
    private Stage stage = new Stage();
    private Skin skin = new Skin();
    private Texture titleImage, sectionImage;

    public HelpScreen3() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        skin.add("white", new Texture(pixmap));
        skin.add("default", Art.bitmapFont);
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

        titleImage = new Texture(Gdx.files.internal("material/UI/HC.png"));

        TextButton button = new TextButton("INSTRUCTIONS", skin);
        table.add(button).pad(20);
        table.row();

        TextButton button2 = new TextButton("SCORE", skin);
        table.add(button2).pad(20);
        table.row();

        sectionImage = new Texture(Gdx.files.internal("material/UI/Crystal.jpg"));

        TextButton button6 = new TextButton("NEXT", skin);
        button6.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                HelpScreen3.this.transitionTo(new HelpScreen4());
            }
        });
        table.add(button6).pad(20).padTop(650);
        table.row();

        TextButton button7 = new TextButton("BACK", skin);
        button7.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                HelpScreen3.this.transitionTo(new MainMenu());
            }
        });
        table.add(button7).pad(20);
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
        spriteBatch.draw(sectionImage, (Gdx.graphics.getWidth()/2)-(sectionImage.getWidth()/2), (Gdx.graphics.getHeight()/2)-(sectionImage.getHeight()/2)-60, 900, 600);
        spriteBatch.end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        super.renderCurtain();
    }

    @Override
    protected void onFadeOutTermination() {
    }
}
