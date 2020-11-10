import jade.Window;

public class Main
{
    public static void main(String [] args) {
       Window window = Window.get(); // only created once calling 1 object all the time
       window.run();
    }
}
