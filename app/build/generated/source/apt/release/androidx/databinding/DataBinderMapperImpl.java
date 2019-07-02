package androidx.databinding;

public class DataBinderMapperImpl extends MergedDataBinderMapper {
  DataBinderMapperImpl() {
    addMapper(new id.amoled.timerapp.DataBinderMapperImpl());
  }
}
