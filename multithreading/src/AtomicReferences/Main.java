package AtomicReferences;

import java.util.concurrent.atomic.AtomicReference;

//working with compareAndSet(..)
public class Main {
    public static void main(String[] args) {
        String oldName = "oldName";
        String newName = "newName";
        AtomicReference<String> atomicReference = new AtomicReference<>(oldName);

        atomicReference.set("Unexpected name");
        if(atomicReference.compareAndSet(oldName, newName)) {
            System.out.println("New Value is " + atomicReference.get());
        } else {
            System.out.println("Nothing has changed");
        }

    }
}
