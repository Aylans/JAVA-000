package Week_01;

/**
 * created by Aylan
 * on 2020/10/16 3:54 下午
 */
public class Hello {

    public static void main(String[] args) {
        int a = 100;
        double b = 0.0D;
        for(int i=1; i<=a; i++){
            if( i%2 == 0 ){
                b = b + i;
            }
        }
        System.out.print(b);
    }
}
