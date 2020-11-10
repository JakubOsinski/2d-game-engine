package components;

import org.joml.Vector2f;
import renderer.Texture;

import java.util.ArrayList;
import java.util.List;

public class Spritesheet
{
    private Texture texture;
    private List<Sprite> sprites;

    public Spritesheet(Texture texture, int spriteW, int spriteH, int numSprites, int spacing) {
        this.sprites = new ArrayList<>();
        this.texture = texture;
        int currentX = 0;
        int currentY = texture.getHeight() - spriteH;
        for(int i =0; i < numSprites; i++) {
            float topY = (currentY + spriteH) / (float)texture.getHeight();
            float rightX = (currentX + spriteW) / (float)texture.getWidth();
            float leftX =   currentX / (float)texture.getWidth();
            float bottomY = currentY / (float)texture.getHeight();

            Vector2f[] texCoords = {
                    new Vector2f(rightX,topY),
                    new Vector2f(rightX,bottomY),
                    new Vector2f(leftX, bottomY),
                    new Vector2f(leftX,topY)
            };

            Sprite sprite = new Sprite();
            sprite.setTexture(this.texture);
            sprite.setTexCoords(texCoords);
            this.sprites.add(sprite);

            currentX += spriteW + spacing;
            if(currentX >= texture.getWidth()) {
                currentX = 0;
                currentY += spriteH+spacing;
            }
        }
    }
    public Sprite getSprite(int i) {
        return this.sprites.get(i);
    }

}
