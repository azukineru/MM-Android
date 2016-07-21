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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.minemeander.Art;

public class CreditsScreen extends AbstractScreen{
    private SpriteBatch spriteBatch = new SpriteBatch();
    private Stage stage = new Stage();
    private Skin skin = new Skin();
    private Skin skin2 = new Skin();
    private Texture titleImage;
    private String creditText, creditText2;

    public CreditsScreen() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);

        skin.add("white", new Texture(pixmap));
        skin.add("default", Art.menuFont);
        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

        pixmap.setColor(Color.WHITE);
        pixmap.fill();

        skin2.add("white", new Texture(pixmap));
        skin2.add("default", Art.bitmapFont);
        TextButtonStyle textButtonStyle2 = new TextButtonStyle();
        textButtonStyle2.up = skin2.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle2.down = skin2.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle2.over = skin2.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle2.font = skin2.getFont("default");
        skin2.add("default", textButtonStyle2);

        Table table = new Table();
        table.padTop(100);
        //table.padLeft(-900);
        table.setFillParent(true);
        stage.addActor(table);

        titleImage = new Texture(Gdx.files.internal("material/UI/HC.png"));

        TextButton button = new TextButton("CREDITS", skin);
        table.add(button).pad(20).padTop(140);
        table.row();

        TextButton creditText = new TextButton("2D SPRITE CHARACTER: SITHJESTER'S RMXP RESOURCES.\n" +
                "HTTP://UNTAMED.WILD-REFUGE.NET/RMXPFAQ.PHP\n", skin2);
        table.add(creditText);
        table.row();

        TextButton creditText2 = new TextButton("2D ENVIRONMENT ASSETS: HTTP://OPENGAMEART.ORG/\n", skin2);
        table.add(creditText2);
        table.row();

        TextButton creditText3 = new TextButton("SOUND EFFECT & MUSIC: HTTP://OPENGAMEART.ORG/\n", skin2);
        table.add(creditText3);
        table.row();

        TextButton creditText4 = new TextButton("UI SKIN: HTTP://OPENCLIPART.ORG/\n", skin2);
        table.add(creditText4);
        table.row();

        TextButton profile = new TextButton("Developed by Faishal Azka J.\nemail : azukineru@gmail.com\n", skin2);
        table.add(profile);
        table.row();

        TextButton button7 = new TextButton("BACK", skin);
        button7.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                Art.buttonSound.play();
                CreditsScreen.this.transitionTo(new MainMenu());
            }
        });
        table.add(button7).padTop(280);
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
        //Art.menuFont.draw(spriteBatch, creditText, (Gdx.graphics.getWidth()/2)-500, Gdx.graphics.getHeight()/2);
        //Art.menuFont.draw(spriteBatch, creditText2, (Gdx.graphics.getWidth()/2)-500, Gdx.graphics.getHeight()/2-70);
        spriteBatch.end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        super.renderCurtain();
    }

    @Override
    protected void onFadeOutTermination() {
    }
}
