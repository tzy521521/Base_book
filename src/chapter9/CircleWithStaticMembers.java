package chapter9;

/**
 * Created by tzy on 2017/3/16.
 */
public class CircleWithStaticMembers {
    /** The radius of the circle */
    double radius;

    /** The number of the objects created */
    static int numberOfObjects=0;

    /** Construct a circle with radius 1 */
    public CircleWithStaticMembers() {
        radius=1.0;
        numberOfObjects++;
    }

    /** Construct a circle with a specified radius */
    public CircleWithStaticMembers(double radius) {
        this.radius = radius;
        numberOfObjects++;
    }

    static int getNumberOfObject() {
        return numberOfObjects;
    }

    /** Return the area of this circle */
    double getArea(){
        return radius*radius*Math.PI;
    }
}
