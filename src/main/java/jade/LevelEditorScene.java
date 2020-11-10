package jade;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import components.Sprite;
import components.SpriteRenderer;
import components.Spritesheet;
import imgui.ImGui;
import org.joml.Vector2f;
import org.joml.Vector4f;
import renderer.Texture;
import util.AssetPool;

import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class LevelEditorScene extends Scene
{
//    private String vertexShaderSrc = "#version 330 core\n" +
//            "layout (location=0) in vec3 aPos;//a = attribiute\n" +
//            "layout (location=1) in vec4 aColor;\n" +
//            "\n" +
//            "//passing to fragment shader\n" +
//            "out vec4 fColor; //f = fragment\n" +
//            "void main() {\n" +
//            "    fColor = aColor;\n" +
//            "    gl_Position = vec4(aPos, 1.0);\n" +
//            "}";
//    private String fragmentShaderSrc = "    #version 330 core\n" +
//            "\n" +
//            "in vec4 fColor;\n" +
//            "out vec4 color;\n" +
//            "void main() {\n" +
//            "    color = fColor;\n" +
//            "}";
//    private int vertexId, fragmentId, shaderProgram;
//    private float[] vertexArray = {
//            //position        // color                         //UV coordinates
//            100.5f, 0.5f, 0.0f,    1.0f, 0.0f, 0.0f, 1.0f,    1,1, // bottom right       0
//            0.5f, 100.5f, 0.0f,    0.0f, 1.0f, 0.0f, 1.0f,    0,0, // top left green     1
//            100.5f, 100.5f, 0.0f,     0.0f, 0.0f, 1.0f, 1.0f, 1,0,   // top right blue     2
//            0.5f, 0.5f, 0.0f,   1.0f, 1.0f, 0.0f, 1.0f,       0,1,   // bottom left yellow 3
////            0.5f, -0.5f, 0.0f,    1.0f, 0.0f, 0.0f, 1.0f, // bottom right       0
////            -0.5f, 0.5f, 0.0f,    0.0f, 1.0f, 0.0f, 1.0f, // top left green     1
////            0.5f, 0.5f, 0.0f,     0.0f, 0.0f, 1.0f, 1.0f, // top right blue     2
////            -0.5f, -0.5f, 0.0f,   1.0f, 1.0f, 0.0f, 1.0f, // bottom left yellow 3
//    };
//    //important: must be in counter-clockwise order
//    private int[] elementArray = {
//            2,1,0,  //top right triangle
//            0,1,3   // bottom left triangle
//    };
//
//    private int vaoID, vboID, eboID;
//    private Shader defaultShader;
//    private Texture testTexture;
//
//    GameObject testObj;
//    private boolean firstTime = false;

    private GameObject obj1;
    private Spritesheet sprites;

    private   SpriteRenderer obj1Sprite;


    public LevelEditorScene() {

    }

    @Override
    public void init() {
        loadResources();
        this.camera = new Camera(new Vector2f(-250, 0));
        if(levelLoaded) {
            return;
        }
         sprites = AssetPool.getSpritesheet("assets/images/spritesheet.png");

        obj1 = new GameObject("Object 1 ", new Transform(new Vector2f(100,100), new Vector2f(128,128)), 10);
     //   obj1.addComponent(new SpriteRenderer(sprites.getSprite(0)));
         obj1Sprite = new SpriteRenderer();
        obj1Sprite.setColor(new Vector4f(1,0,0,1));
        obj1.addComponent(obj1Sprite);
        this.addGameObjectToScene(obj1);

        this.activeGameObject= obj1;

//           GameObject obj2 = new GameObject("Object 2 ", new Transform(new Vector2f(120,400), new Vector2f(128,128)), 1);
//        SpriteRenderer obj2SpriteRenderer = new SpriteRenderer();
//        Sprite obj2Sprite = new Sprite();
//        obj2Sprite.setTexture(AssetPool.getTexture("assets/images/testSprite2.png"));
//        obj2SpriteRenderer.setSprite(obj2Sprite);
//
//        obj2.addComponent(obj2SpriteRenderer);
//        this.addGameObjectToScene(obj2);

    }//end init()

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");
        AssetPool.addSpritesheet("assets/images/spritesheet.png",
                new Spritesheet(AssetPool.getTexture("assets/images/spritesheet.png"),
                        16, 16, 26, 0));
    }

    private int spriteIndex = 0;
    private float spriteFlipTime = 0.2f;
    private float spriteFlipTimeLeft = 0.0f;

    @Override
    public void update(float dt) {


//        spriteFlipTimeLeft -= (dt * 2);
//
//        if(spriteFlipTimeLeft <= 0){
//            spriteFlipTimeLeft  = spriteFlipTime;
//            spriteFlipTimeLeft  = spriteFlipTime;
//            spriteIndex++;
//            if(spriteIndex > 18) {
//                spriteIndex =0;
//            }
//            obj1.getComponent(SpriteRenderer.class).setSprite(sprites.getSprite(spriteIndex));
//        }

   //    obj1.transform.position.x += 10*dt;


   //     System.out.println("FPS : " + (1.0f / dt));
    //   camera.position.y -= dt* 50.0f;
    //    camera.position.x -= dt* 50.0f;
        for(GameObject go: this.gameObjects) {
            go.update(dt);
        }
        this.renderer.render();
    }

    @Override
    public void imgui() {
        ImGui.begin("Test Window");
        ImGui.text("soem randome text");
        ImGui.end();
    }
}
