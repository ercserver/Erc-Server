package db.ohad;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by ohad on 29/5/2015.
 */
public class MainTest {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList();
        numbers.addAll(Arrays.asList(1,2,3,4,5,6,7));
        String str = "hello world";
        System.out.println(numbers.toString().replace('[','(').replace(']',')'));
    }

}
