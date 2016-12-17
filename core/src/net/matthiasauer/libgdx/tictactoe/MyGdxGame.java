package net.matthiasauer.libgdx.tictactoe;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import net.matthiasauer.libgdx.tictactoe.controller.ComputerPlayer;
import net.matthiasauer.libgdx.tictactoe.controller.ControllerManager;
import net.matthiasauer.libgdx.tictactoe.controller.HumanPlayer;
import net.matthiasauer.libgdx.tictactoe.model.Owner;
import net.matthiasauer.libgdx.tictactoe.view.ViewManager;

import javax.inject.Inject;

public class MyGdxGame extends ApplicationAdapter {
	private final ControllerManager controllerManager;
	private final ViewManager viewManager;

	@Inject
	public MyGdxGame(ControllerManager controllerManager, ViewManager viewManager) {
		this.controllerManager = controllerManager;
		this.viewManager = viewManager;
	}
	
	@Override
	public void create () {
		// initalize the game
		this.controllerManager.initialize(
				new HumanPlayer(Owner.Circle),
				new ComputerPlayer(Owner.Cross)
		);
	}

	@Override
	public void render () {
		this.viewManager.render();
	}

	@Override
	public void dispose () {
		this.viewManager.dispose();
	}
}
