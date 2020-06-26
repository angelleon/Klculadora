package com.pandalab.klculadora;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams;
import androidx.constraintlayout.widget.Guideline;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int PLUS = -1;
    private static final int MINUS = -2;
    private static final int TIMES = -3;
    private static final int OVER = -4;
    private static final int EQUAL = -5;
    private static final int BACK = -6;
    private static final int DEL = -7;
    private static final int POINT = -8;

    private static final int VACIO = 0;
    private static final int OPERANDO_A = 1;
    private static final int OPERADOR = 2;
    private static final int OPERANDO_B = 3;
    private static final int CALCULADO = 4;

    private int state;
    private StringBuilder operandoA;
    private StringBuilder operandoB;
    private double a;
    private double b;
    private Integer operador;
    private int estado;
    private float unitPercentH;
    private float unitPercentV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        operandoA = new StringBuilder("0");
        operandoB = new StringBuilder("0");

        this.state = 0;
        // tamanios fijos en pixeles
        int h = getWindow().getDecorView().getHeight();
        int w = getWindow().getDecorView().getWidth();

        // distancias absolutas horizontales
        int ahPx;
        int bhPx;
        int chPx;

        // distancias relativas horizontales
        float ahPc;
        float bhPc;
        float chPc;

        // distancias absolutas verticales
        int avPx;
        int bvPx;
        int cvPx;

        // distancias relativas verticales
        float avPc;
        float bvPc;
        float cvPc;

        Guideline guide0;
        Guideline guide1;

        List<Guideline> guidesH = new ArrayList<>();
        List<Guideline> guidesV = new ArrayList<>();
        int[] ids = new int[] {R.id.guideH2, R.id.guideH3, R.id.guideH4, R.id.guideH5};
        for (int id: ids) {
            guidesH.add((Guideline) findViewById(id));
        }
        ids = new int[] {R.id.guideV1, R.id.guideV2, R.id.guideV3};
        for (int id: ids) {
            guidesV.add((Guideline) findViewById(id));
        }
        guide0 = guidesH.get(0);
        guide1 = guidesH.get(1);
        LayoutParams params0 = (LayoutParams) guide0.getLayoutParams();
        LayoutParams params1 = (LayoutParams) guide1.getLayoutParams();
        unitPercentH = (params1.guidePercent - params0.guidePercent) * w / 100;
        int distH = (int) (params1.guidePercent - params0.guidePercent) * w;
        guide0 = guidesV.get(0);
        guide1 = guidesV.get(1);
        params0 = (LayoutParams) guide0.getLayoutParams();
        params1 = (LayoutParams) guide1.getLayoutParams();
        unitPercentV = (params1.guidePercent - params0.guidePercent) * h / 100;
        int distV = (int) (params1.guidePercent - params0.guidePercent) * h;
        if (distV > distH) {
            guide0 = findViewById(R.id.guideV10);
            params0 = (LayoutParams) guide0.getLayoutParams();

        } else if (distV < distH) {

        }

        Button btn;
        EditText display = findViewById(R.id.txtDisplay);
        btn = findViewById(R.id.btn0);
        btn.setOnClickListener(new BtnListener(0, display));
        btn = findViewById(R.id.btn1);
        btn.setOnClickListener(new BtnListener(1, display));
        btn = findViewById(R.id.btn2);
        btn.setOnClickListener(new BtnListener(2, display));
        btn = findViewById(R.id.btn3);
        btn.setOnClickListener(new BtnListener(3, display));
        findViewById(R.id.btn4).setOnClickListener(new BtnListener(4, display));
        findViewById(R.id.btn5).setOnClickListener(new BtnListener(5, display));
        findViewById(R.id.btn6).setOnClickListener(new BtnListener(6, display));
        findViewById(R.id.btn7).setOnClickListener(new BtnListener(7, display));
        findViewById(R.id.btn8).setOnClickListener(new BtnListener(8, display));
        findViewById(R.id.btn9).setOnClickListener(new BtnListener(9, display));
        findViewById(R.id.btnPlus).setOnClickListener(new BtnListener(PLUS, display));
        findViewById(R.id.btnMinus).setOnClickListener(new BtnListener(MINUS, display));
        findViewById(R.id.btnTimes).setOnClickListener(new BtnListener(TIMES, display));
        findViewById(R.id.btnOver).setOnClickListener(new BtnListener(OVER, display));
        findViewById(R.id.btnEqual).setOnClickListener(new BtnListener(EQUAL, display));
        findViewById(R.id.btnBack).setOnClickListener(new BtnListener(BACK, display));
        btn = findViewById(R.id.btnDel);
        btn.setOnClickListener(new BtnListener(DEL, display));
        findViewById(R.id.btnPoint).setOnClickListener(new BtnListener(POINT, display));
    }

    private class BtnListener implements View.OnClickListener {
        private int btnValue;
        private EditText display;
        BtnListener(int btnValue, EditText display) {
            this.btnValue = btnValue;
            this.display = display;
        }

        public void calcular(int operador) {
            if (estado == CALCULADO) {
                ScrollView sv = findViewById(R.id.visor);
                sv.fullScroll(View.FOCUS_DOWN);
                return;
            }
            if (operandoA.length() > 0 && operandoB.length() > 0) {
                a = Double.parseDouble(operandoA.toString());
                b = Double.parseDouble(operandoB.toString());
                double resultado;
                if (operador == PLUS) {
                    resultado = a + b;
                } else if (operador == MINUS) {
                    resultado = a - b;
                } else if (operador == TIMES) {
                    resultado = a * b;
                } else if (operador == OVER) {
                    resultado = a / b;
                } else
                    resultado = 0.0;
                display.setText(display.getText().append("\n").append(String.valueOf(resultado)));
                estado = CALCULADO;
                clearOperando(operandoA);
                clearOperando(operandoB);
                operandoA.append(resultado);
            }
            display.computeScroll();
            ((LinearLayout) findViewById(R.id.lytDisplayContainer)).computeScroll();
            ScrollView sv = findViewById(R.id.visor);
            sv.computeScroll();
            sv.fullScroll(View.FOCUS_DOWN);
            display.computeScroll();
            ((LinearLayout) findViewById(R.id.lytDisplayContainer)).computeScroll();
            sv.computeScroll();
            sv.startLayoutAnimation();
        }

        private void backspace() {
            Editable text = display.getText();
            if (text.length() >= 1)
                text.delete(text.length()-1, text.length());
            display.setText(text);
        }

        private void clearScreen() {
            Editable text = display.getText();
            text.clear();
            clearOperando(operandoA);
            clearOperando(operandoB);
            operador = 0;
            estado = 0;
            display.setText(text);
        }

        private void clearOperando(StringBuilder operando) {
            operando.delete(0, operando.length() + 1);
            operando.append(0);
        }

        private void procesarOperador(int operador) {
            Editable text = display.getText();
            if (estado == OPERANDO_A) {
                estado = OPERADOR;
            } else if (estado == OPERADOR) {
                backspace();
            } else if (estado == OPERANDO_B) {
                calcular(operador);
            } else if (estado == CALCULADO) {
                estado = OPERANDO_B;
            }
            MainActivity.this.operador = operador;
        }

        public void onClick(View v) {
            Log.d("dbg", operandoA.toString() + " " + operandoB.toString());

            if (btnValue >= 0) {
                if (estado == VACIO) {
                    operandoA.append(btnValue);
                    estado = OPERANDO_A;
                } else if (estado == OPERANDO_A) {
                    operandoA.append(btnValue);
                }
                else if (estado == OPERADOR) {
                    operandoB.append(btnValue);
                    estado = OPERANDO_B;
                } else if (estado == OPERANDO_B) {
                    operandoB.append(btnValue);
                } else if (estado == CALCULADO) {
                    clearScreen();
                    clearOperando(operandoA);
                    clearOperando(operandoB);
                    estado = OPERANDO_A;
                    operandoA.append(btnValue);
                }
                display.setText(display.getText().append(Character.forDigit(btnValue, 10)));
            } else if (btnValue == PLUS) {
                Editable text = display.getText();
                procesarOperador(PLUS);
                display.setText(text.append('+'));
            } else if (btnValue == MINUS) {
                Editable text = display.getText();
                display.setText(text.append('-'));
                procesarOperador(MINUS);
            } else if (btnValue == TIMES) {
                Editable text = display.getText();
                display.setText(text.append('\u00d7'));
                procesarOperador(TIMES);
            } else if (btnValue == OVER) {
                Editable text = display.getText();
                display.setText(text.append('\u00f7'));
                procesarOperador(OVER);
            } else if (btnValue == EQUAL) {
                calcular(operador);
                ScrollView sv = findViewById(R.id.visor);
                sv.fullScroll(View.FOCUS_DOWN);
            } else if (btnValue == BACK) {
                backspace();
            } else if (btnValue == DEL) {
                clearScreen();
            } else if (btnValue == POINT) {
                if (estado == VACIO || estado == OPERANDO_A) {
                    operandoA.append(".");
                } else if (estado == OPERANDO_B || estado == OPERADOR) {
                    operandoB.append(".");
                }
                display.setText(display.getText().append('.'));
            }
            display.setText(display.getText());
            ScrollView sv = findViewById(R.id.visor);
            sv.fullScroll(View.FOCUS_DOWN);
        }
    }
}
