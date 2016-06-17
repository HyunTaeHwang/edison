package com.example.hwanghyuntae.myapplication;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.app.AlertDialog;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class SignUpActivity extends AppCompatActivity {
    private ImageView mLeft,mSignUp,mPhotoImageView;
    private TextView mName,mEmail,mPassword, mConfirmText,mSchoolText, mPositionText,mImagepath;
    private Button mGetcode, mConfirm,mButton;
    private ImageButton mImageButton,mPassButton;

    private static final String TEMP_PHOTO_FILE = "temp.jpg";       // 임시 저장파일
    private static final int REQ_CODE_PICK_IMAGE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mLeft = (ImageView) findViewById(R.id.iv_left);
        mGetcode = (Button) findViewById(R.id.Getcode);
        mConfirm = (Button) findViewById(R.id.Confirm);
        mConfirmText = (TextView) findViewById(R.id.ConfirmText);
        mSignUp = (ImageView) findViewById(R.id.SignUpButton);
        mName = (TextView) findViewById(R.id.editTextName);
        mEmail = (TextView) findViewById(R.id.editTextEmail);
        mPassword = (TextView) findViewById(R.id.editTextPassword);
        mSchoolText = (TextView) findViewById(R.id.SchoolText);
        mPositionText = (TextView) findViewById(R.id.PositionText);
        mImageButton = (ImageButton) findViewById(R.id.ImageButton);
        mPhotoImageView = (ImageView) findViewById(R.id.imageView);
        mButton = (Button) findViewById(R.id.button);
        mImagepath = (TextView) findViewById(R.id.Imagepath);
        mPassButton = (ImageButton) findViewById(R.id.imageButtonPass);

        mLeft.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });

        mGetcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//완료버튼

                String temp;
                String type = "Get Code";
                BackgroundWorker backgroundWorker = new BackgroundWorker(SignUpActivity.this);
                temp = null;
                try {
                    temp = backgroundWorker.execute(type, mEmail.getText().toString()).get();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                if(temp.equals("Mail Sent."))
                {
                    Toast toast = Toast.makeText(getApplicationContext(),"인증코드가 발송 되었습니다. 코드 입력후 확인하여 주세요.",Toast.LENGTH_SHORT);
                    toast.show();
                }
                else
                {
                    Toast toast = Toast.makeText(getApplicationContext(),"인증코드가 발송 되지 않았습니다.",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        mPassButton.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();

                if(action == MotionEvent.ACTION_DOWN) {
                    AccessibilityNodeInfo editTextPassword;
                    mPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                else
                {
                    mPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                return false;
            }
        });
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String temp;
                String type = "Confirm";
                BackgroundWorker backgroundWorker = new BackgroundWorker(SignUpActivity.this);
                temp = null;
                try {
                    temp = backgroundWorker.execute(type, mConfirmText.getText().toString(), mEmail.getText().toString()).get();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                if(temp.equals("no"))
                {
                    Toast toast = Toast.makeText(getApplicationContext(),"인증코드가 확인 되었습니다. 나머지 사항을 입력하여 주세요.",Toast.LENGTH_SHORT);
                    toast.show();
                    mSignUp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {//완료버튼

                            String temp;
                            String type = "Sign Up";
                            BackgroundWorker backgroundWorker = new BackgroundWorker(SignUpActivity.this);
                            temp = null;
                            try {


                                temp = backgroundWorker.execute(type, mEmail.getText().toString(), mPassword.getText().toString(),mName.getText().toString(),mSchoolText.getText().toString(),mPositionText.getText().toString(),mImagepath.getText().toString()).get();
                                Toast toast = Toast.makeText(getApplicationContext(),temp,Toast.LENGTH_SHORT);
                                toast.show();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }

                            if(temp.equals("Success"))
                            {

                                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                            }
                            else
                            {
                                Intent refresh = new Intent(SignUpActivity.this, SignUpActivity.class);
                                startActivity(refresh);
                            }
                        }
                    });
                }
                else
                {
                    Toast toast = Toast.makeText(getApplicationContext(),"인증코드를 다시 한번 확인 바랍니다.",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        Intent.ACTION_GET_CONTENT,      // 또는 ACTION_PICK
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");              // 모든 이미지
                        intent.putExtra("crop", "true");        // Crop기능 활성화
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());     // 임시파일 생성
                        intent.putExtra("outputFormat",         // 포맷방식
                                Bitmap.CompressFormat.JPEG.toString());

                startActivityForResult(intent, REQ_CODE_PICK_IMAGE);
                    // REQ_CODE_PICK_IMAGE == requestCode
                }
            });
    }
        private Uri getTempUri() {
            return Uri.fromFile(getTempFile());
         }
        @Nullable
        private File getTempFile() {
            if (isSDCARDMOUNTED()) {
                File f = new File(Environment.getExternalStorageDirectory(), // 외장메모리 경로
                        TEMP_PHOTO_FILE);
                try {
                    f.createNewFile();      // 외장메모리에 temp.jpg 파일 생성
                } catch (IOException e) {
                }

                return f;
            } else
                return null;
        }

            /** SD카드가 마운트 되어 있는지 확인 */
            private boolean isSDCARDMOUNTED() {
                String status = Environment.getExternalStorageState();
                if (status.equals(Environment.MEDIA_MOUNTED))
                    return true;

                return false;
            }
        /** 다시 액티비티로 복귀하였을때 이미지를 셋팅 */
        protected void onActivityResult(int requestCode, int resultCode,
                                        Intent imageData) {

            //Toast.makeText(getBaseContext(), "resultCode : "+imageData,Toast.LENGTH_SHORT).show();
            super.onActivityResult(requestCode, resultCode, imageData);

            switch (requestCode) {
                case REQ_CODE_PICK_IMAGE:
                    if (resultCode == RESULT_OK) {
                        if (imageData != null) {
                            String filePath = Environment.getExternalStorageDirectory()
                                    + "/temp.jpg";
                            try {

                                Bitmap image_bitmap 	= MediaStore.Images.Media.getBitmap(getContentResolver(),imageData.getData());
                                mImageButton.setImageBitmap(image_bitmap);

                                Toast.makeText(getBaseContext(),mImageButton.toString(),Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            System.out.println(mImageButton); // logCat으로 경로확인.
                            mImagepath.setText(filePath);
                         //   Bitmap selectedImage = BitmapFactory.decodeFile(filePath);
                            // temp.jpg파일을 Bitmap으로 디코딩한다.

                        }
                    }
                    break;
            }
        }

}

