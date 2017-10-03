package cn.succy.aop.util;

/**
 * @author Succy
 * @date 2017-10-02 20:10
 **/

public final class ArrayUtil {
    private static final int INDEX_NOT_FOUND = -1;

    public static boolean contains(Class<?>[] array, Class<?> value) {
        return indexOf(array, value) > INDEX_NOT_FOUND;
    }

    private static int indexOf(Class<?>[] array, Class<?> value) {
        for (int i = 0; i < array.length; i++) {
            if (value == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }
}
