package dalcoms.pub.brainwavestudio;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.sprite.vbo.ISpriteVertexBufferObject;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.VertexBufferObjectManager;


public class TimerSprite extends Sprite{
	public Sprite timerSettingIndicatorSprite;
	public final float INDICATOR_Y = 0f;

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
		timerSettingIndicatorSprite.setPosition(posX, INDICATOR_Y);
	}
	
	private void attachSprites(VertexBufferObjectManager vbom){
//		attachTimerButtonHide(vbom);
		attachTimerIndicator(vbom);
	}
	
	private void attachTimerIndicator(VertexBufferObjectManager vbom){
		timerSettingIndicatorSprite = new Sprite(20,INDICATOR_Y,ResourcesManager.getInstance().mTimerIndicatorRegion,vbom){
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera){
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		attachChild(timerSettingIndicatorSprite);
	}
}

