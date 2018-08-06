package com.unam.alex.pumaride.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;

import com.unam.alex.pumaride.R;

/**
 * Created by alex on 11/12/16.
 */
public class CDialog {
    Dialog dialog;
    int id_layout;
    int bg_color;
    int bg_alpha;
    int animation;

    public Dialog getDialog() {
        return dialog;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public int getId_layout() {
        return id_layout;
    }

    public void setId_layout(int id_layout) {
        this.id_layout = id_layout;
    }

    public int getBg_color() {
        return bg_color;
    }

    public void setBg_color(int bg_color) {
        this.bg_color = bg_color;
    }

    public int getBg_alpha() {
        return bg_alpha;
    }

    public void setBg_alpha(int bg_alpha) {
        this.bg_alpha = bg_alpha;
        ColorDrawable d = new ColorDrawable(Color.BLACK);
        d.setAlpha(bg_alpha);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(d.getColor()));
    }

    public int getAnimation() {
        return animation;
    }

    public void setAnimation(int animation) {
        this.animation = animation;
        dialog.getWindow().setWindowAnimations(animation);
    }
    public CDialog(Activity activity, int id_layout) {
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ColorDrawable d = new ColorDrawable(Color.BLACK);
        d.setAlpha(250);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(d.getColor()));
        dialog.getWindow().setWindowAnimations(R.style.DialogNoAnimation);
        dialog.setContentView(id_layout);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);
    }
    public static class Builder {
        int id_layout;
        int bg_color = Color.BLACK;
        int bg_alpha = 200;
        int animation = android.R.style.Animation_Toast;
        Activity activity;
        public Builder(Activity activity, int id_layout){
            this.activity = activity;
            this.id_layout  =id_layout;
        }
        public Builder setIdLayot(int id_layout) {
            this.id_layout = id_layout;
            return this;
        }

        public Builder setBgColor(int bg_color) {
            this.bg_color = bg_color;
            return this;
        }

        public Builder setBgAlpha(int bg_alpha) {
            this.bg_alpha = bg_alpha;
            return this;
        }

        public Builder setAnimation(int animation) {
            this.animation = animation;
            return this;
        }
        public Dialog build(){
            CDialog cdialog = new CDialog(this.activity,this.id_layout);
            cdialog.setAnimation(this.animation);
            cdialog.setBg_alpha(this.bg_alpha);
            cdialog.setBg_color(this.bg_color);
            return cdialog.getDialog();
        }
    }
}
