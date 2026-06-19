public class ShapeDemo {
    public static void main(String[] args) {
        Shape[] shapes = {
                new Circle("red", true, 5.0),
                new Rectangle("blue", false, 4.0, 6.0),
                new Triangle("green", true, 3.0, 4.0, 5.0)
        };

        System.out.println("Original shapes:");
        printAreas(shapes);

        shapes[0].resize(2.0);
        shapes[1].resize(1.5);
        shapes[2].resize(2.0);

        System.out.println("\nAfter resizing:");
        printAreas(shapes);

        Shape largestShape = largest(shapes);
        System.out.println("\nShape with largest area:");
        System.out.println(largestShape);

        try {
            Triangle invalidTriangle = new Triangle("yellow", false, 1.0, 2.0, 10.0);
            System.out.println(invalidTriangle);
        } catch (InvalidShapeException exception) {
            System.out.println("\nCaught invalid shape:");
            System.out.println(exception.getMessage());
        }
    }

    public static void printAreas(Shape[] shapes) {
        for (Shape shape : shapes) {
            System.out.printf("%s area = %.2f%n",
                    shape.getClass().getSimpleName(),
                    shape.getArea());
        }
    }

    public static Shape largest(Shape[] shapes) {
        if (shapes == null || shapes.length == 0) {
            return null;
        }

        Shape largestShape = shapes[0];
        for (int index = 1; index < shapes.length; index++) {
            if (shapes[index].getArea() > largestShape.getArea()) {
                largestShape = shapes[index];
            }
        }
        return largestShape;
    }
}

abstract class Shape {
    protected String color = "white";
    protected boolean filled;

    public Shape() {
    }

    public Shape(String color, boolean filled) {
        this.color = color;
        this.filled = filled;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    public abstract double getArea();

    public abstract double getPerimeter();

    public abstract void resize(double factor);

    protected void validateResizeFactor(double factor) {
        if (factor <= 0) {
            throw new InvalidShapeException("Resize factor must be positive.");
        }
    }

    @Override
    public String toString() {
        return "color='" + color + '\'' +
                ", filled=" + filled;
    }
}

class Circle extends Shape {
    private double radius;

    public Circle(double radius) {
        this("white", false, radius);
    }

    public Circle(String color, boolean filled, double radius) {
        super(color, filled);
        if (radius <= 0) {
            throw new InvalidShapeException("Circle radius must be positive.");
        }
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        if (radius <= 0) {
            throw new InvalidShapeException("Circle radius must be positive.");
        }
        this.radius = radius;
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public void resize(double factor) {
        validateResizeFactor(factor);
        radius *= factor;
    }

    @Override
    public String toString() {
        return "Circle{" +
                super.toString() +
                ", radius=" + radius +
                ", area=" + String.format("%.2f", getArea()) +
                ", perimeter=" + String.format("%.2f", getPerimeter()) +
                '}';
    }
}

class Rectangle extends Shape {
    private double width;
    private double height;

    public Rectangle(double width, double height) {
        this("white", false, width, height);
    }

    public Rectangle(String color, boolean filled, double width, double height) {
        super(color, filled);
        if (width <= 0 || height <= 0) {
            throw new InvalidShapeException("Rectangle width and height must be positive.");
        }
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        if (width <= 0) {
            throw new InvalidShapeException("Rectangle width must be positive.");
        }
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        if (height <= 0) {
            throw new InvalidShapeException("Rectangle height must be positive.");
        }
        this.height = height;
    }

    @Override
    public double getArea() {
        return width * height;
    }

    @Override
    public double getPerimeter() {
        return 2 * (width + height);
    }

    @Override
    public void resize(double factor) {
        validateResizeFactor(factor);
        width *= factor;
        height *= factor;
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                super.toString() +
                ", width=" + width +
                ", height=" + height +
                ", area=" + String.format("%.2f", getArea()) +
                ", perimeter=" + String.format("%.2f", getPerimeter()) +
                '}';
    }
}

class Triangle extends Shape {
    private double sideOne;
    private double sideTwo;
    private double sideThree;

    public Triangle(double sideOne, double sideTwo, double sideThree) {
        this("white", false, sideOne, sideTwo, sideThree);
    }

    public Triangle(String color, boolean filled, double sideOne, double sideTwo, double sideThree) {
        super(color, filled);
        validateSides(sideOne, sideTwo, sideThree);
        this.sideOne = sideOne;
        this.sideTwo = sideTwo;
        this.sideThree = sideThree;
    }

    public double getSideOne() {
        return sideOne;
    }

    public void setSideOne(double sideOne) {
        validateSides(sideOne, sideTwo, sideThree);
        this.sideOne = sideOne;
    }

    public double getSideTwo() {
        return sideTwo;
    }

    public void setSideTwo(double sideTwo) {
        validateSides(sideOne, sideTwo, sideThree);
        this.sideTwo = sideTwo;
    }

    public double getSideThree() {
        return sideThree;
    }

    public void setSideThree(double sideThree) {
        validateSides(sideOne, sideTwo, sideThree);
        this.sideThree = sideThree;
    }

    @Override
    public double getArea() {
        double halfPerimeter = getPerimeter() / 2;
        return Math.sqrt(halfPerimeter *
                (halfPerimeter - sideOne) *
                (halfPerimeter - sideTwo) *
                (halfPerimeter - sideThree));
    }

    @Override
    public double getPerimeter() {
        return sideOne + sideTwo + sideThree;
    }

    @Override
    public void resize(double factor) {
        validateResizeFactor(factor);
        sideOne *= factor;
        sideTwo *= factor;
        sideThree *= factor;
    }

    private void validateSides(double sideOne, double sideTwo, double sideThree) {
        if (sideOne <= 0 || sideTwo <= 0 || sideThree <= 0) {
            throw new InvalidShapeException("Triangle sides must be positive.");
        }

        if (sideOne + sideTwo <= sideThree ||
                sideOne + sideThree <= sideTwo ||
                sideTwo + sideThree <= sideOne) {
            throw new InvalidShapeException("Triangle sides violate the triangle inequality.");
        }
    }

    @Override
    public String toString() {
        return "Triangle{" +
                super.toString() +
                ", sideOne=" + sideOne +
                ", sideTwo=" + sideTwo +
                ", sideThree=" + sideThree +
                ", area=" + String.format("%.2f", getArea()) +
                ", perimeter=" + String.format("%.2f", getPerimeter()) +
                '}';
    }
}

class InvalidShapeException extends RuntimeException {
    public InvalidShapeException(String message) {
        super(message);
    }
}
