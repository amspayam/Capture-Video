package ir.haftsang.moduleui.component.button;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.wang.avi.AVLoadingIndicatorView;

import ir.haftsang.moduleui.R;
import ir.haftsang.moduleui.util.AnimationHelper;
import ir.haftsang.moduleui.util.UnitUtil;

/**
 * Created by p.kokabi on 9/19/2017.
 */

public class HaftButton extends RelativeLayout {

    enum Type {
        DEFAULT,
        DISABLED
    }

    private AppCompatTextView textView;
    private AVLoadingIndicatorView loadingView;
    private String text;
    private Integer buttonBackground = -1;
    private Integer disableBackground = -1;
    private Integer textColor = Color.WHITE;
    private Integer loadingColor = Color.WHITE;
    private Integer paddingLeft = UnitUtil.dpToPx(32);
    private Integer paddingTop = UnitUtil.dpToPx(12);
    private Integer paddingRight = UnitUtil.dpToPx(32);
    private Integer paddingBottom = UnitUtil.dpToPx(12);
    private Integer textStyle = Typeface.NORMAL;
    private float textSize = getResources().getDimension(R.dimen.titleFontSize);
    private boolean automaticLoading;
    private boolean isButtonEnabled = true;
    private boolean isFull;

    public HaftButton(Context context) {
        super(context);
        init();
    }

    public HaftButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttributes(context, attrs);
        init();
    }

    public HaftButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setAttributes(context, attrs);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public HaftButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setAttributes(context, attrs);
        init();
    }

    private void setAttributes(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HaftButton);
        try {
            text = typedArray.getString(R.styleable.HaftButton_android_text);

            buttonBackground = typedArray.getResourceId(R.styleable.HaftButton_android_background, buttonBackground);
            disableBackground = typedArray.getResourceId(R.styleable.HaftButton_disableBackground, disableBackground);

            textColor = typedArray.getColor(R.styleable.HaftButton_android_textColor, textColor);
            loadingColor = typedArray.getColor(R.styleable.HaftButton_loadingColor, loadingColor);

            automaticLoading = typedArray.getBoolean(R.styleable.HaftButton_automaticLoading, automaticLoading);
            isButtonEnabled = typedArray.getBoolean(R.styleable.HaftButton_android_enabled, isButtonEnabled);
            isFull = typedArray.getBoolean(R.styleable.HaftButton_isFull, isFull);

            textSize = typedArray.getDimensionPixelSize(R.styleable.HaftButton_android_textSize, (int) textSize);
            paddingLeft = typedArray.getDimensionPixelSize(R.styleable.HaftButton_paddingLeft, paddingLeft);
            paddingTop = typedArray.getDimensionPixelSize(R.styleable.HaftButton_paddingTop, paddingTop);
            paddingRight = typedArray.getDimensionPixelSize(R.styleable.HaftButton_paddingRight, paddingRight);
            paddingBottom = typedArray.getDimensionPixelSize(R.styleable.HaftButton_paddingBottom, paddingBottom);

            textStyle = typedArray.getInt(R.styleable.HaftButton_android_textStyle, textStyle);

        } finally {
            typedArray.recycle();
        }
    }

    private void init() {

        initTextView();
        initLoadingView();

        setGravity(Gravity.CENTER);
        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        setBtnEnabled(isButtonEnabled);

        addView(textView);
        addView(loadingView);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Adjust width as necessary
        int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureMode = MeasureSpec.getMode(widthMeasureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            if (!isFull) {
                int maxBoundedWidth = UnitUtil.dpToPx(320);
                if (maxBoundedWidth < measuredWidth)
                    widthMeasureSpec = MeasureSpec.makeMeasureSpec(maxBoundedWidth, measureMode);
            }
        } else {
            if (specMode == MeasureSpec.AT_MOST) {
                textView.measure(0, 0);
                int width = textView.getMeasuredWidth() + paddingRight + paddingLeft;
                widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, measureMode);
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean isInEditMode() {
        return super.isInEditMode();
    }

    private void setHaftBackground(Type type) {
        /*Set Background for EditText*/
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(UnitUtil.dpToPx(48));

        switch (type) {
            case DEFAULT:
                drawable.setColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                if (buttonBackground != -1)
                    setBackgroundResource(buttonBackground);
                else
                    setBackground(drawable);
                break;
            case DISABLED:
                if (disableBackground != -1)
                    setBackgroundResource(disableBackground);
                else {
                    drawable.setColor(ContextCompat.getColor(getContext(), R.color.disableColor));
                    setBackground(drawable);
                }
                break;
        }
    }

    /*Setters=====================================================================================*/

    public void setText(String text) {
        this.text = text;
        textView.setText(text);
    }

    public void setTextColor(int colorFilter) {
        textView.setTextColor(colorFilter);
    }

    /*Methods=====================================================================================*/

    private void initLoadingView() {
        loadingView = new AVLoadingIndicatorView(getContext());
        LayoutParams params = new LayoutParams(UnitUtil.dpToPx(24), UnitUtil.dpToPx(24));
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        loadingView.setVisibility(GONE);
        loadingView.setIndicatorColor(loadingColor);
        if (automaticLoading) {
            textView.setAlpha(0);
            startBtnLoading();
        }
        loadingView.setLayoutParams(params);
    }

    private void initTextView() {
        textView = new AppCompatTextView(getContext());
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        if (!isInEditMode()) {
            Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.iran_sans);
            textView.setTypeface(typeface, textStyle);
        }
        textView.setText(text);
        textView.setTextColor(textColor);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        textView.setLayoutParams(params);
    }

    public void setBtnEnabled(boolean enabled) {
        if (enabled) {
            setHaftBackground(Type.DEFAULT);
            textView.setTextColor(textColor);
        } else {
            setHaftBackground(Type.DISABLED);
            textView.setTextColor(Color.WHITE);
        }
        setEnabled(enabled);
    }

    public void startBtnLoading() {
        if (textView.getVisibility() == VISIBLE) {
            textView.startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out));
            textView.setVisibility(INVISIBLE);
            textView.setEnabled(false);
            setEnabled(false);
            postDelayed(loadingDelayedShow, AnimationHelper.SHORT_DELAY);
        }
    }

    public void stopBtnLoading() {
        if (textView.getVisibility() != VISIBLE)
            postDelayed(allViewDelayShow, AnimationHelper.SHORT_DELAY);
    }

    private Runnable loadingDelayedShow = new Runnable() {

        @Override
        public void run() {
            loadingView.smoothToShow();
            removeCallbacks(loadingDelayedShow);
        }
    };

    private Runnable buttonViewDelayedShow = new Runnable() {

        @Override
        public void run() {
            textView.startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in));
            textView.setAlpha(1);
            textView.setAlpha(1);
            textView.setVisibility(VISIBLE);
            textView.setEnabled(true);
            removeCallbacks(buttonViewDelayedShow);
        }
    };

    private Runnable allViewDelayShow = new Runnable() {
        @Override
        public void run() {
            loadingView.smoothToHide();
            postDelayed(buttonViewDelayedShow, AnimationHelper.SHORT_DELAY);
            setEnabled(true);
        }
    };

}
