package dalcoms.pub.brainwavestudio;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.HorizontalAlign;

import android.util.Log;


public class TimerSprite extends Sprite{
	public Sprite timerSettingIndicatorSprite;
	public Sprite timerLoopOnSprite;
	public final float INDICATOR_Y = 10.793f;
	private boolean flagLoopOn = true;
	private final float TIME_MAX_MINUTE = 60f;
	private final float X_MIN = 128.378f;
	private final float X_MAX = ResourcesManager.getInstance().camera.getWidth()-X_MIN;
	
	public float timerMinute=30f;
	private Text timeText;

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
		timeText.setPosition(posX+3f,47.980f);
	}
	
	public float moveIndicator(float posX){
		float retPosX=0f;
		retPosX = limitPosX(posX);
		timerSettingIndicatorSprite.setPosition(retPosX-timerSettingIndicatorSprite.getWidth()/2, INDICATOR_Y);
		showTimeText(retPosX);
		this.setTimerLoopOn(false);
		
		return retPosX;
	}
	
	private float limitPosX(float posX){
		float retPosX=posX;
		if (posX<this.X_MIN){
			retPosX = this.X_MIN;
		}else if(posX>this.X_MAX){
			retPosX = this.X_MAX;
		}
		
		return retPosX;
	}
	
	public boolean setTimerLoopOn(boolean onOff){
		flagLoopOn = onOff;
		timerLoopOnSprite.setVisible(flagLoopOn);
		timerSettingIndicatorSprite.setVisible(!flagLoopOn);
		timeText.setVisible(!flagLoopOn);
		return flagLoopOn;
	}
	public boolean getTimerLoopOn(){
		return flagLoopOn;
	}
	
	private void attachSprites(VertexBufferObjectManager vbom){
		attachTimerIndicator(vbom);
		attachTimerLoopOn(vbom);
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
		final float posCent[] = {744.903f,23.778f};
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
	
}





























