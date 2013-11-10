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
	
	private void attachSprites(VertexBufferObjectManager vbom){
		attachTimerButtonHide(vbom);
	}
	
	private void attachTimerButtonHide(VertexBufferObjectManager vbom){
		TiledSprite hideButtonSprite;
		hideButtonSprite = new TiledSprite(0, 0, ResourcesManager.getInstance().mTimerBtnHideTextureRegion, vbom){
			@Override
			protected void preDraw(final GLState pGLState, final Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY){
				
				if(pSceneTouchEvent.isActionUp()){
					this.setCurrentTileIndex(0);
				}else{
					this.setCurrentTileIndex(1);
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}
		};
		hideButtonSprite.setCurrentTileIndex(0);
//		registerTouchArea(hideButtonSprite);
	
		attachChild(hideButtonSprite);
	}
}

