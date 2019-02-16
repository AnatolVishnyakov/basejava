import com.basejava.webapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    private static Resume resume = new Resume();

    private static void testChangeFinalField() throws IllegalAccessException {
        System.out.println("\ntest change final field");
        Field field = resume.getClass().getDeclaredFields()[0];
        System.out.println("field name: " + field.getName());
        field.setAccessible(true);
        System.out.println("old value: " + field.get(resume));
        field.set(resume, "new_uuid");
        System.out.println("new value: " + resume);

    }

    private static void testCallMethodToString() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        System.out.println("\ntest call method toString()");
        Method method = resume.getClass().getMethod("toString");
        String result = (String) method.invoke(resume);
        System.out.println(result);
    }

    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        testChangeFinalField();
        testCallMethodToString();
    }
}
