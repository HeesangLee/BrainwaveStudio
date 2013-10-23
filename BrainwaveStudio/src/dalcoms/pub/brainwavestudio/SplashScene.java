package dalcoms.pub.brainwavestudio;

import org.andengine.engine.camera.Camera;
//import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveXModifier;
//import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.util.GLState;

import dalcoms.pub.brainwavestudio.SceneManager.SceneType;
import dalcoms.pub.brainwavestudio.R;

public class SplashScene extends BaseScene{
	private Sprite splashSprite;
	private Sprite loadingSprite;
	private Text companyName;
	private Text appName;
	
	@Override
	public void createScene() {
		attachSprite();
//		attachText();
	}

	protected void attachSprite(){
		splashSprite = new Sprite(0, 0, resourcesManager.splash_region,vbom){
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera){
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		loadingSprite = new Sprite(0, 0, resourcesManager.loading_region,vbom){
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera){
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		
		this.setBackground(new Background(0.0f,0.13f,0.17f));
		
		splashSprite.setPosition(camera.getWidth()/20,
				(camera.getHeight()-splashSprite.getHeight())/2); // 높이는 대충 때려 잡은 것. ㅠㅠ
		
		loadingSprite.setPosition(camera.getWidth()-loadingSprite.getWidth()/2,
				-1*loadingSprite.getHeight()/2);
		
				attachChild(loadingSprite);
		attachChild(splashSprite);
		
		loadingSprite.registerEntityModifier(new LoopEntityModifier(new RotationModifier(3.0f, 0, 360)));
//		splashSprite.registerEntityModifier(new ScaleModifier(2f, 0, 1));
	}
	
	protected void attachText(){
		final int textYMargin = 10;
		companyName = new Text(0, 0, 
				resourcesManager.mFont_Plok, 
				activity.getString(R.string.company), 
				vbom);
		
		appName = new Text(0, 0, 
				resourcesManager.mFont_Plok, 
				activity.getString(R.string.app_name), 
				vbom);
		companyName.setPosition(camera.getWidth(), splashSprite.getY()-companyName.getHeight()-textYMargin);
		appName.setPosition(-appName.getWidth(), splashSprite.getY()+splashSprite.getHeight()+textYMargin);
		
		
		attachChild(companyName);
		attachChild(appName);
		
		SequenceEntityModifier seqModComany = new SequenceEntityModifier(
				new MoveXModifier(
						0.5f, companyName.getX(), (camera.getWidth()-companyName.getWidth())/2),
				new ScaleModifier(0.4f, 0.5f, 1.5f));
		SequenceEntityModifier seqModApp = new SequenceEntityModifier(
				new MoveXModifier(
						0.5f, appName.getX(), (camera.getWidth()-appName.getWidth())/2),
				new ScaleModifier(0.4f, 0.5f, 1.2f));
		
		companyName.registerEntityModifier(seqModComany);
		appName.registerEntityModifier(seqModApp);
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		return;
	}

	@Override
	public SceneType getSceneType() { // return my scene type.
		return SceneType.SCENE_SPLASH;
	}

	@Override
	public void disposeScene() {
		splashSprite.detachSelf();
		splashSprite.dispose();
		
		loadingSprite.detachSelf();
		loadingSprite.dispose();
	
//		companyName.detachSelf();
//		companyName.dispose();
//		
//		appName.detachSelf();
//		appName.dispose();

		this.detachSelf();
		this.dispose();
	}

}
