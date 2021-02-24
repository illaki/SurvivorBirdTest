package com.mcanererdem.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.util.Random;

public class SurvivorBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background,grampy,blue1,blue2,blue3;
	float birdX,birdY;
	float velocity =0;
	float enemyVelocity = 2f;
	float gravity = 0.2f;
	int gameState = 0;
	float[] enemyX;
	int numberOfEnemySet = 4;
	int distance;
	float[] enemyOffSet1;
	float[] enemyOffSet2;
	float[] enemyOffSet3;
	Random random;
	Circle birdCircle;
	Circle[] enemyCircles1;
	Circle[] enemyCircles2;
	Circle[] enemyCircles3;
	BitmapFont font1;
	BitmapFont font2;
	int score = 0;
	int scoredEnemies = 0;

	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("background.png");
		grampy = new Texture("grampy.png");
		blue1 = new Texture("blue.png");
		blue2 = new Texture("blue.png");
		blue3 = new Texture("blue.png");

		enemyX = new float[numberOfEnemySet];
		distance = Gdx.graphics.getWidth() / 2;
		enemyOffSet1 = new float[numberOfEnemySet];
		enemyOffSet2 = new float[numberOfEnemySet];
		enemyOffSet3 = new float[numberOfEnemySet];
		random = new Random();

		birdCircle = new Circle();
		enemyCircles1 = new Circle[numberOfEnemySet];
		enemyCircles2 = new Circle[numberOfEnemySet];
		enemyCircles3 = new Circle[numberOfEnemySet];

		font1 = new BitmapFont();
		font1.setColor(Color.WHITE);
		font1.getData().setScale(3);

		font2 = new BitmapFont();
		font2.setColor(Color.WHITE);
		font2.getData().setScale(5);


		birdX = Gdx.graphics.getWidth() / 6;
		birdY = Gdx.graphics.getHeight() / 2;

		for (int i = 0; i< numberOfEnemySet; i++) {

			enemyX[i] = Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 12 +  i* distance;

			enemyOffSet1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

			enemyCircles1[i] = new Circle();
			enemyCircles2[i] = new Circle();
			enemyCircles3[i] = new Circle();
		}

	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		if (gameState == 1) {

			if (enemyX[scoredEnemies] < Gdx.graphics.getWidth() / 6) {
				score++;
				if (scoredEnemies < numberOfEnemySet -1)
				{
					scoredEnemies++;
				}else
				{
					scoredEnemies = 0;
				}
			}

			if (Gdx.input.justTouched()) {
				velocity = - (Gdx.graphics.getHeight() / 120);

			}

			for (int i = 0; i < numberOfEnemySet; i++) {
				if (enemyX[i] <=  -Gdx.graphics.getWidth() / 12) {

					enemyX[i] = enemyX[i] + numberOfEnemySet * distance;

					enemyOffSet1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
				}
				else {
					enemyX[i] = enemyX[i] - enemyVelocity;
				}
				batch.draw(blue1, enemyX[i] , Gdx.graphics.getHeight() / 2 + enemyOffSet1[i] , Gdx.graphics.getWidth() / 12, Gdx.graphics.getHeight() / 8);
				batch.draw(blue2, enemyX[i] , Gdx.graphics.getHeight() / 2 + enemyOffSet2[i] , Gdx.graphics.getWidth() / 12, Gdx.graphics.getHeight() / 8);
				batch.draw(blue3, enemyX[i] , Gdx.graphics.getHeight() / 2 + enemyOffSet3[i] , Gdx.graphics.getWidth() / 12, Gdx.graphics.getHeight() / 8);

				enemyCircles1[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 24 ,Gdx.graphics.getHeight() / 2 + enemyOffSet1[i] + Gdx.graphics.getHeight() / 16,Gdx.graphics.getWidth() / 24);
				enemyCircles2[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 24 ,Gdx.graphics.getHeight() / 2 + enemyOffSet2[i] + Gdx.graphics.getHeight() / 16,Gdx.graphics.getWidth() / 24);
				enemyCircles3[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 24 ,Gdx.graphics.getHeight() / 2 + enemyOffSet3[i] + Gdx.graphics.getHeight() / 16,Gdx.graphics.getWidth() / 24);
			}

			if (birdY > 0)
			{
				velocity = velocity + gravity ;
				birdY = birdY - velocity;
			}
			else if(birdY <= 0)//Sadece Koşul Silinebilir, Parantez Kalabilir.
			{
				gameState = 2;
			}
		}else if (gameState == 0 ){
			velocity = 0;
			birdX = Gdx.graphics.getWidth() / 6;
			birdY = Gdx.graphics.getHeight() / 2;

			if (Gdx.input.justTouched()) {
				gameState = 1;
			}
		}
		else if (gameState == 2) {
			font2.draw(batch,"Game Over\n  Score : " + score, Gdx.graphics.getWidth() / 2 - 200, Gdx.graphics.getHeight() / 2 +100);

			if (Gdx.input.justTouched())
			{
				gameState = 1;
				birdY = Gdx.graphics.getHeight() / 2;
				for (int i = 0; i < numberOfEnemySet; i++) {
					enemyX[i] = Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 12 +  i* distance;

					enemyOffSet1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

					enemyCircles1[i] = new Circle();
					enemyCircles2[i] = new Circle();
					enemyCircles3[i] = new Circle();
				}
				score = 0;
				scoredEnemies = 0;
				velocity = 0;
			}
		}

		batch.draw(grampy, birdX, birdY, Gdx.graphics.getWidth() / 15,Gdx.graphics.getHeight() / 10);
		font1.draw(batch,String.valueOf(score),5,Gdx.graphics.getHeight() - 5);

		batch.end();

		birdCircle.set(birdX + Gdx.graphics.getWidth() / 30,birdY + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 50 );

		for (int i = 0; i < numberOfEnemySet; i++)
		{
			if (Intersector.overlaps(birdCircle , enemyCircles1[i]) || Intersector.overlaps(birdCircle, enemyCircles2[i]) || Intersector.overlaps(birdCircle,enemyCircles3[i]))
			{
				gameState = 2;
			}
		}
	}
	
	@Override
	public void dispose () {

	}
}
