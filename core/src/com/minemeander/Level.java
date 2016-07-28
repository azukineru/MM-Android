package com.minemeander;

import static com.minemeander.Constant.BACKGROUND_LAYERS;
import static com.minemeander.Constant.METERS_PER_TILE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TmxMapLoader.Parameters;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import com.minemeander.ai.PathingTool;
import com.minemeander.engine.tiles.MMLevelLayout;
import com.minemeander.objects.GameObjectData;
import com.minemeander.objects.LevelObjectManager;
import com.minemeander.objects.CollisionCategory;
import com.minemeander.procedural.ProceduralLevelGenerator;
import com.minemeander.screen.CompletionScreen;
import com.minemeander.screen.GameOverScreen;
import com.minemeander.screen.LevelScreen;

public class Level {
	
	public static final int NUMBER_OF_WORLDS = 16;
	
	public OrthogonalTiledMapRenderer tiledMapRenderer;
	public TiledMap tiledMap;
	
	public boolean debugMode = false;
	public BitmapFont font;
	public World physicalWorld;
	
	public LevelObjectManager objectManager;
	public PathingTool pathingTool;
	public LevelCamera camera;
	public Vector2 gravityVector = new Vector2(0f, -200f);
	public LevelScreen screen;
	public int worldId;
	public int numRooms;

	public static Preferences pref;
	public static int levelState;

	public Level(LevelScreen levelScreen, int worldId) {
		super();
		this.screen = levelScreen;
		this.worldId = worldId;

		this.font = new BitmapFont();
		font.setColor(Color.YELLOW);

		this.physicalWorld = new World(gravityVector, true);
		this.objectManager = new LevelObjectManager(this);
		if( worldId == 1 || worldId == 2)
		{
			System.out.printf("Creating world %d. 4 rooms.\n", worldId);
			numRooms = 4;
		}
		else if(worldId == 3 || worldId == 4)
		{
			System.out.printf("Creating world %d. 5 rooms.\n", worldId);
			numRooms = 5;
		}
		else if(worldId == 5)
		{
			System.out.printf("Creating world %d. 6 rooms.\n", worldId);
			numRooms = 6;
		}
		else if(worldId == 6 || worldId == 7)
		{
			System.out.printf("Creating world %d. 7 rooms.\n", worldId);
			numRooms = 7;
		}
		else if(worldId == 8 || worldId == 9)
		{
			System.out.printf("Creating world %d. 8 rooms.\n", worldId);
			numRooms = 8;
		}
		else if(worldId == 10 || worldId == 11)
		{
			System.out.printf("Creating world %d. 9 rooms.\n", worldId);
			numRooms = 9;
		}
		else if(worldId == 12)
		{
			System.out.printf("Creating world %d. 10 rooms.\n", worldId);
			numRooms = 10;
		}
		else if(worldId == 13 || worldId == 14)
		{
			System.out.printf("Creating world %d. 11 rooms.\n", worldId);
			numRooms = 11;
		}
		else if(worldId == 15)
		{
			System.out.printf("Creating world %d. 12 rooms.\n", worldId);
			numRooms = 12;
		}
		else
		{
			System.out.printf("None.\n");
			numRooms = 1;
		}
		
		MMLevelLayout mmLevelLayout = MMLevelLayout.random(numRooms);
		
		Parameters params = new Parameters();
		params.textureMagFilter = TextureFilter.Nearest;
		params.textureMinFilter = TextureFilter.Nearest;
		TiledMap masterMap = new TmxMapLoader().load("material/output/master.tmx", params);
			
		tiledMap = ProceduralLevelGenerator.generateMap(masterMap, mmLevelLayout, worldId);
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 2f/32f);
		pathingTool = new PathingTool((TiledMapTileLayer) tiledMap.getLayers().get(BACKGROUND_LAYERS[0]), (TiledMapTileLayer) tiledMap.getLayers().get(BACKGROUND_LAYERS[1]));

		createPhysicsWorld();

		objectManager.populateLevel();

		Vector2 position = objectManager.getAvatar().body.getPosition();
		camera = new LevelCamera(this, position.x, position.y);

		pref = Gdx.app.getPreferences("com.minemeander.profile");

		//System.out.printf("levelstate = %d", levelState);
	}
	
	public void reset() {
		screen.reset();
	}
	
	private void createPhysicsWorld() {
		TiledMapTileLayer platforms = (TiledMapTileLayer) tiledMap.getLayers().get(BACKGROUND_LAYERS[0]);
		TiledMapTileLayer ladders = (TiledMapTileLayer) tiledMap.getLayers().get(BACKGROUND_LAYERS[1]);

		// Platforms

		for (int tileY = 0; tileY < platforms.getHeight(); tileY++) {
			int startRectTileX = -1;
			int startRectTileY = -1;
			int firstColIndex = 1;
			int lastColIndex = platforms.getWidth() - 1;
			for (int tileX = firstColIndex; tileX < lastColIndex; tileX++) {
				Object platformCollision = null;
				if (platforms.getCell(tileX, tileY) != null) {
					platformCollision = platforms.getCell(tileX, tileY).getTile().getProperties().get("col");
				}
				Object ladderPresent = null;
				if (ladders.getCell(tileX, tileY) != null) {
					ladderPresent = ladders.getCell(tileX, tileY).getTile().getProperties().get("ladder");
				}

				if (Constant.SOLID_ZONE.equals(platformCollision) && !Constant.LADDER_ZONE.equals(ladderPresent)) {
					if (startRectTileX == -1) {
						startRectTileX = tileX;
						startRectTileY = tileY;
					}
					if (tileX == lastColIndex - 1) {
						createRect(startRectTileX, startRectTileY, tileX, tileY);
						startRectTileX = -1;
						startRectTileY = -1;
					}
				} else if (startRectTileX != -1) {
					createRect(startRectTileX, startRectTileY, tileX - 1, tileY);
					startRectTileX = -1;
					startRectTileY = -1;
				}
			}
		}

		// Create traversable top ladders 

		for (int y = platforms.getHeight() - 2, tileY = 1; y >= 1; y--, tileY++) {
			int firstColIndex = 1;
			int lastColIndex = platforms.getWidth() - 1;
			for (int tileX = firstColIndex; tileX < lastColIndex; tileX++) {

				Object platformCollision = null;
				if (platforms.getCell(tileX, tileY) != null) {
					platformCollision = platforms.getCell(tileX, tileY).getTile().getProperties().get("col");
				}
				Object ladderPresent = null;
				if (ladders.getCell(tileX, tileY) != null) {
					ladderPresent = ladders.getCell(tileX, tileY).getTile().getProperties().get("ladder");
				}

				if ("1".equals(platformCollision) && "1".equals(ladderPresent)) {
					createTraversableEdge(tileX, tileY, tileX + 1, tileY);
				}
			}
		}

		// Ground
		// createRect(0, 0, platforms.getWidth()-1, 0);
		// Ceiling
		// createRect(0, platforms.getHeight()-1, platforms.getWidth()-1, platforms.getHeight()-1);
		// Left wall
		createRect(0, 0, 0, platforms.getHeight() - 1);
		// Right wall
		createRect(platforms.getWidth() - 1, 0, platforms.getWidth() - 1, platforms.getHeight() - 1);

		System.out.println("Rectangles created : " + rectNumber);

		CollisionManager levelContactManager = new CollisionManager(objectManager);
		physicalWorld.setContactListener(levelContactManager);
		physicalWorld.setContactFilter(levelContactManager);
	}
	
	private int rectNumber = 0;
	public int viewPortWidthInMeters;
	public int viewPortHeightInMeters;

	public HUD hud = new HUD();	
	
	private void createRect(int startTileX, int startTileY, int endTileX, int endTileY) {
		rectNumber++;

		int rectWidthInTiles = (endTileX - startTileX) + 1;
		int rectHeightInTiles = (endTileY - startTileY) + 1;

		PolygonShape groundPoly = new PolygonShape();
		float hx = rectWidthInTiles;
		float hy = rectHeightInTiles;
		Vector2 center = new Vector2(startTileX * METERS_PER_TILE + hx, startTileY * METERS_PER_TILE + hy + 0.2f);
		groundPoly.setAsBox(hx, hy - 0.2f, center, 0f);	

		BodyDef groundBodyDef = new BodyDef();
		groundBodyDef.type = BodyType.StaticBody;
		Body body = physicalWorld.createBody(groundBodyDef);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = groundPoly;		
		fixtureDef.density = 1;
		body.createFixture(fixtureDef);

		body.setUserData(new GameObjectData(-1, CollisionCategory.SOLID_PLATFORM));
		groundPoly.dispose();
	}
	
	private void createTraversableEdge(int startTileX, int startTileY, int endTileX, int endTileY) {
		EdgeShape edge = new EdgeShape();
		;
		edge.set(startTileX * METERS_PER_TILE, (startTileY + 1) * METERS_PER_TILE, endTileX * METERS_PER_TILE, (endTileY + 1) * METERS_PER_TILE);

		BodyDef groundBodyDef = new BodyDef();
		groundBodyDef.type = BodyType.StaticBody;
		Body body = physicalWorld.createBody(groundBodyDef);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = edge;
		fixtureDef.density = 1;
		body.createFixture(fixtureDef);

		body.setUserData(new GameObjectData(-1, CollisionCategory.TRAVERSABLE_PLATFORM));
		edge.dispose();
	}
	
	public void step(float deltaTime, int velocityIterations, int positionIterations) {
		physicalWorld.step(deltaTime, velocityIterations, positionIterations);
	}

	public void onCompletion() {
		Art.playMusic.stop();
		int nextWorld = worldId+1;
		if(nextWorld == 2){
			pref.putInteger("level1", 1);
			pref.flush();
		}
		else if(nextWorld == 3){
			pref.putInteger("level2", 1);
			pref.flush();
		}
		if(nextWorld == 4){
			pref.putInteger("level3", 1);
			pref.flush();
		}
		if(nextWorld == 5){
			pref.putInteger("level4", 1);
			pref.flush();
		}
		if(nextWorld == 6){
			pref.putInteger("level5", 1);
			pref.flush();
		}
		if(nextWorld == 7){
			pref.putInteger("level6", 1);
			pref.flush();
		}
		if(nextWorld == 8){
			pref.putInteger("level7", 1);
			pref.flush();
		}
		if(nextWorld == 9){
			pref.putInteger("level8", 1);
			pref.flush();
		}
		if(nextWorld == 10){
			pref.putInteger("level9", 1);
			pref.flush();
		}
		if(nextWorld == 11){
			pref.putInteger("level10", 1);
			pref.flush();
		}
		if(nextWorld == 12){
			pref.putInteger("level11", 1);
			pref.flush();
		}
		if(nextWorld == 13){
			pref.putInteger("level12", 1);
			pref.flush();
		}
		if(nextWorld == 14){
			pref.putInteger("level13", 1);
			pref.flush();
		}
		if(nextWorld == 15){
			pref.putInteger("level14", 1);
			pref.flush();
		}
		if(nextWorld != 16){
			screen.transitionTo(new CompletionScreen(worldId));
		}
	}

	public int getWorldId(){
		return worldId;
	}

	public float getPlatformDamping() {
		/*
		if (worldId == 2) {
			return 0.01f;
		}*/
		return 8f;
	}
	
	public float getAvatarFriction() {
		/*
		if (worldId == 2) {
			return 0.02f;
		}*/
		return 0f;
	}
	
}
