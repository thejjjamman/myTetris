package tgame;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.Input;

public class MIKEMODE extends state {

	private Texture backGround;

	public block[][] movingBlocks;
	public block[][] stopedBlocks;

	private int timeInterval = 5;// 50;//50 is good to start
	private int timeCountMove = 0;
	private int timeCountInput = 0;

	// block colours
	final Texture GRAY = new Texture("gray.png");
	final Texture BLUE = new Texture("blue.png");
	final Texture RED = new Texture("red.png");
	final Texture GREEN = new Texture("green.png");
	final Texture ORANGE = new Texture("orange.png");
	final Texture YELOW = new Texture("yelow.png");
	final Texture MIKE = new Texture("a.png");
	final Texture EXPLOSIVE = new Texture("explosivo.png");

	final Texture LIGHTBLUE = new Texture("lightblue.png");
	final Texture PURPLE = new Texture("purple.png");

	// private Vector3 oldPosition;
	private Vector3 newPosition;

	private int rowLength = 0;

	private Random rn = new Random();
	private int currentShapeCode;
	private int FutureShapeCode = rn.nextInt(7) + 1;
	public block[][] futureBlock;
	private int futureXPix = 300;
	private int futureYPix = 500;

	int exX=0;
	int exY=0;
	
	private int rotation = 0;
	int score=0;
	// make int array of blocks

	protected MIKEMODE(GameStateManager gsmIn) {
		super(gsmIn);
		backGround = new Texture("blackBackground.png");

		stopedBlocks = new block[20][26];
		movingBlocks = new block[20][26];// so yeah my arrays are backwords with
											// the gdx libary calling bot left
											// 0,0 and me calling top left 0.0
		futureBlock = new block[8][6];

		fillstopedBlocks();

		triggerNextShape();

		// stopedBlocks[5][24] = new block(5 * 25, 24 * 25, BLUE);//force game
		// over
		/*
		 * movingBlocks[2][5] = new block(2 * 25, 5 * 25, BLUE);
		 * movingBlocks[2][10] = new block(2 * 25, 10 * 25, BLUE); for (int x =
		 * 1; x < movingBlocks.length - 1; x++) { movingBlocks[x][4] = new
		 * block(x * 25, 4 * 25, BLUE); }
		 * 
		 * stopedBlocks[1][1] = new block(1 * 25, 1 * 25, BLUE);
		 */

		/*
		 * for (int x = 1; x < blocks.length - 2; x++) { blocks[x][2] = new
		 * block(x * 25, 2 * 25, BLUE, false); } for (int x = 1; x <
		 * blocks.length - 2; x++) { blocks[x][4] = new block(x * 25, 4 * 25,
		 * BLUE, false); }
		 * 
		 * 
		 * blocks[1][3] = new block(1 * 25, 3 * 25, BLUE, false);
		 * 
		 * // square blocks[5][5] = new block(5 * 25, 5 * 25, BLUE, false);
		 * blocks[5][6] = new block(5 * 25, 6 * 25, BLUE, false); blocks[6][5] =
		 * new block(6 * 25, 5 * 25, BLUE, false); blocks[6][6] = new block(6 *
		 * 25, 6 * 25, BLUE, false);
		 * 
		 * // pyr blocks[5][10] = new block(5 * 25, 10 * 25, BLUE, false);
		 * blocks[6][10] = new block(6 * 25, 10 * 25, BLUE, false);
		 * blocks[7][10] = new block(7 * 25, 10 * 25, BLUE, false);
		 * blocks[6][11] = new block(6 * 25, 11 * 25, BLUE, false);
		 */
		// testblock = new block((Tetris1.WIDTH/2)-(25/2),(Tetris1.HIGHT-25),new
		// Texture( "blueblock.png"));

	}

	private void fillstopedBlocks() {

		for (int x = 0; x < stopedBlocks.length; x++) {
			for (int y = 0; y < stopedBlocks[x].length; y++) {
				if (y == 0 || y == stopedBlocks[x].length - 1 || x == 0 || x == stopedBlocks.length - 1) {
					stopedBlocks[x][y] = new block(x * 25, y * 25, MIKE, false);
				}
			}
		}
	}

	private void addShape(int code) {
		rotation = 0;
		int x = 10;
		int y = 24;// 24

		if (code == 1) {// square
			movingBlocks[x][y] = new block(x * 25, y * 25, LIGHTBLUE, false);
			movingBlocks[x + 1][y] = new block((x + 1) * 25, y * 25, LIGHTBLUE, false);

			movingBlocks[x][y - 1] = new block(x * 25, (y - 1) * 25, LIGHTBLUE, false);
			movingBlocks[x + 1][y - 1] = new block((x + 1) * 25, (y - 1) * 25, LIGHTBLUE, false);
		}

		else if (code == 2) {// pir
			movingBlocks[x - 1][y] = new block((x - 1) * 25, y * 25, YELOW, false);
			movingBlocks[x][y] = new block(x * 25, y * 25, YELOW, false);
			movingBlocks[x + 1][y] = new block((x + 1) * 25, y * 25, YELOW, false);
			movingBlocks[x][y - 1] = new block(x * 25, (y - 1) * 25, YELOW, true);

		} else if (code == 3) {// line
			movingBlocks[x - 1][y] = new block((x - 1) * 25, y * 25, RED, true);
			movingBlocks[x][y] = new block(x * 25, y * 25, RED, false);
			movingBlocks[x + 1][y] = new block((x + 1) * 25, y * 25, RED, false);
			movingBlocks[x + 2][y] = new block((x + 2) * 25, y * 25, RED, false);
		}

		else if (code == 4) {// left block L
			movingBlocks[x - 1][y] = new block((x - 1) * 25, y * 25, BLUE, false);
			movingBlocks[x][y] = new block(x * 25, y * 25, BLUE, true);
			movingBlocks[x + 1][y] = new block((x + 1) * 25, y * 25, BLUE, false);
			movingBlocks[x - 1][y - 1] = new block((x - 1) * 25, (y - 1) * 25, BLUE, false);

		} else if (code == 5) {// right block L
			movingBlocks[x - 1][y] = new block((x - 1) * 25, y * 25, ORANGE, false);
			movingBlocks[x][y] = new block(x * 25, y * 25, ORANGE, true);
			movingBlocks[x + 1][y] = new block((x + 1) * 25, y * 25, ORANGE, false);
			movingBlocks[x + 1][y - 1] = new block((x + 1) * 25, (y - 1) * 25, ORANGE, false);
		} else if (code == 6) {// -_ shape

			movingBlocks[x][y] = new block(x * 25, y * 25, PURPLE, false);
			movingBlocks[x + 1][y] = new block((x + 1) * 25, y * 25, PURPLE, true);
			movingBlocks[x + 1][y - 1] = new block((x + 1) * 25, (y - 1) * 25, PURPLE, false);
			movingBlocks[x + 2][y - 1] = new block((x + 2) * 25, (y - 1) * 25, PURPLE, false);
		}

		else if (code == 7) {// _- shape
			movingBlocks[x + 1][y] = new block((x + 1) * 25, y * 25, GREEN, true);
			movingBlocks[x + 2][y] = new block((x + 2) * 25, y * 25, GREEN, false);
			movingBlocks[x + 1][y - 1] = new block((x + 1) * 25, (y - 1) * 25, GREEN, false);
			movingBlocks[x][y - 1] = new block((x) * 25, (y - 1) * 25, GREEN, false);
		} else if (code == 8) {// mike
			movingBlocks[x][y] = new block((x + 1) * 25, y * 25, MIKE, true);

		}

	}

	private void handleHoldDownInput() {
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			updateMoveingBlockArrayDirection(-1, 0);
			updateMoveingBlockArray();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			updateMoveingBlockArrayDirection(1, 0);
			updateMoveingBlockArray();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			// System.out.println("up");

			// rotate
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {

			updateMoveingBlockArrayDirection(0, -1);
			updateMoveingBlockArray();
		}
	}

	@Override
	public void handleInput() {

		if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
			updateMoveingBlockArrayDirection(-1, 0);
			updateMoveingBlockArray();
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {

			updateMoveingBlockArrayDirection(1, 0);
			updateMoveingBlockArray();

		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {

			updateMoveingBlockArrayDirection(0, -1);
			updateMoveingBlockArray();
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {

			roatateCurrentShape();
		}

		// block that finds top of shape

	}

	/*
	 * Rotate by +90:
	 * 
	 * Transpose Reverse each row Rotate by -90:
	 * 
	 * Transpose Reverse each column
	 */// or i could hard code for 4 hours .....
	private void roatateCurrentShape() {// takes in location of
		// shape
		boolean broken = false;

		int sX = -1, sY = -1;
		for (int x = 1; x < stopedBlocks.length - 1; x++) {
			for (int y = 1; y < stopedBlocks[x].length - 1; y++) {
				if (movingBlocks[x][y] != null) {
					if (movingBlocks[x][y].isCenter) {
						sX = x;
						sY = y;
						broken = true;
						break;
					}
					if (broken) {
						break;
					}
				}

			}
			if (broken) {
				break;
			}
		}
		// end of block that finds top of currentshape
		if (sX != -1 || sY != -1) {// fail safe

			if (currentShapeCode == 1) {// can do nothing square
				// do nothing its a square
			} else if (currentShapeCode == 2) {
				rotatePir(sX, sY);
			} else if (currentShapeCode == 3) {
				rotateLine(sX, sY);
			}

			else if (currentShapeCode == 4) {// lLLL
				rotateL(sX, sY);

			}

			else if (currentShapeCode == 5) {// _l
				rotateBL(sX, sY);

			} else if (currentShapeCode == 6) {// -_
				rotateBS(sX, sY);

			} else if (currentShapeCode == 7) {// _-
				rotateS(sX, sY);

			}

			if (rotation == 4) {
				rotation = 0;// reset rotation
			}
		}
	}

	private void rotateBS(int sX, int sY) {// -_
		if (rotation == 0 && stopedBlocks[sX][sY + 1] == null
				&& /* stopedBlocks[sX-1][sY] == null && */stopedBlocks[sX - 1][sY - 1] == null) {
			// take away

			// movingBlocks[sX-1][sY] = null;
			movingBlocks[sX][sY - 1] = null;
			movingBlocks[sX + 1][sY - 1] = null;

			// put back
			movingBlocks[sX][sY + 1] = new block((sX) * 25, (sY + 1) * 25, PURPLE, false);
			// movingBlocks[sX-1][sY] = new block((sX-1) * 25, (sY)* 25, PURPLE,
			// false);
			movingBlocks[sX - 1][sY - 1] = new block((sX - 1) * 25, (sY - 1) * 25, PURPLE, false);
			rotation++;
		} else if (rotation == 1 && stopedBlocks[sX - 1][sY + 1] == null && stopedBlocks[sX][sY + 1] == null
				&& stopedBlocks[sX + 1][sY] == null) {
			// take away

			movingBlocks[sX][sY + 1] = null;
			movingBlocks[sX - 1][sY] = null;
			movingBlocks[sX - 1][sY - 1] = null;

			// put back

			movingBlocks[sX - 1][sY + 1] = new block((sX) * 25, (sY + 1) * 25, PURPLE, false);
			movingBlocks[sX][sY + 1] = new block((sX) * 25, (sY + 1) * 25, PURPLE, false);
			movingBlocks[sX + 1][sY] = new block((sX + 1) * 25, (sY) * 25, PURPLE, false);
			rotation++;
		} else if (rotation == 2 && stopedBlocks[sX + 1][sY + 1] == null && stopedBlocks[sX + 1][sY] == null
				&& stopedBlocks[sX][sY - 1] == null) {
			// take away
			movingBlocks[sX - 1][sY + 1] = null;
			movingBlocks[sX][sY + 1] = null;
			movingBlocks[sX + 1][sY] = null;

			// put back

			movingBlocks[sX + 1][sY + 1] = new block((sX + 1) * 25, (sY + 1) * 25, PURPLE, false);
			movingBlocks[sX + 1][sY] = new block((sX + 1) * 25, (sY) * 25, PURPLE, false);
			movingBlocks[sX][sY - 1] = new block((sX) * 25, (sY - 1) * 25, PURPLE, false);
			rotation++;
		}

		else if (rotation == 3 && stopedBlocks[sX - 1][sY] == null && stopedBlocks[sX + 1][sY] == null
				&& stopedBlocks[sX + 1][sY - 1] == null) {
			// take away
			movingBlocks[sX + 1][sY + 1] = null;
			movingBlocks[sX + 1][sY] = null;
			movingBlocks[sX][sY - 1] = null;

			// put back
			movingBlocks[sX - 1][sY] = new block((sX - 1) * 25, (sY) * 25, PURPLE, false);
			movingBlocks[sX][sY - 1] = new block((sX) * 25, (sY - 1) * 25, PURPLE, false);
			movingBlocks[sX + 1][sY - 1] = new block((sX + 1) * 25, (sY - 1) * 25, PURPLE, false);

			rotation++;
		}

	}

	private void rotateS(int sX, int sY) {
		if (rotation == 0 && stopedBlocks[sX - 1][sY + 1] == null && stopedBlocks[sX - 1][sY] == null) {
			// take away

			movingBlocks[sX + 1][sY] = null;

			movingBlocks[sX - 1][sY - 1] = null;

			// put back
			movingBlocks[sX - 1][sY + 1] = new block((sX - 1) * 25, (sY + 1) * 25, GREEN, false);
			movingBlocks[sX - 1][sY] = new block((sX - 1) * 25, (sY) * 25, GREEN, false);

			rotation++;
		} else if (rotation == 1 && stopedBlocks[sX][sY + 1] == null && stopedBlocks[sX + 1][sY + 1] == null) {
			// take away

			movingBlocks[sX - 1][sY + 1] = null;
			movingBlocks[sX][sY - 1] = null;

			// put back

			movingBlocks[sX][sY + 1] = new block((sX) * 25, (sY + 1) * 25, GREEN, false);
			movingBlocks[sX + 1][sY + 1] = new block((sX + 1) * 25, (sY + 1) * 25, GREEN, false);
			rotation++;
		}

		else if (rotation == 2 && stopedBlocks[sX + 1][sY] == null && stopedBlocks[sX + 1][sY - 1] == null) {
			// take away
			movingBlocks[sX - 1][sY] = null;
			movingBlocks[sX + 1][sY + 1] = null;

			// put back
			movingBlocks[sX + 1][sY] = new block((sX + 1) * 25, (sY) * 25, GREEN, false);
			movingBlocks[sX + 1][sY - 1] = new block((sX + 1) * 25, (sY - 1) * 25, GREEN, false);

			rotation++;
		}

		else if (rotation == 3 && stopedBlocks[sX][sY - 1] == null && stopedBlocks[sX - 1][sY - 1] == null) {
			// take away
			movingBlocks[sX][sY + 1] = null;
			movingBlocks[sX + 1][sY - 1] = null;

			// put back

			movingBlocks[sX][sY - 1] = new block((sX) * 25, (sY - 1) * 25, GREEN, false);
			movingBlocks[sX - 1][sY - 1] = new block((sX - 1) * 25, (sY - 1) * 25, GREEN, false);

			rotation++;
		}

	}

	private void rotateBL(int sX, int sY) {
		if (rotation == 0 && stopedBlocks[sX][sY - 1] == null && stopedBlocks[sX][sY + 1] == null
				&& stopedBlocks[sX - 1][sY - 1] == null) {
			// take away

			movingBlocks[sX - 1][sY] = null;
			movingBlocks[sX + 1][sY] = null;
			movingBlocks[sX + 1][sY - 1] = null;

			// put back
			movingBlocks[sX][sY - 1] = new block((sX) * 25, (sY - 1) * 25, ORANGE, false);
			movingBlocks[sX][sY + 1] = new block((sX) * 25, (sY + 1) * 25, ORANGE, false);
			movingBlocks[sX - 1][sY - 1] = new block((sX - 1) * 25, (sY - 1) * 25, ORANGE, false);
			rotation++;
		} else if (rotation == 1 && stopedBlocks[sX][sY - 1] == null && stopedBlocks[sX + 1][sY] == null
				&& stopedBlocks[sX - 1][sY + 1] == null) {
			// take away

			movingBlocks[sX][sY - 1] = null;
			movingBlocks[sX][sY + 1] = null;
			movingBlocks[sX - 1][sY - 1] = null;

			// put back

			movingBlocks[sX - 1][sY] = new block((sX - 1) * 25, (sY) * 25, ORANGE, false);
			movingBlocks[sX + 1][sY] = new block((sX + 1) * 25, (sY) * 25, ORANGE, false);
			movingBlocks[sX - 1][sY + 1] = new block((sX - 1) * 25, (sY + 1) * 25, ORANGE, false);
			rotation++;
		} else if (rotation == 2 && stopedBlocks[sX][sY + 1] == null && stopedBlocks[sX][sY - 1] == null
				&& stopedBlocks[sX + 1][sY + 1] == null) {
			// take away
			movingBlocks[sX - 1][sY] = null;
			movingBlocks[sX + 1][sY] = null;
			movingBlocks[sX - 1][sY + 1] = null;

			// put back

			movingBlocks[sX][sY + 1] = new block((sX) * 25, (sY + 1) * 25, ORANGE, false);
			movingBlocks[sX][sY - 1] = new block((sX) * 25, (sY - 1) * 25, ORANGE, false);
			movingBlocks[sX + 1][sY + 1] = new block((sX + 1) * 25, (sY + 1) * 25, ORANGE, false);
			rotation++;
		}

		else if (rotation == 3 && stopedBlocks[sX - 1][sY] == null && stopedBlocks[sX + 1][sY] == null
				&& stopedBlocks[sX + 1][sY - 1] == null) {
			// take away
			movingBlocks[sX][sY + 1] = null;
			movingBlocks[sX][sY - 1] = null;
			movingBlocks[sX + 1][sY + 1] = null;

			// put back
			movingBlocks[sX - 1][sY] = new block((sX - 1) * 25, sY * 25, ORANGE, false);
			movingBlocks[sX + 1][sY] = new block((sX + 1) * 25, sY * 25, ORANGE, false);
			movingBlocks[sX + 1][sY - 1] = new block((sX + 1) * 25, (sY - 1) * 25, ORANGE, false);

			rotation++;
		}

	}

	private void rotateL(int sX, int sY) {
		if (rotation == 0 && stopedBlocks[sX][sY - 1] == null && stopedBlocks[sX][sY + 1] == null
				&& stopedBlocks[sX - 1][sY + 1] == null) {
			// take away
			// System.out.println("a");
			// movingBlocks[sX][sY] = null;
			movingBlocks[sX - 1][sY] = null;
			movingBlocks[sX + 1][sY] = null;
			movingBlocks[sX - 1][sY - 1] = null;
			// put back

			movingBlocks[sX][sY - 1] = new block((sX) * 25, (sY - 1) * 25, BLUE, false);
			movingBlocks[sX][sY + 1] = new block((sX) * 25, (sY + 1) * 25, BLUE, false);
			movingBlocks[sX - 1][sY + 1] = new block((sX - 1) * 25, (sY + 1) * 25, BLUE, false);
			rotation++;
		} else if (rotation == 1 && stopedBlocks[sX - 1][sY] == null && stopedBlocks[sX + 1][sY] == null
				&& stopedBlocks[sX - 1][sY + 1] == null) {
			// take away

			movingBlocks[sX][sY - 1] = null;
			movingBlocks[sX][sY + 1] = null;
			movingBlocks[sX - 1][sY + 1] = null;

			// put back

			movingBlocks[sX - 1][sY] = new block((sX - 1) * 25, (sY) * 25, BLUE, false);
			movingBlocks[sX + 1][sY] = new block((sX + 1) * 25, (sY) * 25, BLUE, false);
			movingBlocks[sX + 1][sY + 1] = new block((sX + 1) * 25, (sY + 1) * 25, BLUE, false);
			rotation++;
		} else if (rotation == 2 && stopedBlocks[sX][sY + 1] == null && stopedBlocks[sX][sY - 1] == null
				&& stopedBlocks[sX + 1][sY - 1] == null) {
			// take away
			movingBlocks[sX - 1][sY] = null;
			movingBlocks[sX + 1][sY] = null;
			movingBlocks[sX + 1][sY + 1] = null;

			// put back

			movingBlocks[sX][sY + 1] = new block((sX) * 25, (sY + 1) * 25, BLUE, false);
			movingBlocks[sX][sY - 1] = new block((sX) * 25, (sY - 1) * 25, BLUE, false);
			movingBlocks[sX + 1][sY - 1] = new block((sX + 1) * 25, (sY - 1) * 25, BLUE, false);
			rotation++;
		}

		else if (rotation == 3 && stopedBlocks[sX - 1][sY] == null && stopedBlocks[sX + 1][sY] == null
				&& stopedBlocks[sX - 1][sY - 1] == null) {
			// take away
			movingBlocks[sX][sY + 1] = null;
			movingBlocks[sX][sY - 1] = null;
			movingBlocks[sX + 1][sY - 1] = null;

			// put back

			movingBlocks[sX - 1][sY] = new block((sX - 1) * 25, sY * 25, BLUE, false);

			movingBlocks[sX + 1][sY] = new block((sX + 1) * 25, sY * 25, BLUE, false);
			movingBlocks[sX - 1][sY - 1] = new block((sX - 1) * 25, (sY - 1) * 25, BLUE, false);

			rotation++;
		}

	}

	private void rotateLine(int sX, int sY) {

		if (rotation == 0 && stopedBlocks[sX + 2][sY + 1] == null && stopedBlocks[sX + 2][sY - 1] == null
				&& stopedBlocks[sX + 2][sY - 2] == null) {
			// take away
			// System.out.println("a");
			movingBlocks[sX][sY] = null;
			movingBlocks[sX + 1][sY] = null;
			// movingBlocks[sX+2][sY] = null;
			movingBlocks[sX + 3][sY] = null;
			// put back

			movingBlocks[sX + 2][sY + 1] = new block((sX + 2) * 25, (sY + 1) * 25, RED, true);
			movingBlocks[sX + 2][sY - 1] = new block((sX + 2) * 25, (sY - 1) * 25, RED, false);
			movingBlocks[sX + 2][sY - 2] = new block((sX + 2) * 25, (sY - 2) * 25, RED, false);
			rotation++;
		} else if (rotation == 1 && stopedBlocks[sX + 1][sY - 2] == null && stopedBlocks[sX - 1][sY - 2] == null
				&& stopedBlocks[sX - 1][sY - 2] == null) {
			// take awway
			movingBlocks[sX][sY] = null;
			movingBlocks[sX][sY - 1] = null;
			// movingBlocks[sX][sY-2] = null;
			movingBlocks[sX][sY - 3] = null;
			// put back

			movingBlocks[sX + 1][sY - 2] = new block((sX + 1) * 25, (sY - 2) * 25, RED, false);
			movingBlocks[sX - 1][sY - 2] = new block((sX - 1) * 25, (sY - 2) * 25, RED, false);
			movingBlocks[sX - 2][sY - 2] = new block((sX - 2) * 25, (sY - 2) * 25, RED, true);
			rotation++;
		} else if (rotation == 2 && stopedBlocks[sX + 1][sY + 2] == null && stopedBlocks[sX + 1][sY + 1] == null
				&& stopedBlocks[sX + 1][sY - 1] == null) {
			// take awway
			movingBlocks[sX][sY] = null;
			// movingBlocks[sX+1][sY] = null;
			movingBlocks[sX + 2][sY] = null;
			movingBlocks[sX + 3][sY] = null;
			// put back

			movingBlocks[sX + 1][sY + 2] = new block((sX + 1) * 25, (sY + 2) * 25, RED, true);
			movingBlocks[sX + 1][sY + 1] = new block((sX + 1) * 25, (sY + 1) * 25, RED, false);
			movingBlocks[sX + 1][sY - 1] = new block((sX + 1) * 25, (sY - 1) * 25, RED, false);
			rotation++;
		}

		else if (rotation == 3 && stopedBlocks[sX - 1][sY - 1] == null && stopedBlocks[sX + 1][sY - 1] == null
				&& stopedBlocks[sX + 2][sY - 1] == null) {
			// take awway
			movingBlocks[sX][sY] = null;
			// movingBlocks[sX+1][sY-1] = null;
			movingBlocks[sX][sY - 2] = null;
			movingBlocks[sX][sY - 3] = null;
			// put back

			movingBlocks[sX - 1][sY - 1] = new block((sX - 1) * 25, (sY - 1) * 25, RED, true);
			movingBlocks[sX + 1][sY - 1] = new block((sX + 1) * 25, (sY - 1) * 25, RED, false);
			movingBlocks[sX + 2][sY - 1] = new block((sX + 2) * 25, (sY - 1) * 25, RED, false);
			rotation++;
		}

	}

	private void rotatePir(int sX, int sY) {
		if (rotation == 0 && stopedBlocks[sX + 1][sY] == null && stopedBlocks[sX + 1][sY - 1] == null) {
			movingBlocks[sX][sY + 1] = null;
			movingBlocks[sX - 1][sY + 1] = null;

			movingBlocks[sX + 1][sY] = new block((sX + 1) * 25, sY * 25, YELOW, false);
			movingBlocks[sX + 1][sY - 1] = new block((sX + 1) * 25, (sY - 1) * 25, YELOW, false);
			rotation++;

		} else if (rotation == 1 && stopedBlocks[sX - 1][sY - 1] == null && stopedBlocks[sX][sY - 1] == null) {

			movingBlocks[sX + 1][sY + 1] = null;
			movingBlocks[sX + 1][sY] = null;

			movingBlocks[sX - 1][sY - 1] = new block((sX - 1) * 25, (sY - 1) * 25, YELOW, false);

			movingBlocks[sX][sY - 1] = new block((sX) * 25, (sY - 1) * 25, YELOW, false);

			rotation++;
		} else if (rotation == 2 && stopedBlocks[sX - 1][sY + 1] == null && stopedBlocks[sX - 1][sY] == null) {
			movingBlocks[sX][sY - 1] = null;
			movingBlocks[sX + 1][sY - 1] = null;

			movingBlocks[sX - 1][sY + 1] = new block((sX - 1) * 25, (sY + 1) * 25, YELOW, false);

			movingBlocks[sX - 1][sY] = new block((sX - 1) * 25, (sY) * 25, YELOW, false);
			rotation++;

		} else if (rotation == 3 && stopedBlocks[sX + 1][sY + 1] == null && stopedBlocks[sX][sY + 1] == null) {// rotation

			movingBlocks[sX - 1][sY] = null;
			movingBlocks[sX - 1][sY - 1] = null;

			movingBlocks[sX][sY + 1] = new block((sX) * 25, (sY + 1) * 25, YELOW, false);
			movingBlocks[sX + 1][sY + 1] = new block((sX + 1) * 25, (sY + 1) * 25, YELOW, false);
			rotation++;
		}

	}

	// updates the position of each block in my block array. and then tells each
	// block to update in the same loop
	int exploTime=0;
	@Override
	public void update(float dt) {
		// this.handleInput();
		// System.out.print("k "+dt);
		// updateMoveingBlockArray(dt);
		timeCountMove++;
		timeCountInput++;

		handleInput();
		
		if (timeCountMove > timeInterval) {// timeing

			timeCountMove = 0;
			// printgrid();
			// System.out.println("tick");

			updateMoveingBlockArrayDirection(0, -1);// move blocks down
			updateMoveingBlockArray();

			if(exX!=0 && exY!=0){
				exploTime++;
				if(exploTime>=3){
					stopedBlocks[exX][exY]=null;
					exX=0;
					exY=0;
					exploTime=0;
				}
			}
			if (isGameOver()) {
				gsm.set(new gameOverMenueState(gsm));
				this.dispose();// self destruct
			}
		}

	}

	private boolean isGameOver() {
		boolean answer = false;

		for (int x = 1; x < stopedBlocks.length - 1; x++) {// top

			if (stopedBlocks[x][24] != null) {
				answer = true;
				System.out.println("game over");
			}
		}

		return answer;
	}

	private void updateMoveingBlockArrayDirection(int plusX, int plusY) {

		int newX = 0;
		int newY = 0;
		if (movingBlocksCanMove(plusX, plusY)) {// if blocks can move

			if ((plusX == -1 && plusY == 0) || (plusX == 0 && plusY == -1)) {// going
																				// left
																				// or
																				// down
				for (int x = 0; x < movingBlocks.length; x++) {
					for (int y = 0; y < movingBlocks[x].length; y++) {
						if (movingBlocks[x][y] != null) {

							newX = x + plusX;
							newY = y + plusY;

							movingBlocks[newX][newY] = movingBlocks[x][y];

							movingBlocks[x][y] = null;

						}
					}
				}
			} else if (plusX == 1 && plusY == 0) {// going right

				for (int x = movingBlocks.length - 1; x > 0; x--) {
					for (int y = movingBlocks[x].length - 1; y > 0; y--) {
						if (movingBlocks[x][y] != null) {

							newX = x + plusX;
							newY = y + plusY;

							movingBlocks[newX][newY] = movingBlocks[x][y];

							movingBlocks[x][y] = null;
							// movingBlocks[newX][newY].update();
						}
					}
				}
			}
		}

		else if (plusY == -1) {// stop the blocks as they cant go further down
			if (currentShapeCode == 8) {

				for (int x = 0; x < movingBlocks.length; x++) {
					for (int y = 0; y < movingBlocks[x].length; y++) {
						if (movingBlocks[x][y] != null) {
							
							stopedBlocks[x][y] = new block((x-1)*25, (y-1)*25, EXPLOSIVE, true) ;
							exX=x;
							exY=y;
							movingBlocks[x][y] = null;
							if (y + 1 != movingBlocks[0].length-1) {
								
								
								stopedBlocks[x][y + 1] = null;
								if (x+1 != movingBlocks.length-1) {
									stopedBlocks[x + 1][y + 1] = null;
								}
								if (x-1 != 0) {
									stopedBlocks[x - 1][y + 1] = null;
								}
							}

							if (y -1 != 0) {
								stopedBlocks[x][y -1] = null;
								if (x+1 != movingBlocks.length-1) {
									stopedBlocks[x + 1][y - 1] = null;
								}
								if (x-1 != 0) {
									stopedBlocks[x - 1][y - 1] = null;
								}
							}

							if (x+1 != movingBlocks.length - 1) {
								stopedBlocks[x + 1][y] = null;
							}
							if (x -1 != 0) {
								stopedBlocks[x - 1][y] = null;
							}

							break;
						}
					}
				}

			} else {
				for (int x = 0; x < movingBlocks.length; x++) {
					for (int y = 0; y < movingBlocks[x].length; y++) {
						if (movingBlocks[x][y] != null) {

							stopedBlocks[x][y] = movingBlocks[x][y];
							movingBlocks[x][y] = null;
						}
					}
				}
			}

			newCheckTetris();

			triggerNextShape();

		}

		else {// blocks cant move in given direction

			System.out.println();
			// play sound afect like "errr"
			//
			//
			// todo add sound
		}

	}

	private void updateMoveingBlockArray() {// moves blocks on screen for next
											// render

		for (int x = 0; x < movingBlocks.length; x++) {
			for (int y = 0; y < movingBlocks[x].length; y++) {
				if (movingBlocks[x][y] != null) {

					// sort out positions
					// oldPosition = movingBlocks[x][y].getPosition();
					newPosition = new Vector3(x * 25, y * 25, 0);
					// new Vector3(oldPosition.x, oldPosition.y - 25, 0);
					movingBlocks[x][y].setPosition(newPosition);
					//

				}

			}
		}

	}

	private void updatestopedBlockArray() {// moves blocks on screen for next
											// render

		for (int x = 0; x < stopedBlocks.length; x++) {
			for (int y = 0; y < stopedBlocks[x].length; y++) {
				if (stopedBlocks[x][y] != null) {

					// sort out positions
					// oldPosition = movingBlocks[x][y].getPosition();
					newPosition = new Vector3(x * 25, y * 25, 0);
					// new Vector3(oldPosition.x, oldPosition.y - 25, 0);
					stopedBlocks[x][y].setPosition(newPosition);
					//

				}

			}
		}

	}

	private void updateFutureBlockArray() {// moves blocks on screen for next
		// render

		for (int x = 0; x < futureBlock.length; x++) {
			for (int y = 0; y < futureBlock[x].length; y++) {
				if (futureBlock[x][y] != null) {

					// sort out positions
					// oldPosition = movingBlocks[x][y].getPosition();
					newPosition = new Vector3((x * 25) + futureXPix, (y * 25) + futureYPix, 0);

					// new Vector3(oldPosition.x, oldPosition.y - 25, 0);
					futureBlock[x][y].setPosition(newPosition);
					//
				}
			}
		}

	}

	private boolean movingBlocksCanMove(int plusX, int plusY) {

		boolean answer = true;
		for (int x = 0; x < movingBlocks.length; x++) {
			for (int y = 0; y < movingBlocks[x].length; y++) {
				if (movingBlocks[x][y] != null) {
					if ((stopedBlocks[x + plusX][y + plusY] != null)) {
						answer = false;
					}
				}
			}
		}
		return answer;
	}

	private void triggerNextShape() {
		// randumly generates a shape number
		// 1 = square

		// futrueShapeCode= rand num
		// currentShapeCode=futrueShapeCode

		currentShapeCode = FutureShapeCode;
		FutureShapeCode = rn.nextInt(8) + 1;// rando number from 1 to 8
		// inclusive

		updateFutureBlockArray();
		rotation = 0;
		addShape(currentShapeCode);

	}

	private void newCheckTetris() {
		for (int y = 1; y < stopedBlocks[1].length - 1; y++) {
			rowLength = 0;
			for (int x = 1; x < stopedBlocks.length - 1; x++) {
				if (stopedBlocks[x][y] != null) {
					rowLength++;
				}

			}

			if (rowLength >= stopedBlocks.length - 2) {
				for (int x = 1; x < stopedBlocks.length - 1; x++) {

					for (int yy = y; yy < stopedBlocks[x].length - 2; yy++) {

						stopedBlocks[x][yy] = stopedBlocks[x][yy + 1];

					}

				}
				score++;
				updatestopedBlockArray();
				y--;
			}
		}

	}

	private void moveBlockASFarDownAsPosible(int x, int y) {

		for (int i = 0; i < y; i++) {
			if (stopedBlocks[x][y - 1] == null) {

				stopedBlocks[x][y - 1] = stopedBlocks[x][y];
				stopedBlocks[x][y] = null;
				y--;

			}

		}

	}

	private void printgrid() {

		for (int y = movingBlocks[1].length - 1; y > -1; y--) {
			for (int x = 0; x < movingBlocks.length; x++) {

				if (movingBlocks[x][y] != null || stopedBlocks[x][y] != null) {
					System.out.print(1 + " ");
				} else {
					System.out.print(0 + " ");
				}
			}
			System.out.println("");
		}
		System.out.println("");
	}

	private void renderMovingBlocks(SpriteBatch sb) {

		for (int x = 0; x < movingBlocks.length; x++) {
			for (int y = 0; y < movingBlocks[x].length; y++) {
				if (movingBlocks[x][y] != null) {
					sb.draw(movingBlocks[x][y].getImg(), movingBlocks[x][y].getPosition().x,
							movingBlocks[x][y].getPosition().y);
				}
			}
		}

	}

	private void renderFutureBlocks(SpriteBatch sb) {
		for (int x = 0; x < futureBlock.length; x++) {
			for (int y = 0; y < futureBlock[x].length; y++) {
				if (futureBlock[x][y] != null) {
					sb.draw(futureBlock[x][y].getImg(), futureBlock[x][y].getPosition().x,
							futureBlock[x][y].getPosition().y);

				}

			}
		}

	}

	private void renderStoppedBlocks(SpriteBatch sb) {

		// sb.draw(testblock.getImg(),testblock.getPosition().x,testblock.getPosition().y);

		for (int x = 0; x < stopedBlocks.length; x++) {
			for (int y = 0; y < stopedBlocks[x].length; y++) {
				if (stopedBlocks[x][y] != null) {
					sb.draw(stopedBlocks[x][y].getImg(), stopedBlocks[x][y].getPosition().x,
							stopedBlocks[x][y].getPosition().y);
				}
			}
		}

	}

	@Override
	public void render(SpriteBatch sb) {

		sb.begin();
		sb.draw(backGround, 0, 0, Tetris1.WIDTH, Tetris1.HIGHT);

		renderStoppedBlocks(sb);
		renderMovingBlocks(sb);
		renderFutureBlocks(sb);

		sb.end();
	}

	@Override
	public void dispose() {

		backGround.dispose();
		BLUE.dispose();
		GRAY.dispose();

		RED.dispose();
		GREEN.dispose();
		ORANGE.dispose();
		YELOW.dispose();
		MIKE.dispose();

	}

}
