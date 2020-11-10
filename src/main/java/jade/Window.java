package jade;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window
{
    private int width, height;
    private  String title;
    private static Window window = null;
    private long glfwWindow;
    private ImGuiLayer imGuiLayer;

    public float r,g,b,a;
    private boolean fadeToBlack = false;

    //private static int currentSceneIndex = -1; // no need
    private static Scene currentScene = null;

    private Window()
    {
this.width = 1920;
this.height = 1080;
this.title = "Mario";
r = 1; g = 1; b = 1; a = 1;
    //    r = 0.7f; g = 0.2f; b = 0.1f; a = 1;
    }

    public static void changeScene(int newScene) {
        switch(newScene) {
            case 0:  currentScene = new LevelEditorScene();
            currentScene.load();
           currentScene.init();
           currentScene.start();
            break;
            case 1: currentScene = new LevelScene();
                currentScene.load();
                currentScene.init();
                currentScene.start();
                break;
            default:
                assert false :"Unknown state '" + newScene + "'";
            break;
        }
    }

    public static Scene getScene(){
        return get().currentScene;
    }

    public static Window get(){
        if(Window.window == null) {
            Window.window = new Window();
        }
        return Window.window; // singleton, only 1 instance of window
    }

public void run() {
    System.out.println("Hello LWJGL " + Version.getVersion() + "!");
    init();
    loop();
}

    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
   //     System.out.println("we have an error");
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() ) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
// Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE); // start in maximized position
// Create the window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if ( glfwWindow == NULL ) {
            throw new RuntimeException("Failed to create the GLFW window");
            }

        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);
        glfwSetWindowSizeCallback(glfwWindow, (w, newWidth, newHeight) -> {
            Window.setWidth(newWidth);
            Window.setHeight(newHeight);
        });

//make the openGL context
        glfwMakeContextCurrent(glfwWindow);
        //enable v-sync
        glfwSwapInterval(1); //refresh as fast as possible
        //make the window visible
        glfwShowWindow(glfwWindow);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
        this.imGuiLayer = new ImGuiLayer(glfwWindow);
        this.imGuiLayer.initImGui();

        Window.changeScene(0);
    } // end init //    return (float)((System.nanoTime() - timeStarted) * 1E-9); // 1E-9) = convert to nano seconds
    private void loop() {
        float beginTime = (float)glfwGetTime();
        float endTime;
        float dt = -0.0f;



        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while ( !glfwWindowShouldClose(glfwWindow) ) {
            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
            glClearColor(r, b, g, a);
            glClear(GL_COLOR_BUFFER_BIT); // clear the framebuffer

            if(dt >= 0) { // lag of 2 frames
                currentScene.update(dt);
            }

            this.imGuiLayer.update(dt, currentScene);
            glfwSwapBuffers(glfwWindow); // swap the color buffers

            endTime = (float)glfwGetTime();
            dt = endTime - beginTime;
            beginTime = endTime;
        }
        currentScene.saveExit();
    }

    public static int getWidth() {return get().width;}
    public static int getHeight() { return get().height;}

    public static void setWidth(int newWidth) {
        get().width = newWidth;
    }
    public static void setHeight(int newHeight) {
        get().height = newHeight;
    }
}
