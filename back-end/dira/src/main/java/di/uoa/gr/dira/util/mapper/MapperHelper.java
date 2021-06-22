package di.uoa.gr.dira.util.mapper;

import org.modelmapper.ModelMapper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MapperHelper {
    public static <TSource, TTarget> List<TTarget> mapList(ModelMapper mapper, List<TSource> source, Type targetType) {
        ListParameterizedType tokenType = new ListParameterizedType(targetType);
        return source != null ? mapper.map(source, tokenType) : null;
    }

    private static class ListParameterizedType implements ParameterizedType {
        private final Type type;

        private ListParameterizedType(Type type) {
            this.type = type;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{type};
        }

        @Override
        public Type getRawType() {
            return ArrayList.class;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ListParameterizedType that = (ListParameterizedType) o;
            return Objects.equals(type, that.type);
        }
    }
}
