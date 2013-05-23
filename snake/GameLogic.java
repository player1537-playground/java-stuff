class GameLogic {
	public final int mapX = 10;
	public final int mapY = 10;
	Board gameBoard = new Board(mapX, mapY);
	DisplayManager display = new TerminalDisplay(Board);
	
	public static Main(String[] args) {
		GameLogic game = new GameLogic();
		while (true) {
			game.update();
		}
	}

	public void update() {
		gameBoard.update();
		display.update();
	}
}
