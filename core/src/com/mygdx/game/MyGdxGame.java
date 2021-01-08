package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ModelInfluencer;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture cat_left_img, cat_right_img, cat_up_img, cat_down_img;
	Texture mouse_img;
	Texture child_room_img;
	Rectangle cat_rect, mouse_rect;

	Music child_music;
	Sound mouse_sound;
	boolean left, right, up, down;
	Random x_rand_mouse, y_rand_mouse;
	@Override
	public void create () {
		batch = new SpriteBatch();

		cat_left_img = new Texture("cat_left.png");
		cat_right_img = new Texture("cat_right.png");
		cat_up_img = new Texture("cat_up.png");
		cat_down_img = new Texture("cat_down.png");
		mouse_img = new Texture("mouse.png");
		child_room_img = new Texture("child_room.png");

		child_music = Gdx.audio.newMusic(Gdx.files.internal("child_music.mp3"));
		mouse_sound = Gdx.audio.newSound(Gdx.files.internal("mouse_sound.mp3"));

		child_music.play();
		child_music.setLooping(true);

		cat_rect = new Rectangle();
		mouse_rect = new Rectangle();

		cat_rect.width = cat_left_img.getWidth();
		cat_rect.height = cat_left_img.getHeight();
		mouse_rect.width = mouse_img.getWidth();
		mouse_rect.height = mouse_img.getHeight();

		mouse_rect.setPosition(200, 200);

		left = false;
		right = true;
		up = false;
		down = false;

		x_rand_mouse = new Random();
		y_rand_mouse = new Random();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Cat logic movement
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
			cat_rect.x += 10;
			right = true;
			left = false;
			up = false;
			down = false;
		}

		if(Gdx.input.isKeyPressed(Keys.LEFT)) {
			cat_rect.x -= 10;
			right = false;
			left = true;
			up = false;
			down = false;
		}

		if(Gdx.input.isKeyPressed(Keys.DOWN)) {
			cat_rect.y -= 10;
			right = false;
			left = false;
			up = false;
			down = true;
		}

		if(Gdx.input.isKeyPressed(Keys.UP)) {
			cat_rect.y += 10;
			right = false;
			left = false;
			up = true;
			down = false;
		}

		if (cat_rect.getX() < 0)
			cat_rect.x+=10;

		if (cat_rect.getX() + cat_rect.getWidth()>750)
			cat_rect.x-=10;

		if (cat_rect.getY() < 0)
			cat_rect.y+=10;

		if (cat_rect.getY() + cat_rect.getWidth() > 600)
			cat_rect.y-=10;

		if (cat_rect.overlaps(mouse_rect)) {
			mouse_sound.play();
			mouse_rect.x = x_rand_mouse.nextInt(750 - (int) mouse_rect.width);
			mouse_rect.y = y_rand_mouse.nextInt(600 - (int) mouse_rect.height);
		}

		batch.begin();

		batch.draw(child_room_img,0,0);

		if (left)
			batch.draw(cat_left_img, cat_rect.x, cat_rect.y);

		if (right)
			batch.draw(cat_right_img, cat_rect.x, cat_rect.y);

		if (up)
			batch.draw(cat_up_img, cat_rect.x, cat_rect.y);

		if (down)
			batch.draw(cat_down_img, cat_rect.x, cat_rect.y);

		batch.draw(mouse_img, mouse_rect.x, mouse_rect.y);

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		cat_left_img.dispose();
		cat_right_img.dispose();
		cat_down_img.dispose();
		cat_up_img.dispose();
	}
}
