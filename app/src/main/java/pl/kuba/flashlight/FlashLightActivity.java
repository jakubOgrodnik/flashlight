package pl.kuba.flashlight;

import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.hardware.camera2.CameraManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



import butterknife.BindView;
import butterknife.ButterKnife;

public class FlashLightActivity extends AppCompatActivity {
    @BindView(R.id.on_button)
    Button mOnButton;
    private android.hardware.Camera mCamera1;
    android.hardware.Camera.Parameters mParameters;
    private boolean mIsOn = false;
    private boolean mIsFlash = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_light);
        ButterKnife.bind(this);

        if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)) {
            mCamera1 = android.hardware.Camera.open();
            mParameters = mCamera1.getParameters();
            mIsFlash = true;

            mOnButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onButton();
                }
            });
        }
    }

    private void onButton() {
        if (mIsFlash) {
           if (!mIsOn) {
               mOnButton.setBackgroundResource(R.drawable.off_button);
                mParameters.setFlashMode(android.hardware.Camera.Parameters.FLASH_MODE_TORCH);
               mCamera1.setParameters(mParameters);
               mCamera1.startPreview();
               mIsOn = true;
            }else{
               mOnButton.setBackgroundResource(R.drawable.on_button);
               mParameters.setFlashMode(android.hardware.Camera.Parameters.FLASH_MODE_OFF);
               mCamera1.setParameters(mParameters);
               mCamera1.stopPreview();
               mIsOn = false;
           }

        } else {
            AlertDialog alert = new AlertDialog.Builder(FlashLightActivity.this).create();
            alert.setTitle("Błąd");
            alert.setMessage("Brak latarki");
            finish();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mCamera1 !=null) {
            mCamera1.release();
            mCamera1 = null;
        }
    }
}











