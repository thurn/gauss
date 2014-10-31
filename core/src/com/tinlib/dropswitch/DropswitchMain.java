package com.tinlib.dropswitch;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class DropswitchMain extends ApplicationAdapter {
	private SpriteBatch batch;
  private TextureRegion background;

	@Override
	public void create () {
    System.out.println(";;;;; created at size " + Gdx.graphics.getWidth() + "x" + Gdx.graphics.getHeight());
		batch = new SpriteBatch();

    int width = Gdx.graphics.getWidth();
    int height = Gdx.graphics.getHeight();
    int scaledWidth = Math.min(width, MathUtils.roundPositive(height / 1.6f));
    int scaledHeight = Math.min(height, MathUtils.roundPositive(width * 1.6f));
    System.out.println(";;;;; scaled size: " + scaledWidth + "x" + scaledHeight);

    Texture texture = loadTexture("background.png", scaledWidth);
    texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);
    System.out.println(";;;;; texture size " + texture.getWidth() + "x" + texture.getHeight());

    int xOffset = MathUtils.roundPositive((texture.getWidth() - width) / 2.0f);
    int yOffset = MathUtils.roundPositive((texture.getHeight() - height) / 2.0f);
    System.out.println(";;;; scaled offsets: " + xOffset + " " + yOffset);

    background = new TextureRegion(texture, xOffset, yOffset, width, height);
    System.out.println(";;;; bg size " + background.getRegionWidth() + "x" + background.getRegionHeight());
	}

  private Texture loadTexture(String name, int screenWidth) {
    if (screenWidth <= 480) {
      return new Texture("480p/" + name);
    } else if (screenWidth <= 600) {
      return new Texture("600p/" + name);
    } else if (screenWidth <= 640) {
      return new Texture("640p/" + name);
    } else if (screenWidth <= 935) {
      return new Texture("935p/" + name);
    } else if (screenWidth <= 1005) {
      return new Texture("1005p/" + name);
    } else if (screenWidth <= 1063) {
      return new Texture("1063p/" + name);
    } else if (screenWidth <= 1080) {
      return new Texture("1080p/" + name);
    } else if (screenWidth <= 1255) {
      return new Texture("1255p/" + name);
    } else {
      return new Texture("2160p/" + name);
    }
  }

	@Override
	public void render () {
    Gdx.gl.glClearColor(1, 1, 1, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(background, 0, 0);
		batch.end();
	}
}
