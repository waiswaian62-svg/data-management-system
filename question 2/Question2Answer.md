# Question 2 Answer

## UML Class Diagram

```mermaid
classDiagram
    class Shape {
        <<abstract>>
        #String color
        #boolean filled
        +Shape()
        +Shape(String color, boolean filled)
        +getColor() String
        +setColor(String color) void
        +isFilled() boolean
        +setFilled(boolean filled) void
        +getArea()* double
        +getPerimeter()* double
        +resize(double factor)* void
        +toString() String
    }

    class Circle {
        -double radius
        +Circle(double radius)
        +Circle(String color, boolean filled, double radius)
        +getArea() double
        +getPerimeter() double
        +resize(double factor) void
        +toString() String
    }

    class Rectangle {
        -double width
        -double height
        +Rectangle(double width, double height)
        +Rectangle(String color, boolean filled, double width, double height)
        +getArea() double
        +getPerimeter() double
        +resize(double factor) void
        +toString() String
    }

    class Triangle {
        -double sideOne
        -double sideTwo
        -double sideThree
        +Triangle(double sideOne, double sideTwo, double sideThree)
        +Triangle(String color, boolean filled, double sideOne, double sideTwo, double sideThree)
        +getArea() double
        +getPerimeter() double
        +resize(double factor) void
        +toString() String
    }

    class InvalidShapeException {
        +InvalidShapeException(String message)
    }

    Shape <|-- Circle
    Shape <|-- Rectangle
    Shape <|-- Triangle
    Shape ..> InvalidShapeException : throws
    Circle ..> InvalidShapeException : throws
    Rectangle ..> InvalidShapeException : throws
    Triangle ..> InvalidShapeException : throws
```

The abstract class name is `Shape`. The abstract methods are `getArea()`, `getPerimeter()`, and `resize(double factor)`.

## Exception Design

`InvalidShapeException` is implemented as an unchecked exception by extending `RuntimeException`. I chose unchecked because invalid shape dimensions, impossible triangle sides, and non-positive resize factors are validation errors that should be prevented by the program logic, while still allowing the driver to catch and handle them when needed.

## Dynamic Binding Explanation

In `printAreas(Shape[] shapes)`, each item is stored using a superclass reference, but Java calls the correct subclass version of `getArea()` at runtime. For example, when the output says `Circle area = 78.54`, the method call uses the `Circle` implementation even though the array type is `Shape[]`.

## Why Shape Is Abstract

`Shape` is abstract because a general shape does not have enough information to calculate area or perimeter. Only specific shapes such as `Circle`, `Rectangle`, and `Triangle` can provide those calculations.

If you try to create a `Shape` directly using `new Shape()`, Java gives a compile-time error because abstract classes cannot be instantiated.

## Java File Submitted

- `ShapeDemo.java`

The file contains `ShapeDemo`, `Shape`, `Circle`, `Rectangle`, `Triangle`, and `InvalidShapeException` in one runnable Java file.
