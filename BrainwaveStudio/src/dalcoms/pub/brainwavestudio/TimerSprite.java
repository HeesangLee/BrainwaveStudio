package dalcoms.pub.brainwavestudio;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.VertexBufferObjectManager;


public class TimerSprite extends Sprite{
	public Sprite timerSettingIndicatorSprite;
	public Sprite timerLoopOnSprite;
	public final float INDICATOR_Y = 25.793f;
	private boolean flagLoopOn = false;

	public TimerSprite(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager vbom) {
		super(pX, pY, pTextureRegion, vbom);

		attachSprites(vbom);
	}

	@Override
	protected void preDraw(final GLState pGLState, final Camera pCamera) {
		super.preDraw(pGLState, pCamera);
		pGLState.enableDither();
	}
	
	
	public void moveIndicator(float posX){
		timerSettingIndicatorSprite.setPosition(posX-timerSettingIndicatorSprite.getWidth()/2, INDICATOR_Y);
	}
	
	public boolean setTimerLoopOn(boolean onOff){
		flagLoopOn = onOff;
		timerLoopOnSprite.setVisible(flagLoopOn);
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
		timerSettingIndicatorSprite = new Sprite(122f,INDICATOR_Y,ResourcesManager.getInstance().mTimerIndicatorRegion,vbom){
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera){
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		attachChild(timerSettingIndicatorSprite);
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
}





























