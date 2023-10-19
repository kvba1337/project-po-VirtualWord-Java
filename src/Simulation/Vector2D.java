package Simulation;

public class Vector2D {
    public Vector2D(int y, int x){
        this.y = y;
        this.x = x;
    }

    public int getY(){
        return y;
    }

    public int getX(){
        return x;
    }

    public boolean equals(Vector2D v){
        return y == v.y && x == v.x;
    }

    public Vector2D add(Vector2D v){
        return new Vector2D(v.y + y, v.x + x);
    }

    public void addInPlace(Vector2D v){
        y += v.y;
        x += v.x;
    }

    public boolean isOutOfBounds(int height, int width) {
        return y < 0 || x < 0 || y >= height || x >= width;
    }

    private int y, x;
}
