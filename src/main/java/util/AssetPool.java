package util;

import components.Spritesheet;
import renderer.Shader;
import renderer.Texture;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AssetPool
{
    private static Map<String, Shader> shaders = new HashMap<>();
    private static Map<String, Texture> textures = new HashMap<>();
    private static Map<String, Spritesheet> spritesheets = new HashMap<>();

    public static Shader getShader(String resourceName) {
        File file = new File(resourceName);
        if(AssetPool.shaders.containsKey(file.getAbsolutePath())) {
          //  System.out.println("Returning an existing Shader. resourceName : " + resourceName);
            return AssetPool.shaders.get(file.getAbsolutePath());
        }else{
            Shader shader = new Shader(resourceName);
            shader.compile();
            AssetPool.shaders.put(file.getAbsolutePath(), shader);
      //      System.out.println("New Shader created . resourceName : " + resourceName);
            return shader;
        }
    }

    public static Texture getTexture(String resourceName) {
        File file = new File(resourceName);
        if(AssetPool.textures.containsKey(file.getAbsolutePath())) {
          //  System.out.println("Returning an existing Texture. resourceName : " + resourceName);
            return AssetPool.textures.get(file.getAbsolutePath());
        }else{
            Texture texture = new Texture();
            texture.init(resourceName);
            AssetPool.textures.put(file.getAbsolutePath(), texture);
       //     System.out.println("New Texture created . resourceName : " + resourceName);
            return texture;
        }
        //assert false : "Asserting false in getTexture() in AssetPool Class.";
    }

    public static void addSpritesheet(String resourceName, Spritesheet spritesheet)
    {
        File file = new File(resourceName);
        if(!AssetPool.spritesheets.containsKey(file.getAbsolutePath())) {
            AssetPool.spritesheets.put(file.getAbsolutePath(), spritesheet);
        }
    }

    public static Spritesheet getSpritesheet(String resourceName) {
        File file = new File(resourceName);
        if(!AssetPool.spritesheets.containsKey(file.getAbsolutePath())) {
            assert false : "Errpr, trying to access spritesheet : '" + resourceName + "' and it has not been added to AssetPool.";
        } else {
              return AssetPool.spritesheets.getOrDefault(file.getAbsolutePath(), null);
        }
        return null;
    }

}
