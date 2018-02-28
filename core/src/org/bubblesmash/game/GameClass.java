package org.bubblesmash.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

/**
 * Main code of application. An instance of this class is passed to the corresponding launcher classes.
 */
public class GameClass extends ApplicationAdapter {

    private Texture simpleCannonBallTexture;
    private Texture simpleWall;

    private OrthographicCamera camera;
    private SpriteBatch batch;

    private Array<Rectangle> cannonBalls;
    private long lastCannonBallTime;

    @Override
    public void create() {
        simpleCannonBallTexture = new Texture(Gdx.files.internal("ball_simple.png"));
        //simpleWall = new Texture(Gdx.files.internal("bucket.png"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        batch = new SpriteBatch();

        cannonBalls = new Array<Rectangle>();
        spawnCannonBalls();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        // for walls:
        //batch.draw(bucketImage, bucket.x, bucket.y);
        for(Rectangle ball: cannonBalls) {
            batch.draw(simpleCannonBallTexture, ball.x, ball.y);
        }

        batch.end();

        if (TimeUtils.nanoTime() - lastCannonBallTime > 1000000000){
            spawnCannonBalls();
        }

        Iterator<Rectangle> iter = cannonBalls.iterator();
        while(iter.hasNext()) {
            Rectangle cannonBall = iter.next();
            cannonBall.y -= 200 * Gdx.graphics.getDeltaTime();
            if(cannonBall.y + 64 < 0) iter.remove();
        }
    }

    @Override
    public void dispose() {
        simpleCannonBallTexture.dispose();
        batch.dispose();
        //img.dispose();
    }

    private void spawnCannonBalls() {
        Rectangle ball = new Rectangle();
        ball.x = MathUtils.random(0, 800 - 64);
        ball.y = 480;
        ball.width = 64;
        ball.height = 64;
        cannonBalls.add(ball);
        lastCannonBallTime = TimeUtils.nanoTime();
    }
}
