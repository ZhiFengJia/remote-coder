import java.lang.management.*;
import java.util.Arrays;

/**
 * 远程查看服务的各种指标
 *
 * @author jiazhifeng
 * @date 2021/12/07 10:20
 */
public class RemoteMonitor {

    public static void main(String[] args) {
        System.out.println(RemoteMonitor.class.getClassLoader());
        // 监控服务的各种指标
        System.out.println("内存占用");
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        System.out.println(memoryMXBean.getHeapMemoryUsage());
        // 监控服务的垃圾收集
        System.out.println("垃圾收集信息");
        Runtime.getRuntime().gc();
        for (GarbageCollectorMXBean garbageCollectorMXBean : ManagementFactory.getGarbageCollectorMXBeans()) {
            System.out.println("name:" + garbageCollectorMXBean.getName() + " count:" +
                    garbageCollectorMXBean.getCollectionCount() + " time:" +
                    garbageCollectorMXBean.getCollectionTime());
        }
        // 监控服务的class加载信息
        System.out.println("类加载信息");
        ClassLoadingMXBean classLoadingMXBean = ManagementFactory.getClassLoadingMXBean();
        System.out.println("class加载:" + classLoadingMXBean.getLoadedClassCount() +
                " class卸载:" + classLoadingMXBean.getUnloadedClassCount() +
                " class总加载:" + classLoadingMXBean.getTotalLoadedClassCount());
        // 监控服务的线程信息
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        System.out.println("线程数:" + threadMXBean.getThreadCount());
        Arrays.stream(threadMXBean.dumpAllThreads(true, true)).forEach(System.out::println);
    }
}