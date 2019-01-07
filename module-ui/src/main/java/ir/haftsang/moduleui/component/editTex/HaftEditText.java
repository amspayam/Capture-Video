package ir.haftsang.moduleui.component.editTex;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Field;

import ir.haftsang.moduleui.R;
import ir.haftsang.moduleui.util.UnitUtil;

public class HaftEditText extends LinearLayout implements TextWatcher, View.OnClickListener {

    enum Type {
        DEFAULT,
        ERROR,
        DISABLED
    }

    private final static int IMAGE_ID_LEFT = 1;
    private final static int IMAGE_ID_RIGHT = 2;

    private IHaftEditTextListener iHaftEditTextListener;

    private RelativeLayout relativeLayout = new RelativeLayout(getContext());
    private AppCompatEditText editText = new AppCompatEditText(getContext());
    private AppCompatImageView imageViewLeft = new AppCompatImageView(getContext());
    private AppCompatImageView imageViewRight = new AppCompatImageView(getContext());
    private AppCompatTextView errorTv = new AppCompatTextView(getContext());
    private String text;
    private String hint;
    private Integer textColor = -1;
    private Integer textColorHint = -1;
    private Integer inputType = InputType.TYPE_CLASS_TEXT;
    private Integer maxLength = 100;
    private Integer gravity = Gravity.CENTER;
    private Integer lines = 1;
    private Integer maxLines = 1;
    private Integer imeOptions = EditorInfo.IME_ACTION_NEXT;
    private Integer imageLeft = 0;
    private Integer imageRight = 0;
    private Typeface typeface;
    private boolean isEnabled = true;
    private boolean hasError = true;
    private boolean isPassShow;
    private float textSize = getResources().getDimension(R.dimen.titleFontSize);
    private Integer paddingRight = UnitUtil.dpToPx(14);
    private Integer paddingLeft = UnitUtil.dpToPx(14);
    private Integer paddingTop = UnitUtil.dpToPx(14);
    private Integer paddingBottom = UnitUtil.dpToPx(14);

    public HaftEditText(Context context) {
        super(context);
        init();
    }

    public HaftEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setAttributes(context, attrs);
        init();
    }

    public HaftEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttributes(context, attrs);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public HaftEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setAttributes(context, attrs);
        init();
    }

    private void setAttributes(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HaftEditText);
        try {
            text = typedArray.getString(R.styleable.HaftEditText_android_text);
            hint = typedArray.getString(R.styleable.HaftEditText_android_hint);

            textColor = typedArray.getColor(R.styleable.HaftEditText_android_textColor, textColor);
            textColorHint = typedArray.getColor(R.styleable.HaftEditText_android_textColorHint, textColorHint);

            imageLeft = typedArray.getResourceId(R.styleable.HaftEditText_imageLeft, imageLeft);
            imageRight = typedArray.getResourceId(R.styleable.HaftEditText_imageRight, imageRight);

            textSize = typedArray.getDimensionPixelSize(R.styleable.HaftEditText_android_textSize, (int) textSize);

            inputType = typedArray.getInt(R.styleable.HaftEditText_android_inputType, inputType);
            imeOptions = typedArray.getInt(R.styleable.HaftEditText_android_imeOptions, imeOptions);
            gravity = typedArray.getInt(R.styleable.HaftEditText_android_gravity, gravity);
            lines = typedArray.getInt(R.styleable.HaftEditText_android_lines, lines);
            maxLines = typedArray.getInt(R.styleable.HaftEditText_android_lines, maxLines);
            maxLength = typedArray.getInt(R.styleable.HaftEditText_android_maxLength, maxLength);

            isEnabled = typedArray.getBoolean(R.styleable.HaftEditText_android_enabled, isEnabled);
            hasError = typedArray.getBoolean(R.styleable.HaftEditText_hasError, hasError);

        } finally {
            typedArray.recycle();
        }
    }

    public void init() {

        if (!isInEditMode())
            typeface = ResourcesCompat.getFont(getContext(), R.font.iran_sans);

        setOrientation(LinearLayout.VERTICAL);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        initEditText();
        initImageViewLeft();
        initImageViewRight();
        initError();

        /*Add Views*/
        relativeLayout.addView(editText);

        if (imageLeft != 0)
            relativeLayout.addView(imageViewLeft);

        if (imageRight != 0)
            relativeLayout.addView(imageViewRight);

        setClipChildren(false);
        addView(relativeLayout);
        if (hasError)
            addView(errorTv);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Adjust width as necessary
        int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        int mBoundedWidth = UnitUtil.dpToPx(320);
        if (mBoundedWidth < measuredWidth) {
            int measureMode = MeasureSpec.getMode(widthMeasureSpec);
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(mBoundedWidth, measureMode);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean isInEditMode() {
        return super.isInEditMode();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (inputType == InputType.TYPE_CLASS_PHONE && charSequence.toString().length() > 0) {
            if (!charSequence.toString().startsWith("0")) {
                setText(String.valueOf("0" + getText()));
                editText.setSelection(getLength());
            }
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
        clearError();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case IMAGE_ID_LEFT:
                if (editText.getInputType() == 129)
                    if (isPassShow) {
                        isPassShow = false;
                        editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        editText.setSelection(getLength());
                    } else {
                        isPassShow = true;
                        editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        editText.setSelection(getLength());
                    }
                if (iHaftEditTextListener != null)
                    iHaftEditTextListener.leftIconListener();
                break;
            case IMAGE_ID_RIGHT:
                iHaftEditTextListener.rightIconListener();
                break;
        }
    }

    /*Setters=====================================================================================*/

    public void setText(String text) {
        this.text = text;
        editText.setText(text);
    }

    public void setHint(String hint) {
        this.hint = hint;
        editText.setHint(hint);

        clearError();
    }

    public void setError(String error) {
        if (hasError) {
            editText.setTextColor(getTextColor(Type.ERROR));
            editText.setHintTextColor(getTextColorHint(Type.ERROR));
            editText.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
            relativeLayout.setBackground(getBackgroundEdt(Type.ERROR));

            errorTv.setText(error);
            errorTv.setPadding(UnitUtil.dpToPx(8), 0, UnitUtil.dpToPx(8), 0);
        }
    }

    public void setIconListener(IHaftEditTextListener iHaftEditTextListener) {
        this.iHaftEditTextListener = iHaftEditTextListener;
    }

    /*Getters=====================================================================================*/

    public AppCompatEditText getEditText() {
        return editText;
    }

    public ImageView getRightIcon() {
        return imageViewRight;
    }

    public ImageView getLeftIcon() {
        return imageViewLeft;
    }

    public String getText() {
        return editText.getText().toString();
    }

    public Integer getLength() {
        return editText.getText().toString().trim().length();
    }

    public boolean isEmpty() {
        return editText.getText().toString().trim().isEmpty();
    }

    /*Methods=====================================================================================*/

    private void initEditText() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT
                , RelativeLayout.LayoutParams.WRAP_CONTENT);
        editText.setLayoutParams(params);
        editText.setText(text);
        editText.setTextColor(textColor);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        editText.setHint(hint);
        editText.setHintTextColor(textColorHint);
        editText.setInputType(inputType);
        editText.setImeOptions(imeOptions);
        editText.setGravity(gravity);
        editText.setLines(lines);
        editText.setMaxLines(maxLines);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        editText.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        editText.setBackgroundColor(Color.TRANSPARENT);
        if (!isInEditMode())
            editText.setTypeface(typeface);
        editText.setTextDirection(TEXT_DIRECTION_ANY_RTL);
        editText.addTextChangedListener(this);
        editText.measure(0, 0);

        if (imageLeft != 0)
            params.addRule(RelativeLayout.START_OF, IMAGE_ID_LEFT);

        if (imageRight != 0)
            params.addRule(RelativeLayout.END_OF, IMAGE_ID_RIGHT);

        if (inputType == InputType.TYPE_CLASS_PHONE)
            editText.setKeyListener(DigitsKeyListener.getInstance("0123456789"));

        if (!isEnabled) {
            editText.setEnabled(false);
            relativeLayout.setBackground(getBackgroundEdt(Type.DISABLED));
        } else
            relativeLayout.setBackground(getBackgroundEdt(Type.DEFAULT));
        editText.setLayoutParams(params);
    }

    private void initImageViewLeft() {
        if (imageLeft != 0) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT
                    , RelativeLayout.LayoutParams.WRAP_CONTENT);

            imageViewLeft.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageViewLeft.setPadding(UnitUtil.dpToPx(4), UnitUtil.dpToPx(4), UnitUtil.dpToPx(4), UnitUtil.dpToPx(4));
            imageViewLeft.measure(0, 0);
            imageViewLeft.setId(IMAGE_ID_LEFT);

            if (imageLeft != 0) {
                params.addRule(RelativeLayout.ALIGN_PARENT_END);
                params.addRule(RelativeLayout.CENTER_VERTICAL);
                params.setMarginEnd(paddingLeft);
                imageViewLeft.setImageResource(imageLeft);
            }

            imageViewLeft.setOnClickListener(this);
            imageViewLeft.setLayoutParams(params);
        }
    }

    private void initImageViewRight() {
        if (imageRight != 0) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT
                    , RelativeLayout.LayoutParams.WRAP_CONTENT);

            imageViewRight.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageViewRight.setPadding(UnitUtil.dpToPx(4), UnitUtil.dpToPx(4), UnitUtil.dpToPx(4), UnitUtil.dpToPx(4));
            imageViewRight.measure(0, 0);
            imageViewRight.setId(IMAGE_ID_RIGHT);

            if (imageRight != 0) {
                params.addRule(RelativeLayout.ALIGN_PARENT_START);
                params.addRule(RelativeLayout.CENTER_VERTICAL);
                params.setMarginStart(paddingRight);
                imageViewRight.setImageResource(imageRight);
            }
            imageViewRight.setOnClickListener(this);
            imageViewRight.setLayoutParams(params);
        }
    }

    private void initError() {
        errorTv.setTextColor(ContextCompat.getColor(getContext(), R.color.red));
        errorTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        if (!isInEditMode())
            errorTv.setTypeface(typeface);
        errorTv.measure(0, 0);
    }

    private Drawable getBackgroundEdt(Type type) {
        /*Set Background for EditText*/
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        switch (type) {
            case DEFAULT:
                drawable.setStroke(UnitUtil.dpToPx(1), ContextCompat.getColor(getContext(), R.color.black));
                setCursorColor(R.color.black);
                break;
            case ERROR:
                drawable.setStroke(UnitUtil.dpToPx(1), ContextCompat.getColor(getContext(), R.color.red));
                setCursorColor(R.color.red);
                break;
            case DISABLED:
                drawable.setStroke(UnitUtil.dpToPx(1), ContextCompat.getColor(getContext(), R.color.disableColor));
                break;
        }
        drawable.setCornerRadius(UnitUtil.dpToPx(8));
        drawable.setColor(Color.TRANSPARENT);
        return drawable;
    }

    public Integer getTextColor(Type theme) {
        switch (theme) {
            case DEFAULT:
                return ContextCompat.getColor(getContext(), R.color.textColorPrimaryDark);
            case ERROR:
                return ContextCompat.getColor(getContext(), R.color.red);
            case DISABLED:
                return ContextCompat.getColor(getContext(), R.color.disableColor);
            default:
                return ContextCompat.getColor(getContext(), R.color.textColorPrimaryDark);
        }
    }

    public Integer getTextColorHint(Type theme) {
        switch (theme) {
            case DEFAULT:
                return ContextCompat.getColor(getContext(), R.color.textColorSecondaryDark);
            case ERROR:
                return ContextCompat.getColor(getContext(), R.color.red);
            case DISABLED:
                return ContextCompat.getColor(getContext(), R.color.disableColor);
            default:
                return ContextCompat.getColor(getContext(), R.color.textColorSecondaryDark);
        }
    }

    private void setCursorColor(int color) {
        try {
            // Get the cursor resource id
            Field field = TextView.class.getDeclaredField("mCursorDrawableRes");
            field.setAccessible(true);
            int drawableResId = field.getInt(editText);

            // Get the editor
            field = TextView.class.getDeclaredField("mEditor");
            field.setAccessible(true);
            Object editor = field.get(editText);

            // Get the drawable and set a color filter
            Drawable drawable = ContextCompat.getDrawable(editText.getContext(), drawableResId);
            drawable.setColorFilter(ContextCompat.getColor(getContext(), color), PorterDuff.Mode.SRC_IN);
            Drawable[] drawables = {drawable, drawable};

            // Set the drawables
            field = editor.getClass().getDeclaredField("mCursorDrawable");
            field.setAccessible(true);
            field.set(editor, drawables);
        } catch (Exception ignored) {
        }
    }

    public void clearInput() {
        editText.setText("");

        clearError();
    }

    public void clearError() {
        if (hasError) {
            editText.setTextColor(getTextColor(Type.DEFAULT));
            editText.setHintTextColor(getTextColorHint(Type.DEFAULT));
            editText.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
            relativeLayout.setBackground(getBackgroundEdt(Type.DEFAULT));

            errorTv.setText("");
            errorTv.setPadding(UnitUtil.dpToPx(8), 0, UnitUtil.dpToPx(8), 0);
        }
    }

}
