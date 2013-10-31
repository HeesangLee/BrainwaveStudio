package dalcoms.pub.brainwavestudio;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.util.GLState;

import android.content.Intent;
import android.net.Uri;
import dalcoms.pub.brainwavestudio.SceneManager.SceneType;
//import org.andengine.entity.modifier.AlphaModifier;

public class MainMenuScene extends BaseScene{
	
	private TiledSprite btn_sound_1;
	private TiledSprite btn_sound_2;
	private TiledSprite btn_sound_3;
	private TiledSprite btn_sound_4;
	private TiledSprite btn_sound_5;
	private TiledSprite btn_sound_6;
	private TiledSprite btn_sound_7;
	private TiledSprite btn_sound_8;
	private TiledSprite btn_sound_9;
	private TiledSprite btn_sound_10;
	
	public Sprite backgroundSprite;
	
	private Sprite playRingSprite;
	
	private boolean[] flagPlayList = {false,false,false,false,false,false,false,false,false,false};	
	
	//========================================================
	@Override
	public void createScene() {
		createBackground();
		createSoundBtns();
		createExtraButtons(); // review,more,share buttons.
		loadPlayImageSprites();
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
		// 위치는 Python + Inkscape으로 찾음. 수동입력.
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
		final float myButton_width = ResourcesManager.getInstance().mMoreBtnRegion.getWidth();
		final float myButton_height = ResourcesManager.getInstance().mMoreBtnRegion.getWidth();
		final float refYBottom = btn_sound_10.getY()+btn_sound_10.getHeight();
		final float xPos = camera.getWidth()-myButton_width;
		final float yPos1st = refYBottom-myButton_height-minusYOffset;
				
		createMoreButton(xPos,yPos1st-2*myButton_height);
		createShareButton(xPos,yPos1st-myButton_height);
		createReviewButton(xPos,yPos1st);
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
				if(pSceneTouchEvent.isActionDown()){
					this.setScale(1.5f);
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
				if(pSceneTouchEvent.isActionDown()){
					this.setScale(1.5f);
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
				if(pSceneTouchEvent.isActionDown()){
					this.setScale(1.5f);
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















