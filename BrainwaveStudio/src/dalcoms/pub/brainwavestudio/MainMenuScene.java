package dalcoms.pub.brainwavestudio;

import org.andengine.engine.camera.Camera;
//import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.util.GLState;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import dalcoms.pub.brainwavestudio.SceneManager.SceneType;

public class MainMenuScene extends BaseScene{
	
	private TiledSprite btn_sound_1;
	private TiledSprite btn_sound_2;
	private TiledSprite btn_sound_3;
	private TiledSprite btn_sound_4;
	private TiledSprite btn_sound_5;
	private TiledSprite btn_sound_6;
	
	public Sprite backgroundSprite;
	
	private Sprite playRingSprite;
	private Sprite playCircle1Sprite;
	private Sprite playCircle2Sprite;
	private Sprite playCircle3Sprite;
	private Sprite playCircle4Sprite;
	private Sprite playCircle5Sprite;
	private Sprite playCircle6Sprite;
	
	private boolean[] flagPlayList = {false,false,false,false,false,false};	
	
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
		switch(imageNum){
		case 0:
			playCircle1Sprite.setVisible(true);
			break;
		case 1:
			playCircle2Sprite.setVisible(true);
			break;
		case 2:
			playCircle3Sprite.setVisible(true);
			break;
		case 3:
			playCircle4Sprite.setVisible(true);
			break;
		case 4:
			playCircle5Sprite.setVisible(true);
			break;
		case 5:
			playCircle6Sprite.setVisible(true);
			break;
		}
		flagPlayList[imageNum] = true;
	}
	private void disablePlayImageVisible(int imageNum){
		boolean ringVisible = false;
		switch(imageNum){
		case 0:
			playCircle1Sprite.setVisible(false);
			break;
		case 1:
			playCircle2Sprite.setVisible(false);
			break;
		case 2:
			playCircle3Sprite.setVisible(false);
			break;
		case 3:
			playCircle4Sprite.setVisible(false);
			break;
		case 4:
			playCircle5Sprite.setVisible(false);
			break;
		case 5:
			playCircle6Sprite.setVisible(false);
			break;
		}
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
	}
	
	
	private void createExtraButtons(){
		final float button_space = 1.2f;
		final float button_width = ResourcesManager.getInstance().BTN_IMG_WIDTH;
		final float myButton_width = ResourcesManager.getInstance().mMoreBtnRegion.getWidth();
		final float xPos = camera.getWidth()-myButton_width;
		final float yPos_1 = (camera.getHeight()-(button_width*2+button_space))/2;
				
		createMoreButton(xPos,yPos_1);
		createShareButton(xPos,yPos_1+myButton_width-button_space);
		createReviewButton(xPos,yPos_1+2*myButton_width-2*button_space);
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
		final float xPos = 504f;
		final float yPos = (camera.getHeight() - resourcesManager.mPlayRingRegion.getHeight())/2;
		
		playRingSprite = new Sprite(0,0,resourcesManager.mPlayRingRegion,vbom){
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera){
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		playCircle1Sprite = new Sprite(0,0,resourcesManager.mPlayCircle1Region,vbom){
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera){
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		playCircle2Sprite = new Sprite(0,0,resourcesManager.mPlayCircle2Region,vbom){
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera){
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		playCircle3Sprite = new Sprite(0,0,resourcesManager.mPlayCircle3Region,vbom){
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera){
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		playCircle4Sprite = new Sprite(0,0,resourcesManager.mPlayCircle4Region,vbom){
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera){
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		playCircle5Sprite = new Sprite(0,0,resourcesManager.mPlayCircle5Region,vbom){
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera){
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		playCircle6Sprite = new Sprite(0,0,resourcesManager.mPlayCircle6Region,vbom){
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera){
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		
		playRingSprite.setPosition(xPos, yPos);
		playCircle1Sprite.setPosition(xPos, yPos);
		playCircle2Sprite.setPosition(xPos, yPos);
		playCircle3Sprite.setPosition(xPos, yPos);
		playCircle4Sprite.setPosition(xPos, yPos);
		playCircle5Sprite.setPosition(xPos, yPos);
		playCircle6Sprite.setPosition(xPos, yPos);
		
		attachChild(playRingSprite);
		attachChild(playCircle1Sprite);
		attachChild(playCircle2Sprite);
		attachChild(playCircle3Sprite);
		attachChild(playCircle4Sprite);
		attachChild(playCircle5Sprite);
		attachChild(playCircle6Sprite);
		
		playCircle1Sprite.registerEntityModifier(new LoopEntityModifier(new RotationModifier(2f, 0, 360)));
		playCircle2Sprite.registerEntityModifier(new LoopEntityModifier(new RotationModifier(2.3f, 0, 360)));
		playCircle3Sprite.registerEntityModifier(new LoopEntityModifier(new RotationModifier(2.6f, 0, 360)));
		playCircle4Sprite.registerEntityModifier(new LoopEntityModifier(new RotationModifier(2.9f, 0, 360)));
		playCircle5Sprite.registerEntityModifier(new LoopEntityModifier(new RotationModifier(3.1f, 0, 360)));
		playCircle6Sprite.registerEntityModifier(new LoopEntityModifier(new RotationModifier(3.3f, 0, 360)));
		
		playRingSprite.setVisible(false);
		playCircle1Sprite.setVisible(false);
		playCircle2Sprite.setVisible(false);
		playCircle3Sprite.setVisible(false);
		playCircle4Sprite.setVisible(false);
		playCircle5Sprite.setVisible(false);
		playCircle6Sprite.setVisible(false);
	}
	
}















