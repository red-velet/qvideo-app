package icu.chiou.qvideo.utils;

/**
 * <p>
 *
 * </p>
 *
 * @author red-velvet
 * @since 2024/2/7
 */
public class IDGeneratorUtils {
    private static final IDGenerator GENERATOR = new IDGenerator(5, 5);

    private IDGeneratorUtils() {

    }

    public static long getID() {
        return GENERATOR.generateId();
    }
}
