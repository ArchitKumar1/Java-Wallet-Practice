package practice;

interface Drawable {
    void draw();
}

interface Sayable {
    void say(String name);
}

interface Gettable {
    String get(String name);
}

public class Java8 {

    public static void main(String[] args) {
        int width = 10;
        Drawable d = () -> {
            System.out.println("width " + width);
        };
        d.draw();

        Sayable s = (name) -> {
            System.out.println("Say my name " + name);
        };
        String name = "myname";
        s.say(name);

        Gettable g = (x) -> (x+x);
        String get = g.get(name);
        System.out.println("Say my name " + get);
    }
}
