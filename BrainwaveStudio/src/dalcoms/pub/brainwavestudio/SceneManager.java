package dalcoms.pub.brainwavestudio;

import org.andengine.engine.Engine;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

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
	
	private int interstitialAdOnNum = 0;
	private int interstitialAdOnMax = 3; // ÇØ´ç È½¼ö ÀÌ»óÀ¸·Î´Â ÆË¾÷±¤°í¸¦ ¶ç¿ìÁö ¾Ê´Â´Ù.
	private boolean forTStore = false;
	
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
	
	public void popAdmobInterstitialAd(boolean popExitCondition){
		if(interstitialAdOnNum++<interstitialAdOnMax){
			final InterstitialAd adMobInterstitialAd = new InterstitialAd(ResourcesManager.getInstance().activity);
			if(popExitCondition){
				adMobInterstitialAd.setAdUnitId("ca-app-pub-0894410772194388/1314292703");
			}else{
				adMobInterstitialAd.setAdUnitId("ca-app-pub-0894410772194388/8837559502");
			}
			
			final AdRequest adRequest = new AdRequest.Builder().build();
			ResourcesManager.getInstance().activity.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					adMobInterstitialAd.loadAd(adRequest);
				}
			});
			
			adMobInterstitialAd.setAdListener(new AdListener() {
				public void onAdLoaded(){
					displayAdmobInterstitialAd(adMobInterstitialAd);
				}
			});
		}
	}
	
	private void displayAdmobInterstitialAd(InterstitialAd pAd){
		if(pAd.isLoaded()){
			pAd.show();
		}
	}
	
	
	public boolean isForTStore(){
		return forTStore;
	}
	
	public int getInterstitialAdOnMax(){
		return interstitialAdOnMax;
	}
	
	public void setInterstitialAdOnMax(int pVal){
		this.interstitialAdOnMax = pVal;
	}
}









































