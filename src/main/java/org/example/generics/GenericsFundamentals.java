package org.example.generics;

import java.util.ArrayList;
import java.util.List;

public class GenericsFundamentals {

    //Before Generics (Java ≤ 1.4)
    //List list = new ArrayList();
    //list.add("hello");
    //list.add(10);   // allowed
    //String s = (String) list.get(1); // Runtime error
    //
    // Problems:
    //No type safety
    //Explicit casting everywhere
    //Errors discovered at runtime, not compile time

    //With Generics
    //List<String> list = new ArrayList<>();
    //list.add("hello");
    //list.add(10);   // ❌ compile-time error
    //What generics give you
    //Compile-time type safety
    //No casting
    //Self-documenting APIs
    //Generics move errors from runtime to compile time — that’s their biggest value.

    public <T, U> List<T> test(U val) {
        //Why <T> before return type?
        //➡ It declares type parameter for method
        T t = null;
        List<String> stringList = List.of("");
        List<Object> objectList = new ArrayList<>(stringList);//Invariance
        //Generics provide compile-time type safety, eliminate unsafe casts,
        // but can be bypassed using raw types due to backward compatibility.
        if (val instanceof String) {
            System.out.println(val);
        }
        Number n;
        n = 1;
        n = 2.2;
        return List.of(t);
    }

    //Now we restrict what types are allowed in generics.

    //Why Bounds Are Needed
//    class Calculator<T> {
//        double add(T a, T b) {
//            return a + b; // ❌ compile error
//        }
//    }

    class Calculator<T extends Number & Comparable<T>> {//Class must come first Interfaces after

        double add(T a, T b) {
            return a.doubleValue() + b.doubleValue(); // T must be Number or subclass of Number
        }
    }
    class Box<T> {
//        T value = new T(); // ❌ Compile error
    }
//    Why?
//    Type info erased at runtime
//    JVM doesn’t know which constructor to call


    double sum(List<? extends Number> list) {//wildcards - I don’t care about the exact type, but I want some safety rules.
        double total = 0;
        for (Number n : list) {
            total += n.doubleValue();
        }
        return total;
    }

    //Type erasure removes generic type information at runtime to maintain backward compatibility. Generic types exist only at compile time.
    //🟢 T vs ? — CORE DIFFERENCE
    //Feature	T (Type Parameter)	? (Wildcard)
    //Meaning	Placeholder for a type you define	Unknown type (exists already)
    //Usage	Class or method definition	Method parameters / API consumption
    //Declaration	<T> before class/method	? in type usage (List<?>)
    //Control	High — you can use it for variables, return type, parameters	Limited — mainly for method arguments, cannot define new variable of ? type
    //Read/Write	Can read & write	Depends on bounds: extends = read-only, super = write-only
}
