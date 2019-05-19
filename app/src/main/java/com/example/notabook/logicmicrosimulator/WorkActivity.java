package com.example.notabook.logicmicrosimulator;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;

public class WorkActivity extends AppCompatActivity implements View.OnClickListener {
    final int nHigh = 7, nLow = 7, nPort = 8, nPos = 2;
    boolean notCheck, orCheck, andCheck, xorCheck, check1, check2;
    ImageView[] port = new ImageView[nPort]; //0-A 1-B 2-C 3-D 4-E 5-F 6-G 7-position1 to position2
    RadioButton[] high = new RadioButton[nHigh]; //0-A 1-B 2-C 3-D 4-E 5-F 6-G
    RadioButton[] low = new RadioButton[nLow]; //0-A 1-B 2-C 3-D 4-E 5-F 6-G
    ImageButton[] pos = new ImageButton[nPos]; //0-position1 1-position2
    boolean[] value = new boolean[nHigh]; //0-A 1-B 2-C 3-D 4-E 5-F 6-G
    boolean[] portCheck = new boolean[nHigh]; //0-A 1-B 2-C 3-D 4-E 5-F 6-G
    int[] portId = {R.id.aPort, R.id.bPort, R.id.cPort, R.id.dPort, R.id.ePort, R.id.fPort, R.id.gPort, R.id.port,};
    int[] highId = {R.id.aH, R.id.bH, R.id.cH, R.id.dH, R.id.eH, R.id.fH, R.id.gH};
    int[] lowId = {R.id.aL, R.id.bL, R.id.cL, R.id.dL, R.id.eL, R.id.fL, R.id.gL};
    int[] portDraw = {R.drawable.image_port_a, R.drawable.image_port_b, R.drawable.image_port_c, R.drawable.image_port_d, R.drawable.image_port_e, R.drawable.image_port_f, R.drawable.image_port_g};
    int[] posId = {R.id.set1chip, R.id.set2chip};
    int pos1 = 0, pos2 = 0;
    ImageView light;
    ImageButton not, or, and, xor, delete, run;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); //Turn to landscape orientation
        id(); //R.id.
        allLow(); //All port low
        setClick(); //setOnClickListener
    }

    public boolean getResValue() {
        boolean resValue = getFirstPosValue();
        switch (pos2) {
            case 0:
                resValue = getFirstPosValue();
                break;
            case 1:
                resValue = not(getFirstPosValue());
                break;
            case 2:
                if (!portCheck[5]&&!portCheck[6]) {
                    resValue = or(new boolean[]{getFirstPosValue(), value[4]});
                } else if (!portCheck[5]) {
                    resValue = or(new boolean[]{getFirstPosValue(), value[4], value[6]});
                } else if (!portCheck[6]) {
                    resValue = or(new boolean[]{getFirstPosValue(), value[4], value[5]});
                } else resValue = or(new boolean[]{getFirstPosValue(), value[4], value[5], value[6]});
                break;
            case 3:
                if (!portCheck[5]&&!portCheck[6]) {
                    resValue = and(new boolean[]{getFirstPosValue(), value[4]});
                } else if (!portCheck[5]) {
                    resValue = and(new boolean[]{getFirstPosValue(), value[4], value[6]});
                } else if (!portCheck[6]) {
                    resValue = and(new boolean[]{getFirstPosValue(), value[4], value[5]});
                } else resValue = and(new boolean[]{getFirstPosValue(), value[4], value[5], value[6]});
                break;
            case 4:
                if (!portCheck[5]&&!portCheck[6]) {
                    resValue = xor(new boolean[]{getFirstPosValue(), value[4]});
                } else if (!portCheck[5]) {
                    resValue = xor(new boolean[]{getFirstPosValue(), value[4], value[6]});
                } else if (!portCheck[6]) {
                    resValue = xor(new boolean[]{getFirstPosValue(), value[4], value[5]});
                } else resValue = xor(new boolean[]{getFirstPosValue(), value[4], value[5], value[6]});
                break;
        }
        return resValue;
    }

    public boolean getFirstPosValue() {
        boolean firstPosValue = value[0];
        switch (pos1) {
            case 0:
                firstPosValue = value[0];
                break;
            case 1:
                firstPosValue = not(value[0]);
                break;
            case 2:
                if (!portCheck[2]&&!portCheck[3]) {
                    firstPosValue = or(new boolean[]{value[0], value[1]});
                } else if (!portCheck[2]) {
                    firstPosValue = or(new boolean[]{value[0], value[1], value[3]});
                } else if (!portCheck[3]) {
                    firstPosValue = or(new boolean[]{value[0], value[1], value[2]});
                } else firstPosValue = or(new boolean[]{value[0], value[1], value[2], value[3]});
                break;
            case 3:
                if (!portCheck[2]&&!portCheck[3]) {
                    firstPosValue = and(new boolean[]{value[0], value[1]});
                } else if (!portCheck[2]) {
                    firstPosValue = and(new boolean[]{value[0], value[1], value[3]});
                } else if (!portCheck[3]) {
                    firstPosValue = and(new boolean[]{value[0], value[1], value[2]});
                } else firstPosValue = and(new boolean[]{value[0], value[1], value[2], value[3]});
                break;
            case 4:
                if (!portCheck[2]&&!portCheck[3]) {
                    firstPosValue = xor(new boolean[]{value[0], value[1]});
                } else if (!portCheck[2]) {
                    firstPosValue = xor(new boolean[]{value[0], value[1], value[3]});
                } else if (!portCheck[3]) {
                    firstPosValue = xor(new boolean[]{value[0], value[1], value[2]});
                } else firstPosValue = xor(new boolean[]{value[0], value[1], value[2], value[3]});
                break;
        }
        return firstPosValue;
    }

    public void refresh() {
        for (int i = 0; i < nHigh; i++) {
            if (high[i].isChecked()) {
                value[i] = true;
            } else {
                value[i] = false;
            }
        }
    }

    public void id() {
        run = (ImageButton) findViewById(R.id.run);
        not = (ImageButton) findViewById(R.id.not);
        or = (ImageButton) findViewById(R.id.or);
        and = (ImageButton) findViewById(R.id.and);
        xor = (ImageButton) findViewById(R.id.xor);
        delete = (ImageButton) findViewById(R.id.delete); //Delete
        light = (ImageView) findViewById(R.id.lightDiode); //Visual result
        for (int i = 0; i < port.length; i++) {
            port[i] = (ImageView) findViewById(portId[i]);
        }
        for (int i = 0; i < high.length; i++) {
            high[i] = (RadioButton) findViewById(highId[i]);
        }
        for (int i = 0; i < low.length; i++) {
            low[i] = (RadioButton) findViewById(lowId[i]);
        }
        for (int i = 0; i < pos.length; i++) {
            pos[i] = (ImageButton) findViewById(posId[i]);
        }
    }

    public void setClick() { //setOnClickListener
        not.setOnClickListener(this);
        or.setOnClickListener(this);
        and.setOnClickListener(this);
        xor.setOnClickListener(this);
        pos[0].setOnClickListener(this);
        pos[1].setOnClickListener(this);
        delete.setOnClickListener(this);
        run.setOnClickListener(this);
        for (int i = 1; i < nHigh; i++) {
            port[i].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.not: //NOT
                notCheck = true;
                check1 = false;
                check2 =false;
                orCheck=false;
                andCheck=false;
                xorCheck=false;
                break;
            case R.id.or: //OR
                orCheck = true;
                check1 = false;
                check2 =false;
                notCheck =false;
                andCheck=false;
                xorCheck=false;
                break;
            case R.id.and: //AND
                andCheck = true;
                check1 = false;
                check2 =false;
                notCheck =false;
                orCheck=false;
                xorCheck=false;
                break;
            case R.id.xor: //XOR
                xorCheck = true;
                check1 = false;
                check2 =false;
                notCheck =false;
                orCheck=false;
                andCheck=false;
                break;
            case R.id.set1chip: //First pos
                if (notCheck) {
                    pos[0].setBackgroundResource(R.drawable.button_not_long);
                    pos1 = 1;
                    setFirstType(1);
                    notCheck = false;
                } else if (orCheck) {
                    pos[0].setBackgroundResource(R.drawable.button_or_long);
                    pos1 = 2;
                    setSecondType(1);
                    orCheck = false;
                } else if (andCheck) {
                    pos[0].setBackgroundResource(R.drawable.button_and_long);
                    pos1 = 3;
                    setSecondType(1);
                    andCheck = false;
                } else if (xorCheck) {
                    pos[0].setBackgroundResource(R.drawable.button_xor_long);
                    pos1 = 4;
                    setSecondType(1);
                    xorCheck = false;
                } else {
                    check1 = true;
                    check2 =false;
                    notCheck =false;
                    orCheck=false;
                    andCheck=false;
                    xorCheck=false;
                }
                break;
            case R.id.set2chip: //Second pos
                if (notCheck) {
                    pos[1].setBackgroundResource(R.drawable.button_not_long);
                    pos2 = 1;
                    setFirstType(2);
                    notCheck = false;
                } else if (orCheck) {
                    pos[1].setBackgroundResource(R.drawable.button_or_long);
                    pos2 = 2;
                    setSecondType(2);
                    orCheck = false;
                } else if (andCheck) {
                    pos[1].setBackgroundResource(R.drawable.button_and_long);
                    pos2 = 3;
                    setSecondType(2);
                    andCheck = false;
                } else if (xorCheck) {
                    pos[1].setBackgroundResource(R.drawable.button_xor_long);
                    pos2 = 4;
                    setSecondType(2);
                    xorCheck = false;
                } else {
                    check2 = true;
                    check1 =false;
                    notCheck =false;
                    orCheck=false;
                    andCheck=false;
                    xorCheck=false;
                }
                break;
            case R.id.delete:
                if (check1) {
                    pos[0].setBackgroundResource(R.drawable.button_empty_long);
                    pos1 = 0;
                    setFirstType(1);
                    check1 = false;
                }
                if (check2) {
                    pos[1].setBackgroundResource(R.drawable.button_empty_long);
                    pos2 = 0;
                    setFirstType(2);
                    check2 = false;
                }
                break;
            case R.id.run:
                refresh();
                if (getResValue()) {
                    light.setBackgroundResource(R.drawable.image_light_on);
                } else {
                    light.setBackgroundResource(R.drawable.image_light_off);
                }
                break;
            case R.id.bPort:
                if (portCheck[1]) {
                    portCheck[1] = false;
                    port[1].setBackgroundResource(R.drawable.button_plus);
                    high[1].setEnabled(false);
                    low[1].setEnabled(false);
                } else {
                    portCheck[1] = true;
                    port[1].setBackgroundResource(portDraw[1]);
                    high[1].setEnabled(true);
                    low[1].setEnabled(true);
                }
                break;
            case R.id.cPort:
                if (portCheck[2]) {
                    portCheck[2] = false;
                    port[2].setBackgroundResource(R.drawable.button_plus);
                    high[2].setEnabled(false);
                    low[2].setEnabled(false);
                } else {
                    portCheck[2] = true;
                    port[2].setBackgroundResource(portDraw[2]);
                    high[2].setEnabled(true);
                    low[2].setEnabled(true);
                }
                break;
            case R.id.dPort:
                if (portCheck[3]) {
                    portCheck[3] = false;
                    port[3].setBackgroundResource(R.drawable.button_plus);
                    high[3].setEnabled(false);
                    low[3].setEnabled(false);
                } else {
                    portCheck[3] = true;
                    port[3].setBackgroundResource(portDraw[3]);
                    high[3].setEnabled(true);
                    low[3].setEnabled(true);
                }
                break;
            case R.id.ePort:
                if (portCheck[4]) {
                    portCheck[4] = false;
                    port[4].setBackgroundResource(R.drawable.button_plus);
                    high[4].setEnabled(false);
                    low[4].setEnabled(false);
                } else {
                    portCheck[4] = true;
                    port[4].setBackgroundResource(portDraw[4]);
                    high[4].setEnabled(true);
                    low[4].setEnabled(true);
                }
                break;
            case R.id.fPort:
                if (portCheck[5]) {
                    portCheck[5] = false;
                    port[5].setBackgroundResource(R.drawable.button_plus);
                    high[5].setEnabled(false);
                    low[5].setEnabled(false);
                } else {
                    portCheck[5] = true;
                    port[5].setBackgroundResource(portDraw[5]);
                    high[5].setEnabled(true);
                    low[5].setEnabled(true);
                }
                break;
            case R.id.gPort:
                if (portCheck[6]) {
                    portCheck[6] = false;
                    port[6].setBackgroundResource(R.drawable.button_plus);
                    high[6].setEnabled(false);
                    low[6].setEnabled(false);
                } else {
                    portCheck[6] = true;
                    port[6].setBackgroundResource(portDraw[6]);
                    high[6].setEnabled(true);
                    low[6].setEnabled(true);
                }
                break;

        }

    }

    public void allLow() {
        for (int i = 0; i < low.length; i++) {
            low[i].setChecked(true);
        }
    }

    public void allHigh() {
        for (int i = 0; i < high.length; i++) {
            high[i].setChecked(true);
        }
    }

    public void setFirstType(int pos) {
        if (pos == 1) {
            for (int i = 1; i < 4; i++) {
                port[i].setVisibility(View.INVISIBLE); //B-D invisible
                port[i].setEnabled(false); //Button port B-D don't enabled
                high[i].setEnabled(false); //RadioButton high B-D don't enabled
                low[i].setEnabled(false); //RadioButton low B-D don't enabled
            }
        }
        if (pos == 2) {
            for (int i = 4; i < 7; i++) {
                port[i].setVisibility(View.INVISIBLE); //E-G invisible
                port[i].setEnabled(false); //Button port E-G don't enabled
                high[i].setEnabled(false); //RadioButton high E-G don't enabled
                low[i].setEnabled(false); //RadioButton low E-G don't enabled
            }
        }
    }

    public void setSecondType(int pos) {
        if (pos == 1) {
            port[1].setVisibility(View.VISIBLE);
            port[1].setEnabled(false);
            high[1].setEnabled(true);
            low[1].setEnabled(true);
            for (int i = 2; i < 4; i++) {
                port[i].setVisibility(View.VISIBLE);
                port[i].setBackgroundResource(R.drawable.button_plus);
                port[i].setEnabled(true);
                high[i].setEnabled(false);
                low[i].setEnabled(false);
            }
        }
        if (pos == 2) {
            port[4].setVisibility(View.VISIBLE);
            port[4].setEnabled(false);
            high[4].setEnabled(true);
            low[4].setEnabled(true);
            for (int i = 5; i < 7; i++) {
                port[i].setVisibility(View.VISIBLE);
                port[i].setBackgroundResource(R.drawable.button_plus);
                port[i].setEnabled(true);
                high[i].setEnabled(false);
                low[i].setEnabled(false);
            }
        }
    }

    public boolean not(boolean value) {
        if (value) return false;
        return true;
    }

    public boolean or(boolean[] value) {
        boolean res = value[0];
        for (int i = 1; i < value.length; i++) {
            res = res | value[i];
        }
        if (res) return true;
        return false;
    }

    public boolean and(boolean[] value) {
        boolean res = value[0];
        for (int i = 1; i < value.length; i++) {
            res = res & value[i];
        }
        if (res) return true;
        return false;
    }

    public boolean xor(boolean[] value) {
        boolean res = value[0];
        for (int i = 1; i < value.length; i++) {
            res = res ^ value[i];
        }
        if (res) return true;
        return false;
    }
}
