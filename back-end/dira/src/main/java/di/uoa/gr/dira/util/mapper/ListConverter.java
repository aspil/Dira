package di.uoa.gr.dira.util.mapper;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;

import java.lang.reflect.Type;
import java.util.List;

public class ListConverter<TSource, TDest> extends AbstractConverter<List<TSource>, List<TDest>> {
    private final ModelMapper mapper;
    private final Type destType;

    public ListConverter(ModelMapper mapper, Type destType) {
        this.mapper = mapper;
        this.destType = destType;
    }

    public static <TSource, TDest> ListConverter<TSource, TDest> withMapper(ModelMapper mapper, Type destType) {
        return new ListConverter<>(mapper, destType);
    }

    @Override
    protected List<TDest> convert(List<TSource> source) {
        return MapperHelper.mapList(mapper, source, destType);
    }
}
