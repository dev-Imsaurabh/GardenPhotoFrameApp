package com.mac.gardenphotoframeapp.editorUI;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mac.gardenphotoframeapp.Fragments.Background_Fragment;
import com.mac.gardenphotoframeapp.Fragments.Frame_Fragment;
import com.mac.gardenphotoframeapp.Fragments.Template_Frame_Fragment;
import com.mac.gardenphotoframeapp.R;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Objects;

import yuku.ambilwarna.AmbilWarnaDialog;

public class Editor_Activity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton choose_Frame_Btn, add_text_Btn, choose_Sticker_Btn, saveBtn, delete_element, choose_font_Btn,choose_bg_Btn,add_image;
    private View template, sticker_template;
    //    private FrameLayout template_frame1;
    private String getImage;
    private RelativeLayout panel;
    private ImageView movableImageView;
    private TextView movableTextView;
    private int defaultColor;
    private Bitmap save_bitmap;
    private BitmapDrawable bitmapDrawable;
    private Canvas canvas;
    private String finalColor;
    private Typeface finalFont;
    private ImageView movableSticker,save_imageView,add_imageView;
    private int toggle=0;
    private Dialog dialog,dialog2;
    private int finalID;
    private final  int REQ=1;
    private Bitmap bitmap;
    private String realPath;
    private Uri ImageUri,finalUri;
    private SomeView mSomeView;



    //////////editor start
    float[] lastEvent = null;
    float d = 0f;
    float newRot = 0f;
    private boolean isZoomAndRotate;
    private boolean isOutSide;
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private int mode = NONE;
    private PointF start = new PointF();
    private PointF mid = new PointF();
    float oldDist = 1f;
    private float xCoOrdinate, yCoOrdinate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        SharedPreferences getShared = getSharedPreferences("image", MODE_PRIVATE);
        String newImage = getShared.getString("encoded", "");

        SharedPreferences getShared1 = getSharedPreferences("image_bg2", MODE_PRIVATE);
        String newImage1 = getShared1.getString("encoded", "");


        getImage = getIntent().getStringExtra("image_id");
        String background = getIntent().getStringExtra("background");


        choose_Frame_Btn = findViewById(R.id.choose_frame_Btn);
        add_text_Btn = findViewById(R.id.add_text_Btn);
        choose_Sticker_Btn = findViewById(R.id.choose_sticker_Btn);
        add_imageView=findViewById(R.id.add_imageView);
        choose_bg_Btn = findViewById(R.id.choose_bg_Btn);
        add_image=findViewById(R.id.add_image);
        saveBtn = findViewById(R.id.save_Btn);
        template = findViewById(R.id.template);
        panel = (RelativeLayout) findViewById(R.id.panel);
        movableImageView = findViewById(R.id.movableImageView);
        save_imageView = findViewById(R.id.save_imageView);
        movableSticker=findViewById(R.id.movableSticker);
        delete_element = findViewById(R.id.delete_element);
        choose_font_Btn = findViewById(R.id.choose_font_Btn);

        if(background==null){
            add_image.setVisibility(View.GONE);
            choose_bg_Btn.setVisibility(View.GONE);
        }else{
            choose_Frame_Btn.setVisibility(View.GONE);
        }



        passImage();
        addText();
        save();

        if(background==null){
            if (!newImage.equalsIgnoreCase("")) {
                byte[] b = Base64.decode(newImage, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
                movableImageView.setImageBitmap(bitmap);
            }



        }


            if (!newImage1.equalsIgnoreCase("")) {
                byte[] b = Base64.decode(newImage1, Base64.DEFAULT);
                Bitmap backbitmap = BitmapFactory.decodeByteArray(b, 0, b.length);

                add_imageView = new ImageView(getApplicationContext());
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                lp.addRule(RelativeLayout.BELOW);
                add_imageView.setLayoutParams(lp);
                add_imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                add_imageView.setVisibility(View.VISIBLE);
                panel.addView(add_imageView);
                add_imageView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v2, MotionEvent event) {

                        ImageView v = (ImageView) v2;
                        v.bringToFront();
                        onTouch3(v, event);

                        return true;
                    }
                });
                add_imageView.setImageBitmap(backbitmap);
                getShared1.edit().clear().commit();
            }




        movableImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v2, MotionEvent event) {

                ImageView v = (ImageView) v2;
                onTouch2(v, event);

                return true;
            }
        });
        movableSticker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v2, MotionEvent event) {

                ImageView v = (ImageView) v2;
                onTouch2(v, event);

                return true;
            }
        });

        add_imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v2, MotionEvent event) {

                ImageView v = (ImageView) v2;
                onTouch2(v, event);

                return true;
            }
        });



        startFragmentTransaction();

    }




    private void startFragmentTransaction() {

        choose_Frame_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Frame_Fragment fragment = new Frame_Fragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.template, fragment);
                transaction.commit();
            }
        });

        choose_bg_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Background_Fragment fragment = new Background_Fragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.template, fragment);
                transaction.commit();
            }
        });

        choose_Sticker_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog=new Dialog(Editor_Activity.this);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.sticker_dialog);
                dialog.setCancelable(true);

                findingImages(dialog);




                dialog.show();
            }
        });

        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2=new Dialog(Editor_Activity.this);

                dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog2.setContentView(R.layout.add_image_dialog_layout);
                dialog2.setCancelable(true);
                CardView select_cam = dialog2.findViewById(R.id.select_cam);
                CardView select_gallery = dialog2.findViewById(R.id.select_gallery);

                select_cam.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent,100);

                    }
                });

                select_gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, REQ);


                    }
                });




                dialog2.show();
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            ImageUri = uri;
            try {
                bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(), ImageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            sendImage(bitmap);





        }

        try {
            if(requestCode==100){
                Bitmap bitmapCam = (Bitmap) data.getExtras().get("data");
                Uri uri = getImageUri(getApplicationContext(),bitmapCam);
                finalUri=uri;
                try {
                    bitmapCam=MediaStore.Images.Media.getBitmap(getContentResolver(), finalUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                sendImage(bitmapCam);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            dialog2.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    private Uri getImageUri(Context context, Bitmap inbitmap){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        inbitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        realPath =MediaStore.Images.Media.insertImage(context.getContentResolver(),inbitmap,String.valueOf(System.currentTimeMillis()),null);

        return Uri.parse(realPath);
    }

    private void passImage() {

        Template_Frame_Fragment fragment = new Template_Frame_Fragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.template, fragment);
        Bundle bundle = new Bundle();
        bundle.putString("frame_id", String.valueOf(getImage));
        fragment.setArguments(bundle);
        transaction.commit();

    }


    private void addText() {
        add_text_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Editor_Activity.this);
                View add_custom_text_layout = getLayoutInflater().inflate(R.layout.add_custom_text_lauout, null);
                builder.setView(add_custom_text_layout);
                builder.setTitle("Add text");
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        EditText customText = add_custom_text_layout.findViewById(R.id.customText);
                        movableTextView = new TextView(Editor_Activity.this);
                        RelativeLayout.LayoutParams textalignment = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        textalignment.addRule(RelativeLayout.CENTER_IN_PARENT);
                        movableTextView.bringToFront();
                        movableTextView.setLayoutParams(textalignment);
                        movableTextView.setTextSize(60);
                        movableTextView.setGravity(View.TEXT_ALIGNMENT_CENTER);
                        movableTextView.setMaxLines(3);
                        movableTextView.setBackground(getDrawable(R.drawable.border));
                        panel.addView(movableTextView);
                        movableTextView.setText(customText.getText().toString());

                        movableTextView.setTextColor(Color.WHITE);
                        movableTextView.setVisibility(View.VISIBLE);

                        movableTextView.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View view, MotionEvent motionEvent) {

                                TextView v = (TextView) view;
                                v.bringToFront();
                                viewTransformation(v, motionEvent);

                                return true;
                            }
                        });


                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
    }


    private void onTouch2(ImageView imageView, MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                xCoOrdinate = imageView.getX() - event.getRawX();
                yCoOrdinate = imageView.getY() - event.getRawY();

                start.set(event.getX(), event.getY());
                isOutSide = false;
                mode = DRAG;
                lastEvent = null;



                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist = spacing(event);
                if (oldDist > 10f) {
                    midPoint(mid, event);
                    mode = ZOOM;
                }

                lastEvent = new float[4];
                lastEvent[0] = event.getX(0);
                lastEvent[1] = event.getX(1);
                lastEvent[2] = event.getY(0);
                lastEvent[3] = event.getY(1);
                d = rotation(event);
                break;
            case MotionEvent.ACTION_UP:
                isZoomAndRotate = false;
                if (mode == DRAG) {
                    float x = event.getX();
                    float y = event.getY();
                }
            case MotionEvent.ACTION_OUTSIDE:
                isOutSide = true;
                mode = NONE;
                lastEvent = null;
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                lastEvent = null;

                break;
            case MotionEvent.ACTION_MOVE:
                if (!isOutSide) {
                    if (mode == DRAG) {
                        isZoomAndRotate = false;
                        imageView.animate().x(event.getRawX() + xCoOrdinate).y(event.getRawY() + yCoOrdinate).setDuration(0).start();
                    }
                    if (mode == ZOOM && event.getPointerCount() == 2) {
                        float newDist1 = spacing(event);
                        if (newDist1 > 10f) {
                            float scale = newDist1 / oldDist * imageView.getScaleX();
                            imageView.setScaleX(scale);
                            imageView.setScaleY(scale);


                        }
                        if (lastEvent != null) {
                            newRot = rotation(event);
                            imageView.setRotation((float) (imageView.getRotation() + (newRot - d)));


                        }
                    }
                }

                break;
        }
    }


    private void onTouch3(ImageView imageView, MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                xCoOrdinate = imageView.getX() - event.getRawX();
                yCoOrdinate = imageView.getY() - event.getRawY();

                start.set(event.getX(), event.getY());
                isOutSide = false;
                mode = DRAG;
                lastEvent = null;

                imageView.setBackground(getDrawable(R.drawable.border1));


                delete_element.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        panel.removeView(imageView);
                    }
                });


                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist = spacing(event);
                if (oldDist > 10f) {
                    midPoint(mid, event);
                    mode = ZOOM;
                }

                lastEvent = new float[4];
                lastEvent[0] = event.getX(0);
                lastEvent[1] = event.getX(1);
                lastEvent[2] = event.getY(0);
                lastEvent[3] = event.getY(1);
                d = rotation(event);
                break;
            case MotionEvent.ACTION_UP:
                isZoomAndRotate = false;
                if (mode == DRAG) {
                    float x = event.getX();
                    float y = event.getY();
                    imageView.setBackground(null);

                }
            case MotionEvent.ACTION_OUTSIDE:
                isOutSide = true;
                mode = NONE;
                lastEvent = null;
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                lastEvent = null;

                break;
            case MotionEvent.ACTION_MOVE:
                if (!isOutSide) {
                    if (mode == DRAG) {
                        isZoomAndRotate = false;
                        imageView.animate().x(event.getRawX() + xCoOrdinate).y(event.getRawY() + yCoOrdinate).setDuration(0).start();
                    }
                    if (mode == ZOOM && event.getPointerCount() == 2) {
                        float newDist1 = spacing(event);
                        if (newDist1 > 10f) {
                            float scale = newDist1 / oldDist * imageView.getScaleX();
                            imageView.setScaleX(scale);
                            imageView.setScaleY(scale);


                        }
                        if (lastEvent != null) {
                            newRot = rotation(event);
                            imageView.setRotation((float) (imageView.getRotation() + (newRot - d)));


                        }
                    }
                }

                break;
        }
    }



    private void viewTransformation(TextView textView, MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                xCoOrdinate = textView.getX() - event.getRawX();
                yCoOrdinate = textView.getY() - event.getRawY();

                start.set(event.getX(), event.getY());
                isOutSide = false;
                mode = DRAG;
                lastEvent = null;
                textView.setBackground(getDrawable(R.drawable.border1));

//                blendSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//                    @Override
//                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                        blendValue = i / 100f;
//                        textView.setAlpha(blendValue);
//
//
//                    }
//
//                    @Override
//                    public void onStartTrackingTouch(SeekBar seekBar) {
//
//                    }
//
//                    @Override
//                    public void onStopTrackingTouch(SeekBar seekBar) {
//
//                    }
//                });
                ////////for color changing

//                addTextColor.setOnClickListener(view -> {
//                    AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(MainActivity.this, defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
//                        @Override
//                        public void onCancel(AmbilWarnaDialog dialog) {
//
//
//                        }
//
//                        @Override
//                        public void onOk(AmbilWarnaDialog dialog, int color) {
//
//                            defaultColor = color;
//                            textView.setTextColor(defaultColor);
//
//                        }
//                    });
//                    colorPicker.show();
//                });
//                ////color changing finish
//

                choose_font_Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Editor_Activity.this);
                        View addFont = getLayoutInflater().inflate(R.layout.font_layout, null);
                        builder.setView(addFont);
                        builder.setTitle("Add font");

                        setFont(addFont);


                        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @SuppressLint("ResourceAsColor")
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if (finalFont != null) {
                                    try {
                                        textView.setTypeface(finalFont);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                try {
                                    if (finalColor != null) {
                                        textView.setTextColor(Integer.parseInt(finalColor));


                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            }
                        });

                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                });
                delete_element.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        panel.removeView(textView);
                    }
                });


                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist = spacing(event);
                if (oldDist > 10f) {
                    midPoint(mid, event);
                    mode = ZOOM;
                }

                lastEvent = new float[4];
                lastEvent[0] = event.getX(0);
                lastEvent[1] = event.getX(1);
                lastEvent[2] = event.getY(0);
                lastEvent[3] = event.getY(1);
                d = rotation(event);
                break;
            case MotionEvent.ACTION_UP:
                isZoomAndRotate = false;
                if (mode == DRAG) {
                    float x = event.getX();
                    float y = event.getY();
                    textView.setBackground(null);

                }
            case MotionEvent.ACTION_OUTSIDE:
                isOutSide = true;
                mode = NONE;
                lastEvent = null;
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                lastEvent = null;
                break;
            case MotionEvent.ACTION_MOVE:
                if (!isOutSide) {
                    if (mode == DRAG) {
                        isZoomAndRotate = false;
                        textView.animate().x(event.getRawX() + xCoOrdinate).y(event.getRawY() + yCoOrdinate).setDuration(0).start();
                    }
                    if (mode == ZOOM && event.getPointerCount() == 2) {
                        float newDist1 = spacing(event);
                        if (newDist1 > 10f) {
                            float scale = newDist1 / oldDist * textView.getScaleX();
                            textView.setScaleX(scale);
                            textView.setScaleY(scale);

                        }
                        if (lastEvent != null) {
                            newRot = rotation(event);
                            textView.setRotation((float) (textView.getRotation() + (newRot - d)));
                        }
                    }
                }
                break;
        }
    }


    private float rotation(MotionEvent event) {
        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (int) Math.sqrt(x * x + y * y);
    }

    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }


    private void setFont(View addFont) {
        TextView font1 = addFont.findViewById(R.id.font1);
        TextView font2 = addFont.findViewById(R.id.font2);
        TextView font3 = addFont.findViewById(R.id.font3);
        TextView font4 = addFont.findViewById(R.id.font4);
        TextView font5 = addFont.findViewById(R.id.font5);
        TextView font6 = addFont.findViewById(R.id.font6);
        TextView font7 = addFont.findViewById(R.id.font7);
        TextView font8 = addFont.findViewById(R.id.font8);
        TextView font9 = addFont.findViewById(R.id.font9);
        TextView font10 = addFont.findViewById(R.id.font10);
        TextView font11 = addFont.findViewById(R.id.font11);
        TextView font12 = addFont.findViewById(R.id.font12);
        TextView font13 = addFont.findViewById(R.id.font13);
        TextView font15 = addFont.findViewById(R.id.font15);
        TextView font17 = addFont.findViewById(R.id.font17);
        TextView font18 = addFont.findViewById(R.id.font18);
        TextView font19 = addFont.findViewById(R.id.font19);
        TextView font20 = addFont.findViewById(R.id.font20);
        TextView font21 = addFont.findViewById(R.id.font21);
        TextView font22 = addFont.findViewById(R.id.font22);
        TextView font23 = addFont.findViewById(R.id.font23);
        TextView font24 = addFont.findViewById(R.id.font24);
        Button choose_color = addFont.findViewById(R.id.choose_color);
        font1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalFont = font1.getTypeface();

            }
        });
        font2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalFont = font2.getTypeface();

            }
        });
        font3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalFont = font3.getTypeface();

            }
        });
        font4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalFont = font4.getTypeface();

            }
        });
        font5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalFont = font5.getTypeface();

            }
        });
        font6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalFont = font6.getTypeface();

            }
        });
        font7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalFont = font7.getTypeface();

            }
        });
        font8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalFont = font8.getTypeface();

            }
        });
        font9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalFont = font9.getTypeface();

            }
        });
        font10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalFont = font10.getTypeface();

            }
        });
        font11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalFont = font11.getTypeface();

            }
        });
        font12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalFont = font12.getTypeface();

            }
        });
        font13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalFont = font13.getTypeface();

            }
        });
        font15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalFont = font15.getTypeface();

            }
        });
        font17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalFont = font17.getTypeface();

            }
        });

        font18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalFont = font18.getTypeface();

            }
        });
        font19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalFont = font19.getTypeface();

            }
        });
        font20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalFont = font20.getTypeface();

            }
        });
        font21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalFont = font21.getTypeface();

            }
        });
        font22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalFont = font22.getTypeface();

            }
        });
        font23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalFont = font23.getTypeface();

            }
        });
        font24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalFont = font24.getTypeface();

            }
        });

        choose_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(Editor_Activity.this, defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {


                    }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {

                        defaultColor = color;
                        finalColor = String.valueOf(defaultColor);

                    }
                });
                colorPicker.show();

            }
        });
    }

    private void findingImages(Dialog dialog) {
        ImageView s1 = dialog.findViewById(R.id.s1);
        ImageView s2 = dialog.findViewById(R.id.s2);
        ImageView s3 = dialog.findViewById(R.id.s3);
        ImageView s4 = dialog.findViewById(R.id.s4);
        ImageView s5 = dialog.findViewById(R.id.s5);
        ImageView s6 = dialog.findViewById(R.id.s6);
        ImageView s7 = dialog.findViewById(R.id.s7);
        ImageView s8 = dialog.findViewById(R.id.s8);
        ImageView s9 = dialog.findViewById(R.id.s9);
        ImageView s10 = dialog.findViewById(R.id.s10);
        ImageView s11= dialog.findViewById(R.id.s11);
        ImageView s12 = dialog.findViewById(R.id.s12);
        ImageView s13 = dialog.findViewById(R.id.s13);
        ImageView s14 = dialog.findViewById(R.id.s14);
        ImageView s15 = dialog.findViewById(R.id.s15);
        ImageView s16= dialog.findViewById(R.id.s16);
        ImageView s17 = dialog.findViewById(R.id.s17);
        ImageView s18 = dialog.findViewById(R.id.s18);
        ImageView s19 = dialog.findViewById(R.id.s19);
        ImageView s20 = dialog.findViewById(R.id.s20);
        ImageView s21 = dialog.findViewById(R.id.s21);
        ImageView s22 = dialog.findViewById(R.id.s22);
        ImageView s23 = dialog.findViewById(R.id.s23);
        ImageView s24 = dialog.findViewById(R.id.s24);
        ImageView s25 = dialog.findViewById(R.id.s25);
        ImageView s26 = dialog.findViewById(R.id.s26);
        ImageView s27 = dialog.findViewById(R.id.s27);
        ImageView s28 = dialog.findViewById(R.id.s28);
        ImageView s29 = dialog.findViewById(R.id.s29);
        ImageView s30 = dialog.findViewById(R.id.s30);
        ImageView s31 = dialog.findViewById(R.id.s31);
        ImageView s32 = dialog.findViewById(R.id.s32);
        ImageView s33 = dialog.findViewById(R.id.s33);

        s1.setOnClickListener(this);
        s2.setOnClickListener(this);
        s3.setOnClickListener(this);
        s4.setOnClickListener(this);
        s5.setOnClickListener(this);
        s6.setOnClickListener(this);
        s7.setOnClickListener(this);
        s8.setOnClickListener(this);
        s9.setOnClickListener(this);
        s10.setOnClickListener(this);
        s11.setOnClickListener(this);
        s12.setOnClickListener(this);
        s13.setOnClickListener(this);
        s14.setOnClickListener(this);
        s15.setOnClickListener(this);
        s16.setOnClickListener(this);
        s17.setOnClickListener(this);
        s18.setOnClickListener(this);
        s19.setOnClickListener(this);
        s20.setOnClickListener(this);
        s21.setOnClickListener(this);
        s22.setOnClickListener(this);
        s23.setOnClickListener(this);
        s24.setOnClickListener(this);
        s25.setOnClickListener(this);
        s26.setOnClickListener(this);
        s27.setOnClickListener(this);
        s28.setOnClickListener(this);
        s29.setOnClickListener(this);
        s30.setOnClickListener(this);
        s31.setOnClickListener(this);
        s32.setOnClickListener(this);
        s33.setOnClickListener(this);

    }

    private void addSticker(View view) {

        movableSticker = new ImageView(getApplicationContext());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.BELOW);
        movableSticker.setLayoutParams(lp);
        movableSticker.setScaleType(ImageView.ScaleType.FIT_CENTER);
        movableSticker.setVisibility(View.GONE);
        panel.addView(movableSticker);
        movableSticker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v2, MotionEvent event) {

                ImageView v = (ImageView) v2;
                v.bringToFront();
                onTouch3(v, event);

                return true;
            }
        });
        movableSticker.setImageResource(finalID);
        movableSticker.setVisibility(View.VISIBLE);
        dialog.dismiss();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.s1:
                finalID=R.drawable.garden1;
                addSticker(view);
                break;
            case R.id.s2:
                finalID=R.drawable.garden2;
                addSticker(view);
                break;
            case R.id.s3:
                finalID=R.drawable.garden3;
                addSticker(view);
                break;

            case R.id.s4:
                finalID=R.drawable.garden4;
                addSticker(view);
                break;
            case R.id.s5:
                finalID=R.drawable.garden5;
                addSticker(view);
                break;
            case R.id.s6:
                finalID=R.drawable.garden6;
                addSticker(view);
                break;
            case R.id.s7:
                finalID=R.drawable.garden7;
                addSticker(view);
                break;
            case R.id.s8:
                finalID=R.drawable.garden8;
                addSticker(view);
                break;
            case R.id.s9:
                finalID=R.drawable.garden10;
                addSticker(view);
                break;
            case R.id.s11:
                finalID=R.drawable.garden11;
                addSticker(view);
                break;
            case R.id.s12:
                finalID=R.drawable.garden12;
                addSticker(view);
                break;
            case R.id.s13:
                finalID=R.drawable.garden13;
                addSticker(view);
                break;
            case R.id.s14:
                finalID=R.drawable.garden14;
                addSticker(view);
                break;
            case R.id.s15:
                finalID=R.drawable.garden15;
                addSticker(view);
                break;
            case R.id.s16:
                finalID=R.drawable.garden16;
                addSticker(view);
                break;
            case R.id.s17:
                finalID=R.drawable.garden17;
                addSticker(view);
                break;
            case R.id.s18:
                finalID=R.drawable.garden18;
                addSticker(view);
                break;
            case R.id.s19:
                finalID=R.drawable.garden19;
                addSticker(view);
                break;
            case R.id.s20:
                finalID=R.drawable.garden20;
                addSticker(view);
                break;
            case R.id.s21:
                finalID=R.drawable.garden21;
                addSticker(view);
                break;
            case R.id.s22:
                finalID=R.drawable.garden22;
                addSticker(view);
                break;
            case R.id.s23:
                finalID=R.drawable.garden23;
                addSticker(view);
                break;
            case R.id.s24:
                finalID=R.drawable.garden24;
                addSticker(view);
                break;
            case R.id.s25:
                finalID=R.drawable.garden25;
                addSticker(view);
                break;
            case R.id.s26:
                finalID=R.drawable.garden26;
                addSticker(view);
                break;
            case R.id.s27:
                finalID=R.drawable.garden27;
                addSticker(view);
                break;
            case R.id.s28:
                finalID=R.drawable.garden28;
                addSticker(view);
                break;
            case R.id.s29:
                finalID=R.drawable.garden29;
                addSticker(view);
                break;
            case R.id.s30:
                finalID=R.drawable.garden30;
                addSticker(view);
                break;
            case R.id.s31:
                finalID=R.drawable.garden31;
                addSticker(view);
                break;

            case R.id.s32:
                finalID=R.drawable.garden32;
                addSticker(view);
                break;
            case R.id.s33:
                finalID=R.drawable.garden33;
                addSticker(view);
                break;

        }
    }

    private void save() {
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                panel.setDrawingCacheEnabled(true);
                panel.buildDrawingCache(true);

                save_bitmap = Bitmap.createBitmap(panel.getDrawingCache());
                canvas = new Canvas();
                panel.draw(canvas);
                panel.setDrawingCacheEnabled(false);
                save_imageView.setImageBitmap(save_bitmap);
                saveImage();

            }
        });
    }

    private void saveImage() {
        bitmapDrawable = (BitmapDrawable) save_imageView.getDrawable();
        save_bitmap = bitmapDrawable.getBitmap();
        saveImageToGallery(save_bitmap);
    }

    private void saveImageToGallery(Bitmap bitmap4) {

        OutputStream fos;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ContentResolver resolver = getContentResolver();
                ContentValues contentValues = new ContentValues();
                String fileName = "Image_" + System.currentTimeMillis() + ".jpg";
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "images/jpg");
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM + File.separator + "Garden Photo Editor");
                Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                fos = resolver.openOutputStream(Objects.requireNonNull(imageUri));
                bitmap4.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                Objects.requireNonNull(fos);

                sendImagetoshareActivity(bitmap4,imageUri,fileName);


                Toast.makeText(Editor_Activity.this, "saved in > Internal storage/DCIM/Garden Photo Editor", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {

            Toast.makeText(Editor_Activity.this, "Error\n" + e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }


    private void sendImagetoshareActivity(Bitmap bitmap4, Uri imageUri, String fileName) {
        String encodedImage = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap4.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] b = baos.toByteArray();

            encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }


        SharedPreferences sharedPreferences = getSharedPreferences("image", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("encoded", encodedImage);
        editor.apply();


        Intent intent = new Intent(Editor_Activity.this, Save_Activity.class);
        intent.putExtra("pathUri",imageUri.getPath());
        intent.putExtra("imageName",fileName);
        startActivity(intent);

    }

    private void sendImage(Bitmap bitmap) {

        String encodedImage = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] b = baos.toByteArray();

            encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }


        SharedPreferences sharedPreferences = getSharedPreferences("image_bg1", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("encoded", encodedImage);
        editor.apply();
        ContentResolver resolver = getContentResolver();
        try {
            resolver.delete(finalUri,null,null);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Intent intent = new Intent(Editor_Activity.this, Free_Hand_Activity.class);
        intent.putExtra("image_id", getImage);
        startActivity(intent);



    }



}