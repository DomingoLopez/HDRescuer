package com.hdrescuer.hdrescuer.ui.ui.config;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.hdrescuer.hdrescuer.R;
import com.hdrescuer.hdrescuer.common.Constants;

/**
 * Fragmento de configuración. Donde se puede elegir la tasa de muestreo de los datos
 * @author Domingo Lopez
 */
public class ConfigFragment extends Fragment {

    RadioButton sr_150;
    RadioButton sr_200;
    RadioButton sr_300;
    RadioButton sr_500;
    RadioButton sr_1000;
    RadioGroup radioGroupSample;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_config, container, false);

        findViews(root);
        setFormValues();


        return root;
    }


    /**
     * Método que inicia la vista y las tasa de muestreo
     * @author Domingo Lopez
     * @param root
     */
    private void findViews(View root) {


        this.sr_150 = root.findViewById(R.id.sr_150);
        this.sr_200 = root.findViewById(R.id.sr_200);
        this.sr_300 = root.findViewById(R.id.sr_300);
        this.sr_500 = root.findViewById(R.id.sr_500);
        this.sr_1000 = root.findViewById(R.id.sr_1000);

        this.radioGroupSample = root.findViewById(R.id.radioGroupSample);

        this.radioGroupSample.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i){
                    case R.id.sr_150:
                        Constants.SAMPLE_RATE = 150;
                        break;

                    case R.id.sr_200:
                        Constants.SAMPLE_RATE = 200;
                        break;

                    case R.id.sr_300:
                        Constants.SAMPLE_RATE = 300;
                        break;

                    case R.id.sr_500:
                        Constants.SAMPLE_RATE = 500;
                        break;

                    case R.id.sr_1000:
                        Constants.SAMPLE_RATE = 1000;
                        break;
                }
            }
        });

    }

    /**
     * Método que setea la vista en funcion del valor que tengamos para la SAMPLE RATE en Constants
     * @author Domingo Lopez
     */
    private void setFormValues() {

        switch (Constants.SAMPLE_RATE){

            case 150:
                this.radioGroupSample.check(R.id.sr_150);
                break;

            case 200:
                this.radioGroupSample.check(R.id.sr_200);
                break;

            case 300:
                this.radioGroupSample.check(R.id.sr_300);
                break;

            case 500:
                this.radioGroupSample.check(R.id.sr_500);
                break;

            case 1000:
                this.radioGroupSample.check(R.id.sr_1000);
                break;
        }



    }
}