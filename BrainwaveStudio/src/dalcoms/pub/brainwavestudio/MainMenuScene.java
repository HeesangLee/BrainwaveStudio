package dalcoms.pub.brainwavestudio;

import java.util.Stack;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.util.modifier.ease.EaseBounceOut;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.net.Uri;
import dalcoms.pub.brainwavestudio.SceneManager.SceneType;

public class MainMenuScene extends BaseScene{
	
	ButtonSprite reviewButtonSprite;
	ButtonSprite shareButtonSprite;
	ButtonSprite moreButtonSprite;
	ButtonSprite alramButtonSprite;
	ButtonSprite blindButtonSprite;
	ButtonSprite volumeButtonSprite;
	
	private SoundButtonTiledSprite btn_sound_1;
	private SoundButtonTiledSprite btn_sound_2;
	private SoundButtonTiledSprite btn_sound_3;
	private SoundButtonTiledSprite btn_sound_4;
	private SoundButtonTiledSprite btn_sound_5;
	private SoundButtonTiledSprite btn_sound_6;
	private SoundButtonTiledSprite btn_sound_7;
	private SoundButtonTiledSprite btn_sound_8;
	private SoundButtonTiledSprite btn_sound_9;
	private SoundButtonTiledSprite btn_sound_10;
	
	public Sprite backgroundSprite;
	public TimerSprite timerSettingSprite;
	public TiledSprite hideButtonSprite;
	public TiledSprite TimerSetButtonSprite;
	public ButtonSprite timerLoopOnSprite ;
	public Sprite curtainSprite;
	
	Sprite spriteSetVoluemBg;
	Stack<SlideSetVerticalSprite> spriteVolSlides = new Stack<SlideSetVerticalSprite>();
	Stack<Sprite> spriteVolText = new Stack<Sprite>();
	public TiledSprite spriteHideVolumeSetBtn;
	
	private Sprite playRingSprite;
	
	private boolean[] flagPlayList = {false,false,false,false,false,false,false,false,false,false};	
	
	public final float btnHidePosX = (camera.getWidth()-resourcesManager.mTimerBtnHideTextureRegion.getWidth())/2;
	public final float btnHidePosY_sleep = -1.0f*resourcesManager.mTimerSettingBgRegion.getHeight();//camera.getHeight()+resourcesManager.mTimerSettingBgRegion.getHeight(); // 정확한 위치일 필요 없다.;
	public final float btnHidePosY_active = camera.getHeight()-resourcesManager.mTimerBtnHideTextureRegion.getHeight()+15.0f;
	public float btnTimerSetPosX=65.69f;
	public final float btnTimerSetPosY_sleep=-1.0f*resourcesManager.mTimerBtnSetRegion.getHeight();
	public final float btnTimerSetPosY_active = 202.5f;
	
	public final float btnTimerLoopPosX = 744.903f-resourcesManager.mTimerLoopOnBtnRegion.getWidth()/2;
	public final float btnTimerLoopPosY_sleep = -1.1f*resourcesManager.mTimerLoopOnBtnRegion.getHeight();
	public final float btnTimerLoopPosY_active = 268.240f-resourcesManager.mTimerLoopOnBtnRegion.getHeight()/2;
	
	public boolean flagTimerSetting = false;
	public boolean flagCurtainOn = false;
	public boolean flagVolumeSetting = false;
	
	//========================================================

	@Override
	public void createScene() {
		engine.runOnUpdateThread(new Runnable() {
			
			@Override
			public void run() {
				createBackground();
				createSoundBtns();
				createExtraButtons(); // review,more,share buttons.
				loadPlayImageSprites();
				createTimerSettingWindow();
				registerUpdateHandlerForTimer();
				setInitialAudioVolumn();
				createCurtainSprite();
				createVolumeSetSprites();
			}
		});
		
	}

	@Override
	public void onBackKeyPressed() {
		
		saveCurrentVolume();
		
		if (Math.random()<0.78){
			if(Math.random()<0.5){
				SceneManager.getInstance().popAdmobInterstitialAd(true);
			}
			popUpExtiMessageDlg();
		}else{
			if(Math.random()<0.25){
				SceneManager.getInstance().popAdmobInterstitialAd(true);
			}
			popUpAdMsgDlg();
		}
		
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_MENEU;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		
	}
	
	private void setInitialAudioVolumn(){
		AudioManager am = (AudioManager) resourcesManager.activity.getSystemService(Context.AUDIO_SERVICE);
		am.setStreamVolume(AudioManager.STREAM_MUSIC, 
				getSavedVolume(am), 
				AudioManager.FLAG_PLAY_SOUND);
	}
	
	private int getSavedVolume(AudioManager am){
		SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
		int currentVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);
		int maxVolume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		int savedVolume ;
		
		if(currentVolume<0.2*maxVolume){
			savedVolume = sharedPref.getInt(activity.getString(R.string.saved_volume), 2*maxVolume/10);
			if(savedVolume<2*maxVolume/10){
				savedVolume = 2*maxVolume/10;
			}
		}else{
			savedVolume = currentVolume;
		}
		return savedVolume;
	}
	
	private void saveCurrentVolume(){
		AudioManager am = (AudioManager) resourcesManager.activity.getSystemService(Context.AUDIO_SERVICE);
		
		SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putInt(activity.getString(R.string.saved_volume), 
				am.getStreamVolume(AudioManager.STREAM_MUSIC));
		editor.commit();
	}
	
	private void popUpExtiMessageDlg(){
		AlertDialog.Builder dlgBackPressed = new AlertDialog.Builder(activity);
		dlgBackPressed.setMessage(R.string.say_good_bye)
		.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				System.exit(0);
			}
		})
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		})
		.setTitle("Exit")
		.setIcon(R.drawable.ic_launcher)
		.show();
	}
	private void popUpAdMsgDlg(){
		AlertDialog.Builder dlgBackPressed = new AlertDialog.Builder(activity);
		dlgBackPressed.setMessage(R.string.advertize_myself)
		.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				try{
					if(Math.random()<0.6f){
						activity.startActivity(
								new Intent(Intent.ACTION_VIEW,
										Uri.parse("market://search/?q=pub:Dalcoms")));
					}else{
						activity.startActivity(
								new Intent(Intent.ACTION_VIEW,
										Uri.parse("market://details?id=dalcoms.pub.circlecolormatch")));
					}
					
				}catch(android.content.ActivityNotFoundException e){
					activity.startActivity(
							new Intent(Intent.ACTION_VIEW,
									Uri.parse("https://play.google.com/store/search?q=dalcoms")));
				}
			}
		})
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				popUpExtiMessageDlg();
			}
		})
		.setTitle("Free app")
		.setIcon(R.drawable.ic_launcher)
		.show();
	}
	private void offSoundsAll(){
		final int numOfSound = 10;
		btn_sound_1.offSound();
		btn_sound_2.offSound();
		btn_sound_3.offSound();
		btn_sound_4.offSound();
		btn_sound_5.offSound();
		btn_sound_6.offSound();
		btn_sound_7.offSound();
		btn_sound_8.offSound();
		btn_sound_9.offSound();
		btn_sound_10.offSound();
		
		for (int i=0;i<numOfSound;i++){
			disablePlayImageVisible(i);
		}
	}
	private void registerUpdateHandlerForTimer(){
		final float timerSecond = 1f;
		this.registerUpdateHandler(new TimerHandler(timerSecond, true, new ITimerCallback() {
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				if(flagTimerSetting==false&&!timerSettingSprite.getTimerLoopOn()){//gogo
					timerSettingSprite.moveIndicator(timerSettingSprite.getPosition()
							-timerSettingSprite.getDiffPositonByMinute(timerSecond/60f));
//					Log.v("timer","go"+timerSettingSprite.getDiffPositonByMinute(timerSecond/60f));
					if(timerSettingSprite.checkTimerZeroOn()){
						//TODO : 음원재생 끝내고 timer를 loop on 으로
						// offSoundsAll>>>
						offSoundsAll();
						timerSettingSprite.clearTimerZeroOn();
						timerSettingSprite.setTimerLoopOn(true);
						if(Math.random()<0.85){
							SceneManager.getInstance().popAdmobInterstitialAd(false);
						}
					}
				}
			}
		}));
	}
	
	private void createTimerSettingWindow(){
		timerSettingSprite = new TimerSprite(0,443.26f,resourcesManager.mTimerSettingBgRegion,vbom){
		};
		attachChild(timerSettingSprite);
		
		createTimerButtonHide();
		createTimerButtonSet();
		createTimerLoopOn();
	}
	
	private void createTimerLoopOn(){
		final float posX = 744.903f-resourcesManager.mTimerLoopOnBtnRegion.getWidth()/2;
		final float posY = -1.1f*resourcesManager.mTimerLoopOnBtnRegion.getHeight();
		
		timerLoopOnSprite = new ButtonSprite(
				posX,
				posY,
				ResourcesManager.getInstance().mTimerLoopOnBtnRegion,
				engine.getVertexBufferObjectManager()){
			@Override
			protected void preDraw(final GLState pGLState, final Camera pCamera){
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY){
				
				if(pSceneTouchEvent.isActionDown()){
					this.setScale(1.5f);
					if(timerSettingSprite.getTimerLoopOn()==true){
						timerSettingSprite.setTimerLoopOn(false);
					}else{
						timerSettingSprite.setTimerLoopOn(true);
					}
					resourcesManager.mSoundEffect_btnClick.play();
				}else{
					this.setScale(1f);
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}
		};
		
		registerTouchArea(timerLoopOnSprite);
		attachChild(timerLoopOnSprite);
	}
	
	private void createTimerButtonSet(){
		final float PosX = timerSettingSprite.getPosition()-resourcesManager.mTimerBtnSetRegion.getWidth()/2;
		final float PosY = -1.0f*resourcesManager.mTimerBtnSetRegion.getHeight();
		TimerSetButtonSprite = new TiledSprite(PosX, PosY, 
				resourcesManager.mTimerBtnSetRegion, vbom){
			@Override
			protected void preDraw(final GLState pGLState, final Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY){
				this.setPosition(timerSettingSprite.moveIndicator(pSceneTouchEvent.getX())-this.getWidth()/2, 
						btnTimerSetPosY_active);
				if(pSceneTouchEvent.isActionUp()){
					this.setCurrentTileIndex(0);
					
				}else{
					if(pSceneTouchEvent.isActionDown()){
						this.setCurrentTileIndex(1);
					}else{
						this.setCurrentTileIndex(0);
					}
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}
		};
		TimerSetButtonSprite.setCurrentTileIndex(0);
		registerTouchArea(TimerSetButtonSprite);
	
		attachChild(TimerSetButtonSprite);
	}
	private void createTimerButtonHide(){
		final float PosX = (camera.getWidth()-resourcesManager.mTimerBtnHideTextureRegion.getWidth())/2;
		final float PosY = -1.0f*resourcesManager.mTimerSettingBgRegion.getHeight();//camera.getHeight()+resourcesManager.mTimerSettingBgRegion.getHeight(); // 정확한 위치일 필요 없다.;
		hideButtonSprite = new TiledSprite(PosX, PosY, resourcesManager.mTimerBtnHideTextureRegion, vbom){
			@Override
			protected void preDraw(final GLState pGLState, final Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown()){//hide
					this.setCurrentTileIndex(1);
					resourcesManager.mSoundEffect_btnClick.play();
				}else{
					if(pSceneTouchEvent.isActionUp()){
						timerSeetingHide();
						this.setCurrentTileIndex(0);
						resourcesManager.mSoundEffect_settingHide.play();
					}
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}
		};
		hideButtonSprite.setCurrentTileIndex(0);
		registerTouchArea(hideButtonSprite);
	
		attachChild(hideButtonSprite);
	}
	
	private void createVolumeSetSprites(){
		createVolumeSetBg();
		createVoumeSlides();
		createVolmeText();
		createHideButton();
	}

	private void createHideButton() {
		final float PosX = (camera.getWidth()-resourcesManager.mTimerBtnHideTextureRegion.getWidth())/2;
		final float PosY = -1.0f*resourcesManager.mTimerSettingBgRegion.getHeight();

		spriteHideVolumeSetBtn = new TiledSprite(PosX, PosY,
				resourcesManager.mTimerBtnHideTextureRegion, vbom){
			@Override
			protected void preDraw(final GLState pGLState, final Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown()){//hide
					this.setCurrentTileIndex(1);
					resourcesManager.mSoundEffect_btnClick.play();
				}else{
					if(pSceneTouchEvent.isActionUp()){
						hideVolumeSetting();
						this.setCurrentTileIndex(0);
						resourcesManager.mSoundEffect_settingHide.play();
					}
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}
		};
		spriteHideVolumeSetBtn.setCurrentTileIndex(0);
		registerTouchArea(spriteHideVolumeSetBtn);
	
		attachChild(spriteHideVolumeSetBtn);
		
	}

	private void createVolmeText() {
		final float cameraHeight = camera.getHeight();
		final int slideNo = 10;
		final float xSpace = 8;
		
		ITextureRegion[] tempRegion = {
				resourcesManager.mVolDeltaRegion,
				resourcesManager.mVolThetaRegion,
				resourcesManager.mVolAlphaRegion,
				resourcesManager.mVolBrownRegion,
				resourcesManager.mVolPinkRegion,
				resourcesManager.mVolBetaSmrRegion,
				resourcesManager.mVolBetaMRegion,
				resourcesManager.mVolBetaHRegion,
				resourcesManager.mVolGammaRegion,
				resourcesManager.mVolWhiteRegion
		};
		
		for(int i=0;i<slideNo;i++){
			SlideSetVerticalSprite curSlide = spriteVolSlides.get(i);
			Sprite tempText = 
					new Sprite((curSlide.getX()+curSlide.getWidth()/2f)-tempRegion[i].getWidth()/2f, 
							cameraHeight, tempRegion[i],vbom);

			spriteVolText.push(tempText);
			attachChild(tempText);
		}
	}

	private void createVoumeSlides() {
		final float cameraHeight = camera.getHeight();
		final int slideNo = 10;
		final float xSpace = 20f;
		final float arrayLength = camera.getWidth()-2*xSpace-resourcesManager.mSlideKnobRegion.getWidth();
		
		SoundButtonTiledSprite[] sndBtns={
				btn_sound_1,
				btn_sound_2,
				btn_sound_3,
				btn_sound_4,
				btn_sound_5,
				btn_sound_6,
				btn_sound_7,
				btn_sound_8,
				btn_sound_9,
				btn_sound_10
		};
		
		for(int i=0;i<slideNo;i++){
			SlideSetVerticalSprite tempSlide = 
					new SlideSetVerticalSprite(xSpace+i*(arrayLength/((float)slideNo-1f)), 
							cameraHeight, resourcesManager.mSlideBgRegion, 
			resourcesManager.mSlideKnobRegion, vbom);
			tempSlide.setSoundBtn(sndBtns[i]);
			spriteVolSlides.push(tempSlide);
			attachChild(tempSlide);
			registerTouchArea(tempSlide);
		}
	}
	

	private void createVolumeSetBg() {
		final float cameraHeight = camera.getHeight();
		
		//Attach Bg.
		spriteSetVoluemBg = new Sprite(0f,cameraHeight,resourcesManager.mVolumeSetBgRegion,vbom);
		attachChild(spriteSetVoluemBg);
	}
	

	private void createBackground(){
		backgroundSprite = new Sprite(0,0,resourcesManager.mMainBackgroundRegion,vbom){
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera){
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		backgroundSprite.setPosition(0, 0);
		attachChild(backgroundSprite);
	}
	
	private void createCurtainSprite(){
		curtainSprite = new Sprite(0,0,resourcesManager.mCurtainRegion,vbom){
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera){
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
			
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown()){
					this.registerEntityModifier(new MoveModifier(0.35f, 0, camera.getWidth(), 0, 0){
						@Override
						protected void onModifierFinished(IEntity pItem){
							super.onModifierFinished(pItem);
							flagCurtainOn = false;
							setSoundBtnTouch();
						}
					});
					resourcesManager.mSoundEffect_settingHide.play();
				}

				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}
		};
		curtainSprite.setPosition(camera.getWidth(), 0);
		attachChild(curtainSprite);
		registerTouchArea(curtainSprite);
	}
	
	
	private void enablePlayImageVisible(int imageNum){
		if(playRingSprite.isVisible()==false){
			playRingSprite.setVisible(true);
		}

		flagPlayList[imageNum] = true;
	}
	private void disablePlayImageVisible(int imageNum){
		boolean ringVisible = false;

		flagPlayList[imageNum] = false;
		for(int i = 0;i<flagPlayList.length;i++){
			if(flagPlayList[i]==true){
				ringVisible = true;
				break;
			}
		}
		playRingSprite.setVisible(ringVisible);
	}

	private void createSoundBtns(){
		final float[] pxs={108,226,344,462,580,
						   108,226,344,462,580};
		final float[] pys={139,139,139,139,139,
						   289,289,289,289,289};
		int curIndex = 0;
		
		btn_sound_1 = new SoundButtonTiledSprite(pxs[0], pys[0], 
				resourcesManager.mSoundBtnTextureRegion, 
				engine.getVertexBufferObjectManager(),
				resourcesManager.mSoundArray.get(0),
				0){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY){
				if((flagTimerSetting==true)||(flagCurtainOn==true)||(flagVolumeSetting==true)){
					return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
							pTouchAreaLocalY);
				}
				if(pSceneTouchEvent.isActionUp()){
					if (flagButtonStatus%2 == 0){//pause to play
						enablePlayImageVisible(0);
					}else{//play to pause
						disablePlayImageVisible(0);
					}
//					this.setCurrentTileIndex(flagButtonStatus);
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}
		};
		btn_sound_2 = new SoundButtonTiledSprite(pxs[1], pys[1], 
				resourcesManager.mSoundBtnTextureRegion, 
				engine.getVertexBufferObjectManager(),
				resourcesManager.mSoundArray.get(1),
				1){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY){
				if((flagTimerSetting==true)||(flagCurtainOn==true)||(flagVolumeSetting==true)){
					return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
							pTouchAreaLocalY);
				}
				if(pSceneTouchEvent.isActionUp()){
					if (flagButtonStatus%2 == 0){//pause to play
						enablePlayImageVisible(1);
					}else{//play to pause
						disablePlayImageVisible(1);
					}
					this.setCurrentTileIndex(flagButtonStatus);
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}
		};
		btn_sound_3 = new SoundButtonTiledSprite(pxs[2], pys[2], 
				resourcesManager.mSoundBtnTextureRegion, 
				engine.getVertexBufferObjectManager(),
				resourcesManager.mSoundArray.get(2),
				2){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY){
				if((flagTimerSetting==true)||(flagCurtainOn==true)||(flagVolumeSetting==true)){
					return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
							pTouchAreaLocalY);
				}
				if(pSceneTouchEvent.isActionUp()){
					if (flagButtonStatus%2 == 0){//pause to play
						enablePlayImageVisible(2);
					}else{//play to pause
						disablePlayImageVisible(2);
					}
					this.setCurrentTileIndex(flagButtonStatus);
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}
		};
		btn_sound_4 = new SoundButtonTiledSprite(pxs[3], pys[3], 
				resourcesManager.mSoundBtnTextureRegion, 
				engine.getVertexBufferObjectManager(),
				resourcesManager.mSoundArray.get(3),
				3){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY){
				if((flagTimerSetting==true)||(flagCurtainOn==true)||(flagVolumeSetting==true)){
					return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
							pTouchAreaLocalY);
				}
				if(pSceneTouchEvent.isActionUp()){
					if (flagButtonStatus%2 == 0){//pause to play
						enablePlayImageVisible(3);
					}else{//play to pause
						disablePlayImageVisible(3);
					}
					this.setCurrentTileIndex(flagButtonStatus);
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}
		};
		btn_sound_5 = new SoundButtonTiledSprite(pxs[4], pys[4], 
				resourcesManager.mSoundBtnTextureRegion, 
				engine.getVertexBufferObjectManager(),
				resourcesManager.mSoundArray.get(4),
				4){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY){
				if((flagTimerSetting==true)||(flagCurtainOn==true)||(flagVolumeSetting==true)){
					return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
							pTouchAreaLocalY);
				}
				if(pSceneTouchEvent.isActionUp()){
					if (flagButtonStatus%2 == 0){//pause to play
						enablePlayImageVisible(4);
					}else{//play to pause
						disablePlayImageVisible(4);
					}
					this.setCurrentTileIndex(flagButtonStatus);
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}
		};
		btn_sound_6 = new SoundButtonTiledSprite(pxs[5], pys[5], 
				resourcesManager.mSoundBtnTextureRegion, 
				engine.getVertexBufferObjectManager(),
				resourcesManager.mSoundArray.get(5),
				5){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY){
				if((flagTimerSetting==true)||(flagCurtainOn==true)||(flagVolumeSetting==true)){
					return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
							pTouchAreaLocalY);
				}
				if(pSceneTouchEvent.isActionUp()){
					if (flagButtonStatus%2 == 0){//pause to play
						enablePlayImageVisible(5);
					}else{//play to pause
						disablePlayImageVisible(5);
					}
					this.setCurrentTileIndex(flagButtonStatus);
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}
		};

		curIndex=6;
		btn_sound_7 = new SoundButtonTiledSprite(pxs[curIndex], pys[curIndex], 
				resourcesManager.mSoundBtnTextureRegion, 
				engine.getVertexBufferObjectManager(),
				resourcesManager.mSoundArray.get(curIndex),
				curIndex){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY){
				if((flagTimerSetting==true)||(flagCurtainOn==true)||(flagVolumeSetting==true)){
					return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
							pTouchAreaLocalY);
				}
				if(pSceneTouchEvent.isActionUp()){
					if (flagButtonStatus%2 == 0){//pause to play
						enablePlayImageVisible(6);
					}else{//play to pause
						disablePlayImageVisible(6);
					}
					this.setCurrentTileIndex(flagButtonStatus);
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}
		};
		curIndex=7;
		btn_sound_8 = new SoundButtonTiledSprite(pxs[curIndex], pys[curIndex], 
				resourcesManager.mSoundBtnTextureRegion, 
				engine.getVertexBufferObjectManager(),
				resourcesManager.mSoundArray.get(curIndex),
				curIndex){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY){
				if((flagTimerSetting==true)||(flagCurtainOn==true)||(flagVolumeSetting==true)){
					return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
							pTouchAreaLocalY);
				}
				if(pSceneTouchEvent.isActionUp()){
					if (flagButtonStatus%2 == 0){//pause to play
						enablePlayImageVisible(7);
					}else{//play to pause
						disablePlayImageVisible(7);
					}
					this.setCurrentTileIndex(flagButtonStatus);
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}
		};
		curIndex=8;
		btn_sound_9 = new SoundButtonTiledSprite(pxs[curIndex], pys[curIndex], 
				resourcesManager.mSoundBtnTextureRegion, 
				engine.getVertexBufferObjectManager(),
				resourcesManager.mSoundArray.get(curIndex),
				curIndex){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY){
				if((flagTimerSetting==true)||(flagCurtainOn==true)||(flagVolumeSetting==true)){
					return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
							pTouchAreaLocalY);
				}
				if(pSceneTouchEvent.isActionUp()){
					if (flagButtonStatus%2 == 0){//pause to play
						enablePlayImageVisible(8);
					}else{//play to pause
						disablePlayImageVisible(8);
					}
					this.setCurrentTileIndex(flagButtonStatus);
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}
		};
		curIndex=9;
		btn_sound_10 = new SoundButtonTiledSprite(pxs[curIndex], pys[curIndex], 
				resourcesManager.mSoundBtnTextureRegion, 
				engine.getVertexBufferObjectManager(),
				resourcesManager.mSoundArray.get(curIndex),
				curIndex){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY){
				if((flagTimerSetting==true)||(flagCurtainOn==true)||(flagVolumeSetting==true)){
					return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
							pTouchAreaLocalY);
				}
				if(pSceneTouchEvent.isActionUp()){
					if (flagButtonStatus%2 == 0){//pause to play
						enablePlayImageVisible(9);
					}else{//play to pause
						disablePlayImageVisible(9);
					}
					this.setCurrentTileIndex(flagButtonStatus);
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}
		};

		registerTouchArea(btn_sound_1);
		attachChild(btn_sound_1);
		registerTouchArea(btn_sound_2);
		attachChild(btn_sound_2);
		registerTouchArea(btn_sound_3);
		attachChild(btn_sound_3);
		registerTouchArea(btn_sound_4);
		attachChild(btn_sound_4);
		registerTouchArea(btn_sound_5);
		attachChild(btn_sound_5);
		registerTouchArea(btn_sound_6);
		attachChild(btn_sound_6);
		registerTouchArea(btn_sound_7);
		attachChild(btn_sound_7);
		registerTouchArea(btn_sound_8);
		attachChild(btn_sound_8);
		registerTouchArea(btn_sound_9);
		attachChild(btn_sound_9);
		registerTouchArea(btn_sound_10);
		attachChild(btn_sound_10);
	}
	
	private void createExtraButtons(){
		final float minusYOffset=6.765f;
		final float myButton_width = resourcesManager.mMoreBtnRegion.getWidth();
		final float myButton_height = resourcesManager.mMoreBtnRegion.getWidth()-0.2f;
		final float refYBottom = btn_sound_10.getY()+btn_sound_10.getHeight();
		final float xPos = camera.getWidth()-myButton_width;
		final float yPos1st = refYBottom-myButton_height-minusYOffset;
		
		createShareButton(xPos,yPos1st-myButton_height);
		createReviewButton(xPos,yPos1st);
		createMoreButton(xPos,yPos1st-2*myButton_height);
		createAlarmButton(xPos,yPos1st-3*myButton_height);
		createVolumeSetButton(xPos, yPos1st-4*myButton_height);
		createBlindButton(xPos, yPos1st-5*myButton_height);
	}
	
	
	private void showVolumeSetting(){
		final float movDuration = 0.45f;
		final float cameraHeight = camera.getHeight();
		flagVolumeSetting = true;
		
		spriteSetVoluemBg.registerEntityModifier(new MoveModifier(movDuration, 0, 0, 
				cameraHeight, cameraHeight-resourcesManager.mVolumeSetBgRegion.getHeight()));
		spriteHideVolumeSetBtn.registerEntityModifier(new MoveModifier(movDuration*4, btnHidePosX, btnHidePosX, 
				btnHidePosY_sleep, btnHidePosY_active, EaseBounceOut.getInstance()));
		for(SlideSetVerticalSprite pSlide:spriteVolSlides){
			pSlide.registerEntityModifier(new MoveYModifier(movDuration, cameraHeight, 218f));
		}
		for(Sprite pSprite:spriteVolText){
			pSprite.registerEntityModifier(new MoveYModifier(movDuration, cameraHeight, 155f));
		}
		
		reviewButtonSprite.setPosition(camera.getWidth(), reviewButtonSprite.getY());
		shareButtonSprite.setPosition(camera.getWidth(), shareButtonSprite.getY());
		moreButtonSprite.setPosition(camera.getWidth(), moreButtonSprite.getY());
		alramButtonSprite.setPosition(camera.getWidth(), alramButtonSprite.getY());
		blindButtonSprite.setPosition(camera.getWidth(), blindButtonSprite.getY());
		volumeButtonSprite.setPosition(camera.getWidth(), volumeButtonSprite.getY());
		
		setSoundBtnTouch();
	}
	
	private void hideVolumeSetting(){
		flagVolumeSetting = false;
		final float xPos = camera.getWidth()-resourcesManager.mMoreBtnRegion.getWidth();
		
		final float movDuration = 0.45f;
		final float cameraHeight = camera.getHeight();
		
		spriteSetVoluemBg.registerEntityModifier(new MoveYModifier(movDuration, cameraHeight-resourcesManager.mVolumeSetBgRegion.getHeight(), cameraHeight));
		
		spriteHideVolumeSetBtn.registerEntityModifier(new MoveModifier(movDuration/2, btnHidePosX, btnHidePosX, 
				btnHidePosY_active, btnHidePosY_sleep));
		
		for(SlideSetVerticalSprite pSlide:spriteVolSlides){
			pSlide.registerEntityModifier(new MoveYModifier(movDuration, 218f, cameraHeight));
		}
		for(Sprite pSprite:spriteVolText){
			pSprite.registerEntityModifier(new MoveYModifier(movDuration, 155f, cameraHeight));
		}
		
		reviewButtonSprite.setPosition(xPos, reviewButtonSprite.getY());
		shareButtonSprite.setPosition(xPos, shareButtonSprite.getY());
		moreButtonSprite.setPosition(xPos, moreButtonSprite.getY());
		alramButtonSprite.setPosition(xPos, alramButtonSprite.getY());
		blindButtonSprite.setPosition(xPos, blindButtonSprite.getY());
		volumeButtonSprite.setPosition(xPos, volumeButtonSprite.getY());
		
		setSoundBtnTouch();
	}
	
	private void createVolumeSetButton(final float pX, final float pY){
		final float positionX = pX;
		final float positionY = pY;
		
		volumeButtonSprite = new ButtonSprite(
				positionX,
				positionY,
				ResourcesManager.getInstance().mVolumeSetRegion,
				engine.getVertexBufferObjectManager()){
			@Override
			protected void preDraw(final GLState pGLState, final Camera pCamera){
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY){
				if((flagTimerSetting==true)||(flagCurtainOn==true)||(flagVolumeSetting==true)){
					return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
							pTouchAreaLocalY);
				}
				if(pSceneTouchEvent.isActionDown()){
					this.setScale(1.5f);
					resourcesManager.mSoundEffect_btnClick.play();
				}else{
					this.setScale(1f);
					if(pSceneTouchEvent.isActionUp()){
						showVolumeSetting();
//						setSoundBtnTouch();
					}
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}
		};
		
		registerTouchArea(volumeButtonSprite);
		attachChild(volumeButtonSprite);
	}
	
	private void createBlindButton(final float pX, final float pY){
		final float positionX = pX;
		final float positionY = pY;
		
		blindButtonSprite = new ButtonSprite(
				positionX,
				positionY,
				ResourcesManager.getInstance().mBlindBtnRegion,
				engine.getVertexBufferObjectManager()){
			@Override
			protected void preDraw(final GLState pGLState, final Camera pCamera){
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY){
				if((flagTimerSetting==true)||(flagCurtainOn==true)||(flagVolumeSetting==true)){
					return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
							pTouchAreaLocalY);
				}
				
				if(pSceneTouchEvent.isActionDown()){
					this.setScale(1.5f);
					resourcesManager.mSoundEffect_btnClick.play();
				}else{
					this.setScale(1f);
					if(pSceneTouchEvent.isActionUp()){
						curtainShow();
					}
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}
		};
		
		registerTouchArea(blindButtonSprite);
		attachChild(blindButtonSprite);
	}
	
	private void curtainShow(){
		flagCurtainOn = true;
		curtainSprite.registerEntityModifier(new MoveModifier(0.45f, camera.getWidth(), 0, 0, 0));
		setSoundBtnTouch();
	}
	
	private void createAlarmButton(final float pX, final float pY){
		final float positionX = pX;
		final float positionY = pY;
		
		alramButtonSprite = new ButtonSprite(
				positionX,
				positionY,
				ResourcesManager.getInstance().mAlramBtnRegion,
				engine.getVertexBufferObjectManager()){
			@Override
			protected void preDraw(final GLState pGLState, final Camera pCamera){
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY){
				if((flagTimerSetting==true)||(flagCurtainOn==true)||(flagVolumeSetting==true)){
					return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
							pTouchAreaLocalY);
				}
				if(pSceneTouchEvent.isActionDown()){
					this.setScale(1.5f);
					resourcesManager.mSoundEffect_btnClick.play();
				}else{
					this.setScale(1f);
					if(pSceneTouchEvent.isActionUp()){
						//TODO here
						timerSeetingShow();
					}
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}
		};
		
		registerTouchArea(alramButtonSprite);
		attachChild(alramButtonSprite);
	}
	private void setSoundBtnTouch(){
		btn_sound_1.setTouchDisable(flagTimerSetting|flagCurtainOn|flagVolumeSetting);
		btn_sound_2.setTouchDisable(flagTimerSetting|flagCurtainOn|flagVolumeSetting);
		btn_sound_3.setTouchDisable(flagTimerSetting|flagCurtainOn|flagVolumeSetting);
		btn_sound_4.setTouchDisable(flagTimerSetting|flagCurtainOn|flagVolumeSetting);
		btn_sound_5.setTouchDisable(flagTimerSetting|flagCurtainOn|flagVolumeSetting);
		btn_sound_6.setTouchDisable(flagTimerSetting|flagCurtainOn|flagVolumeSetting);
		btn_sound_7.setTouchDisable(flagTimerSetting|flagCurtainOn|flagVolumeSetting);
		btn_sound_8.setTouchDisable(flagTimerSetting|flagCurtainOn|flagVolumeSetting);
		btn_sound_9.setTouchDisable(flagTimerSetting|flagCurtainOn|flagVolumeSetting);
		btn_sound_10.setTouchDisable(flagTimerSetting|flagCurtainOn|flagVolumeSetting);
		
		timerSettingSprite.setTimerSettingShowFlag(flagTimerSetting);
	}
	
	private void timerSeetingShow(){
		float duration = 0.45f;
		flagTimerSetting = true;
		
		timerSettingSprite.registerEntityModifier(new MoveModifier(duration, 0, 0, 
				443.26f, 133.02f));
		
		hideButtonSprite.registerEntityModifier(new MoveModifier(duration*4, btnHidePosX, btnHidePosX, 
				btnHidePosY_sleep, btnHidePosY_active, EaseBounceOut.getInstance()));
		
		TimerSetButtonSprite.registerEntityModifier(new MoveModifier(duration*2, 
				timerSettingSprite.getPosition()-resourcesManager.mTimerBtnSetRegion.getWidth()/2, 
				timerSettingSprite.getPosition()-resourcesManager.mTimerBtnSetRegion.getWidth()/2, 
				btnTimerSetPosY_sleep, btnTimerSetPosY_active, EaseBounceOut.getInstance()));
		
		timerLoopOnSprite.registerEntityModifier(new MoveModifier(duration*3, btnTimerLoopPosX, btnTimerLoopPosX, 
				btnTimerLoopPosY_sleep, btnTimerLoopPosY_active, EaseBounceOut.getInstance()));
		
		setSoundBtnTouch();
		
	}
	
	private void timerSeetingHide(){
		float duration = 0.65f;
		flagTimerSetting = false;

		timerSettingSprite.registerEntityModifier(new MoveModifier(duration, 0, 0, 
				133.02f,443.26f ));
		
		hideButtonSprite.registerEntityModifier(new MoveModifier(duration/2, btnHidePosX, btnHidePosX, 
				btnHidePosY_active, btnHidePosY_sleep));
		
		TimerSetButtonSprite.registerEntityModifier(new MoveModifier(duration/2, 
				timerSettingSprite.getPosition()-resourcesManager.mTimerBtnSetRegion.getWidth()/2, 
				timerSettingSprite.getPosition()-resourcesManager.mTimerBtnSetRegion.getWidth()/2,  
				btnTimerSetPosY_active, btnTimerSetPosY_sleep));
		
		timerLoopOnSprite.registerEntityModifier(new MoveModifier(duration/2, btnTimerLoopPosX, btnTimerLoopPosX, 
				btnTimerLoopPosY_active, btnTimerLoopPosY_sleep));
		setSoundBtnTouch();
	}
	
	private void createReviewButton(final float pX, final float pY){
		final float positionX = pX;
		final float positionY = pY;
		
		reviewButtonSprite = new ButtonSprite(
				positionX,
				positionY,
				ResourcesManager.getInstance().mReviewBtnRegion,
				engine.getVertexBufferObjectManager()){
			String appId = "dalcoms.pub.brainwavestudio";
			@Override
			protected void preDraw(final GLState pGLState, final Camera pCamera){
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY){
				if((flagTimerSetting==true)||(flagCurtainOn==true)||(flagVolumeSetting==true)){
					return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
							pTouchAreaLocalY);
				}
				if(pSceneTouchEvent.isActionDown()){
					this.setScale(1.5f);
					resourcesManager.mSoundEffect_btnClick.play();
				}else{
					this.setScale(1f);
					if(pSceneTouchEvent.isActionUp()){
						try{
							activity.startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id="+appId)));
							
						}catch(android.content.ActivityNotFoundException e){
							activity.startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://play.google.com/store/apps/details?id="+appId)));
						}
					}
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}
		};
		
		registerTouchArea(reviewButtonSprite);
		attachChild(reviewButtonSprite);
	}
	
	private void createShareButton(final float pX, final float pY){
		final float positionX = pX;
		final float positionY = pY;
		
		shareButtonSprite = new ButtonSprite(
				positionX,
				positionY,
				ResourcesManager.getInstance().mShareBtnRegion,
				engine.getVertexBufferObjectManager()){
			String appId = "dalcoms.pub.brainwavestudio";
			@Override
			protected void preDraw(final GLState pGLState, final Camera pCamera){
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY){
				if((flagTimerSetting==true)||(flagCurtainOn==true)||(flagVolumeSetting==true)){
					return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
							pTouchAreaLocalY);
				}
				if(pSceneTouchEvent.isActionDown()){
					this.setScale(1.5f);
					resourcesManager.mSoundEffect_btnClick.play();
				}else{
					this.setScale(1f);
					if(pSceneTouchEvent.isActionUp()){
						try{
							Intent sendIntent = new Intent();
							sendIntent.setAction(Intent.ACTION_SEND);
							sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Free App.recommendation : Brainwave Studio");
							sendIntent.putExtra(Intent.EXTRA_TEXT, "Free Application recommendation : \n" +
									"https://play.google.com/store/apps/details?id=dalcoms.pub.brainwavestudio \n" +
									"Brainwave EGG generator");
							sendIntent.setType("text/plain");
							activity.startActivity(Intent.createChooser(sendIntent, "Sharing"));
						}catch(android.content.ActivityNotFoundException e){
							activity.startActivity(
									new Intent(Intent.ACTION_VIEW,Uri.parse("http://play.google.com/store/apps/details?id="+appId)));
						}
					}
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}
		};
		
		registerTouchArea(shareButtonSprite);
		attachChild(shareButtonSprite);
	}
	
	private void createMoreButton(final float pX, final float pY){
		final float positionX = pX;
		final float positionY = pY;
		
		moreButtonSprite = new ButtonSprite(
				positionX,
				positionY,
				ResourcesManager.getInstance().mMoreBtnRegion,
				engine.getVertexBufferObjectManager()){
//			String appId = "dalcoms.pub.mathkids";
			@Override
			protected void preDraw(final GLState pGLState, final Camera pCamera){
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY){
				if((flagTimerSetting==true)||(flagCurtainOn==true)||(flagVolumeSetting==true)){
					return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
							pTouchAreaLocalY);
				}
				if(pSceneTouchEvent.isActionDown()){
					this.setScale(1.5f);
					resourcesManager.mSoundEffect_btnClick.play();
				}else{
					this.setScale(1f);
					if(pSceneTouchEvent.isActionUp()){
						try{
							activity.startActivity(
									new Intent(Intent.ACTION_VIEW,
											Uri.parse("market://search/?q=pub:Dalcoms")));
						}catch(android.content.ActivityNotFoundException e){
							activity.startActivity(
									new Intent(Intent.ACTION_VIEW,
											Uri.parse("https://play.google.com/store/search?q=dalcoms")));
						}
					}
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}
		};
		
		registerTouchArea(moreButtonSprite);
		attachChild(moreButtonSprite);
	}
	
	private void loadPlayImageSprites(){
		final float xPos = camera.getWidth()-resourcesManager.mPlayRingRegion.getWidth()/2;
		final float yPos = -1*resourcesManager.mPlayRingRegion.getHeight()/2;
		
		playRingSprite = new Sprite(0,0,resourcesManager.mPlayRingRegion,vbom){
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera){
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		
		playRingSprite.setPosition(xPos, yPos);
		
		attachChild(playRingSprite);
		
		playRingSprite.registerEntityModifier(new LoopEntityModifier(new RotationModifier(6f, 360, 0)));
		playRingSprite.setVisible(false);
	}
	
}










