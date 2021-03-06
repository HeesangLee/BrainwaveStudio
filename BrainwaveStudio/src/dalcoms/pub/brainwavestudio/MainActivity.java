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
import android.view.KeyEvent;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
//import android.util.Log;

public class MainActivity extends LayoutGameActivity {
	
	private AdView adMobAdView;
	
	final boolean AD_ON = true;
	
	public final int CAMERA_WIDTH = 800;
	public final int CAMERA_HEIGHT = 480;
	
	public Scene mCurrentScene;
	public static MainActivity instance;
	
	public Font mFontDefault;
	public Camera mCamera;
	
	public final float TimeSplashToMenu = 2.5f;
	
	private final int MYFPS = 60;
	
//	private ResourcesManager resourcesManager;
	
	@Override
	protected void onSetContentView(){
		super.onSetContentView();
		mRenderSurfaceView = new RenderSurfaceView(this);
		mRenderSurfaceView.setEGLConfigChooser(8,8,8,8,24,0);
		mRenderSurfaceView.setRenderer(mEngine, this);
		mRenderSurfaceView.getHolder().setFormat(PixelFormat.RGBA_8888);
		loadAdView(AD_ON);
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
		
		return new LimitedFPSEngine(pEngineOptions, MYFPS);
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
			public void onTimePassed(TimerHandler pTimerHandler) {//Splash scene 을 일정 시간 띄운 후 main menu scene을 실행.
				mEngine.unregisterUpdateHandler(pTimerHandler);
				SceneManager.getInstance().createMenuScene();//메인메뉴 실행.
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
		adMobAdView.destroy();
		super.onDestroy();
		System.exit(0);
	}
	
	@Override
	public void onPause(){
		adMobAdView.pause();
		super.onPause();
	}
	
	@Override
	public void onResume(){
		adMobAdView.resume();
		super.onResume();
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
			adMobAdView = (AdView) this.findViewById(R.id.adView);
			
			AdRequest request = new AdRequest.Builder().
					addTestDevice(AdRequest.DEVICE_ID_EMULATOR).
					setGender(AdRequest.GENDER_MALE).
					build();
			
			adMobAdView.loadAd(request);
			adMobAdView.setBackgroundColor(android.graphics.Color.TRANSPARENT);
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














