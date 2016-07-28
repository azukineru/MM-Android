package com.minemeander.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.minemeander.Art;

public class SettingsScreen extends AbstractScreen{
    private SpriteBatch spriteBatch = new SpriteBatch();
    private Stage stage = new Stage();
    private Skin skin = new Skin();
    private Skin skin2 = new Skin();
    private Texture titleImage, sectionImage;
    private String creditText, creditText2;
    public static Preferences pref;

    public SettingsScreen(){
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);

        pref = Gdx.app.getPreferences("com.minemeander.profile");

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
        textButtonStyle2.font = skin2.getFont("default");
        skin2.add("default", textButtonStyle2);

        Table table = new Table();
        table.padTop(100);
        //table.padLeft(-900);
        table.setFillParent(true);
        stage.addActor(table);

        titleImage = new Texture(Gdx.files.internal("material/UI/BackgroundHelp.jpg"));

        TextButton button = new TextButton("SETTINGS", skin);
        table.add(button).pad(20).padTop(140);
        table.row();

        TextButton message = new TextButton("Reset Game Data\nPress this button to reset game progress ( You can not undo this action ).\n", skin2);
        table.add(message);
        table.row();

        Image resetbutton = new Image(new Texture(Gdx.files.internal("material/UI/button/reset_button.png")));
        resetbutton.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Art.buttonSound.play();
                pref.putInteger("level1", 0);
                pref.putInteger("level2", 0);
                pref.putInteger("level3", 0);
                pref.putInteger("level4", 0);
                pref.putInteger("level5", 0);
                pref.putInteger("level6", 0);
                pref.putInteger("level7", 0);
                pref.putInteger("level8", 0);
                pref.putInteger("level9", 0);
                pref.putInteger("level10", 0);
                pref.putInteger("level11", 0);
                pref.putInteger("level12", 0);
                pref.putInteger("level13", 0);
                pref.putInteger("level14", 0);
                pref.putInteger("level15", 0);
                pref.flush();
                SettingsScreen.this.transitionTo(new MainMenu());
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        table.add(resetbutton).pad(20);
        table.row();

        Image backbutton = new Image(new Texture(Gdx.files.internal("material/UI/button/back_button.png")));
        backbutton.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Art.buttonSound.play();
                SettingsScreen.this.transitionTo(new MainMenu());
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        table.add(backbutton).padTop(250);
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

    @Override
    protected void onFadeOutTermination() {
    }
}
