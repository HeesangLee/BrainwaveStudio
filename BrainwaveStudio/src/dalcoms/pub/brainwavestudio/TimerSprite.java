package dalcoms.pub.brainwavestudio;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class TimerSprite extends Sprite{
	public Sprite timerSettingIndicatorSprite;
	public Sprite timerLoopOnSprite;
	public Sprite timerTimeLineSprite;
	public Sprite helpSprite;
	public final float INDICATOR_Y = 10.793f;
	private boolean flagLoopOn = true;
	private final float TIME_MAX_MINUTE = 60f;
	private final float X_MIN = 128.378f;
	private final float X_MAX = ResourcesManager.getInstance().camera.getWidth()-X_MIN;
	
	public float timerMinute=30f;
	private Text timeText;
	
	private boolean flagTimerSettingShow=false;
	private boolean flagTriggerTimeZero=false;

	public TimerSprite(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager vbom) {
		super(pX, pY, pTextureRegion, vbom);

		attachSprites(vbom);
		loadText();
	}

	@Override
	protected void preDraw(final GLState pGLState, final Camera pCamera) {
		super.preDraw(pGLState, pCamera);
		pGLState.enableDither();
	}
	
	private void loadText(){	
		timeText = ResourcesManager.getInstance().timeText;
		attachChild(timeText);
		this.showTimeText(this.getPosition());
		timeText.setVisible(!flagLoopOn);
	}
	private void showTimeText(float posX){
		this.setTimerTime(this.pos2Time(posX));
		timeText.setText(String.format("%.1fmin",this.timerMinute));
		timeText.setColor((this.timerMinute/this.TIME_MAX_MINUTE)*0.9f, 
				(1f-this.timerMinute/this.TIME_MAX_MINUTE)*0.8f,
				(1f-this.timerMinute/this.TIME_MAX_MINUTE)*0.3f);//시간에 따른 색상 변경
		
		if(this.flagTimerSettingShow){//인디게이터 옆에 위치하도록
			timeText.setPosition(posX+3f,47.980f);
			timeText.setScale(1f);
		}else{//좌측 상단에 위치하도록 
			if(timeText.getY()>20f){
				timeText.setPosition(4f,5.65f);
				timeText.setScale(0.88f);
			}
		}
	}
	
	public float moveIndicator(float posX){
		float retPosX=0f;
		retPosX = limitPosX(posX);
		timerSettingIndicatorSprite.setPosition(retPosX-timerSettingIndicatorSprite.getWidth()/2, INDICATOR_Y);
		showTimeText(retPosX);
		this.setTimerLoopOn(false);
		
		detachChild(helpSprite);
		return retPosX;
	}
	
	private float limitPosX(float posX){
		float retPosX=posX;
		if (posX<this.X_MIN){
			retPosX = this.X_MIN;
			if(!flagTimerSettingShow){//시간이 지남에 따라 0에 도달 했을 경우, 도달플래그 세트
				flagTriggerTimeZero=true;
			}
		}else if(posX>this.X_MAX){
			retPosX = this.X_MAX;
		}
		
		return retPosX;
	}
	
	public boolean setTimerLoopOn(boolean onOff){
		flagLoopOn = onOff;
		timerLoopOnSprite.setVisible(flagLoopOn);
		timerSettingIndicatorSprite.setVisible(!flagLoopOn);
		timerTimeLineSprite.setVisible(!flagLoopOn);
		timeText.setVisible(!flagLoopOn);
		return flagLoopOn;
	}
	public boolean getTimerLoopOn(){
		return flagLoopOn;
	}
	
	private void attachSprites(VertexBufferObjectManager vbom){
		attachTimerIndicator(vbom);
		attachTimerLoopOn(vbom);
		attachTimerTimeLine(vbom);
		attachTimerHelp(vbom);
	}
	
	private void attachTimerHelp(VertexBufferObjectManager vbom){

		helpSprite= new Sprite(248.03f,
				105.453f,
				ResourcesManager.getInstance().mTimerHelpRegion,
				vbom){
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera){
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		attachChild(helpSprite);
	}
	
	private void attachTimerTimeLine(VertexBufferObjectManager vbom){
		final float xCent=ResourcesManager.getInstance().camera.getWidth()/2;
		final float yCent=23.785f;
		timerTimeLineSprite= new Sprite(xCent-ResourcesManager.getInstance().mTimerTimeLineRegion.getWidth()/2,
				yCent-ResourcesManager.getInstance().mTimerTimeLineRegion.getHeight()/2,
				ResourcesManager.getInstance().mTimerTimeLineRegion,
				vbom){
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera){
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		attachChild(timerTimeLineSprite);
		timerTimeLineSprite.setVisible(!flagLoopOn);
		timerTimeLineSprite.registerEntityModifier(
				new LoopEntityModifier(
						new SequenceEntityModifier(
								new AlphaModifier(2f, 0.35f, 0.8f),
								new AlphaModifier(2f, 0.8f, 1f),
								new AlphaModifier(2f, 1f, 0.8f),
								new AlphaModifier(2f, 0.8f, 0.35f)
								)
						)
				);
	}
	
	private void attachTimerIndicator(VertexBufferObjectManager vbom){
		timerSettingIndicatorSprite = new Sprite(this.getPosition()-ResourcesManager.getInstance().mTimerIndicatorRegion.getWidth()/2,
				INDICATOR_Y,
				ResourcesManager.getInstance().mTimerIndicatorRegion,
				vbom){
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera){
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		attachChild(timerSettingIndicatorSprite);
		timerSettingIndicatorSprite.setVisible(!flagLoopOn);
	}
	
	private void attachTimerLoopOn(VertexBufferObjectManager vbom){
		final float posCent[] = {744.903f,23.785f};
		final float posX = posCent[0]-ResourcesManager.getInstance().mTimerLoopOnRegion.getWidth()/2;
		final float posY = posCent[1]-ResourcesManager.getInstance().mTimerLoopOnRegion.getHeight()/2;
		timerLoopOnSprite = new Sprite(posX,posY,
				ResourcesManager.getInstance().mTimerLoopOnRegion,vbom){
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera){
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		attachChild(timerLoopOnSprite);
		timerLoopOnSprite.registerEntityModifier(
				new LoopEntityModifier(
						new SequenceEntityModifier(
								new AlphaModifier(2f, 0.65f, 0.8f),
								new AlphaModifier(2f, 0.8f, 1f),
								new AlphaModifier(2f, 1f, 0.8f),
								new AlphaModifier(2f, 0.8f, 0.65f)
								)
						)
				);
		timerLoopOnSprite.setVisible(flagLoopOn);
	}
	
	public float pos2Time(float posX){
		float timeMinute = 0;
		timeMinute = (TIME_MAX_MINUTE*(posX-X_MIN))/(X_MAX-X_MIN);
		return timeMinute;
	}
	
	public float time2Pos(float timeMinute){
		float pos = 0;
		pos = (((X_MAX-X_MIN)*timeMinute)/(TIME_MAX_MINUTE))+X_MIN;
		return pos;
	}
	
	public float getPosition(){
		return time2Pos(timerMinute);
	}
	
	private void setTimerTime(float timeMinute){
		this.timerMinute = timeMinute;
	}
	public float getDiffPositonByMinute(float timeMinute){
		float retPos=0;
		retPos=this.time2Pos(timeMinute)-this.time2Pos(0);
		return retPos;
	}
	public float getCurrentTimer(){
		return this.timerMinute;
	}
	public void setTimerSettingShowFlag(boolean flagShow){
		flagTimerSettingShow = flagShow;
		showTimeText(getPosition());
	}
	public boolean checkTimerZeroOn(){
		return this.flagTriggerTimeZero;
	}
	public void clearTimerZeroOn(){
		this.flagTriggerTimeZero = false;
	}
}





























