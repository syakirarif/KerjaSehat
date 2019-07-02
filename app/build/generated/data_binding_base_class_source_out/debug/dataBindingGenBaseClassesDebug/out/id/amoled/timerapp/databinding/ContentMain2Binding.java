package id.amoled.timerapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.airbnb.lottie.LottieAnimationView;
import java.lang.Deprecated;
import java.lang.Object;
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

  protected ContentMain2Binding(Object _bindingComponent, View _root, int _localFieldCount,
      LottieAnimationView animationKerja, MaterialProgressBar progressCountdown,
      TextView tvCountdown, TextView tvTekanPlay) {
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

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.content_main2, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ContentMain2Binding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ContentMain2Binding>inflateInternal(inflater, id.amoled.timerapp.R.layout.content_main2, root, attachToRoot, component);
  }

  @NonNull
  public static ContentMain2Binding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.content_main2, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ContentMain2Binding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ContentMain2Binding>inflateInternal(inflater, id.amoled.timerapp.R.layout.content_main2, null, false, component);
  }

  public static ContentMain2Binding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.bind(view, component)
   */
  @Deprecated
  public static ContentMain2Binding bind(@NonNull View view, @Nullable Object component) {
    return (ContentMain2Binding)bind(component, view, id.amoled.timerapp.R.layout.content_main2);
  }
}
