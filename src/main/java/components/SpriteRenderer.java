package components;

import imgui.ImGui;
import jade.Component;
import jade.Transform;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.w3c.dom.Text;
import renderer.Texture;

public class    SpriteRenderer extends Component {

    private Vector4f color = new Vector4f(1,1,1,1);
    private Sprite sprite = new Sprite();

    private transient Transform lastTransform;
    private transient boolean isDirty = true;

//    public SpriteRenderer(Vector4f color) {
//        this.color = color;
//        this.sprite = new Sprite(null);
//        this.isDirty = true;
//    }
//
//    public SpriteRenderer(Sprite sprite) {
//        this.sprite = sprite;
//        this.color = new Vector4f(1, 1, 1, 1);
//        this.isDirty = true;
//    }

    @Override
    public void start() {
        this.lastTransform = gameObject.transform.copy();
    }

    @Override
    public void update(float dt) {
        if (!this.lastTransform.equals(this.gameObject.transform)) { // if game object has changed in anyway it's position or scale...
            this.gameObject.transform.copy(this.lastTransform);
            isDirty = true;
        }


    }

    @Override
    public void imgui() {
      //  ImGui.text("Color Picker: ");
        float [] imColor = {color.x, color.y, color.z, color.w};

        if( ImGui.colorPicker4("Color Picker: ", imColor)){
            this.color.set(imColor[0], imColor[1], imColor[2], imColor[3]);
            this.isDirty = true;
        }

    }

    public Vector4f getColor() {
        return this.color;
    }

    public Texture getTexture() {
        return sprite.getTexture();
    }

    public Vector2f[] getTexCoords() {
        return sprite.getTexCoords();
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
        this.isDirty = true;
    }

    public void setColor(Vector4f color) {
        if (!this.color.equals(color)) { // don't change the color if you try to change to same color
            this.isDirty = true;
            this.color.set(color);
        }
    }

    public boolean isDirty() { return this.isDirty;}
    public void setClean() {this.isDirty = false;}



}
