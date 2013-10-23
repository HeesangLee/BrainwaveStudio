package dalcoms.pub.brainwavestudio;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.app.Activity;
import dalcoms.pub.brainwavestudio.SceneManager.SceneType;

public abstract class BaseScene extends Scene{
	//******************************
	// Variables
	//******************************
	protected Engine engine;
	protected Activity activity;
	protected ResourcesManager resourcesManager;
	protected VertexBufferObjectManager vbom;
	protected Camera camera;
	
	//******************************
	// Constructor
	//******************************
	public BaseScene(){
		//ResourcesManager class 를 MainActivity 에서 prepare를 통해 MainActivity의 하기 인자들을 공유했음. 
		//결과적으로 리소스메니저의 인스턴트를 통해 mainActivity의 주요 인자들을 쓸 수 있도록 함.
		this.resourcesManager = ResourcesManager.getInstance();
		this.engine = resourcesManager.engine;
		this.activity = resourcesManager.activity;
		this.vbom = resourcesManager.vbom;
		this.camera = resourcesManager.camera;
		createScene();
	}
	
	//******************************
	// Abstract
	//******************************
	public abstract void createScene();
	public abstract void onBackKeyPressed();
	public abstract SceneType getSceneType();
	public abstract void disposeScene();
	
}
