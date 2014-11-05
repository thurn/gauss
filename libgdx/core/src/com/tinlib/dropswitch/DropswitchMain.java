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
  private Sprite background;
  private OrthographicCamera camera;

  private Sprite red;

	@Override
	public void create () {
		batch = new SpriteBatch();

    camera = new OrthographicCamera();
    int width = Gdx.graphics.getWidth();
    int height = Gdx.graphics.getHeight();
    camera.setToOrtho(false /* yDown */, width, height);

    int shortEdge = width;
    int longEdge = height;
    if (width > height) {
      shortEdge = height;
      longEdge = width;
//      rotateToLandscape(true /* clockwise */);
    }

    int scaledShortEdge = Math.min(width, MathUtils.roundPositive(longEdge / ASPECT_RATIO));

    Texture texture = loadTexture("background.png", scaledShortEdge);
    texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);

    int xOffset = MathUtils.roundPositive((texture.getWidth() - shortEdge) / 2.0f);
    int yOffset = MathUtils.roundPositive((texture.getHeight() - longEdge) / 2.0f);

    background = new Sprite(new TextureRegion(texture, xOffset, yOffset, shortEdge, longEdge));
    red = new Sprite(new Texture("bucket.png"));
    red.setPosition(200, 200);
	}

  @Override
  public void resize(int width, int height) {
    if (width > height) {
      Gdx.app.log(";;;", "width>height");
//      background.rotate(-90);
      red.setOrigin(0, 0);
//      red.rotate(-90);
      red.setPosition(625, 200);
//      camera.rotate(-90);
    } else {
      Gdx.app.log(";;;", "height>width");
    }
//    Gdx.app.log(";;;", "resize " + width + "x" + height);
//
//    camera = new OrthographicCamera();
//    camera.setToOrtho(false /* yDown */, width, height);
//
//    int shortEdge = width;
//    int longEdge = height;
//    if (width > height) {
//      shortEdge = height;
//      longEdge = width;
////      rotateToLandscape(true /* clockwise */);
//    }
//
//    int scaledShortEdge = Math.min(width, MathUtils.roundPositive(longEdge / ASPECT_RATIO));
//
//    Texture texture = loadTexture("background.png", scaledShortEdge);
//    texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);
//
//    int xOffset = MathUtils.roundPositive((texture.getWidth() - shortEdge) / 2.0f);
//    int yOffset = MathUtils.roundPositive((texture.getHeight() - longEdge) / 2.0f);
//
//    background = new TextureRegion(texture, xOffset, yOffset, shortEdge, longEdge);
  }

  private void rotateToLandscape(boolean clockwise) {
    camera.translate(-Gdx.graphics.getWidth() / 2, -Gdx.graphics.getHeight() / 2);
    camera.rotate(clockwise ? -90 : 90);
    camera.translate(Gdx.graphics.getHeight() / 2, Gdx.graphics.getWidth() / 2);
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

//    camera.update();
//    batch.setProjectionMatrix(camera.combined);

		batch.begin();
//    background.draw(batch);
    red.draw(batch);
//    batch.draw(red, 0, 0);
//    batch.draw(background, 0, 0);
//		batch.draw(background, 140, -140);
//    background.draw(batch);
		batch.end();
	}
}
