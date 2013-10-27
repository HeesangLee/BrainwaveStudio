package dalcoms.pub.brainwavestudio;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.view.RenderSurfaceView;
import org.andengine.ui.activity.LayoutGameActivity;

import android.graphics.PixelFormat;
//import android.util.Log;
import android.view.KeyEvent;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

import dalcoms.pub.brainwavestudio.R;

public class MainActivity extends LayoutGameActivity {
	private AdView adView;
	final boolean AD_ON = true;
	
	public final int CAMERA_WIDTH = 800;
	public final int CAMERA_HEIGHT = 480;
	
	public Scene mCurrentScene;
	public static MainActivity instance;
	
	public Font mFontDefault;
	public Camera mCamera;
	
	public final float TimeSplashToMenu = 2.5f;
	
//	private ResourcesManager resourcesManager;
	
	@Override
	protected void onSetContentView(){
		super.onSetContentView();
		mRenderSurfaceView = new RenderSurfaceView(this);
		mRenderSurfaceView.setEGLConfigChooser(8,8,8,8,24,0);
		mRenderSurfaceView.setRenderer(mEngine, this);
		mRenderSurfaceView.getHolder().setFormat(PixelFormat.RGBA_8888);
//		loadAdView(AD_ON);
	}
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		instance = this;
		mCamera = new Camera(0,0,CAMERA_WIDTH,CAMERA_HEIGHT);
		
		EngineOptions engineOptions = new EngineOptions(
				true,
				ScreenOrientation.LANDSCAPE_SENSOR, 
				new RatioResolutionPolicy(CAMERA_WIDTH,CAMERA_HEIGHT),
				mCamera);
		engineOptions.getRenderOptions().setDithering(true);
		engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
		engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_DIM);//해당 옵션은 본 어플에서는 필요하지 않을 수 있으므로 추후에 수정/삭제할 수 있음.
		
		return engineOptions;
	}
/**
 * LimitedFPSEngine() : Specify the amount of updates per seconds ->To work at similar speeds on various devices.
 */
	@Override
	public Engine onCreateEngine(EngineOptions pEngineOptions){
		
		return new LimitedFPSEngine(pEngineOptions, 60);
	}
	
	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws Exception {
		ResourcesManager.prepareManager(mEngine, this, mCamera, getVertexBufferObjectManager());
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {
		SceneManager.getInstance().createSplashScene(pOnCreateSceneCallback);
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		mEngine.registerUpdateHandler(new TimerHandler(TimeSplashToMenu,new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				mEngine.unregisterUpdateHandler(pTimerHandler);
				SceneManager.getInstance().createMenuScene();
			}
		}));
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

	@Override
	protected int getLayoutID() {
		return R.layout.activity_main;
	}

	@Override
	protected int getRenderSurfaceViewID() {
		return R.id.gameSurfaceView;
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		System.exit(0);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if(keyCode == KeyEvent.KEYCODE_BACK){
			SceneManager.getInstance().getCurrentScene().onBackKeyPressed();
		}
		return false;
	}
	protected void loadAdView(boolean onOff){
		//TODO : 프로그램 종료시에 ad 종료하도록 코드 추가 할 것.
		if(onOff == true){
			AdRequest adRequest = new AdRequest();
			adView = (AdView)findViewById(R.id.adView);
//			adView.setVisibility(AdView.VISIBLE);
			adView.loadAd(adRequest);
		}
	}
	
	public static MainActivity getSharedInstance(){
		return instance;
	}
	
	public void setCurrentScene(Scene scene){
		mCurrentScene = scene;
		getEngine().setScene(mCurrentScene);
	}
}














