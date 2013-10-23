package dalcoms.pub.brainwavestudio;

import org.andengine.engine.Engine;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

/*
 * @author Heesang.Lee
 */
public class SceneManager {
	//==============================
	// Scenes
	//==============================
	private BaseScene splashScene;
	private BaseScene mainMenuScene;
	
	//==============================
	// Variables
	//==============================
	private static final SceneManager instance = new SceneManager();
	private SceneType currentSceneType = SceneType.SCENE_SPLASH;
	private BaseScene currentScene;
	private Engine engine = ResourcesManager.getInstance().engine;
	
	public enum SceneType{
		SCENE_SPLASH,
		SCENE_MENEU
	}
	//==============================
	public static SceneManager getInstance(){
		return instance;
	}
	
	public void setScene(BaseScene scene){
		engine.setScene(scene);
		currentScene = scene;
		currentSceneType = scene.getSceneType();
	}
	
	public void setScene(SceneType sceneType){
		switch(sceneType){
		case SCENE_SPLASH:
			setScene(splashScene);
			break;
		case SCENE_MENEU:
			setScene(mainMenuScene);
			break;
		}
	}
	
	public SceneType getCurrentSceneType(){
		return currentSceneType;
	}
	
	public BaseScene getCurrentScene(){
		return currentScene;
	}
	
	public void createMenuScene(){
		ResourcesManager.getInstance().loadMenuResources();
		mainMenuScene = new MainMenuScene();
		setScene(mainMenuScene);
		disposeSplashScene();
	}
	
	public void createSplashScene(OnCreateSceneCallback pOnCreateSceneCallback){
		ResourcesManager.getInstance().loadSplashScreen();
		splashScene = new SplashScene();
		currentScene = splashScene;
		pOnCreateSceneCallback.onCreateSceneFinished(splashScene);
	}
	
	private void disposeSplashScene(){
		ResourcesManager.getInstance().unloadSplashScreen();
		splashScene.disposeScene();
		splashScene = null;
	}
}
