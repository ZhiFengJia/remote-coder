/**
 * 查看远程编译代码的"ClassLoader"类
 *
 * @author jiazhifeng
 * @date 2021/12/07 10:15
 */
public class RemoteClassLoader {
    public static void main(String[] args) {
        System.out.println(RemoteClassLoader.class.getClassLoader());
    }
}
