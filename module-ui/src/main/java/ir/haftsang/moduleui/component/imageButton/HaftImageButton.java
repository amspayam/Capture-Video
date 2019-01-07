package ir.haftsang.moduleui.component.imageButton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.wang.avi.AVLoadingIndicatorView;

import ir.haftsang.moduleui.R;
import ir.haftsang.moduleui.util.AnimationHelper;
import ir.haftsang.moduleui.util.UnitUtil;


/**
 * Created by p.kokabi on 9/19/2017.
 */

public class HaftImageButton extends LinearLayout {

    private final static int RIGHT = 1;
    private final static int LEFT = 2;
    private final static int TOP = 3;
    private final static int BOTTOM = 4;

    private RelativeLayout relativeLayout;
    private AppCompatImageView imageView;
    private AVLoadingIndicatorView loadingView;
    private AppCompatTextView textView;
    private String text;
    private Integer icon = 0;
    private Integer textColor = Color.WHITE;
    private Integer tintColor = 0;
    private Integer iconPosition = RIGHT;
    private Integer marginIcon = UnitUtil.dpToPx(4);
    private Integer textStyle = Typeface.NORMAL;
    private Integer gravity = Gravity.START;
    private float textSize = getResources().getDimension(R.dimen.titleFontSize);
    private Drawable bg;


    public HaftImageButton(Context context) {
        super(context);
        init();
    }

    public HaftImageButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttributes(context, attrs);
        init();
    }

    public HaftImageButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setAttributes(context, attrs);
        init();
    }

    private void setAttributes(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HaftImageButton);
        try {
            text = typedArray.getString(R.styleable.HaftImageButton_android_text);

            textColor = typedArray.getColor(R.styleable.HaftImageButton_android_textColor, ContextCompat.getColor(context, R.color.textColorPrimaryDark));
            tintColor = typedArray.getColor(R.styleable.HaftImageButton_android_tint, tintColor);

            icon = typedArray.getResourceId(R.styleable.HaftImageButton_icon, icon);

            textSize = typedArray.getDimensionPixelSize(R.styleable.HaftImageButton_android_textSize, (int) textSize);
            marginIcon = typedArray.getDimensionPixelSize(R.styleable.HaftImageButton_marginIcon, marginIcon);

            iconPosition = typedArray.getInteger(R.styleable.HaftImageButton_iconPosition, 0);
            textStyle = typedArray.getInteger(R.styleable.HaftImageButton_android_textStyle, textStyle);
            gravity = typedArray.getInteger(R.styleable.HaftImageButton_android_gravity, gravity);

        } finally {
            typedArray.recycle();
        }
    }

    public void init() {

        relativeLayout = new RelativeLayout(getContext());
        imageView = new AppCompatImageView(getContext());
        loadingView = new AVLoadingIndicatorView(getContext());
        textView = new AppCompatTextView(getContext());

        initImageView();
        initLoadingView();
        initTextView();
        showIconPosition();

    }

    @Override
    public boolean isInEditMode() {
        return super.isInEditMode();
    }

    private void showIconPosition() {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        switch (iconPosition) {
            case RIGHT:
                setGravity(Gravity.CENTER);
                setOrientation(HORIZONTAL);
                addView(relativeLayout);
                addView(textView);
                params.setMargins(0, 0, marginIcon, 0);
                relativeParams.addRule(RelativeLayout.CENTER_VERTICAL);
                break;
            case LEFT:
                setGravity(Gravity.CENTER);
                setOrientation(HORIZONTAL);
                addView(textView);
                addView(relativeLayout);
                params.setMargins(marginIcon, 0, 0, 0);
                relativeParams.addRule(RelativeLayout.CENTER_VERTICAL);
                break;
            case TOP:
                setGravity(Gravity.CENTER);
                setOrientation(VERTICAL);
                addView(relativeLayout);
                addView(textView);
                params.setMargins(0, marginIcon, 0, 0);
                relativeParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                break;
            case BOTTOM:
                setGravity(Gravity.CENTER);
                setOrientation(VERTICAL);
                addView(textView);
                addView(relativeLayout);
                params.setMargins(0, 0, 0, marginIcon);
                relativeParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                break;
            default:
                setGravity(Gravity.CENTER);
                setOrientation(HORIZONTAL);
                addView(relativeLayout);
                relativeParams.addRule(RelativeLayout.CENTER_VERTICAL);
        }
        loadingView.setLayoutParams(relativeParams);
        imageView.setLayoutParams(relativeParams);
        textView.setLayoutParams(params);
    }

    /*Setters=====================================================================================*/

    public void setText(String text) {
        this.text = text;
        textView.setText(text);
    }

    public void setIcon(int icon) {
        this.icon = icon;
        imageView.setImageResource(icon);
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        textView.setTextColor(textColor);
        loadingView.setIndicatorColor(textColor);
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
    }

    public void setColorFilter(int colorFilter) {
        tintColor = colorFilter;
        imageView.setColorFilter(tintColor);
        setTextColor(colorFilter);
    }

    /*Getters=====================================================================================*/

    public AppCompatImageView getImageView() {
        return imageView;
    }

    public String getText() {
        return text;
    }

    /*Behavior====================================================================================*/

    private void initImageView() {

        imageView.setImageResource(icon);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        if (tintColor != 0)
            imageView.setColorFilter(tintColor);
        relativeLayout.addView(imageView);
    }

    private void initLoadingView() {
        loadingView = new AVLoadingIndicatorView(getContext());
        loadingView.setVisibility(GONE);
        loadingView.setIndicatorColor(textColor);
        relativeLayout.addView(loadingView);
    }

    private void initTextView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            textView.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);

        textView.setText(text);
        textView.setTextColor(textColor);
        textView.setGravity(gravity);
        textView.setTextDirection(TEXT_DIRECTION_ANY_RTL);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        if (!isInEditMode()) {
            Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.iran_sans);
            textView.setTypeface(typeface, textStyle);
        }
    }

    /*Loading Behavior============================================================================*/

    public void startLoading() {
        if (imageView.getVisibility() == VISIBLE) {
            bg = getBackground();
            setBackgroundResource(0);
            imageView.startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out));
            imageView.setVisibility(INVISIBLE);
            imageView.setEnabled(false);
            setEnabled(false);
            postDelayed(loadingDelayedShow, AnimationHelper.SHORT_DELAY);
        }
    }

    public void stopLoading() {
        if (imageView.getVisibility() != VISIBLE)
            postDelayed(allViewDelayShow, AnimationHelper.SHORT_DELAY);
    }

    private Runnable loadingDelayedShow = new Runnable() {

        @Override
        public void run() {
            loadingView.smoothToShow();
            removeCallbacks(loadingDelayedShow);
        }
    };

    private Runnable viewDelayedShow = new Runnable() {

        @Override
        public void run() {
            imageView.startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in));
            imageView.setVisibility(VISIBLE);
            imageView.setEnabled(true);
            setBackground(bg);
            removeCallbacks(viewDelayedShow);
        }
    };


    private Runnable allViewDelayShow = new Runnable() {
        @Override
        public void run() {
            loadingView.smoothToHide();
            postDelayed(viewDelayedShow, AnimationHelper.SHORT_DELAY);
            setEnabled(true);
        }
    };

}
