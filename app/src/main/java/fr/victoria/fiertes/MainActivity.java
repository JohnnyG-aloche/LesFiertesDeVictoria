package fr.victoria.fiertes;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.SharedPreferences;

public class MainActivity extends Activity {
    private SharedPreferences prefs;
    private TextView diamondsValue, poopValue;
    private int diamonds, poop;
    private int dp(float v) { return (int)(v * getResources().getDisplayMetrics().density + 0.5f); }
    private LinearLayout.LayoutParams lp(int w, int h) { return new LinearLayout.LayoutParams(w < 0 ? w : dp(w), h < 0 ? h : dp(h)); }
    private TextView label(String s, int size, int color, boolean bold) {
        TextView t = new TextView(this); t.setText(s); t.setTextSize(size); t.setTextColor(color); t.setGravity(Gravity.CENTER);
        if (bold) t.setTypeface(Typeface.DEFAULT, Typeface.BOLD); return t;
    }
    private Button btn(String s, int color) {
        Button b = new Button(this); b.setText(s); b.setTextSize(20); b.setTextColor(Color.WHITE); b.setAllCaps(false);
        b.setBackgroundTintList(android.content.res.ColorStateList.valueOf(color)); return b;
    }
    @Override public void onCreate(Bundle b) {
        super.onCreate(b); prefs = getSharedPreferences("victoria_stock", MODE_PRIVATE);
        diamonds = prefs.getInt("diamonds", 0); poop = prefs.getInt("poop", 0); screen();
    }
    private void screen() {
        LinearLayout page = new LinearLayout(this); page.setOrientation(1); page.setGravity(Gravity.CENTER_HORIZONTAL);
        page.setPadding(dp(22), dp(26), dp(22), dp(24)); page.setBackgroundColor(Color.rgb(245,247,252));
        page.addView(label("🏆 Les fiertés de Victoria", 25, Color.rgb(38,48,85), true), lp(-1,72));
        page.addView(label("Le petit inventaire Minecraft", 15, Color.rgb(95,101,120), false), lp(-1,36));
        diamondsValue = counter(page, "💎  Diamants", true); poopValue = counter(page, "💩  Cacas", false);
        TextView note = label("Les quantités sont sauvegardées automatiquement sur ce téléphone.", 12, Color.rgb(110,115,130), false);
        LinearLayout.LayoutParams np = lp(-1,-2); np.topMargin = dp(18); page.addView(note,np); setContentView(page); refresh();
    }
    private TextView counter(LinearLayout page, String title, boolean diamond) {
        LinearLayout card = new LinearLayout(this); card.setOrientation(1); card.setGravity(Gravity.CENTER); card.setPadding(dp(16),dp(14),dp(16),dp(14));
        card.setBackgroundColor(Color.WHITE); card.setElevation(dp(3)); LinearLayout.LayoutParams cp = lp(-1,0); cp.weight=1; cp.topMargin=dp(14); page.addView(card,cp);
        card.addView(label(title,23,Color.rgb(45,51,70),true),lp(-1,48));
        TextView value = label("0",48,Color.rgb(35,45,80),true); card.addView(value,lp(-1,78));
        LinearLayout actions = new LinearLayout(this); actions.setGravity(Gravity.CENTER); card.addView(actions,lp(-1,60));
        Button minus=btn("− 1",Color.rgb(220,75,85)), plus=btn("+ 1",Color.rgb(45,155,105));
        LinearLayout.LayoutParams ap=new LinearLayout.LayoutParams(0,dp(54),1); ap.setMargins(dp(6),0,dp(6),0); actions.addView(minus,ap); actions.addView(plus,ap);
        minus.setOnClickListener(v->{if(diamond) diamonds=Math.max(0,diamonds-1); else poop=Math.max(0,poop-1); save();});
        plus.setOnClickListener(v->{if(diamond) diamonds++; else poop++; save();}); return value;
    }
    private void save(){prefs.edit().putInt("diamonds",diamonds).putInt("poop",poop).apply();refresh();}
    private void refresh(){diamondsValue.setText(String.valueOf(diamonds));poopValue.setText(String.valueOf(poop));}
}
