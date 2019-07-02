package id.amoled.timerapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.airbnb.lottie.LottieAnimationView;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public abstract class ContentMain2Binding extends ViewDataBinding {
  @NonNull
  public final LottieAnimationView animationKerja;

  @NonNull
  public final MaterialProgressBar progressCountdown;

  @NonNull
  public final TextView tvCountdown;

  @NonNull
  public final TextView tvTekanPlay;

  protected ContentMain2Binding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, LottieAnimationView animationKerja,
      MaterialProgressBar progressCountdown, TextView tvCountdown, TextView tvTekanPlay) {
    super(_bindingComponent, _root, _localFieldCount);
    this.animationKerja = animationKerja;
    this.progressCountdown = progressCountdown;
    this.tvCountdown = tvCountdown;
    this.tvTekanPlay = tvTekanPlay;
  }

  @NonNull
  public static ContentMain2Binding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ContentMain2Binding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ContentMain2Binding>inflate(inflater, id.amoled.timerapp.R.layout.content_main2, root, attachToRoot, component);
  }

  @NonNull
  public static ContentMain2Binding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ContentMain2Binding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ContentMain2Binding>inflate(inflater, id.amoled.timerapp.R.layout.content_main2, null, false, component);
  }

  public static ContentMain2Binding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static ContentMain2Binding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (ContentMain2Binding)bind(component, view, id.amoled.timerapp.R.layout.content_main2);
  }
}
