package dalcoms.pub.brainwavestudio;

import java.io.IOException;
import java.util.ArrayList;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
//import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.bitmap.BitmapTextureFormat;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

public class ResourcesManager {
	//******************************
	// Variables
	//******************************
	private static final ResourcesManager instance = new ResourcesManager();
	
	public Engine engine;
	public MainActivity activity;
	public Camera camera;
	public VertexBufferObjectManager vbom;
	
	public final int SPLASH_LOGO_WIDTH 	= 673;
	public final int SPLASH_LOGO_HEIGHT = 232;
	public final int LOADING_IMG_WIDTH 	= 518;
	public final int LOADING_IMG_HEIGHT = 518;
	
	public Font mFont_grossnet;
	public Font mFont_Plok;
	protected boolean isFontLoaded = false;
	
	
	public final int BTN_IMG_WIDTH = 112;
	public final int BTN_IMG_HEIGHT = 152;
	private final int BTN_IMG_TILE_ROW = 10;
	private final int BTN_IMG_TILE_COLUMN = 2;
	
	public ITextureRegion splash_region;
	public ITextureRegion loading_region;
	private BuildableBitmapTextureAtlas splashTextureAtlas;
	
	public ITiledTextureRegion mSoundBtnTextureRegion;
	private BuildableBitmapTextureAtlas mSoundBtnTextureAtlas;
	
	public ITextureRegion mMainBackgroundRegion;
	private BitmapTextureAtlas mainBgTextureAtlas;
	
	public ITextureRegion mReviewBtnRegion;
	public ITextureRegion mMoreBtnRegion;
	public ITextureRegion mShareBtnRegion;
	private BuildableBitmapTextureAtlas buttonsTextureAtlas;
	
	public ITextureRegion mPlayRingRegion;
//	public ITextureRegion mPlayCircle1Region;
//	public ITextureRegion mPlayCircle2Region;
//	public ITextureRegion mPlayCircle3Region;
//	public ITextureRegion mPlayCircle4Region;
//	public ITextureRegion mPlayCircle5Region;
//	public ITextureRegion mPlayCircle6Region;
	
	private BuildableBitmapTextureAtlas playImageAtlas;
	
	public Music mSound_1;
	public Music mSound_2;
	public Music mSound_3;
	public Music mSound_4;
	public Music mSound_5;
	public Music mSound_6;
	public Music mSound_7;
	public Music mSound_8;
	public Music mSound_9;
	public Music mSound_10;
	
	public ArrayList<Music> mSoundArray = new ArrayList<Music>();
	//====================================================================
	
	public static ResourcesManager getInstance(){
		return instance;
	}
	
	/**
     * @param engine
     * @param activity
     * @param camera
     * @param vbom
     * <br><br>
     * We use this method at beginning of game loading, to prepare Resources Manager properly,
     * setting all needed parameters, so we can latter access them from different classes (eg. scenes)
     */
	public static void prepareManager(
			Engine engine,
			MainActivity activity,
			Camera camera,
			VertexBufferObjectManager vbom){
		
		getInstance().engine = engine;
		getInstance().activity = activity;
		getInstance().camera = camera;
		getInstance().vbom = vbom;
		
	}
	
	
	public void loadMenuResources(){
		loadMenuGraphics();
		loadMenuAudio();
		loadFonts();
	}
	public void unLoadMenuResources(){
		unLoadMenuGraphics();
	}
	//==========Graphics==================
	private void unLoadMenuGraphics(){

	}
	
	private void loadMenuGraphics(){
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		loadBackgroundGraphics();
		loadSoundBtnGraphics();
		loadButtonsGraphics();
		loadPlayImages();
	}
	
	private void loadBackgroundGraphics(){
		mainBgTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 
				activity.CAMERA_WIDTH, activity.CAMERA_HEIGHT,
				BitmapTextureFormat.RGBA_8888,TextureOptions.BILINEAR);
		mMainBackgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mainBgTextureAtlas, 
				activity, "bg.png",0,0);
		mainBgTextureAtlas.load();
	}
	
	private void loadButtonsGraphics(){
		buttonsTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 
				128, 128*3,
				BitmapTextureFormat.RGBA_8888,TextureOptions.BILINEAR);
		mReviewBtnRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(buttonsTextureAtlas, 
				activity, "review_icon.png");
		mMoreBtnRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(buttonsTextureAtlas, 
				activity, "more_icon.png");
		mShareBtnRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(buttonsTextureAtlas, 
				activity, "share_icon.png");
		
		try{
			buttonsTextureAtlas.build(
					new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>
					(0,0,0));
			buttonsTextureAtlas.load();
		}catch(TextureAtlasBuilderException e){
			e.printStackTrace();
		}
	}
	
	private void loadPlayImages(){
		playImageAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 
				300, 300,
				BitmapTextureFormat.RGBA_8888,TextureOptions.BILINEAR);
		
		mPlayRingRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(playImageAtlas, 
				activity, "play_ring.png");
//		mPlayCircle1Region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(playImageAtlas, 
//				activity, "play_circle_1.png");
//		mPlayCircle2Region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(playImageAtlas, 
//				activity, "play_circle_2.png");
//		mPlayCircle3Region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(playImageAtlas, 
//				activity, "play_circle_3.png");
//		mPlayCircle4Region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(playImageAtlas, 
//				activity, "play_circle_4.png");
//		mPlayCircle5Region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(playImageAtlas, 
//				activity, "play_circle_5.png");
//		mPlayCircle6Region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(playImageAtlas, 
//				activity, "play_circle_6.png");
		
		try{
			playImageAtlas.build(
					new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>
					(0,0,0));
			playImageAtlas.load();
		}catch(TextureAtlasBuilderException e){
			e.printStackTrace();
		}
	}
	
	
	private void loadSoundBtnGraphics(){
		mSoundBtnTextureAtlas = new BuildableBitmapTextureAtlas(
				engine.getTextureManager(), 
				BTN_IMG_WIDTH*BTN_IMG_TILE_COLUMN, 
				BTN_IMG_HEIGHT*BTN_IMG_TILE_ROW,
				BitmapTextureFormat.RGBA_8888,TextureOptions.BILINEAR);
		
		mSoundBtnTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(mSoundBtnTextureAtlas, activity.getAssets(),
						"sound_btns.png", BTN_IMG_TILE_COLUMN, BTN_IMG_TILE_ROW);
		
		try{
			mSoundBtnTextureAtlas.build(
					new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>
					(0,0,0));
			mSoundBtnTextureAtlas.load();
		}catch(TextureAtlasBuilderException e){
			e.printStackTrace();
		}
	}
	
	//======================
	
	private void loadMenuAudio(){
		MusicFactory.setAssetBasePath("sfx/");
		//TODO : 오디오 파일이 이어지는 부분이 매끄럽지 못함...>> 음원파일 수정해야 함. 상당히 거슬려
		try{
			mSound_1 = MusicFactory.createMusicFromAsset(
					engine.getMusicManager(), activity, "wdelta_f2_l250_r252_a0p8.ogg");
			mSound_2 = MusicFactory.createMusicFromAsset(
					engine.getMusicManager(), activity, "wtheta_f6_l324_r318_a0p8.ogg");
			mSound_3 = MusicFactory.createMusicFromAsset(
					engine.getMusicManager(), activity, "walpha_f10_l386_r396_a0p8.ogg");
			mSound_4 = MusicFactory.createMusicFromAsset(
					engine.getMusicManager(), activity, "brownnoise_a0p2.ogg");
			mSound_5 = MusicFactory.createMusicFromAsset(
					engine.getMusicManager(), activity, "pinknoise_a0p2.ogg");
			mSound_6 = MusicFactory.createMusicFromAsset(
					engine.getMusicManager(), activity, "wbetasmr_f13p5_l454_r467p5_a0p8.ogg");
			mSound_7 = MusicFactory.createMusicFromAsset(
					engine.getMusicManager(), activity, "wbetam_f17p5_l522_r539p5_a0p8.ogg");
			mSound_8 = MusicFactory.createMusicFromAsset(
					engine.getMusicManager(), activity, "wbetah_f25_l590_r615.ogg");
			mSound_9 = MusicFactory.createMusicFromAsset(
					engine.getMusicManager(), activity, "wgama_f40_l658_r698.ogg");
			mSound_10 = MusicFactory.createMusicFromAsset(
					engine.getMusicManager(), activity, "whitenoise_a0p2.ogg");
			
			mSoundArray.add(mSound_1);
			mSoundArray.add(mSound_2);
			mSoundArray.add(mSound_3);
			mSoundArray.add(mSound_4);
			mSoundArray.add(mSound_5);
			mSoundArray.add(mSound_6);
			mSoundArray.add(mSound_7);
			mSoundArray.add(mSound_8);
			mSoundArray.add(mSound_9);
			mSoundArray.add(mSound_10);
			
		}catch(IllegalStateException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	private void loadFonts(){
		if(isFontLoaded == true){
			return;
		}
		
		FontFactory.setAssetBasePath("fonts/");
		mFont_grossnet = FontFactory.createFromAsset(activity.getFontManager(), 
				activity.getTextureManager(),
				256,256,
				activity.getAssets(),
				"GROSSNET.TTF",
				32f,
				true,
				Color.WHITE_ABGR_PACKED_INT);
		
		mFont_Plok = FontFactory.createFromAsset(activity.getFontManager(), 
				activity.getTextureManager(),
				256,256,
				activity.getAssets(),
				"Plok.ttf",
				32f,
				true,
				Color.WHITE_ABGR_PACKED_INT);
		
		mFont_grossnet.load();
		mFont_Plok.load();
		isFontLoaded = true;
	}
	
	public void loadSplashScreen(){
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		splashTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 
				SPLASH_LOGO_WIDTH, SPLASH_LOGO_HEIGHT+LOADING_IMG_HEIGHT+10,
				BitmapTextureFormat.RGBA_8888,TextureOptions.BILINEAR);
		splash_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, 
													activity, 
													"splash_logo.png");
		loading_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, 
				activity, 
				"loading.png");
		try{
			splashTextureAtlas.build(
					new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>
					(0,0,0));
			splashTextureAtlas.load();
		}catch(TextureAtlasBuilderException e){
			e.printStackTrace();
		}
		loadFonts();
	}
	
	public void unloadSplashScreen(){
		splashTextureAtlas.unload();
		splash_region = null;
	}
}
