import lombok.Data;

import java.io.Serializable;

/**
 * created by Aylan
 * on 2020/11/18 3:27 下午
 */
@Data
public class User implements Serializable {

    private String name;

    private String age;

}
