package com.minemeander.screen;

import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.minemeander.Art;

/**
 * Created by tex on 6/5/2016.
 */
public class HelpScreen5 extends AbstractScreen {
    private SpriteBatch spriteBatch = new SpriteBatch();
    private Stage stage = new Stage();
    private Skin skin = new Skin();
    private Texture titleImage, sectionImage;

    public HelpScreen5() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        skin.add("white", new Texture(pixmap));
        skin.add("default", Art.menuFont);
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

        Table table = new Table();
        table.padTop(100).padLeft(650);
        table.setFillParent(true);
        stage.addActor(table);

        titleImage = new Texture(Gdx.files.internal("material/UI/BackgroundHelp.jpg"));

        TextButton button2 = new TextButton("Level Completion", skin);
        table.add(button2).padTop(120);
        table.row();

        sectionImage = new Texture(Gdx.files.internal("material/UI/Completion.jpg"));

        Image nextbutton = new Image(new Texture(Gdx.files.internal("material/UI/button/next_button.png")));
        nextbutton.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Art.buttonSound.play();
                HelpScreen5.this.transitionTo(new HelpScreen());
                return super.touchDown(event, x, y, pointer, button);
            }
        });


        //table.row();

        Image backbutton = new Image(new Texture(Gdx.files.internal("material/UI/button/back_button.png")));
        backbutton.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Art.buttonSound.play();
                HelpScreen5.this.transitionTo(new MainMenu());
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        table.add(backbutton).pad(20).padTop(650);
        table.add(nextbutton).pad(20).padLeft(200).padTop(-200);
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
