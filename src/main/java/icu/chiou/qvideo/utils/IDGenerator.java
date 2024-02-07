package icu.chiou.qvideo.utils;

/**
 * <p>
 * id生成器
 * </p>
 *
 * @author red-velvet
 * @since 2024/2/7
 */
public class IDGenerator {
    // 距离自己出生时间开始的时间戳
    private static final long TIME_STAMP = DateUtils.get("2000-07-22").getTime();
    // 机房和机器号的位数和最大值

    private static final long DATA_CENTER_BIT = 5L;

    private static final long MACHINE_BIT = 5L;
    private static final long DATA_CENTER_MAX = ~(-1L << DATA_CENTER_BIT);
    private static final long MACHINE_MAX = ~(-1L << MACHINE_BIT);

    // 时间戳和序列号的位数和最大值
    private static final long TIMESTAMP_BIT = 42L;
    private static final long SEQUENCE_BIT = 12L;
    private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);

    // 左移位数
    private static final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BIT + MACHINE_BIT + DATA_CENTER_BIT;
    private static final long DATA_CENTER_LEFT_SHIFT = SEQUENCE_BIT + MACHINE_BIT;
    private static final long MACHINE_LEFT_SHIFT = SEQUENCE_BIT;

    // 机器节点ID和数据中心ID
    private long machineId = 0; // 机器号，根据实际情况设置，范围为0-31
    private long dataCenterId = 0; // 机房号，根据实际情况设置，范围为0-31

    // 序列号，每毫秒生成4096个不同的ID
    private static long sequenceId = 0L;

    // 上次生成ID的时间戳
    private static long lastTimestamp = -1L;

    /**
     * 构造函数，创建一个ID生成器实例
     *
     * @param machineId    机器号，范围为0-31
     * @param dataCenterId 机房号，范围为0-31
     * @throws IllegalArgumentException 如果传入的数据中心编号或机器号超出合法范围
     */
    public IDGenerator(long machineId, long dataCenterId) {
        if (dataCenterId > DATA_CENTER_MAX || machineId > MACHINE_MAX) {
            throw new IllegalArgumentException("您传入的数据中心编号和机器号不合法");
        }
        this.machineId = machineId;
        this.dataCenterId = dataCenterId;
    }

    /**
     * 生成全局唯一ID
     *
     * @return 64位全局唯一ID
     * @throws RuntimeException 如果服务器时钟发生回拨
     */
    public long generateId() {
        // 1.设置自定义的时间戳
        long currTimeStamp = System.currentTimeMillis();
        long myTimeStamp = currTimeStamp - TIME_STAMP;

        // 检查时钟回拨问题
        if (myTimeStamp < lastTimestamp) {
            throw new RuntimeException("Your server clock moved backwards. Refusing to generate id.");
        }

        // 2.处理序列号，如果是同一时间节点，需要自增
        if (myTimeStamp == lastTimestamp) {
            // 同一毫秒内生成多个ID，需要增加序列号，确保不重复
            // 使用了位运算，避免了在高并发场景下对AtomicLong进行原子操作的开销
            // 使用++操作来增加序列号的方式在多线程环境下可能会导致争抢问题
            sequenceId = (sequenceId + 1) & MAX_SEQUENCE;
            if (sequenceId == 0) {
                // 当前毫秒内序列号用尽，等待下一毫秒
                myTimeStamp = getNextMillis(lastTimestamp);
            }
        } else {
            sequenceId = 0L;
        }

        // 3.执行结束，保存上一次时间
        lastTimestamp = myTimeStamp;


        // 返回组合后的ID
        return myTimeStamp << TIMESTAMP_LEFT_SHIFT |
                dataCenterId << DATA_CENTER_LEFT_SHIFT |
                machineId << MACHINE_LEFT_SHIFT |
                sequenceId;
    }

    /**
     * 等待下一毫秒的方法
     *
     * @param lastTimestamp 上次生成ID的时间戳
     * @return 下一毫秒的时间戳
     */
    private static long getNextMillis(long lastTimestamp) {
        long myTimestamp = System.currentTimeMillis() - TIME_STAMP;
        // 如果还不当前小于或等于，就继续等待
        while (myTimestamp <= lastTimestamp) {
            myTimestamp = System.currentTimeMillis() - TIME_STAMP;
        }
        return myTimestamp;
    }

    public static void main(String[] args) {
        // 测试生成1000个ID
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                System.out.println("Generated ID: " + new IDGenerator(1, 1).generateId());
            }).start();
        }
    }
}
