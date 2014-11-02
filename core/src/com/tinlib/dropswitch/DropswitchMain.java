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
  private static final float ASPECT_RATIO = 1.7f;

	private SpriteBatch batch;
  private TextureRegion background;

	@Override
	public void create () {
		batch = new SpriteBatch();
	}

  @Override
  public void resize(int width, int height) {
    int shortEdge = width;
    int longEdge = height;
    if (width > height) {
      shortEdge = height;
      longEdge = width;
    }

    CurrentLauncher.get().log("Resize to " + width + "x" + height);
    int scaledWidth = Math.min(width, MathUtils.roundPositive(longEdge / ASPECT_RATIO));

    Texture texture = loadTexture("background.png", scaledWidth);
    texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);

    int xOffset = MathUtils.roundPositive((texture.getWidth() - shortEdge) / 2.0f);
    int yOffset = MathUtils.roundPositive((texture.getHeight() - longEdge) / 2.0f);

    background = new TextureRegion(texture, xOffset, yOffset, shortEdge, longEdge);
  }

  private Texture loadTexture(String name, int screenWidth) {
    if (screenWidth <= 400) {
      return new Texture("400p/" + name);
    } else if (screenWidth <= 600) {
      return new Texture("600p/" + name);
    } else if (screenWidth <= 640) {
      return new Texture("640p/" + name);
    } else if (screenWidth <= 710) {
      return new Texture("710p/" + name);
    } else if (screenWidth <= 750) {
      return new Texture("750p/" + name);
    } else if (screenWidth <= 880) {
      return new Texture("880p/" + name);
    } else if (screenWidth <= 1001) {
      return new Texture("1001p/" + name);
    } else if (screenWidth <= 1005) {
      return new Texture("1005p/" + name);
    } else if (screenWidth <= 1040) {
      return new Texture("1040p/" + name);
    } else if (screenWidth <= 1080) {
      return new Texture("1080p/" + name);
    } else if (screenWidth <= 1181) {
      return new Texture("1181p/" + name);
    } else {
      return new Texture("2160p/" + name);
    }
  }

	@Override
	public void render () {
    Gdx.gl.glClearColor(0.13f, 0.37f, 0.41f, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(background, 0, 0);
		batch.end();
	}
}
