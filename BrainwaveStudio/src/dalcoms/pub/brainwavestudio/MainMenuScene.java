package dalcoms.pub.brainwavestudio;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.util.GLState;
import org.andengine.util.modifier.ease.EaseBounceInOut;
import org.andengine.util.modifier.ease.EaseBounceOut;
import org.andengine.util.modifier.ease.EaseElasticIn;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import dalcoms.pub.brainwavestudio.SceneManager.SceneType;
//import org.andengine.entity.modifier.AlphaModifier;

public class MainMenuScene extends BaseScene{
	
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
	
	private Sprite playRingSprite;
	
	private boolean[] flagPlayList = {false,false,false,false,false,false,false,false,false,false};	
	
	public float timerMinute=0;
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
	
	//========================================================

	@Override
	public void createScene() {
		createBackground();
		createSoundBtns();
		createExtraButtons(); // review,more,share buttons.
		loadPlayImageSprites();
		createTimerSettingWindow();
	}

	@Override
	public void onBackKeyPressed() {
		// TODO : Pop-up 다이얼로그를 띄움 -> 다른 무료 앱을 원하냐?(내 앱들...House ad)  아니면 프로그램 종료.
		// 우선은 프로그램 종료하도록 설정..
		System.exit(0);
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_MENEU;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		
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
					resourcesManager.mSoundEffect_btnClick.play();
				}else{
					this.setScale(1f);
					if(pSceneTouchEvent.isActionUp()){
						if(timerSettingSprite.getTimerLoopOn()==true){
							timerSettingSprite.setTimerLoopOn(false);
						}else{
							timerSettingSprite.setTimerLoopOn(true);
						}
					}
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}
		};
		
		registerTouchArea(timerLoopOnSprite);
		attachChild(timerLoopOnSprite);
	}
	
	private void createTimerButtonSet(){
		final float PosX = 0;
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
				this.setPosition(pSceneTouchEvent.getX()-this.getWidth()/2, btnTimerSetPosY_active);
				timerSettingSprite.moveIndicator(pSceneTouchEvent.getX());
//				Log.v("TouchEvent",""+pSceneTouchEvent.getAction());
				
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
				if(flagTimerSetting==true){
					return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
							pTouchAreaLocalY);
				}
				if(pSceneTouchEvent.isActionUp()){
					if (flagButtonStatus%2 == 0){//pause to play
						enablePlayImageVisible(0);
					}else{//play to pause
						disablePlayImageVisible(0);
					}
					this.setCurrentTileIndex(flagButtonStatus);
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
				if(flagTimerSetting==true){
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
				if(flagTimerSetting==true){
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
				if(flagTimerSetting==true){
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
				if(flagTimerSetting==true){
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
				if(flagTimerSetting==true){
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
				if(flagTimerSetting==true){
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
				if(flagTimerSetting==true){
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
				if(flagTimerSetting==true){
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
				if(flagTimerSetting==true){
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
	}
	
	private void createAlarmButton(final float pX, final float pY){
		final float positionX = pX;
		final float positionY = pY;
		
		ButtonSprite alramButtonSprite = new ButtonSprite(
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
				if(flagTimerSetting==true){
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
		btn_sound_1.setTouchDisable(flagTimerSetting);
		btn_sound_2.setTouchDisable(flagTimerSetting);
		btn_sound_3.setTouchDisable(flagTimerSetting);
		btn_sound_4.setTouchDisable(flagTimerSetting);
		btn_sound_5.setTouchDisable(flagTimerSetting);
		btn_sound_6.setTouchDisable(flagTimerSetting);
		btn_sound_7.setTouchDisable(flagTimerSetting);
		btn_sound_8.setTouchDisable(flagTimerSetting);
		btn_sound_9.setTouchDisable(flagTimerSetting);
		btn_sound_10.setTouchDisable(flagTimerSetting);
	}
	private void timerSeetingShow(){
		float duration = 0.45f;
		flagTimerSetting = true;
		
		timerSettingSprite.registerEntityModifier(new MoveModifier(duration, 0, 0, 
				443.26f, 133.02f));
		
		hideButtonSprite.registerEntityModifier(new MoveModifier(duration*4, btnHidePosX, btnHidePosX, 
				btnHidePosY_sleep, btnHidePosY_active, EaseBounceOut.getInstance()));
		
		TimerSetButtonSprite.registerEntityModifier(new MoveModifier(duration*2, btnTimerSetPosX, btnTimerSetPosX, 
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
		
		TimerSetButtonSprite.registerEntityModifier(new MoveModifier(duration/2, btnTimerSetPosX, btnTimerSetPosX, 
				btnTimerSetPosY_active, btnTimerSetPosY_sleep));
		
		timerLoopOnSprite.registerEntityModifier(new MoveModifier(duration/2, btnTimerLoopPosX, btnTimerLoopPosX, 
				btnTimerLoopPosY_active, btnTimerLoopPosY_sleep));
		setSoundBtnTouch();
	}
	
	private void createReviewButton(final float pX, final float pY){
		final float positionX = pX;
		final float positionY = pY;
		
		ButtonSprite reviewButtonSprite = new ButtonSprite(
				positionX,
				positionY,
				ResourcesManager.getInstance().mReviewBtnRegion,
				engine.getVertexBufferObjectManager()){
			String appId = "dalcoms.pub.naturesound";
			@Override
			protected void preDraw(final GLState pGLState, final Camera pCamera){
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY){
				if(flagTimerSetting==true){
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
		
		ButtonSprite shareButtonSprite = new ButtonSprite(
				positionX,
				positionY,
				ResourcesManager.getInstance().mShareBtnRegion,
				engine.getVertexBufferObjectManager()){
			String appId = "dalcoms.pub.naturesound";
			@Override
			protected void preDraw(final GLState pGLState, final Camera pCamera){
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY){
				if(flagTimerSetting==true){
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
							sendIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=dalcoms.pub.naturesound");
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
		
		ButtonSprite shareButtonSprite = new ButtonSprite(
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
				if(flagTimerSetting==true){
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
		
		registerTouchArea(shareButtonSprite);
		attachChild(shareButtonSprite);
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










