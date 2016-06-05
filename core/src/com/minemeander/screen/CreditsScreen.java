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
public class CreditsScreen extends AbstractScreen{
    private SpriteBatch spriteBatch = new SpriteBatch();
    private Stage stage = new Stage();
    private Skin skin = new Skin();
    private Texture titleImage;
    private String creditText, creditText2;

    public CreditsScreen() {
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

        TextButton button = new TextButton("CREDITS", skin);
        table.add(button).pad(20);
        table.row();

        creditText = "2D SPRITE CHARACTER: SITHJESTER'S RMXP RESOURCES.\n" +
                "HTTP://UNTAMED.WILD-REFUGE.NET/RMXPFAQ.PHP\n";

        creditText2 = "2D ENVIRONMENT ASSETS: HTTP://OPENGAMEART.ORG/\n";

        TextButton button7 = new TextButton("BACK", skin);
        button7.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                CreditsScreen.this.transitionTo(new MainMenu());
            }
        });
        table.add(button7).padTop(600);
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
        Art.bitmapFont.draw(spriteBatch, creditText, (Gdx.graphics.getWidth()/2)-500, Gdx.graphics.getHeight()/2);
        Art.bitmapFont.draw(spriteBatch, creditText2, (Gdx.graphics.getWidth()/2)-500, Gdx.graphics.getHeight()/2-70);
        spriteBatch.end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        super.renderCurtain();
    }

    @Override
    protected void onFadeOutTermination() {
    }
}
