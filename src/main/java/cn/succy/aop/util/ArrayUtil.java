package cn.succy.aop.util;

/**
 * 数据工具类
 *
 * @author Succy
 * @date 2017-10-02 20:10
 **/

public final class ArrayUtil {
    private static final int INDEX_NOT_FOUND = -1;

    public static <T> boolean contains(T[] array, T value) {
        return indexOf(array, value) > INDEX_NOT_FOUND;
    }

    private static <T> int indexOf(T[] array, T value) {
        for (int i = 0; i < array.length; i++) {
            if (equal(value, array[i])) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    private static boolean equal(Object obj1, Object obj2) {
        return (obj1 != null) ? (obj1.equals(obj2)) : (obj2 == null);
    }
}
